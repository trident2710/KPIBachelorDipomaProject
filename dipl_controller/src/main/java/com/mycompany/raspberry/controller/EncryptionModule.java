/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.raspberry.controller;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author trident
 */

public class EncryptionModule {
    
    private static KeyPair keyPair;
    private static String xform;
    public static final String X_FORM_DEFAULT = "RSA/ECB/PKCS1Padding";
    /**
	 * This method must be used to create pairs of private and public keys. It returns a List of two objects, the PublicKey and the
	 * PrivateKey of java.security.KeyPair
	 * @param algorithm: RSA, DSA
	 * @param keysize: 512, 1024, 2048
	 * @throws NoSuchAlgorithmException
	 */
	public static void init(String algorithm, int keysize,String xform) throws NoSuchAlgorithmException {
//            
//            ECGenParameterSpec     ecGenSpec = new ECGenParameterSpec("prime192v1");
//            KeyPairGenerator    g = KeyPairGenerator.getInstance("RSA");
//            g.initialize(ecGenSpec, new SecureRandom());
//            KeyPair pair = g.generateKeyPair();
//            return pair;
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm);
            kpg.initialize(keysize);
            KeyPair kp = kpg.generateKeyPair();
            keyPair = kp;
            EncryptionModule.xform = xform;
	}
	
	/**
	 * The method to encrypt data. It requires a previous generated private key and a transformation model.
	 * A transformation is a string that describes the operation to be performed on the given input.
	 * The transformation string is of the form: algorithm/mode/padding.<br><br>
	 * Transformation examples: <b>"DES/CFB8/NoPadding", "DES/OFB32/PKCS5Padding", "RSA/ECB/PKCS1Padding"</b><br>
	 * 
	 * @param inputBytes byte[]
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] encrypt (byte[] inputBytes) throws Exception {
		Cipher cipher = Cipher.getInstance(xform);
		cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
		return cipher.doFinal(inputBytes);
	}
        
       
        
        public static byte[] encrypt (byte[] inputBytes,PublicKey key) throws Exception {
		Cipher cipher = Cipher.getInstance(X_FORM_DEFAULT);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(inputBytes);
	}

	/**
	 * The method for data decryption. It requires a previous generated bublic key and the transformation model used for the previous encryption.
	 * A transformation is a string that describes the operation to be performed on the given input.
	 * The transformation string is of the form: algorithm/mode/padding.<br><br>
	 * Transformation examples: <b>"DES/CFB8/NoPadding", "DES/OFB32/PKCS5Padding", "RSA/ECB/PKCS1Padding"</b><br>
	 * @param inputBytes
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] decrypt (byte[] inputBytes) throws Exception{
		Cipher cipher = Cipher.getInstance(xform);
		cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
		return cipher.doFinal(inputBytes);
	}
        
        @PostConstruct 
        public static void init() throws NoSuchAlgorithmException{
            init("RSA", 2048,"RSA/ECB/PKCS1Padding");
        }
        
        
        public static PublicKey getPublicKey() throws NoSuchAlgorithmException{
            if(keyPair==null)
                init();
            
            return keyPair.getPublic();
               
        }
        
        public static String encryptSymmetric(final String valueEnc, final SecretKey key) { 

            String encryptedVal = null;

            try {
                final Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
                c.init(Cipher.ENCRYPT_MODE, key);
                final byte[] encValue = c.doFinal(valueEnc.getBytes());
                encryptedVal =  new BASE64Encoder().encode(encValue);
            } catch(Exception ex) {
                System.out.println("The Exception is=" + ex);
            }

            return encryptedVal;
        }
        
        public static String decryptSymmetric(final String encryptedVal, final SecretKey key) {

            String decryptedVal = null;

            try {

                final Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
                c.init(Cipher.DECRYPT_MODE, key);
                final byte[] decorVal = new BASE64Decoder().decodeBuffer(encryptedVal);
                final byte[] decValue = c.doFinal(decorVal);
                decryptedVal = new String(decValue);
            } catch(Exception ex) {
                System.out.println("The Exception is=" + ex);
            }

            return decryptedVal;
        }   
}
       