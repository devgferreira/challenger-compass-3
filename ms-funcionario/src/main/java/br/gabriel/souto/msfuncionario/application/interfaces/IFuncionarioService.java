package br.gabriel.souto.msfuncionario.application.interfaces;

import br.gabriel.souto.msfuncionario.application.dtos.FuncionarioDTO;

public interface IFuncionarioService {
    FuncionarioDTO criarFuncionario(FuncionarioDTO funcionarioDTO);

    FuncionarioDTO buscarFuncionarioPorCpf(String cpf);

    boolean validator(String cpf);
}
