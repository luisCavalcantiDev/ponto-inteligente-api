package br.com.dev.pontointeligente.api.services.impl;

import br.com.dev.pontointeligente.api.entities.Lancamento;
import br.com.dev.pontointeligente.api.repositories.LancamentoRepository;
import br.com.dev.pontointeligente.api.services.LancamentoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author LuisGustavo
 */
@Service
public class LancamentoServiceImpl implements LancamentoService {
    
    private static final Logger _log = LoggerFactory.getLogger(LancamentoServiceImpl.class);
    
    @Autowired
    private LancamentoRepository _lancamentoRepository;
    
    @Override
    public Page<Lancamento> buscarFuncionarioPorId(Long funcionarioId, PageRequest pageRequest) {
	_log.info("Buscando lançamentos para um funcionario ID {}", funcionarioId);
	return this._lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
	
    }
    
    @Override
    public Optional<Lancamento> buscarPorId(Long id) {
	_log.info("Buscando um lancamento por ID {}", id);
	return Optional.ofNullable(this._lancamentoRepository.findOne(id));
    }
    
    @Override
    public Lancamento persistir(Lancamento lancamento) {
	_log.info("Persistindo o lançamento ID{}", lancamento);
	return this._lancamentoRepository.save(lancamento);
    }
    
    @Override
    public void remover(Long id) {
	_log.info("Removendo o lancamento ID {}", id);
	this._lancamentoRepository.delete(id);
    }
    
}
