/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.raspberry.controller;



import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.*;
import java.io.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.FileUtils;
public class Main {
    
    public static String TERMINAL_CODE = "";
    private static final int PORT = 6666;
    
    
    private static final String CODE_FILENAME = "data.diploma";
    private static final String ARG_SETUP = "setup";
    private static final String ARG_SETUP_ACCESS_CODE = "-c";
    
    
    private static final String SETUP_HELP = "help";
    private static final String SETUP_SET_CODE = "setparams";
    private static final String SETUP_GET_CODE = "printparams";
    private static final String SETUP_SET_EXIT = "exit";
    
    public static void main(String[] args)  throws NoSuchAlgorithmException,InvalidKeySpecException{
        
        
        
        
        writeLog("init");
       
        RestRequestor requestor = new RestRequestor(); 
        GPIOConnector.prepare();
        GPIOConnector.waiting();
            
        String[] values = restoreCodeAndHost();
        TERMINAL_CODE = values[0];
        RestRequestor.HOST = values[1];
        
        for(String arg: args){
            System.out.println("arg:"+arg);
        }
        
        if((args.length==3&&args[0].equals(ARG_SETUP)&&args[1].equals(ARG_SETUP_ACCESS_CODE))||(TERMINAL_CODE.equals("")||RestRequestor.HOST.equals(""))){
            System.out.println("Setup mode requested: ");
            System.out.println("Waiting for the answer from the server... ");
            
            if(TERMINAL_CODE.equals("")||requestor.requestSetupAllowed(args[2])){
                System.out.println("Access granted!");
                System.out.println("Welcome to the admin console. Type \'help\' to return the list of commands");
                boolean exit = false;
                Console console = System.console();
                
                loop: while (!exit) {                    
                    switch(console.readLine(">>")){
                        case SETUP_HELP:
                            printCommands();
                            break;
                        case SETUP_SET_CODE:
                            String code = console.readLine("Enter the new code: ");
                            String host = console.readLine("Enter the new host name: ");
                            if(saveCodeAndHost(code,host)){
                                System.out.println("Successfully saved");
                            } else System.out.println("Not saved");
                            break;
                        case SETUP_GET_CODE:
                            System.out.println("code: "+restoreCodeAndHost()[0]+" host: "+restoreCodeAndHost()[1]);
                            break;
                        case SETUP_SET_EXIT:
                            System.out.println("Bye!");
                            TERMINAL_CODE = values[0];
                            RestRequestor.HOST = values[1];
                            break loop;
                        default:
                            System.out.println("Wrong command");
                            break;
                    }
                }
                
            } else{
               System.out.println("Access denied!");
               return;
            }
        } else
          {
              writeLog("in main block");
            if(RestRequestor.HOST.equals("")||TERMINAL_CODE.equals("")){
                System.out.println("Unable to start work. Params not set");
                return;
            }  
            
              System.out.println("Host: "+RestRequestor.HOST);
              System.out.println("Code: "+TERMINAL_CODE);
            String line = "";
            writeLog("here 1");
            final ProtocolMessageParser parser = new ProtocolMessageParser();
            try {
                writeLog("here 2");
                final ServerSocket ss = new ServerSocket(PORT); 
                while (true) {    
                    try {
                        writeLog("here 3");
                        System.out.println("Waiting for a client...");

                        final Socket socket = ss.accept();

                        Thread thread = new Thread(){
                            @Override
                            public void run(){
                                try {
                                    System.out.println("Got a client");
                                    System.out.println();

                                    InputStream sin = socket.getInputStream();
                                    OutputStream sout = socket.getOutputStream();

                                    DataInputStream in = new DataInputStream(sin);
                                    final DataOutputStream out = new DataOutputStream(sout);

                                    while(true) { 
                                        
                                        String line = "";
                                        line = in.readUTF(); 
                                        writeLog("here 4");
                                        System.out.println("Got a line: "+line);
                                        parser.processInputMessage(line, new ProtocolMessageParser.AnswerCallback() {
                                            @Override
                                            public void onAnswer(String answer,int code) {
                                                try {
                                                    System.out.println("Answer: "+answer); 
                                                    out.writeUTF(answer);
                                                    switch(code){
                                                        case RestRequestor.ACCESS_ACCEPT:
                                                        case RestRequestor.ACCESS_ADMIN:
                                                            GPIOConnector.allowAccess();
                                                            break;
                                                        case RestRequestor.ACCESS_ERROR:
                                                            out.writeUTF("e!s=o");
                                                        default:
                                                            GPIOConnector.rejectAccess();
                                                            break;
                                                    }
                                                } catch (Exception e) {
                                                    System.err.println("Error while writing answer");
                                                    e.printStackTrace();
                                                }
                                            }
                                        });    
                                    }
                                } catch (Exception e) {
                                    System.err.println("Error while listening for messages");
                                    e.printStackTrace();
                                }
                            }
                        };
                        thread.start();
                    } catch(Exception x) { 
                        System.err.println("Unable to accept connection");
                        x.printStackTrace(); 
                        break;
                    }    
                }
            } catch (Exception e) {
                System.err.println("Unable to open server socket");
                e.printStackTrace();
            }    
        }       
    }
    
    
    private static void printCommands(){
        System.out.println("\n\'help\' - this list");
        System.out.println("\'setparams\' - set terminal code and host");
        System.out.println("\'printparams\' - print terminal code and host");
        System.out.println("\'exit\' - exit");
    }
    
    private static boolean saveCodeAndHost(String code,String host){
        try {
            File f = new File(CODE_FILENAME);
            FileOutputStream s = new FileOutputStream(f);
            DataOutputStream bout = new DataOutputStream(s);
            JsonObject object = new JsonObject();
            object.addProperty("host", host);
            object.addProperty("code", code);
            
            bout.writeUTF(object.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }
    
    private static String[]  restoreCodeAndHost(){
        try{
            File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            String inputFilePath = jarFile.getParent() + File.separator + CODE_FILENAME; 
            System.out.println(inputFilePath);
            
            File f = new File(inputFilePath);
            if(!f.exists()){
                return new String[]{"",""};
            } else{
                FileInputStream s = new FileInputStream(f);

                DataInputStream bin = new DataInputStream(s);
                JsonParser p = new JsonParser();
                String sd = bin.readUTF();
                System.out.println("sd");
                JsonObject object  = p.parse(sd).getAsJsonObject();
                System.out.println("obj: "+object.toString());
                return new String[]{object.get("code").getAsString(),object.get("host").getAsString()};
            } 
        } catch(Exception ex){
            ex.printStackTrace();
            return new String[]{"",""};
        }
        
    }
    
    private static void writeLog(String message){
        try {
            File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            String inputFilePath = jarFile.getParent() + File.separator + "log.txt"; 
            System.out.println(inputFilePath);

            File f = new File(inputFilePath);
            FileUtils.writeStringToFile(f, "\n "+System.currentTimeMillis()+" "+message, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
}