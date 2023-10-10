package br.gabriel.souto.msfuncionario.domain.interfaces;

import br.gabriel.souto.msfuncionario.domain.model.funcionario.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Funcionario findFuncionarioByCpf(String cpf);
}
