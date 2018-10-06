package br.com.dev.pontointeligente.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author LuisGustavo
 */
public class PasswordUtils {

    private static final Logger _log = LoggerFactory.getLogger(PasswordUtils.class);

    public PasswordUtils() {
    }

    public static String gerarBCrypt(String senha) {
	if (senha == null) {
	    return senha;
	}

	_log.info("Gerando hash com o BCrypt.");
	BCryptPasswordEncoder _bCryptEncoder = new BCryptPasswordEncoder();

	return _bCryptEncoder.encode(senha);
    }

    public static boolean senhaValida(String senha, String senhaEncoded) {
	BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
	return bCryptEncoder.matches(senha, senhaEncoded);
    }

}
