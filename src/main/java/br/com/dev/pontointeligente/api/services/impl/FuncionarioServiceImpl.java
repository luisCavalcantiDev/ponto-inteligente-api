package br.com.dev.pontointeligente.api.services.impl;

import br.com.dev.pontointeligente.api.entities.Funcionario;
import br.com.dev.pontointeligente.api.repositories.FuncionarioRepository;
import br.com.dev.pontointeligente.api.services.FuncionarioService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LuisGustavo
 */
@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private static final Logger _log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

    @Autowired
    private FuncionarioRepository _funcionarioRepository;

    @Override
    public Funcionario persistir(Funcionario funcionario) {
	_log.info("Persistindo funcionario {}", funcionario);
	return this._funcionarioRepository.save(funcionario);
    }

    @Override
    public Optional<Funcionario> buscarPorCpf(String cpf) {
	return Optional.ofNullable(_funcionarioRepository.findByCpf(cpf));
    }

    @Override
    public Optional<Funcionario> buscarPorEmail(String email) {
	return Optional.ofNullable(_funcionarioRepository.findByEmail(email));
    }

    @Override
    public Optional<Funcionario> buscarPorId(Long id) {
	return Optional.ofNullable(_funcionarioRepository.findOne(id));
    }

}
