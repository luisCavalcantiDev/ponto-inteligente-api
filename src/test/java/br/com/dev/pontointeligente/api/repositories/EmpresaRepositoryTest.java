package br.com.dev.pontointeligente.api.repositories;

import br.com.dev.pontointeligente.api.entities.Empresa;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author LuisGustavo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {

    @Autowired
    private EmpresaRepository _empresaRepository;

    private static final String _CNPJ = "51463645000100";

    public EmpresaRepositoryTest() {
    }

    @Before
    public void setUp() throws Exception {
	Empresa _empresa = new Empresa();
	_empresa.setRazaoSocial("Empresa de exemplo");
	_empresa.setCnpj(_CNPJ);
	this._empresaRepository.save(_empresa);
    }

    @After
    public void tearDown() {
	this._empresaRepository.deleteAll();
    }

    @Test
    public void testFindByCnpj() {
	Empresa _empresa = this._empresaRepository.findByCnpj(_CNPJ);

	Assert.assertEquals(_CNPJ, _empresa.getCnpj());
    }   
}
