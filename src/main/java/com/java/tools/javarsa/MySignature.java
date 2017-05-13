package com.java.tools.javarsa;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * RSA数字签名，借用MyRSA中的算法，不再重复 数字签名遵循“私钥签名，公钥验签”原则，因为私钥是个人身份认证
 * 
 */
public class MySignature {

	/** 数字签名算法。JDK只提供了MD2withRSA, MD5withRSA, SHA1withRSA，其他的算法需要第三方包才能支持 */
	public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

	/**
	 * 签名，三步走 1. 实例化，传入算法 2. 初始化，传入私钥 3. 签名
	 * 
	 * @param key
	 * @param plainText
	 * @return
	 */
	public static byte[] sign(PrivateKey privateKey, byte[] plainText) {
		try {
			// 实例化
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);

			// 初始化，传入私钥
			signature.initSign(privateKey);

			// 更新
			signature.update(plainText);

			// 签名
			return signature.sign();

		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 验签，三步走 1. 实例化，传入算法 2. 初始化，传入公钥 3. 验签
	 * 
	 * @param publicKey
	 * @param signatureVerify
	 * @param plainText
	 * @return
	 */
	public static boolean verify(PublicKey publicKey, byte[] signatureVerify, byte[] plainText) {
		try {
			// 实例化
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);

			// 初始化
			signature.initVerify(publicKey);

			// 更新
			signature.update(plainText);

			// 验签
			return signature.verify(signatureVerify);
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
}
