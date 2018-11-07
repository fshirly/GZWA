package com.fable.insightview.platform.common.entry;

/**
 * Modified by lubw on 2016/6/1
 * 新增一对加解密方法，自己提供加密密码
 */
public interface SymmetricCrypto {
	public byte[] encrypt(byte[] datasource, String password) throws Exception;
	public byte[] decrypt(byte[] src, String password) throws Exception;
	public String decrypt(String password) throws Exception;
	public String encrypt(String password) throws Exception;
}
