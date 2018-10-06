package br.com.dev.pontointeligente.api.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class PasswordUtilsTest {
    
    public PasswordUtilsTest() {
    }
    
    @Test
    public void testGerarBCrypt() {
	String _senhaEncoded = PasswordUtils.gerarBCrypt("123456");
	Assert.assertTrue(_senhaEncoded != null);
    }
    
    @Test
    public void testSenhaValida() {
	String _senhaEncoded = PasswordUtils.gerarBCrypt("123456");
	boolean _senhaValida = PasswordUtils.senhaValida("123456", _senhaEncoded);
	
	Assert.assertTrue(_senhaValida);
    }
}
