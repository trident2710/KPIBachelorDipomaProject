/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.raspberry.controller;

import com.google.gson.JsonObject;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author trident
 */
public class RestRequestor {
    
    public static String HOST = "http://77.47.130.174:9090/rest-public/";
    public static final int ACCESS_ADMIN = 0x001;
    public static final int ACCESS_ACCEPT = 0x002;
    public static final int ACCESS_DECLINE = 0x003;
    public static final int ACCESS_ERROR = 0x004;
    
    public PublicKey getPublicKeyFromServer() throws NoSuchAlgorithmException,InvalidKeySpecException{
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(HOST+"security/public_key");
        String response = target.request(MediaType.TEXT_PLAIN).get(String.class);
        //System.out.println("response "+response);
        
        PublicKey publicKey = 
        KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(response)));
        
        //System.out.println("public key "+publicKey.toString());
        
        return publicKey;
    }
    
    public boolean requestSetupAllowed(String securityCode) {
        try{
            PublicKey key = getPublicKeyFromServer();
            String encryptedData = Base64.getEncoder().encodeToString(EncryptionModule.encrypt(securityCode.getBytes(), key));
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(HOST+"/terminal/setup_request?data="+encryptedData);
            return target.request(MediaType.TEXT_PLAIN).get(Boolean.class);  
        } catch(Exception ex){
            ex.printStackTrace();
            return false;
        }  
    }
    public int requestAccessAllowedForStaff(String staffSecurityCode,String terminalSecurityCode){
        try{
            String data = terminalSecurityCode+";"+staffSecurityCode;
            PublicKey key = getPublicKeyFromServer();
            
            InformationSecuity secuity = new InformationSecuity();
            SecuredData transfer = secuity.createSecuredData(data, key);
            System.out.println("transfer: "+transfer.toString());
 
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(HOST+"/event/permission_request");
            String  object =  target.request(MediaType.TEXT_PLAIN).post(Entity.entity(transfer, MediaType.APPLICATION_JSON),String.class);  
            System.out.println("object: "+object);
            switch(object){
                case "admin":{
                    return ACCESS_ADMIN;
                }
                case "rejected":{
                    return ACCESS_DECLINE;
                }
                case "granted":{
                    return ACCESS_ACCEPT;
                }
            }
        } catch(Exception ex){
            ex.printStackTrace();
            return ACCESS_ERROR;
            
        }  
        return ACCESS_DECLINE;
    }
    
}
