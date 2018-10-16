package br.com.dev.pontointeligente.api.controllers;

import br.com.dev.pontointeligente.api.dtos.PessoaJuridicaDto;
import br.com.dev.pontointeligente.api.entities.Empresa;
import br.com.dev.pontointeligente.api.entities.Funcionario;
import br.com.dev.pontointeligente.api.services.EmpresaService;
import br.com.dev.pontointeligente.api.services.FuncionarioService;
import com.google.gson.Gson;
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
public class PessoaJuridicaControllerTest {

    @Autowired
    private MockMvc _mvc;

    @MockBean
    private EmpresaService _empresaService;

    @MockBean
    private FuncionarioService _funcionarioService;

    private static final String _CADASTRAR_PJ_URL = "/api/cadastro/pj";
    private static final Long _ID = Long.valueOf(1);
    private static final String _NOME = "Funcionario 1";
    private static final String _EMAIL = "email@email.com";
    private static final String _SENHA = "123456";
    private static final String _CPF = "56249244026";
    private static final String _RAZAO_SOCIAL = "Empresa XYZ";
    private static final String _CNPJ = "66387844000145";

    public PessoaJuridicaControllerTest() {
    }

    @Test
    public void testCadastrarInValido() throws Exception {
	BDDMockito.given(this._empresaService.persistir(Mockito.any(Empresa.class))).willReturn(new Empresa());
	BDDMockito.given(this._empresaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.of(new Empresa()));

	BDDMockito.given(this._funcionarioService.persistir(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
	BDDMockito.given(this._funcionarioService.buscarPorCpf(Mockito.anyString())).willReturn(Optional.of(new Funcionario()));
	BDDMockito.given(this._funcionarioService.buscarPorEmail(Mockito.anyString())).willReturn(Optional.of(new Funcionario()));

	PessoaJuridicaDto _cadastro = this.obterDadosDto();
	String _content = this.converterDtoParaStringJson(_cadastro);

	_mvc.perform(MockMvcRequestBuilders.post(_CADASTRAR_PJ_URL)
		.content(_content)
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());

    }

    @Test
    public void testCadastrarInvalido() throws Exception {
    }

    private PessoaJuridicaDto obterDadosDto() {
	PessoaJuridicaDto _cadastro = new PessoaJuridicaDto();
	_cadastro.setId(_ID);
	_cadastro.setNome(_NOME);
	_cadastro.setEmail(_EMAIL);
	_cadastro.setSenha(_SENHA);
	_cadastro.setCpf(_CPF);
	_cadastro.setRazaoSocial(_RAZAO_SOCIAL);
	_cadastro.setCnpj(_CNPJ);

	return _cadastro;
    }

    private String converterDtoParaStringJson(PessoaJuridicaDto cadastro) {
	Gson _gson = new Gson();
	String _json = _gson.toJson(cadastro);

	return _json;
    }
}
