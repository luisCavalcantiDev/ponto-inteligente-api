package br.com.dev.pontointeligente.api.controllers;

import br.com.dev.pontointeligente.api.dtos.PessoaFisicaDto;
import br.com.dev.pontointeligente.api.entities.Empresa;
import br.com.dev.pontointeligente.api.entities.Funcionario;
import br.com.dev.pontointeligente.api.services.EmpresaService;
import br.com.dev.pontointeligente.api.services.FuncionarioService;
import com.google.gson.Gson;
import java.util.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author LuisGustavo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PessoaFisicaControllerTest {

    @Autowired
    private MockMvc _mvc;

    @MockBean
    private EmpresaService _empresaService;

    @MockBean
    private FuncionarioService _funcionarioService;

    private static final String _CADASTRAR_PF_URL = "/api/cadastro/pf";
    private static final Long _ID = Long.valueOf(1);
    private static final String _NOME = "Funcionario 2";
    private static final String _EMAIL = "email@email.com";
    private static final String _SENHA = "654321";
    private static final String _CPF = "71512956180";
    private static final Optional<String> _QTD_HORAS_ALMOCO = Optional.of("1");
    private static final Optional<String> _QTD_HORAS_TRABALHO_DIA = Optional.of("8");
    private static final Optional<String> _VALOR_HORA = Optional.of("30");

    public PessoaFisicaControllerTest() {
    }

    @Before
    public void setUp() throws Exception {
	BDDMockito.given(this._empresaService.persistir(Mockito.any(Empresa.class))).willReturn(new Empresa());
	BDDMockito.given(this._empresaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.of(new Empresa()));

	BDDMockito.given(this._funcionarioService.persistir(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
	BDDMockito.given(this._funcionarioService.buscarPorCpf(Mockito.anyString())).willReturn(Optional.of(new Funcionario()));
	BDDMockito.given(this._funcionarioService.buscarPorEmail(Mockito.anyString())).willReturn(Optional.of(new Funcionario()));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCadastrarInvalido() throws Exception {

	PessoaFisicaDto _cadastro = this.obterDadosDto();
	String _content = this.converterDtoParaStringJson(_cadastro);

	_mvc.perform(MockMvcRequestBuilders.post(_CADASTRAR_PF_URL)
		.content(_content)
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());

    }

    private PessoaFisicaDto obterDadosDto() {
	PessoaFisicaDto _cadastro = new PessoaFisicaDto();
	_cadastro.setId(_ID);
	_cadastro.setNome(_NOME);
	_cadastro.setEmail(_EMAIL);
	_cadastro.setSenha(_SENHA);
	_cadastro.setCpf(_CPF);
	_cadastro.setQtdHorasAlmoco(_QTD_HORAS_ALMOCO);
	_cadastro.setQtdHorasTrabalhoDia(_QTD_HORAS_TRABALHO_DIA);
	_cadastro.setValorHora(_VALOR_HORA);

	return _cadastro;
    }

    private String converterDtoParaStringJson(PessoaFisicaDto cadastro) {
	Gson _gson = new Gson();
	String _json = _gson.toJson(cadastro);

	return _json;
    }

}
