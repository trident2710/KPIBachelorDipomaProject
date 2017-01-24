/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;

/**
 *
 * @author trident
 */
public class SecuredData{
        @JsonProperty(value = "key")
        String encryptedSymmetricKey;
        @JsonProperty(value = "data")
        String encryptedData;
        @JsonProperty(value = "ass_key")
        public String assKey;
        
        
        
        public SecuredData(){
            
        }

        public SecuredData(String encryptedSymmetricKey, String encryptedData,String assKey) {
            this.encryptedSymmetricKey = encryptedSymmetricKey;
            this.encryptedData = encryptedData;
            this.assKey = assKey;
        }
        

        public String getEncryptedData() {
            return encryptedData;
        }

        public void setEncryptedData(String encryptedData) {
            this.encryptedData = encryptedData;
        }
        
        public String toString(){
            JsonObject object = new JsonObject();
            object.addProperty("data", encryptedData);
            object.addProperty("key", encryptedSymmetricKey);
            object.addProperty("ass_key", assKey);
            return object.toString();
        }
        
        
    }
