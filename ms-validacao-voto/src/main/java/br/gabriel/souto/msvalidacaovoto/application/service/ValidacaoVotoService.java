package br.gabriel.souto.msvalidacaovoto.application.service;

import br.gabriel.souto.msvalidacaovoto.application.interfaces.IValidacaoVotoService;
import br.gabriel.souto.msvalidacaovoto.domain.enums.ValidarVoto;
import br.gabriel.souto.msvalidacaovoto.domain.model.funcionario.FuncionarioResponse;
import br.gabriel.souto.msvalidacaovoto.domain.model.proposta.PropostaResponse;
import br.gabriel.souto.msvalidacaovoto.infra.client.FuncionarioControllerClient;
import br.gabriel.souto.msvalidacaovoto.infra.client.PropostaControllerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidacaoVotoService implements IValidacaoVotoService {

    private final FuncionarioControllerClient _funcionarioControllerClient;
    private final PropostaControllerClient _propostaControllerClient;

    @Autowired
    public ValidacaoVotoService(FuncionarioControllerClient funcionarioControllerClient, PropostaControllerClient propostaControllerClient) {
        _funcionarioControllerClient = funcionarioControllerClient;
        _propostaControllerClient = propostaControllerClient;
    }

    @Override
    public ValidarVoto validarVoto(Long propostaId, String funcionarioCpf) {
        PropostaResponse propostaResponse = _propostaControllerClient.buscarPropostaPorId(propostaId);
        FuncionarioResponse funcionarioResponse = _funcionarioControllerClient.buscarFuncionarioPorCpf(funcionarioCpf);

        if(!funcionarioResponse.getSetor().equals(propostaResponse.getSetor())){
            return ValidarVoto.nao_pode_votar;
        }
        return ValidarVoto.pode_votar;
    }
}
