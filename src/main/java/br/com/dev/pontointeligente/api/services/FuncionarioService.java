package br.com.dev.pontointeligente.api.services;

import br.com.dev.pontointeligente.api.entities.Funcionario;
import java.util.Optional;

/**
 *
 * @author LuisGustavo
 */
public interface FuncionarioService {

    /**
     * Cadastra um novo funcionario na base de dados
     * 
     * @param funcionario
     * @return Funcionario
     */
    Funcionario persistir(Funcionario funcionario);

    /**
     * Retorna um funcionario dado um cpf
     * 
     * @param cpf
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorCpf(String cpf);

    /**
     * Retorna um funcionario dado um email
     * 
     * @param email
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorEmail(String email);

    /**
     * Retorna um funcionario dado um id
     * 
     * @param id
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorId(Long id);

}
