package br.com.dev.pontointeligente.api.controllers;

import br.com.dev.pontointeligente.api.dtos.LancamentoDto;
import br.com.dev.pontointeligente.api.entities.Funcionario;
import br.com.dev.pontointeligente.api.entities.Lancamento;
import br.com.dev.pontointeligente.api.enums.TipoEnum;
import br.com.dev.pontointeligente.api.response.Response;
import br.com.dev.pontointeligente.api.services.FuncionarioService;
import br.com.dev.pontointeligente.api.services.LancamentoService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 *
 * @author LuisGustavo
 */
@RestController
@RequestMapping("/api/lancamentos")
@CrossOrigin(origins = "*")
public class LancamentoController {

    private static final Logger _log = LoggerFactory.getLogger(LancamentoController.class);
    private final SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private LancamentoService _lancamentoService;

    @Autowired
    private FuncionarioService _funcionarioService;

    @Value("${paginacao.qtd_por_pagina}")
    private int qtd_por_pagina;

    public LancamentoController() {
    }

    /**
     * Retorna a listagem de lançamentos de um funcionário.
     *
     * @param funcionarioId
     * @return ResponseEntity<Response<LancamentoDto>>
     */
    @GetMapping(value = "/funcionario/{funcionarioId}")
    public ResponseEntity<Response<Page<LancamentoDto>>> listarPorFuncionarioId(
	    @PathVariable("funcionarioId") Long funcionarioId,
	    @RequestParam(value = "pag", defaultValue = "0") int pag,
	    @RequestParam(value = "ord", defaultValue = "id") String ord,
	    @RequestParam(value = "dir", defaultValue = "DESC") String dir) {

	_log.info("Buscando lançamentos por ID do funcionário: {}, página: {}", funcionarioId, pag);
	Response<Page<LancamentoDto>> _response = new Response<Page<LancamentoDto>>();

	PageRequest _pageRequest = new PageRequest(pag, this.qtd_por_pagina, Direction.valueOf(dir), ord);
	Page<Lancamento> _lancamentos = this._lancamentoService.buscarFuncionarioPorId(funcionarioId, _pageRequest);
	Page<LancamentoDto> _lancamentosDto = _lancamentos.map(lancamento -> this.converterLancamentoDto(lancamento));

	_response.setData(_lancamentosDto);
	return ResponseEntity.ok(_response);
    }

    /**
     * Retorna um lançamento por ID.
     *
     * @param id
     * @return ResponseEntity<Response<LancamentoDto>>
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<LancamentoDto>> listarPorId(@PathVariable("id") Long id) {

	_log.info("Buscando lançamento por ID: {}", id);
	Response<LancamentoDto> _response = new Response<LancamentoDto>();
	Optional<Lancamento> _lancamento = this._lancamentoService.buscarPorId(id);

	if (!_lancamento.isPresent()) {
	    _log.info("Lançamento não encontrado para o ID: {}", id);
	    _response.getErrors().add("Lançamento não encontrado para o id " + id);
	    return ResponseEntity.badRequest().body(_response);
	}

	_response.setData(this.converterLancamentoDto(_lancamento.get()));
	return ResponseEntity.ok(_response);
    }

    /**
     * Adiciona um novo lançamento.
     *
     * @param lancamento
     * @param result
     * @return ResponseEntity<Response<LancamentoDto>> @thr Throws
     * ParseException
     */
    @PostMapping
    public ResponseEntity<Response<LancamentoDto>> adicionar(@Valid @RequestBody LancamentoDto cadastro, BindingResult result) throws ParseException {

	_log.info("Adicionando lançamento: {}", cadastro.toString());
	Response<LancamentoDto> _response = new Response<LancamentoDto>();
	this.validarFuncionario(cadastro, result);
	Lancamento _lancamento = this.converterDtoParaLancamento(cadastro, result);

	if (result.hasErrors()) {
	    _log.error("Erro validando lançamento: {}", result.getAllErrors());
	    result.getAllErrors().forEach(error -> _response.getErrors().add(error.getDefaultMessage()));
	    return ResponseEntity.badRequest().body(_response);
	}

	_lancamento = this._lancamentoService.persistir(_lancamento);
	_response.setData(this.converterLancamentoDto(_lancamento));
	return ResponseEntity.ok(_response);
    }

    /**
     * Atualiza os dados de um lançamento.
     *
     * @param id
     * @param atualizacao
     * @return ResponseEntity<Response<Lancamento>> @thr ows ParseException
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<LancamentoDto>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody LancamentoDto atualizacao, BindingResult result) throws ParseException {

	_log.info("Atualizando lançamento: {}", atualizacao.toString());
	Response<LancamentoDto> _response = new Response<LancamentoDto>();
	validarFuncionario(atualizacao, result);
	atualizacao.setId(Optional.of(id));
	Lancamento _lancamento = this.converterDtoParaLancamento(atualizacao, result);

	if (result.hasErrors()) {
	    _log.error("Erro validando lançamento: {}", result.getAllErrors());
	    result.getAllErrors().forEach(error -> _response.getErrors().add(error.getDefaultMessage()));
	    return ResponseEntity.badRequest().body(_response);
	}

	_lancamento = this._lancamentoService.persistir(_lancamento);
	_response.setData(this.converterLancamentoDto(_lancamento));
	return ResponseEntity.ok(_response);
    }

    /**
     * Remove um lançamento por ID.
     *
     * @param id
     * @return ResponseEntity<Response<Lancamento>>
     */
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {

	_log.info("Removendo lançamento: {}", id);
	Response<String> _response = new Response<String>();
	Optional<Lancamento> _lancamento = this._lancamentoService.buscarPorId(id);

	if (!_lancamento.isPresent()) {
	    _log.info("Erro ao remover devido ao lançamento ID: {} ser inválido.", id);
	    _response.getErrors().add("Erro ao remover lançamento. Registro não encontrado para o id " + id);
	    return ResponseEntity.badRequest().body(_response);
	}

	this._lancamentoService.remover(id);
	return ResponseEntity.ok(new Response<String>());
    }

    /**
     * Valida um funcionário, verificando se ele é existente e válido no
     * sistema.
     *
     * @param lancamentoDto
     * @param result
     */
    private void validarFuncionario(LancamentoDto lancamentoDto, BindingResult result) {

	if (lancamentoDto.getFuncionarioId() == null) {
	    result.addError(new ObjectError("funcionario", "Funcionário não informado."));
	    return;
	}

	_log.info("Validando funcionário id {}: ", lancamentoDto.getFuncionarioId());
	Optional<Funcionario> funcionario = this._funcionarioService.buscarPorId(lancamentoDto.getFuncionarioId());
	if (!funcionario.isPresent()) {
	    result.addError(new ObjectError("funcionario", "Funcionário não encontrado. ID inexistente."));
	}
    }

    /**
     * Converte uma entidade lançamento para seu respectivo DTO.
     *
     * @param lancamento
     * @return LancamentoDto
     */
    private LancamentoDto converterLancamentoDto(Lancamento lancamento) {
	LancamentoDto _cadastro = new LancamentoDto();
	_cadastro.setId(Optional.of(lancamento.getId()));
	_cadastro.setData(this._dateFormat.format(lancamento.getDataLcto()));
	_cadastro.setTipo(lancamento.getTipo().toString());
	_cadastro.setDescricao(lancamento.getDescricao());
	_cadastro.setLocalizacao(lancamento.getLocalizacao());
	_cadastro.setFuncionarioId(lancamento.getFuncionario().getId());

	return _cadastro;
    }

    /**
     * Converte um LancamentoDto para uma entidade Lancamento.
     *
     * @param lancamentoDto
     * @param result
     * @return Lancamento
     * @throws ParseException
     */
    private Lancamento converterDtoParaLancamento(LancamentoDto lancamentoDto, BindingResult result) throws ParseException {
	Lancamento _lancamento = new Lancamento();

	if (lancamentoDto.getId().isPresent()) {
	    Optional<Lancamento> lanc = this._lancamentoService.buscarPorId(lancamentoDto.getId().get());
	    if (lanc.isPresent()) {
		_lancamento = lanc.get();
	    } else {
		result.addError(new ObjectError("lancamento", "Lançamento não encontrado."));
	    }
	} else {
	    _lancamento.setFuncionario(new Funcionario());
	    _lancamento.getFuncionario().setId(lancamentoDto.getFuncionarioId());
	}

	_lancamento.setDescricao(lancamentoDto.getDescricao());
	_lancamento.setLocalizacao(lancamentoDto.getLocalizacao());
	_lancamento.setDataLcto(this._dateFormat.parse(lancamentoDto.getData()));

	if (EnumUtils.isValidEnum(TipoEnum.class, lancamentoDto.getTipo())) {
	    _lancamento.setTipo(TipoEnum.valueOf(lancamentoDto.getTipo()));
	} else {
	    result.addError(new ObjectError("tipo", "Tipo inválido."));
	}

	return _lancamento;
    }
}
