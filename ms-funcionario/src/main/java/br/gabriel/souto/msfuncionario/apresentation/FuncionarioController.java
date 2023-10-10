package br.gabriel.souto.msfuncionario.apresentation;

import br.gabriel.souto.msfuncionario.application.dtos.FuncionarioDTO;
import br.gabriel.souto.msfuncionario.application.interfaces.IFuncionarioService;
import br.gabriel.souto.msfuncionario.domain.model.funcionario.response.FuncionarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final IFuncionarioService _funcionarioService;

    @Autowired
    public FuncionarioController(IFuncionarioService funcionarioService) {
        _funcionarioService = funcionarioService;
    }

    @PostMapping
    public ResponseEntity<FuncionarioResponse> criarFuncionario(@RequestBody FuncionarioDTO funcionarioDTO){
        FuncionarioDTO funcionario =  _funcionarioService.criarFuncionario(funcionarioDTO);
        FuncionarioResponse funcionarioResponse = new FuncionarioResponse(funcionario);
        return new ResponseEntity<>(funcionarioResponse, HttpStatus.CREATED);
    }
}
