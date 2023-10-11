package br.gabriel.souto.msfuncionario.application.service;
import br.gabriel.souto.msfuncionario.domain.enums.ErrorCodes;
import br.gabriel.souto.msfuncionario.application.dtos.FuncionarioDTO;
import br.gabriel.souto.msfuncionario.application.interfaces.IFuncionarioService;
import br.gabriel.souto.msfuncionario.domain.interfaces.IFuncionarioRepository;
import br.gabriel.souto.msfuncionario.infra.constants.ErrorConstants;
import br.gabriel.souto.msfuncionario.domain.model.funcionario.Funcionario;
import br.gabriel.souto.msfuncionario.infra.exceptions.CpfInvalidoExeception;
import br.gabriel.souto.msfuncionario.infra.exceptions.ExceptionResponse;
import br.gabriel.souto.msfuncionario.infra.exceptions.FuncionarioJaExisteExeception;
import br.gabriel.souto.msfuncionario.infra.exceptions.FuncionarioNaoEncontradoExeception;
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
        if(_funcionarioRepository.findFuncionarioByCpf(funcionario.getCpf()).isPresent()){
            throw new FuncionarioJaExisteExeception(
                    new ExceptionResponse(ErrorCodes.FUNCIONARIO_JA_EXISTE, ErrorConstants.FUNCIONARIO_JA_EXISTE)
            );
        }
        boolean cpfValido = funcionarioDTO.getCpf().length() == 11;
        if(!cpfValido){
            throw new CpfInvalidoExeception(
                    new ExceptionResponse(ErrorCodes.CPF_INVALIDO, ErrorConstants.CPF_INVALIDO)
            );
        }
        return _modelMapper.map(_funcionarioRepository.save(funcionario), FuncionarioDTO.class);
    }

    @Override
    public FuncionarioDTO buscarFuncionarioPorCpf(String cpf) {
       Funcionario funcionario = _funcionarioRepository.findFuncionarioByCpf(cpf).orElseThrow(
               () -> new FuncionarioNaoEncontradoExeception(
               new ExceptionResponse(ErrorCodes.FUNCIONARIO_NAO_ENCONTRADO,
                       ErrorConstants.FUNCIONARIO_NAO_ENCONTRADO)));

       return _modelMapper.map(funcionario, FuncionarioDTO.class);
    }

}
