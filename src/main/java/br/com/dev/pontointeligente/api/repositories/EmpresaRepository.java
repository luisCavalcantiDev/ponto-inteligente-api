package br.com.dev.pontointeligente.api.repositories;

import br.com.dev.pontointeligente.api.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author LuisGustavo
 */
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    @Transactional(readOnly = true)
    Empresa findByCnpj(String cnpj);
}
