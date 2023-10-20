package br.gabriel.souto.msvoto.application.service;

import br.gabriel.souto.msvoto.application.interfaces.IVotoService;
import br.gabriel.souto.msvoto.domain.enums.ErrorCodes;
import br.gabriel.souto.msvoto.domain.enums.VotoStatus;
import br.gabriel.souto.msvoto.domain.interfaces.IVotoRepository;
import br.gabriel.souto.msvoto.domain.model.funcionario.Funcionario;
import br.gabriel.souto.msvoto.domain.model.proposta.Proposta;
import br.gabriel.souto.msvoto.domain.model.voto.Voto;
import br.gabriel.souto.msvoto.infra.client.FuncionarioControllerClient;
import br.gabriel.souto.msvoto.infra.client.PropostaControllerClient;
import br.gabriel.souto.msvoto.infra.client.ValidacaoVotoControllerClient;
import br.gabriel.souto.msvoto.infra.constants.ErrorConstants;
import br.gabriel.souto.msvoto.infra.exceptions.*;
import br.gabriel.souto.msvoto.infra.mqueue.EmitirPropostaResultadoPublisher;
import br.gabriel.souto.msvoto.infra.mqueue.EmitirResultadoVotacaoPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
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
        Funcionario funcionario;
        Proposta proposta;
        try {
             funcionario = _funcionarioControllerClient.buscarFuncionarioPorCpf(funcionarioCpf);
        }
        catch (FeignException.NotFound ex){
            throw new FuncionarioNaoEncontradoExeception(new ExceptionResponse(
                    ErrorCodes.FUNCIONARIO_NAO_ENCONTRADO, ErrorConstants.FUNCIONARIO_NAO_ENCONTRADO));
        }
        try{
            proposta = _propostaControllerClient.buscarPropostaPorId(propostaId);

        }catch (FeignException.NotFound ex){
            throw new PropostaNaoEncontradoExeception(new ExceptionResponse(
                    ErrorCodes.PROPOSTA_NAO_ENCONTRADA, ErrorConstants.PROPOSTA_NAO_ENCONTRADA
            ));
        }
        String voto_valido = _validacaoVotoControllerClient.validarVoto(propostaId, funcionarioCpf);

        if(voto_valido.equals("nao_pode_votar")){
            throw new NaoPodeVotarExeception(new ExceptionResponse(ErrorCodes.NAO_PODE_VOTAR, ErrorConstants.NAO_PODE_VOTAR));
        }

        if(proposta.isAberta()){
            Optional<Voto> votoExistente = _votoRepository.findByFuncionarioCpfAndPropostaId(
                    funcionario.getCpf(), proposta.getId());
            if(votoExistente.isPresent()){
                throw new FuncionarioJaVotoExeception(new ExceptionResponse(
                        ErrorCodes.FUNCIONARIO_JA_VOTO, ErrorConstants.FUNCIONARIO_JA_VOTO
                ));
            }
            if(status == null){
                throw new VotoInvalidoExeception(new ExceptionResponse(ErrorCodes.VOTO_INVALIDO, ErrorConstants.VOTO_INVALIDO));
            }
            Voto novoVoto = new Voto();
            novoVoto.setFuncionarioCpf(funcionario.getCpf());
            novoVoto.setPropostaId(proposta.getId());
            novoVoto.setStatus(status);

            _votoRepository.save(novoVoto);
        }

        if(!proposta.isAberta()){
            throw new VotacaoEncerradaExeception(new ExceptionResponse(
                    ErrorCodes.VOTACAO_ENCERRADA, ErrorConstants.VOTACAO_ENCERRADA
            ));
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
                                    " para a proposta: " + resultado.getTitulo() + " do ID: " + resultado.getId();
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
