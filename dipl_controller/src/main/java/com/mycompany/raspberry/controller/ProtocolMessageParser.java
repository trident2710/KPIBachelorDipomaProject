/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.raspberry.controller;

import com.google.gson.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author trident
 */
public class ProtocolMessageParser {
    RestRequestor requestor;
    
    public ProtocolMessageParser(){
        this.requestor = new RestRequestor();
    }
    
    public static interface AnswerCallback{
        void onAnswer(String answer,int code);
    }
    
    
    public void processInputMessage(String message,AnswerCallback callback){
        if(message.contains("lr!c=")){
            System.out.println("It's login request");
            String code = message.substring(5);
            System.out.println("code: "+code);
            
            switch(requestor.requestAccessAllowedForStaff(code, Main.TERMINAL_CODE)){
                case RestRequestor.ACCESS_ACCEPT:
                    callback.onAnswer("la!s=a",RestRequestor.ACCESS_ACCEPT);
                    break;
                case RestRequestor.ACCESS_DECLINE:
                    callback.onAnswer("la!s=d",RestRequestor.ACCESS_DECLINE);
                    break;
                case RestRequestor.ACCESS_ADMIN:
                    callback.onAnswer("la!s=s",RestRequestor.ACCESS_ADMIN);
                    break;   
            }
            
        }
    }
    
}
