package br.com.dev.pontointeligente.api.controllers;

import br.com.dev.pontointeligente.api.dtos.PessoaFisicaDto;
import br.com.dev.pontointeligente.api.entities.Empresa;
import br.com.dev.pontointeligente.api.entities.Funcionario;
import br.com.dev.pontointeligente.api.enums.PerfilEnum;
import br.com.dev.pontointeligente.api.response.Response;
import br.com.dev.pontointeligente.api.services.EmpresaService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LuisGustavo
 */
@RestController
@RequestMapping("/api/cadastro/pf")
@CrossOrigin(origins = "*")
public class PessoaFisicaController {

    private static final Logger _log = LoggerFactory.getLogger(PessoaFisicaController.class);

    @Autowired
    private EmpresaService _empresaService;

    @Autowired
    private FuncionarioService _funcionarioService;

    /**
     * Cadastra um funcionário pessoa física no sistema.
     *
     * @param cadastro
     * @param result
     * @return ResponseEntity<Response<PessoaFisicaDto>> @thr Throws
     * NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<PessoaFisicaDto>> cadastrar(@Valid @RequestBody PessoaFisicaDto cadastro, BindingResult result) throws NoSuchAlgorithmException {

	_log.info("Cadastrando PF: {}", cadastro.toString());
	Response<PessoaFisicaDto> _response = new Response<PessoaFisicaDto>();

	validarDados(cadastro, result);
	Funcionario _funcionario = this.converterDtoParaFuncionario(cadastro, result);

	if (result.hasErrors()) {
	    _log.error("Erro validando dados de cadastro PF: {}", result.getAllErrors());
	    result.getAllErrors().forEach(error -> _response.getErrors().add(error.getDefaultMessage()));
	    return ResponseEntity.badRequest().body(_response);
	}

	Optional<Empresa> _empresa = this._empresaService.buscarPorCnpj(cadastro.getCnpj());
	_empresa.ifPresent(emp -> _funcionario.setEmpresa(emp));
	this._funcionarioService.persistir(_funcionario);

	_response.setData(this.converterDtoParaFuncionario(_funcionario));
	return ResponseEntity.ok(_response);
    }

    /**
     * Verifica se a empresa está cadastrada e se o funcionário não existe na
     * base de dados.
     *
     * @param cadastro
     * @param result
     */
    private void validarDados(PessoaFisicaDto cadastro, BindingResult result) {

	Optional<Empresa> _empresa = this._empresaService.buscarPorCnpj(cadastro.getCnpj());
	if (!_empresa.isPresent()) {
	    result.addError(new ObjectError("empresa", "Empresa não cadastrada."));
	}

	this._funcionarioService.buscarPorCpf(cadastro.getCpf())
		.ifPresent(func -> result.addError(new ObjectError("funcionario", "CPF já existente.")));

	this._funcionarioService.buscarPorEmail(cadastro.getEmail())
		.ifPresent(func -> result.addError(new ObjectError("funcionario", "Email já existente.")));
    }

    /**
     * Converte os dados do DTO para funcionário.
     *
     * @param cadastroPFDto
     * @param result
     * @return Funcionario
     * @throws NoSuchAlgorithmException
     */
    private Funcionario converterDtoParaFuncionario(PessoaFisicaDto cadastroPFDto, BindingResult result) throws NoSuchAlgorithmException {

	Funcionario _funcionario = new Funcionario();
	_funcionario.setNome(cadastroPFDto.getNome());
	_funcionario.setEmail(cadastroPFDto.getEmail());
	_funcionario.setCpf(cadastroPFDto.getCpf());
	_funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
	_funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPFDto.getSenha()));

	cadastroPFDto.getQtdHorasAlmoco().ifPresent(qtdHorasAlmoco -> _funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
	cadastroPFDto.getQtdHorasTrabalhoDia().ifPresent(qtdHorasTrabDia -> _funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabDia)));
	cadastroPFDto.getValorHora().ifPresent(valorHora -> _funcionario.setValorHora(new BigDecimal(valorHora)));

	return _funcionario;
    }

    /**
     * Popula o DTO de cadastro com os dados do funcionário e empresa.
     *
     * @param funcionario
     * @return CadastroPFDto
     */
    private PessoaFisicaDto converterDtoParaFuncionario(Funcionario funcionario) {

	PessoaFisicaDto _cadastro = new PessoaFisicaDto();
	_cadastro.setId(funcionario.getId());
	_cadastro.setNome(funcionario.getNome());
	_cadastro.setEmail(funcionario.getEmail());
	_cadastro.setCpf(funcionario.getCpf());
	_cadastro.setCnpj(funcionario.getEmpresa().getCnpj());

	funcionario.getQtdHorasAlmocoOpt().ifPresent(qtdHorasAlmoco -> _cadastro.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
	funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(qtdHorasTrabDia -> _cadastro.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabDia))));
	funcionario.getValorHoraOpt().ifPresent(valorHora -> _cadastro.setValorHora(Optional.of(valorHora.toString())));

	return _cadastro;
    }
}
