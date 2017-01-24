/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import com.google.gson.JsonObject;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author trident
 */
public class InformationSecuity {
    
    public SecuredData createSecuredData(String information,PublicKey assymetricKey) throws Exception{
        SecretKey symmetricKey = KeyGenerator.getInstance("AES").generateKey();
        String encData = EncryptionModule.encryptSymmetric(information, symmetricKey);
        String transferedSecretKey = Base64.getEncoder().encodeToString(EncryptionModule.encrypt(Base64.getEncoder().encodeToString(symmetricKey.getEncoded()).getBytes(),assymetricKey));
        return new SecuredData(transferedSecretKey,encData,Base64.getEncoder().encodeToString(assymetricKey.getEncoded()));
    }
    
    public String decodeSecuredData(SecuredData data) throws Exception{
        byte[] decoded = Base64.getDecoder().decode(EncryptionModule.decrypt(Base64.getDecoder().decode(data.encryptedSymmetricKey)));
        SecretKey originalKey = new SecretKeySpec(decoded, 0, decoded.length, "AES");
        return EncryptionModule.decryptSymmetric(data.encryptedData, originalKey);
    
    }
    
}
