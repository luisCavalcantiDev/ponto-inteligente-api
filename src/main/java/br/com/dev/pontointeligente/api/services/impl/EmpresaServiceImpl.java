package br.com.dev.pontointeligente.api.services.impl;

import br.com.dev.pontointeligente.api.entities.Empresa;
import br.com.dev.pontointeligente.api.repositories.EmpresaRepository;
import br.com.dev.pontointeligente.api.services.EmpresaService;
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
public class EmpresaServiceImpl implements EmpresaService {

    private static final Logger _log = LoggerFactory.getLogger(EmpresaServiceImpl.class);

    @Autowired
    private EmpresaRepository _empresaRepository;

    @Override
    public Optional<Empresa> buscarPorCnpj(String cnpj) {
	_log.info("Buscando empresa para o cnpj  {}", cnpj);
	return Optional.ofNullable(_empresaRepository.findByCnpj(cnpj));
    }

    @Override
    public Empresa persistir(Empresa empresa) {
	_log.info("Persistindo empresa: {}", empresa);
	return this._empresaRepository.save(empresa);
    }

}
