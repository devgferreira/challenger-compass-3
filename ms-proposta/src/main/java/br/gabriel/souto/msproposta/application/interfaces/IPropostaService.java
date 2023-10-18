package br.gabriel.souto.msproposta.application.interfaces;

import br.gabriel.souto.msproposta.application.dtos.PropostaDTO;
import java.util.List;

public interface IPropostaService {
    PropostaDTO criarProposta(PropostaDTO propostaDTO);
    PropostaDTO buscarPropostaPorId(Long id);

    List<PropostaDTO> buscarTodasAsPropostas();
    void verificarTempo();
}
