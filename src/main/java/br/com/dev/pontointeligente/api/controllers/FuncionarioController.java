package br.com.dev.pontointeligente.api.controllers;

import br.com.dev.pontointeligente.api.dtos.FuncionarioDto;
import br.com.dev.pontointeligente.api.entities.Funcionario;
import br.com.dev.pontointeligente.api.response.Response;
import br.com.dev.pontointeligente.api.services.FuncionarioService;
import br.com.dev.pontointeligente.api.utils.PasswordUtils;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LuisGustavo
 */
@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

    private static final Logger _log = LoggerFactory.getLogger(FuncionarioController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    public FuncionarioController() {
    }

    /**
     * Atualiza os dados de um funcionário.
     *
     * @param id
     * @param cadastro
     * @param result
     * @return ResponseEntity<Response<FuncionarioDto>> @thr ows
     * NoSuchAlgorithmException
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody FuncionarioDto cadastro, BindingResult result) throws NoSuchAlgorithmException {

	_log.info("Atualizando funcionário: {}", cadastro.toString());
	Response<FuncionarioDto> _response = new Response<FuncionarioDto>();

	Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(id);
	if (!funcionario.isPresent()) {
	    result.addError(new ObjectError("funcionario", "Funcionário não encontrado."));
	}

	this.atualizarDadosFuncionario(funcionario.get(), cadastro, result);

	if (result.hasErrors()) {
	    _log.error("Erro validando funcionário: {}", result.getAllErrors());
	    result.getAllErrors().forEach(error -> _response.getErrors().add(error.getDefaultMessage()));
	    return ResponseEntity.badRequest().body(_response);
	}

	this.funcionarioService.persistir(funcionario.get());
	_response.setData(this.converterFuncionarioDto(funcionario.get()));

	return ResponseEntity.ok(_response);
    }

    /**
     * Atualiza os dados do funcionário com base nos dados encontrados no DTO.
     *
     * @param funcionario
     * @param cadastro
     * @param result
     * @throws NoSuchAlgorithmException
     */
    private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDto cadastro, BindingResult result)
	    throws NoSuchAlgorithmException {

	funcionario.setNome(cadastro.getNome());

	if (!funcionario.getEmail().equals(cadastro.getEmail())) {
	    this.funcionarioService.buscarPorEmail(cadastro.getEmail())
		    .ifPresent(func -> result.addError(new ObjectError("email", "Email já existente.")));
	    funcionario.setEmail(cadastro.getEmail());
	}

	funcionario.setQtdHorasAlmoco(null);
	cadastro.getQtdHorasAlmoco()
		.ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));

	funcionario.setQtdHorasTrabalhoDia(null);
	cadastro.getQtdHorasTrabalhoDia()
		.ifPresent(qtdHorasTrabDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabDia)));

	funcionario.setValorHora(null);
	cadastro.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

	if (cadastro.getSenha().isPresent()) {
	    funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastro.getSenha().get()));
	}
    }

    /**
     * Retorna um DTO com os dados de um funcionário.
     *
     * @param funcionario
     * @return FuncionarioDto
     */
    private FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {

	FuncionarioDto _cadastro = new FuncionarioDto();
	_cadastro.setId(funcionario.getId());
	_cadastro.setEmail(funcionario.getEmail());
	_cadastro.setNome(funcionario.getNome());
	funcionario.getQtdHorasAlmocoOpt().ifPresent(
		qtdHorasAlmoco -> _cadastro.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
	funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(
		qtdHorasTrabDia -> _cadastro.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabDia))));
	funcionario.getValorHoraOpt()
		.ifPresent(valorHora -> _cadastro.setValorHora(Optional.of(valorHora.toString())));

	return _cadastro;
    }

}
