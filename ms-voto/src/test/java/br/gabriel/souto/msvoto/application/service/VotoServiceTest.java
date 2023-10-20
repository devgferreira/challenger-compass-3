package br.gabriel.souto.msvoto.application.service;

import br.gabriel.souto.msvoto.domain.enums.VotoStatus;
import br.gabriel.souto.msvoto.domain.interfaces.IVotoRepository;
import br.gabriel.souto.msvoto.domain.model.funcionario.Funcionario;
import br.gabriel.souto.msvoto.domain.model.proposta.Proposta;
import br.gabriel.souto.msvoto.domain.model.voto.Voto;
import br.gabriel.souto.msvoto.infra.client.FuncionarioControllerClient;
import br.gabriel.souto.msvoto.infra.client.PropostaControllerClient;
import br.gabriel.souto.msvoto.infra.client.ValidacaoVotoControllerClient;
import br.gabriel.souto.msvoto.infra.exceptions.FuncionarioNaoEncontradoExeception;
import br.gabriel.souto.msvoto.infra.exceptions.NaoPodeVotarExeception;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class VotoServiceTest {

    @InjectMocks
    private VotoService votacaoService;

    @Mock
    private FuncionarioControllerClient _funcionarioControllerClient;

    @Mock
    private PropostaControllerClient _propostaControllerClient;

    @Mock
    private ValidacaoVotoControllerClient _validacaoVotoControllerClient;

    @Mock
    private IVotoRepository _votoRepository;

    @Test
    public void testVotar_Sucesso() throws JsonProcessingException {
        String funcionarioCpf = "12345678901";
        Long propostaId = 1L;
        VotoStatus status = VotoStatus.APROVADO;

        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(funcionarioCpf);

        Proposta proposta = new Proposta();
        proposta.setId(propostaId);
        proposta.setAberta(true);

        when(_funcionarioControllerClient.buscarFuncionarioPorCpf(funcionarioCpf)).thenReturn(funcionario);
        when(_propostaControllerClient.buscarPropostaPorId(propostaId)).thenReturn(proposta);
        when(_validacaoVotoControllerClient.validarVoto(propostaId, funcionarioCpf)).thenReturn("pode_votar");
        when(_votoRepository.findByFuncionarioCpfAndPropostaId(funcionarioCpf, propostaId)).thenReturn(Optional.empty());

        votacaoService.votar(funcionarioCpf, propostaId, status);
        verify(_votoRepository, times(1)).save(any(Voto.class));
    }

    @Test()
    public void testVotar_NaoPodeVotar(){
        String funcionarioCpf = "12345678901";
        Long propostaId = 1L;
        VotoStatus status = VotoStatus.APROVADO;

        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(funcionarioCpf);

        Proposta proposta = new Proposta();
        proposta.setId(propostaId);
        proposta.setAberta(true);

        when(_funcionarioControllerClient.buscarFuncionarioPorCpf(funcionarioCpf)).thenReturn(funcionario);
        when(_propostaControllerClient.buscarPropostaPorId(propostaId)).thenReturn(proposta);
        when(_validacaoVotoControllerClient.validarVoto(propostaId, funcionarioCpf)).thenReturn("nao_pode_votar");
        assertThrows(NaoPodeVotarExeception.class, () -> {
            votacaoService.votar(funcionarioCpf, propostaId, status);;
        });
    }
}