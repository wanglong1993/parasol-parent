package com.ginkgocap.parasol.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;

import com.ginkgocap.ywxt.util.Encodes;

/**
 * @Title: UserUtils.java
 * @Package com.ginkgocap.ywxt.utils
 * @Description:
 * @author haiyan
 * @date 2015-5-28 下午3:50:26
 */
public class UserUtils {

	public String getBase64Password(String salt, String password) {
		int hashIterations = 5000;
		RandomNumberGenerator saltGenerator = new SecureRandomNumberGenerator();
		// Base64解密
		byte[] bt = Base64.decode(password);
		// 生成新的code
		password = new String(bt);
		return new Sha256Hash(password, salt, hashIterations).toHex();
	}
}
