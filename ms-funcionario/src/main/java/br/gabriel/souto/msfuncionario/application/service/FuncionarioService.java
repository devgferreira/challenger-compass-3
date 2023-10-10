package br.gabriel.souto.msfuncionario.application.service;

import br.gabriel.souto.msfuncionario.application.dtos.FuncionarioDTO;
import br.gabriel.souto.msfuncionario.application.interfaces.IFuncionarioService;
import br.gabriel.souto.msfuncionario.domain.interfaces.IFuncionarioRepository;
import br.gabriel.souto.msfuncionario.domain.model.funcionario.Funcionario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService implements IFuncionarioService {

    private final IFuncionarioRepository _funcionarioRepository;
    private final ModelMapper _modelMapper;

    @Autowired
    public FuncionarioService(IFuncionarioRepository funcionarioRepository, ModelMapper modelMapper) {
        _funcionarioRepository = funcionarioRepository;
        _modelMapper = modelMapper;
    }

    @Override
    public FuncionarioDTO criarFuncionario(FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = _modelMapper.map(funcionarioDTO, Funcionario.class);
        return _modelMapper.map(_funcionarioRepository.save(funcionario), FuncionarioDTO.class);
    }
}
