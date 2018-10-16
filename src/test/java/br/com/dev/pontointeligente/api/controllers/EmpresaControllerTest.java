package br.com.dev.pontointeligente.api.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import br.com.dev.pontointeligente.api.entities.Empresa;
import br.com.dev.pontointeligente.api.services.EmpresaService;

/**
 *
 * @author LuisGustavo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmpresaControllerTest {

    @Autowired
    private MockMvc _mvc;

    @MockBean
    private EmpresaService _empresaService;

    private static final String _BUSCAR_EMPRESA_CNPJ_URL = "/api/empresas/cnpj/";
    private static final Long _ID = Long.valueOf(1);
    private static final String _CNPJ = "51463645000100";
    private static final String _RAZAO_SOCIAL = "Empresa XYZ";

    public EmpresaControllerTest() {
    }

    @Test
    @WithMockUser
    public void testBuscarPorCnpjInvalido() throws Exception {
	BDDMockito.given(this._empresaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.empty());

	_mvc.perform(MockMvcRequestBuilders.get(_BUSCAR_EMPRESA_CNPJ_URL + _CNPJ).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors").value("Empresa não localizada para o CNPJ " + _CNPJ));
    }

    @Test
    @WithMockUser
    public void testBuscarPorCnpjValido() throws Exception {
	BDDMockito.given(this._empresaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.ofNullable(this.obterDadosEmpresa()));

	_mvc.perform(MockMvcRequestBuilders.get(_BUSCAR_EMPRESA_CNPJ_URL + _CNPJ)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.id").value(_ID))
		.andExpect(jsonPath("$.data.razaoSocial", equalTo(_RAZAO_SOCIAL)))
		.andExpect(jsonPath("$.data.cnpj", equalTo(_CNPJ)))
		.andExpect(jsonPath("$.errors").isEmpty());
    }

    private Empresa obterDadosEmpresa() {
	Empresa _empresa = new Empresa();
	_empresa.setId(_ID);
	_empresa.setCnpj(_CNPJ);
	_empresa.setRazaoSocial(_RAZAO_SOCIAL);

	return _empresa;
    }
}
