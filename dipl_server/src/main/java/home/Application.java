/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

/**
 *
 * @author trident
 */
import com.google.gson.JsonObject;
import io.netty.handler.codec.base64.Base64Encoder;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SpringBootApplication
@EnableJpaRepositories
public class Application {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);

//        byte[] encrypted = EncryptionModule.encrypt("Hello world ".getBytes());
//        String decrypted = new String(EncryptionModule.decrypt(encrypted));
//        System.out.println("result "+decrypted);
//        System.out.println("public key "+EncryptionModule.getPublicKey());
//        
//        PublicKey publicKey = 
//        KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(EncryptionModule.getPublicKey().getEncoded()));
//        
//        System.out.println("public key recovered"+EncryptionModule.getPublicKey());

//          SecretKey key = EncryptionModule.generateSymmetricKey();
//          String enc = EncryptionModule.encrypt("Hello whore!", key);
//          System.out.println("enc: "+enc);
//          String dec = EncryptionModule.decrypt(enc, key);
//          System.out.println("dec: "+dec);
//          
//          byte[] encryptedSecret = EncryptionModule.encrypt(key.getEncoded());
//          String encryptedSecretStr = new BASE64Encoder().encode(encryptedSecret);
//          
//          System.out.println("Encrypted secret: "+encryptedSecretStr);
//          System.out.println("Encrypted data: "+enc);
//          
//          
//          byte[] returnedSecret = new BASE64Decoder().decodeBuffer(encryptedSecretStr);
//          SecretKey originalKey = new SecretKeySpec(returnedSecret, 0, returnedSecret.length, "AES"); 
//          String restored_data = EncryptionModule.decrypt(enc, originalKey);
//          System.out.println("restored data: "+restored_data);

//    SecretKey secretKey = null;
//    String stringKey = "";
//
//    try {secretKey = KeyGenerator.getInstance("AES").generateKey();}
//    catch (NoSuchAlgorithmException e) {/* LOG YOUR EXCEPTION */}
//    
//    
//    String encData = EncryptionModule.encryptSymmetric("Hello whore!", secretKey);
//
//    if (secretKey != null) {
//        stringKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
//    }
//    
//    String transferedSecretKey = Base64.getEncoder().encodeToString(EncryptionModule.encrypt(stringKey.getBytes()));
//    byte[] decoded = Base64.getDecoder().decode(EncryptionModule.decrypt(Base64.getDecoder().decode(transferedSecretKey)));
//    
//    
//    // DECODE YOUR BASE64 STRING
//    // REBUILD KEY USING SecretKeySpec
//
//    byte[] encodedKey     = Base64.getDecoder().decode(stringKey);
//    
//    encodedKey = decoded;
//    
//    SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
//    
//    String decData = EncryptionModule.decryptSymmetric(encData, originalKey);
//    
//    System.out.println("dec Data: "+decData);


      String keyStr = Base64.getEncoder().encodeToString(EncryptionModule.getPublicKey().getEncoded());
      PublicKey publicKey = 
      KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(keyStr)));
      InformationSecuity secuity = new InformationSecuity();
      SecuredData data =  secuity.createSecuredData("34141234567890123456;1234567890987651233", publicKey);
      System.out.println("output: "+secuity.decodeSecuredData(data));
          
        
    }
    
    

}
