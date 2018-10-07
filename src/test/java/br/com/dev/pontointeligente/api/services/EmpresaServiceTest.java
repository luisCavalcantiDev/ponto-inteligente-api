package br.com.dev.pontointeligente.api.services;

import br.com.dev.pontointeligente.api.entities.Empresa;
import br.com.dev.pontointeligente.api.repositories.EmpresaRepository;
import java.util.Optional;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author LuisGustavo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaServiceTest {

    @MockBean
    private EmpresaRepository _empresaRepository;

    @Autowired
    private EmpresaService _empresaService;

    private static final String _CNPJ = "51463645000100";

    public EmpresaServiceTest() {
    }

    @Before
    public void setUp() throws Exception {
	BDDMockito.given(this._empresaRepository.findByCnpj(Mockito.anyString())).willReturn(new Empresa());
	BDDMockito.given(this._empresaRepository.save(Mockito.any(Empresa.class))).willReturn(new Empresa());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBuscarPorCnpj() {
	Optional<Empresa> _empresa = this._empresaService.buscarPorCnpj(_CNPJ);

	Assert.assertTrue(_empresa.isPresent());
    }

    @Test
    public void testPersistir() {
	Empresa _empresa = this._empresaService.persistir(new Empresa());

	Assert.assertNotNull(_empresa);
    }
}
