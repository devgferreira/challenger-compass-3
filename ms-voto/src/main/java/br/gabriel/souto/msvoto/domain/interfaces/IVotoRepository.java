package br.gabriel.souto.msvoto.domain.interfaces;

import br.gabriel.souto.msvoto.domain.enums.VotoStatus;
import br.gabriel.souto.msvoto.domain.model.funcionario.Funcionario;
import br.gabriel.souto.msvoto.domain.model.proposta.Proposta;
import br.gabriel.souto.msvoto.domain.model.voto.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IVotoRepository extends JpaRepository<Voto, Long> {

    Optional<Voto> findByFuncionarioCpfAndPropostaId(String funcionarioCpf, Long propostaId);
    Long countByPropostaIdAndStatus(Long propostaId, VotoStatus votoStatus);
}
