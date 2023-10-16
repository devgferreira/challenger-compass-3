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
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VotoService implements IVotoService {

    private final FuncionarioControllerClient _funcionarioControllerClient;
    private final PropostaControllerClient _propostaControllerClient;
    private final ValidacaoVotoControllerClient _validacaoVotoControllerClient;
    private final EmitirPropostaResultadoPublisher _emiterPropostaResultado;

    private final IVotoRepository _votoRepository;

    @Autowired
    public VotoService(FuncionarioControllerClient funcionarioControllerClient, PropostaControllerClient propostaControllerClient, ValidacaoVotoControllerClient validacaoVotoControllerClient, EmitirPropostaResultadoPublisher emiterPropostaResultado, IVotoRepository votoRepository) {
        _funcionarioControllerClient = funcionarioControllerClient;
        _propostaControllerClient = propostaControllerClient;
        _validacaoVotoControllerClient = validacaoVotoControllerClient;
        _emiterPropostaResultado = emiterPropostaResultado;
        _votoRepository = votoRepository;
    }

    @Override
    public void votar(String funcionarioCpf, Long propostaId, VotoStatus status) throws JsonProcessingException {
        Funcionario funcionario = _funcionarioControllerClient.buscarFuncionarioPorCpf(funcionarioCpf);
        Proposta proposta = _propostaControllerClient.buscarPropostaPorId(propostaId);
        String voto_valido = _validacaoVotoControllerClient.validarVoto(propostaId, funcionarioCpf);


        Optional<Voto> votoExistente = _votoRepository.findByFuncionarioCpfAndPropostaId(funcionario.getCpf(), proposta.getId());
        if(votoExistente.isPresent()){
            throw new RuntimeException("Funcionario já votou nessa proposta");
        }

        Voto novoVoto = new Voto();
        novoVoto.setFuncionarioCpf(funcionario.getCpf());
        novoVoto.setPropostaId(proposta.getId());
        novoVoto.setStatus(status);

        _votoRepository.save(novoVoto);

        if(!proposta.isAberta()){
            Long votosAprovados = _votoRepository.countByPropostaIdAndStatus(proposta.getId(), VotoStatus.APROVADO);
            Long votosReprovados = _votoRepository.countByPropostaIdAndStatus(proposta.getId(), VotoStatus.REPROVADO);

            if (votosAprovados > votosReprovados) {
                proposta.setResultado("Aprovado");
            } else {
                proposta.setResultado("Reprovado");
            }
            _emiterPropostaResultado.emitirPropostaResultado(proposta);
        }

    }
}
