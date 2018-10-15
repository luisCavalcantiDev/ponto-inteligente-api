package br.com.dev.pontointeligente.api.services;

import br.com.dev.pontointeligente.api.entities.Empresa;
import java.util.Optional;

/**
 *
 * @author LuisGustavo
 */
public interface EmpresaService {
    
    /**
     * Retorna uma empresa dado um cnpj
     * 
     * @param cnpj
     * @return Optional<Empresa>
     */
    Optional<Empresa>buscarPorCnpj(String cnpj);
    
    /**
     * Cadastra uma nova empresa na base de dados
     * 
     * @param empresa
     * @return Empresa
     */
    Empresa persistir(Empresa empresa);
    
}
