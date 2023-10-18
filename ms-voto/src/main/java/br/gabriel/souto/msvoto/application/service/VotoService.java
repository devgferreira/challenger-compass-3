package br.gabriel.souto.msvoto.application.service;

import br.gabriel.souto.msvoto.application.interfaces.IVotoService;
import br.gabriel.souto.msvoto.domain.enums.VotoStatus;
import br.gabriel.souto.msvoto.domain.interfaces.IVotoRepository;
import br.gabriel.souto.msvoto.domain.model.funcionario.Funcionario;
import br.gabriel.souto.msvoto.domain.model.proposta.Proposta;
import br.gabriel.souto.msvoto.domain.model.voto.Voto;
import br.gabriel.souto.msvoto.infra.client.FuncionarioControllerClient;
import br.gabriel.souto.msvoto.infra.client.PropostaControllerClient;
import br.gabriel.souto.msvoto.infra.client.ValidacaoVotoControllerClient;
import br.gabriel.souto.msvoto.infra.mqueue.EmitirPropostaResultadoPublisher;
import br.gabriel.souto.msvoto.infra.mqueue.EmitirResultadoVotacaoPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class VotoService implements IVotoService {

    private final FuncionarioControllerClient _funcionarioControllerClient;
    private final PropostaControllerClient _propostaControllerClient;
    private final ValidacaoVotoControllerClient _validacaoVotoControllerClient;
    private final EmitirPropostaResultadoPublisher _emiterPropostaResultado;
    private final EmitirResultadoVotacaoPublisher _emitirResultadoVotacao;

    private final IVotoRepository _votoRepository;

    @Autowired
    public VotoService(FuncionarioControllerClient funcionarioControllerClient, PropostaControllerClient propostaControllerClient, ValidacaoVotoControllerClient validacaoVotoControllerClient, EmitirPropostaResultadoPublisher emiterPropostaResultado, EmitirResultadoVotacaoPublisher emitirResultadoVotacao, IVotoRepository votoRepository) {
        _funcionarioControllerClient = funcionarioControllerClient;
        _propostaControllerClient = propostaControllerClient;
        _validacaoVotoControllerClient = validacaoVotoControllerClient;
        _emiterPropostaResultado = emiterPropostaResultado;
        _emitirResultadoVotacao = emitirResultadoVotacao;
        _votoRepository = votoRepository;
    }

    @Override
    public void votar(String funcionarioCpf, Long propostaId, VotoStatus status) throws JsonProcessingException {
        Funcionario funcionario = _funcionarioControllerClient.buscarFuncionarioPorCpf(funcionarioCpf);
        Proposta proposta = _propostaControllerClient.buscarPropostaPorId(propostaId);
        String voto_valido = _validacaoVotoControllerClient.validarVoto(propostaId, funcionarioCpf);

        if(voto_valido.equals("nao_pode_votar")){
            throw new RuntimeException("O Funcionario não pode votar nessa proposta");
        }

        if(proposta.isAberta()){
            Optional<Voto> votoExistente = _votoRepository.findByFuncionarioCpfAndPropostaId(
                    funcionario.getCpf(), proposta.getId());
            if(votoExistente.isPresent()){
                throw new RuntimeException("Funcionario já votou nessa proposta");
            }

            Voto novoVoto = new Voto();
            novoVoto.setFuncionarioCpf(funcionario.getCpf());
            novoVoto.setPropostaId(proposta.getId());
            novoVoto.setStatus(status);

            _votoRepository.save(novoVoto);
        }

        if(!proposta.isAberta()){
            throw new RuntimeException("A votação foi encerrada!");
        }

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            List<Proposta> propostas = _propostaControllerClient.buscarTodasAsPropostas();
            for (Proposta item : propostas) {
                Proposta resultado = _propostaControllerClient.buscarPropostaPorId(item.getId());
                if(!resultado.isAberta()){
                    Long votosAprovados = _votoRepository.countByPropostaIdAndStatus(resultado.getId(), VotoStatus.APROVADO);
                    Long votosReprovados = _votoRepository.countByPropostaIdAndStatus(resultado.getId(), VotoStatus.REPROVADO);

                    if (votosAprovados > votosReprovados) {
                        resultado.setResultado("Aprovado");
                    } else {
                        resultado.setResultado("Reprovado");
                    }
                    try {
                        _emiterPropostaResultado.emitirPropostaResultado(resultado);

                        if(!(item.getResultado().equals("Aprovado") || item.getResultado().equals("Reprovado"))){
                            String mensagem = "O resultado da votação foi: " + resultado.getResultado() +
                                    " para a proposta: " + resultado.getTitulo() + " do id: " + resultado.getId();
                            _emitirResultadoVotacao.emitirVotacaoResultado(mensagem);
                        }
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, 0, 20, TimeUnit.SECONDS);
    }
}
