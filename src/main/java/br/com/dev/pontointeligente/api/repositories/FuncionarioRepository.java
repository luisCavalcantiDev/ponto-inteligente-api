package br.com.dev.pontointeligente.api.repositories;

import br.com.dev.pontointeligente.api.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author LuisGustavo
 */
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Funcionario findByCpf(String cpf);

    Funcionario findByEmail(String email);

    Funcionario findByCpfOrEmail(String cpd, String email);
}
