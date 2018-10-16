package br.com.dev.pontointeligente.api.controllers;

import br.com.dev.pontointeligente.api.dtos.EmpresaDto;
import br.com.dev.pontointeligente.api.entities.Empresa;
import br.com.dev.pontointeligente.api.response.Response;
import br.com.dev.pontointeligente.api.services.EmpresaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LuisGustavo
 */
@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    private static final Logger _log = LoggerFactory.getLogger(EmpresaController.class);

    @Autowired
    private EmpresaService _empresaService;

    /**
     * Retorna uma empresa dado um CNPJ.
     *
     * @param cnpj
     * @return ResponseEntity<Response<EmpresaDto>>
     */
    @GetMapping(value = "/cnpj/{cnpj}")
    public ResponseEntity<Response<EmpresaDto>> buscarPorCnpj(@PathVariable("cnpj") String cnpj) {
	_log.info("Buscando empresa por CNPJ: {}", cnpj);

	Response<EmpresaDto> _response = new Response<EmpresaDto>();

	Optional<Empresa> _empresa = _empresaService.buscarPorCnpj(cnpj);
	if (!_empresa.isPresent()) {
	    _log.info("Empresa não localizada para o CNPJ: {}", cnpj);
	    _response.getErrors().add("Empresa não localizada para o CNPJ " + cnpj);
	    return ResponseEntity.badRequest().body(_response);
	}

	_response.setData(this.converterEmpresaDto(_empresa.get()));

	return ResponseEntity.ok(_response);
    }

    /**
     * Popula um DTO com os dados de uma empresa
     *
     * @param empresa
     * @return EmpresaDto
     */
    private EmpresaDto converterEmpresaDto(Empresa _empresa) {
	EmpresaDto _empresaDto = new EmpresaDto();
	_empresaDto.setId(_empresa.getId());
	_empresaDto.setRazaoSocial(_empresa.getRazaoSocial());
	_empresaDto.setCnpj(_empresa.getCnpj());

	return _empresaDto;
    }
}
