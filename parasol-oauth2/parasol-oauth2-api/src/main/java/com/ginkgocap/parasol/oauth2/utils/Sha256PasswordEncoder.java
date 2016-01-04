package com.ginkgocap.parasol.oauth2.utils;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class Sha256PasswordEncoder implements PasswordEncoder {

	// @实现加密的方法，既将明文转换为密文的方法
	public String encodePassword(String rawPass, Object salt) {
		int hashIterations = 5000;
		// Base64解密
//		byte[] bt = Base64.decode(rawPass);
		// 生成新的code
//		String password = new String(bt);
		String newPass = new Sha256Hash(rawPass, salt, hashIterations).toHex();
		return newPass;
	}

	// @验证密码是否有效的方法，返回'true'则登录成功
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		String pass1 = "" + encPass;
		String pass2 = encodePassword(rawPass, salt);
		return pass1.equals(pass2);
	}
}