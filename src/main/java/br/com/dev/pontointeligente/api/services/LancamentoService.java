package br.com.dev.pontointeligente.api.services;

import br.com.dev.pontointeligente.api.entities.Lancamento;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 *
 * @author LuisGustavo
 */
public interface LancamentoService {
    
    /**
     * Retorna os lançamentos de um funcionario dado um id e paginacao
     * 
     * @param funcionarioId
     * @param pageRequest
     * @return Page<Lancamento>
     */
    Page<Lancamento> buscarFuncionarioPorId(Long funcionarioId, PageRequest pageRequest);
    
    /**
     * Retorna um lancamento dado um id
     * 
     * @param id
     * @return Lancamento
     */
    Optional<Lancamento> buscarPorId(Long id);
    
    /**
     * Cadastra um lancamento na base de dados
     * 
     * @param lancamento
     * @return Lancamento
     */
    Lancamento persistir(Lancamento lancamento);
    
    /**
     * Remove um lancamento na base de dados
     * 
     * @param id    
     */
    void remover(Long id);
    
}
