package br.gabriel.souto.msproposta.application.interfaces;

import br.gabriel.souto.msproposta.application.dtos.PropostaDTO;

public interface IPropostaService {
    PropostaDTO criarProposta(PropostaDTO propostaDTO);
    PropostaDTO buscarPropostaPorId(Long id);
    void verificarPropostas();
}
