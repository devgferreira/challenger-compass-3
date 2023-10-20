package br.gabriel.souto.msvalidacaovoto.application.service;

import br.gabriel.souto.msvalidacaovoto.application.interfaces.IValidacaoVotoService;
import br.gabriel.souto.msvalidacaovoto.domain.enums.ErrorCodes;
import br.gabriel.souto.msvalidacaovoto.domain.enums.ValidarVoto;
import br.gabriel.souto.msvalidacaovoto.domain.model.funcionario.FuncionarioResponse;
import br.gabriel.souto.msvalidacaovoto.domain.model.proposta.PropostaResponse;
import br.gabriel.souto.msvalidacaovoto.infra.client.FuncionarioControllerClient;
import br.gabriel.souto.msvalidacaovoto.infra.client.PropostaControllerClient;
import br.gabriel.souto.msvalidacaovoto.infra.constants.ErrorConstants;
import br.gabriel.souto.msvalidacaovoto.infra.exceptions.CpfInvalidoExeception;
import br.gabriel.souto.msvalidacaovoto.infra.exceptions.ExceptionResponse;
import br.gabriel.souto.msvalidacaovoto.infra.exceptions.PropostaNaoEncontradoExeception;
import feign.FeignException;
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
        PropostaResponse propostaResponse;
        FuncionarioResponse funcionarioResponse;
        try {
            funcionarioResponse = _funcionarioControllerClient.buscarFuncionarioPorCpf(funcionarioCpf);
        } catch (FeignException.NotFound ex) {
            throw new CpfInvalidoExeception(new ExceptionResponse(ErrorCodes.CPF_INVALIDO, ErrorConstants.CPF_INVALIDO));
        }
        try {
            propostaResponse = _propostaControllerClient.buscarPropostaPorId(propostaId);
        } catch (FeignException.NotFound ex) {
            throw new PropostaNaoEncontradoExeception(new ExceptionResponse(ErrorCodes.PROPOSTA_NAO_ENCONTRADA, ErrorConstants.PROPOSTA_NAO_ENCONTRADA));
        }
        if(propostaResponse.getSetor() == null){
            return ValidarVoto.pode_votar;
        }

        if (!funcionarioResponse.getSetor().equals(propostaResponse.getSetor())) {
            return ValidarVoto.nao_pode_votar;
        }
        return ValidarVoto.pode_votar;
    }
}
