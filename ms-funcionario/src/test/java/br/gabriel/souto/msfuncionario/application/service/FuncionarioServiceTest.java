package br.gabriel.souto.msfuncionario.application.service;

import br.gabriel.souto.msfuncionario.application.dtos.FuncionarioDTO;
import br.gabriel.souto.msfuncionario.domain.enums.Setor;
import br.gabriel.souto.msfuncionario.domain.interfaces.IFuncionarioRepository;
import br.gabriel.souto.msfuncionario.domain.model.funcionario.Funcionario;
import br.gabriel.souto.msfuncionario.infra.exceptions.CpfInvalidoExeception;
import br.gabriel.souto.msfuncionario.infra.exceptions.FuncionarioJaExisteExeception;
import br.gabriel.souto.msfuncionario.infra.exceptions.FuncionarioNaoEncontradoExeception;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

    @InjectMocks
    private FuncionarioService _funcionarioService;
    @Mock
    private IFuncionarioRepository _funcionarioRepository;
    @Mock
    private ModelMapper _modelMapper;

    @Test
    void criarFuncionarioTest() {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO(1L, "32904234098", "Gabriel", Setor.RH);

        Funcionario funcionario = new Funcionario(1L, "32904234098", "Gabriel", Setor.RH);

        when(_modelMapper.map(funcionarioDTO, Funcionario.class)).thenReturn(funcionario);
        when(_funcionarioRepository.findFuncionarioByCpf(funcionario.getCpf())).thenReturn(Optional.empty());
        when(_funcionarioRepository.save(funcionario)).thenReturn(funcionario);
        when(_modelMapper.map(funcionario, FuncionarioDTO.class)).thenReturn(funcionarioDTO);

        FuncionarioDTO result = _funcionarioService.criarFuncionario(funcionarioDTO);

        assertEquals(result, funcionarioDTO);
    }

    @Test
    void criarFuncionario_ComCpf_ExistenteTest() {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setCpf("32904234098");

        Funcionario funcionario = new Funcionario();
        funcionario.setCpf("32904234098");

        when(_modelMapper.map(funcionarioDTO, Funcionario.class)).thenReturn(funcionario);
        when(_funcionarioRepository.findFuncionarioByCpf(anyString())).thenReturn(Optional.of(funcionario));

        assertThrows(FuncionarioJaExisteExeception.class, () -> {
            _funcionarioService.criarFuncionario(funcionarioDTO);
        });
    }

    @Test
    void criarFuncionario_ComCpf_InvalidoTest() {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setCpf("1234567890");

        Funcionario funcionario = new Funcionario();
        funcionario.setCpf("1234567890");

        when(_modelMapper.map(funcionarioDTO, Funcionario.class)).thenReturn(funcionario);
        assertThrows(CpfInvalidoExeception.class, () -> {
            _funcionarioService.criarFuncionario(funcionarioDTO);
        });
    }

    @Test
    public void buscarFuncionarioPorCpfTest() {
        String cpf = "32904234098";
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(cpf);
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setCpf(cpf);

        when(_funcionarioRepository.findFuncionarioByCpf(anyString())).thenReturn(Optional.of(funcionario));
        when(_modelMapper.map(funcionario, FuncionarioDTO.class)).thenReturn(funcionarioDTO);

        FuncionarioDTO result = _funcionarioService.buscarFuncionarioPorCpf(cpf);

        assertEquals(funcionarioDTO, result);
    }

    @Test
    public void buscarFuncionarioPorCpf_Nao_Econtrado_Test() {
        String cpf = "32904234098";

        when(_funcionarioRepository.findFuncionarioByCpf(cpf)).thenReturn(Optional.empty());

        assertThrows(FuncionarioNaoEncontradoExeception.class, () -> {
            _funcionarioService.buscarFuncionarioPorCpf(cpf);
        });
    }

}