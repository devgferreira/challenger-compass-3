package br.gabriel.souto.msproposta.apresentation;


import br.gabriel.souto.msproposta.application.dtos.PropostaDTO;
import br.gabriel.souto.msproposta.application.interfaces.IPropostaService;
import br.gabriel.souto.msproposta.domain.model.Response.PropostaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("propostas")
public class PropostaController {

    private final IPropostaService _propostaService;
    @Autowired
    public PropostaController(IPropostaService propostaService) {
        _propostaService = propostaService;
    }

    @PostMapping
    public ResponseEntity<PropostaResponse> criarProposta(@RequestBody PropostaDTO propostaDTO){

        PropostaDTO proposta = _propostaService.criarProposta(propostaDTO);
        PropostaResponse propostaResponse = new PropostaResponse(proposta);
        return new ResponseEntity<>(propostaResponse, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PropostaResponse> buscarPropostaPorId(@PathVariable Long id){
        PropostaDTO proposta = _propostaService.buscarPropostaPorId(id);
        _propostaService.verificarPropostas();
        PropostaResponse propostaResponse = new PropostaResponse(proposta);
        return ResponseEntity.ok(propostaResponse);
    }
}
