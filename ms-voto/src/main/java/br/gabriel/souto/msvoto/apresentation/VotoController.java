package br.gabriel.souto.msvoto.apresentation;

import br.gabriel.souto.msvoto.application.interfaces.IVotoService;
import br.gabriel.souto.msvoto.domain.model.voto.request.VotoRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("votos")
public class VotoController {
    private final IVotoService _votoService;

    @Autowired
    public VotoController(IVotoService votoService) {
        _votoService = votoService;
    }


    @PostMapping("/funcionario/{funcionarioCpf}/proposta/{propostaId}")
    public ResponseEntity<String> votar(@PathVariable String funcionarioCpf,
                                   @PathVariable Long propostaId,
                                   @RequestBody VotoRequest status) throws JsonProcessingException {
        _votoService.votar(funcionarioCpf, propostaId, status.getStatus());

        return ResponseEntity.ok("Voto feito com sucesso");
    }
}
