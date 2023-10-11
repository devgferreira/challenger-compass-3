package br.gabriel.souto.msproposta.domain.interfaces;

import br.gabriel.souto.msproposta.domain.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPropostaRepository extends JpaRepository<Proposta, Long> {
}
