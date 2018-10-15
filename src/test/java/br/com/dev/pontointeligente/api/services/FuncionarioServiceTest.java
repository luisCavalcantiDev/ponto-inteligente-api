package br.com.dev.pontointeligente.api.services;

import br.com.dev.pontointeligente.api.entities.Funcionario;
import br.com.dev.pontointeligente.api.repositories.FuncionarioRepository;
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
public class FuncionarioServiceTest {

    @MockBean
    private FuncionarioRepository _funcionarioRepository;

    @Autowired
    private FuncionarioService _funcionarioService;

    private static final String _CPF = "24291173474";

    public FuncionarioServiceTest() {
    }

    @Before
    public void setUp() throws Exception {
	BDDMockito.given(this._funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
	BDDMockito.given(this._funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
	BDDMockito.given(this._funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());
	BDDMockito.given(this._funcionarioRepository.findOne(Mockito.anyLong())).willReturn(new Funcionario());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPersistir() {
	Funcionario _funcionario = this._funcionarioService.persistir(new Funcionario());

	Assert.assertNotNull(_funcionario);
    }

    @Test
    public void testBuscarPorCpf() {
	Optional<Funcionario> _funcionario = this._funcionarioService.buscarPorCpf(_CPF);

	Assert.assertTrue(_funcionario.isPresent());
    }

    @Test
    public void testBuscarPorEmail() {
	Optional<Funcionario> _funcionario = this._funcionarioService.buscarPorEmail("email@email.com");

	Assert.assertTrue(_funcionario.isPresent());
    }

    @Test
    public void testBuscarPorId() {
	Optional<Funcionario> _funcionario = this._funcionarioService.buscarPorId(1L);

	Assert.assertTrue(_funcionario.isPresent());
    }
}
