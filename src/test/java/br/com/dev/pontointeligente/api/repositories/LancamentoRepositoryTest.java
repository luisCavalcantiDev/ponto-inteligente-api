package br.com.dev.pontointeligente.api.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import br.com.dev.pontointeligente.api.entities.Empresa;
import br.com.dev.pontointeligente.api.entities.Funcionario;
import br.com.dev.pontointeligente.api.entities.Lancamento;
import br.com.dev.pontointeligente.api.enums.PerfilEnum;
import br.com.dev.pontointeligente.api.enums.TipoEnum;
import br.com.dev.pontointeligente.api.utils.PasswordUtils;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author LuisGustavo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {
    
    @Autowired
    private LancamentoRepository _lancamentoRepository;
    
    @Autowired
    private FuncionarioRepository _funcionarioRepository;
    
    @Autowired
    private EmpresaRepository _empresaRepository;
    
    private Long _funcionarioId;
    
    @Before
    public void setUp() throws Exception {
	Empresa _empresa = this._empresaRepository.save(obterDadosEmpresa());
	
	
	Funcionario _funcionario = this._funcionarioRepository.save(obterDadosFuncionario(_empresa));
	this._funcionarioId = _funcionario.getId();
	
	this._lancamentoRepository.save(obterDadosLancamentos(_funcionario));
    }
    
    @After
    public void tearDown() throws Exception {
	this._lancamentoRepository.deleteAll();
	this._funcionarioRepository.deleteAll();
	this._empresaRepository.deleteAll();
    }
    
    @Test
    public void testfindByFuncionarioIdList() {
	List<Lancamento> _lancamentos = this._lancamentoRepository.findByFuncionarioId(_funcionarioId);
	
	assertEquals(1, _lancamentos.size());
    }
    
    @Test
    public void testfindByFuncionarioIdPage() {
	PageRequest page = new PageRequest(0, 10);
	Page<Lancamento> _lancamentos = this._lancamentoRepository.findByFuncionarioId(_funcionarioId, page);
	
	assertEquals(1, _lancamentos.getTotalElements());
    }
    
    private Lancamento obterDadosLancamentos(Funcionario funcionario) {
	Lancamento _lancamento = new Lancamento();
	_lancamento.setDataCriacao(new Date());
	_lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
	_lancamento.setFuncionario(funcionario);
	_lancamento.setDataLcto(new Date());
	_lancamento.setDescricao("descrição");
	_lancamento.setLocalizacao("Santos");
	return _lancamento;
    }
    
    private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
	Funcionario _funcionario = new Funcionario();
	_funcionario.setNome("Nome de Funcionario 001");
	_funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
	_funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
	_funcionario.setCpf("24291173474");
	_funcionario.setEmail("email@email.com");
	_funcionario.setEmpresa(empresa);
	_funcionario.setQtdHorasAlmoco(Float.MIN_NORMAL);
	_funcionario.setQtdHorasTrabalhoDia(Float.MIN_NORMAL);
	_funcionario.setValorHora(BigDecimal.ZERO);
	return _funcionario;
    }
    
    private Empresa obterDadosEmpresa() {
	Empresa _empresa = new Empresa();
	_empresa.setRazaoSocial("Nome da Empresa 001");
	_empresa.setCnpj("51463645000100");
	return _empresa;
    }
}
