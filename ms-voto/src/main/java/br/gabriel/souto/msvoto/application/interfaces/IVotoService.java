package br.gabriel.souto.msvoto.application.interfaces;


import br.gabriel.souto.msvoto.domain.enums.VotoStatus;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IVotoService {
    void votar(String funcionarioCpf, Long propostaId, VotoStatus status) throws JsonProcessingException;
}
