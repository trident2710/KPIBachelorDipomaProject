/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.raspberry.controller;

//import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author trident
 */
public class SecuredData{
        @JsonProperty(value = "key")
        String key;
        @JsonProperty(value = "data")
        String data;
        @JsonProperty(value = "ass_key")
        String ass_key;
        
        
        
        public SecuredData(){
            
        }

        public SecuredData(String encryptedSymmetricKey, String encryptedData,String assKey) {
            this.key = encryptedSymmetricKey;
            this.data = encryptedData;
            this.ass_key = assKey;
        }
        

        public String getEncryptedData() {
            return data;
        }

        public void setEncryptedData(String encryptedData) {
            this.data = encryptedData;
        }
        
        public String toString(){
            JsonObject object = new JsonObject();
            object.addProperty("data", data);
            object.addProperty("key", key);
            object.addProperty("ass_key", ass_key);
            return object.toString();
        }
        
        
    }
