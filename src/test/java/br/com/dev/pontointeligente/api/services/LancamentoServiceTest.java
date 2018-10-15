package br.com.dev.pontointeligente.api.services;

import br.com.dev.pontointeligente.api.entities.Lancamento;
import br.com.dev.pontointeligente.api.repositories.LancamentoRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author LuisGustavo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoServiceTest {

    @MockBean
    private LancamentoRepository _lancamentoRepository;

    @Autowired
    private LancamentoService _lancamentoService;

    public LancamentoServiceTest() {
    }

    @Before
    public void setUp() throws Exception {
	BDDMockito.given(this._lancamentoRepository.findByFuncionarioId(Mockito.anyLong(),
		Mockito.any(PageRequest.class))).willReturn(new PageImpl<Lancamento>(new ArrayList<Lancamento>()));
	BDDMockito.given(this._lancamentoRepository.findOne(Mockito.anyLong())).willReturn(new Lancamento());
	BDDMockito.given(this._lancamentoRepository.save(Mockito.any(Lancamento.class))).willReturn(new Lancamento());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBuscarFuncionarioPorId() {
	Page<Lancamento> _lancamento = this._lancamentoService.buscarFuncionarioPorId(1L, new PageRequest(0, 10));
	
	Assert.assertNotNull(_lancamento);
    }

    @Test
    public void testBuscarPorId() {
	Optional<Lancamento> _lancamento = this._lancamentoService.buscarPorId(1L);
	
	Assert.assertTrue(_lancamento.isPresent());
    }

    @Test
    public void testPersistir() {
	Lancamento _lancamento = this._lancamentoService.persistir(new Lancamento());
	
	Assert.assertNotNull(_lancamento);
    }           
}
