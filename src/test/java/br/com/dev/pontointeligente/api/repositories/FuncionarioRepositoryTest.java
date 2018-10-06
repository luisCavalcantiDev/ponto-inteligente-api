package br.com.dev.pontointeligente.api.repositories;

import br.com.dev.pontointeligente.api.entities.Empresa;
import br.com.dev.pontointeligente.api.entities.Funcionario;
import br.com.dev.pontointeligente.api.enums.PerfilEnum;
import br.com.dev.pontointeligente.api.utils.PasswordUtils;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
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
public class FuncionarioRepositoryTest {

    @Autowired
    private FuncionarioRepository _funcionarioRepository;

    @Autowired
    private EmpresaRepository _empresaRepository;

    private static final String _EMAIL = "email@email.com";
    private static final String _CPF = "24291173474";

    public FuncionarioRepositoryTest() {
    }

    @Before
    public void setUp() throws Exception {
	Empresa _empresa = this._empresaRepository.save(obterDadosEmpresa());
	this._funcionarioRepository.save(obterDadosFuncionario(_empresa));
    }

    @After
    public void tearDown() {
	this._funcionarioRepository.deleteAll();
	this._empresaRepository.deleteAll();
    }

    @Test
    public void testFindByCpf() {
	Funcionario _funcionario = this._funcionarioRepository.findByCpf(_CPF);

	Assert.assertEquals(_CPF, _funcionario.getCpf());
    }

    @Test
    public void testFindByEmail() {
	Funcionario _funcionario = this._funcionarioRepository.findByEmail(_EMAIL);

	Assert.assertEquals(_EMAIL, _funcionario.getEmail());
    }

    @Test
    public void testFindByCpfOrEmail() {
	Funcionario _funcionario = this._funcionarioRepository.findByCpfOrEmail(_CPF, _EMAIL);

	Assert.assertNotNull(_funcionario);
    }

    @Test
    public void testFindByCpfOrEmail_InvalidEmail() {
	Funcionario _funcionario = this._funcionarioRepository.findByCpfOrEmail(_CPF, "email@invalido.com");

	Assert.assertNotNull(_funcionario);
    }

    @Test
    public void testFindByCpfOrEmail_InvalidCpf() {
	Funcionario _funcionario = this._funcionarioRepository.findByCpfOrEmail("12345678901", _EMAIL);

	Assert.assertNotNull(_funcionario);
    }

    private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
	Funcionario _funcionario = new Funcionario();
	_funcionario.setNome("Nome Funcionario 001");
	_funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
	_funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
	_funcionario.setCpf(_CPF);
	_funcionario.setEmail(_EMAIL);
	_funcionario.setEmpresa(empresa);
	_funcionario.setQtdHorasAlmoco(Float.MIN_NORMAL);
	_funcionario.setQtdHorasTrabalhoDia(Float.MIN_NORMAL);
	_funcionario.setValorHora(BigDecimal.ZERO);
	return _funcionario;
    }

    private Empresa obterDadosEmpresa() {
	Empresa _empresa = new Empresa();
	_empresa.setRazaoSocial("Nome Empresa 001");
	_empresa.setCnpj("51463645000100");
	return _empresa;
    }
}
