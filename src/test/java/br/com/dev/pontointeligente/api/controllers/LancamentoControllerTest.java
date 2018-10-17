package br.com.dev.pontointeligente.api.controllers;


import br.com.dev.pontointeligente.api.dtos.LancamentoDto;
import br.com.dev.pontointeligente.api.entities.Funcionario;
import br.com.dev.pontointeligente.api.entities.Lancamento;
import br.com.dev.pontointeligente.api.enums.TipoEnum;
import br.com.dev.pontointeligente.api.services.FuncionarioService;
import br.com.dev.pontointeligente.api.services.LancamentoService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author LuisGustavo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LancamentoControllerTest {

    @Autowired
    private MockMvc _mvc;

    @MockBean
    private LancamentoService _lancamentoService;

    @MockBean
    private FuncionarioService _funcionarioService;

    private static final String _URL_BASE = "/api/lancamentos/";
    private static final Long _ID_FUNCIONARIO = 1L;
    private static final Long _ID_LANCAMENTO = 1L;
    private static final String _TIPO = TipoEnum.INICIO_TRABALHO.name();
    private static final Date _DATA = new Date();

    private final SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /*TODO - check erro  Request processing failed; nested exception is java.lang.NullPointerException
	at br.com.dev.pontointeligente.api.controllers.LancamentoController.converterDtoParaLancamento(LancamentoController.java:240)
	at br.com.dev.pontointeligente.api.controllers.LancamentoController.adicionar(LancamentoController.java:126)*/
    /*
    @Test
    @WithMockUser
    public void testCadastrarLancamento() throws Exception {

	Lancamento _lancamento = obterDadosLancamento();
	BDDMockito.given(this._funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Funcionario()));
	BDDMockito.given(this._lancamentoService.persistir(Mockito.any(Lancamento.class))).willReturn(_lancamento);

	_mvc.perform(MockMvcRequestBuilders.post(_URL_BASE)
		.content(this.obterJsonRequisicaoPost())
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.id").value(_ID_LANCAMENTO))
		.andExpect(jsonPath("$.data.tipo").value(_TIPO))
		.andExpect(jsonPath("$.data.data").value(this._dateFormat.format(_DATA)))
		.andExpect(jsonPath("$.data.funcionarioId").value(_ID_FUNCIONARIO))
		.andExpect(jsonPath("$.errors").isEmpty());
    }
    */

    /*
    @Test
    @WithMockUser
    public void testCadastrarLancamentoFuncionarioIdInvalido() throws Exception {

	BDDMockito.given(this._funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.empty());

	_mvc.perform(MockMvcRequestBuilders.post(_URL_BASE)
		.content(this.obterJsonRequisicaoPost())
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors").value("Funcionário não encontrado. ID inexistente."))
		.andExpect(jsonPath("$.data").isEmpty());
    }
    */
    
    @Test
    @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
    public void testRemoverLancamento() throws Exception {

	BDDMockito.given(this._lancamentoService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Lancamento()));

	_mvc.perform(MockMvcRequestBuilders.delete(_URL_BASE + _ID_LANCAMENTO)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
    }
    /*
    @Test
    @WithMockUser
    public void testRemoverLancamentoAcessoNegado() throws Exception {

	BDDMockito.given(this._lancamentoService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Lancamento()));

	_mvc.perform(MockMvcRequestBuilders.delete(_URL_BASE + _ID_LANCAMENTO)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isForbidden());
    }
    */

    private String obterJsonRequisicaoPost() throws JsonProcessingException {

	LancamentoDto _cadastro = new LancamentoDto();
	_cadastro.setId(null);
	_cadastro.setData(this._dateFormat.format(_DATA));
	_cadastro.setTipo(_TIPO);
	_cadastro.setFuncionarioId(_ID_FUNCIONARIO);
	ObjectMapper mapper = new ObjectMapper();

	return mapper.writeValueAsString(_cadastro);
    }

    private Lancamento obterDadosLancamento() {

	Lancamento _lancamento = new Lancamento();
	_lancamento.setId(_ID_LANCAMENTO);
	_lancamento.setDataLcto(_DATA);
	_lancamento.setTipo(TipoEnum.valueOf(_TIPO));
	_lancamento.setFuncionario(new Funcionario());
	_lancamento.getFuncionario().setId(_ID_FUNCIONARIO);

	return _lancamento;
    }
}
