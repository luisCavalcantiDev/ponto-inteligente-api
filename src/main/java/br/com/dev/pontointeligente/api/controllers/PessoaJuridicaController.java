package br.com.dev.pontointeligente.api.controllers;

import br.com.dev.pontointeligente.api.dtos.PessoaJuridicaDto;
import br.com.dev.pontointeligente.api.entities.Empresa;
import br.com.dev.pontointeligente.api.entities.Funcionario;
import br.com.dev.pontointeligente.api.enums.PerfilEnum;
import br.com.dev.pontointeligente.api.response.Response;
import br.com.dev.pontointeligente.api.services.EmpresaService;
import br.com.dev.pontointeligente.api.services.FuncionarioService;
import br.com.dev.pontointeligente.api.utils.PasswordUtils;
import java.security.NoSuchAlgorithmException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LuisGustavo
 */
@RestController
@RequestMapping("/api/cadastro/pj")
@CrossOrigin(origins = "*")
public class PessoaJuridicaController {

    private static final Logger _log = LoggerFactory.getLogger(PessoaJuridicaController.class);

    @Autowired
    private FuncionarioService _funcionarioService;

    @Autowired
    private EmpresaService _empresaService;

    public PessoaJuridicaController() {
    }

    /**
     * Cadastra uma pessoa jurídica no sistema
     *
     * @param cadastro
     * @param result
     * @return ResponseEntity<Response<PessoaJuridicaDto>> @ throws
     * NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<PessoaJuridicaDto>> cadastrar(@Valid @RequestBody PessoaJuridicaDto cadastro, BindingResult result) throws NoSuchAlgorithmException {

	_log.info("Cadastrando Pessoa Jurídica: {}", cadastro);
	Response<PessoaJuridicaDto> _response = new Response<PessoaJuridicaDto>();

	this.validarDados(cadastro, result);
	Empresa _empresa = this.converterDtoParaEmpresa(cadastro, result);
	Funcionario _funcionario = this.converterDtoParaFuncionario(cadastro, result);

	if (result.hasErrors()) {
	    _log.error("Erro validação no cadastro Pessoa Jurídica: {}", result.getAllErrors());
	    result.getAllErrors().forEach(_error -> _response.getErrors().add(_error.getDefaultMessage()));
	    return ResponseEntity.badRequest().body(_response);
	}
	
	this._empresaService.persistir(_empresa);
	_funcionario.setEmpresa(_empresa);
	this._funcionarioService.persistir(_funcionario);
	
	_response.setData(this.converterFuncionarioParaDto(_funcionario));	

	return ResponseEntity.ok(_response);

    }

    /**
     * Verifica se a empresa ou funcionário já existem na base de dados
     *
     * @param cadastro
     * @param result
     */
    private void validarDados(PessoaJuridicaDto cadastro, BindingResult result) {
	this._empresaService.buscarPorCnpj(cadastro.getCnpj())
		.ifPresent(_emp -> result.addError(new ObjectError("Empresa", "Empresa já existente.")));

	this._funcionarioService.buscarPorCpf(cadastro.getCpf())
		.ifPresent(_func -> result.addError(new ObjectError("Funcionário", "CPF já existente.")));

	this._funcionarioService.buscarPorEmail(cadastro.getEmail())
		.ifPresent(_func -> result.addError(new ObjectError("Funcionário", "Email já existente.")));
    }

    /**
     * Converte dados do Dto para entidade Empresa
     *
     * @param cadastro
     * @return Empresa
     */
    private Empresa converterDtoParaEmpresa(PessoaJuridicaDto cadastro, BindingResult result) {
	Empresa _empresa = new Empresa();
	_empresa.setCnpj(cadastro.getCnpj());
	_empresa.setRazaoSocial(cadastro.getRazaoSocial());

	return _empresa;
    }

    /**
     * Converte dados do Dto para entidade Funcionario
     *
     * @param cadastro
     * @return Funcionario
     */
    private Funcionario converterDtoParaFuncionario(PessoaJuridicaDto cadastro, BindingResult result) {
	Funcionario _funcionario = new Funcionario();
	_funcionario.setCpf(cadastro.getCpf());
	_funcionario.setNome(cadastro.getNome());
	_funcionario.setEmail(cadastro.getEmail());
	_funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
	_funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastro.getSenha()));

	return _funcionario;
    }

    /**
     * Converte dados do Funcionario para um Dto
     *
     * @param funcionario
     * @return PessoaJuridicaDto
     */
    private PessoaJuridicaDto converterFuncionarioParaDto(Funcionario funcionario) {
	PessoaJuridicaDto _cadastro = new PessoaJuridicaDto();
	_cadastro.setId(funcionario.getId());
	_cadastro.setCpf(funcionario.getCpf());
	_cadastro.setNome(funcionario.getNome());
	_cadastro.setEmail(funcionario.getEmail());
	_cadastro.setCnpj(funcionario.getEmpresa().getCnpj());

	return _cadastro;
    }
}
