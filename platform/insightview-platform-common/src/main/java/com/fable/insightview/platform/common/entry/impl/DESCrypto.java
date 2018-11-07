package com.fable.insightview.platform.common.entry.impl;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.common.entry.SymmetricCrypto;

/**
 * Created by huapengzhu on 2016/2/17.
 * 简单的对称加密实现.
 * Modified by lubw on 2016/6/1
 * 新增一对加解密方法，自己提供加密密码
 * DES是一种对称加密算法，所谓对称加密算法即：加密和解密使用相同密钥的算法。DES加密算法出自IBM的研究，
 后来被美国政府正式采用，之后开始广泛流传，但是近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，
 24小时内即可被破解。虽然如此，在某些简单应用中，我们还是可以使用DES加密算法。
 注意：DES加密和解密过程中，密钥长度都必须是8的倍数
 */
@Service("desCryptoService")
public class DESCrypto implements SymmetricCrypto {
	/**
	 * 加密
	 * @param datasource
	 * @param password
	 * @return
	 * @throws Exception
     */
	@Override
	public  byte[] encrypt(byte[] datasource, String password) throws Exception {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			//创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			//Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			//用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			//现在，获取数据并加密
			//正式执行加密操作
			return cipher.doFinal(datasource);
	}

	/**
	 * 解密
	 * @param src
	 * @param password
	 * @return
	 * @throws Exception
     */
	@Override
	public byte[] decrypt(byte[] src, String password) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		// 创建一个DESKeySpec对象
		DESKeySpec desKey = new DESKeySpec(password.getBytes());
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// 将DESKeySpec对象转换成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// 真正开始解密操作
		return cipher.doFinal(src);
	}

	public static void main(String[] args) {
		DESCrypto desc = new DESCrypto();
		String str = "root123";

		try {
			String  ss = new String(Base64.encode(desc.encrypt(str.getBytes("utf-8"),"12345678")),"utf-8");
			System.out.println("加密："+ss);
			String  s = new String(desc.decrypt(Base64.decode(ss.getBytes("utf-8")),"12345678"));
			System.out.println("解密："+s+" length: "+s.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String decrypt(String password) throws Exception {
		return new String(this.decrypt(Base64.decode(password.getBytes("utf-8")),"12345678"),"utf-8");
	}

	@Override
	public String encrypt(String password) throws Exception {
		return new String(Base64.encode(this.encrypt(password.getBytes("utf-8"),"12345678")),"utf-8");
	}
}
