package br.gabriel.souto.msvalidacaovoto.apresentation;

import br.gabriel.souto.msvalidacaovoto.application.interfaces.IValidacaoVotoService;
import br.gabriel.souto.msvalidacaovoto.domain.enums.ValidarVoto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("validacao-votos")
public class ValidacaoVotoController {

    private final IValidacaoVotoService _validacaoVotoService;

    public ValidacaoVotoController(IValidacaoVotoService validacaoVotoService) {
        _validacaoVotoService = validacaoVotoService;
    }

    @GetMapping("propostas/{propostaId}/funcionarios/{funcionarioCpf}")
    public String validarVoto(@PathVariable Long propostaId, @PathVariable String funcionarioCpf){
        ValidarVoto validarVoto = _validacaoVotoService.validarVoto(propostaId, funcionarioCpf);
        return String.valueOf(validarVoto);
    }
}
