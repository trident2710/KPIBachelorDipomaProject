package com.dev.trident.diplomaterminal.network;

import android.util.Log;

import com.dev.trident.diplomaterminal.other.AbstractCallback;
import com.dev.trident.diplomaterminal.protocol.MessageCallback;
import com.dev.trident.diplomaterminal.protocol.MessageProcessor;

/**
 * trident 16.05.16.
 */
public class RaspberryPiConnector {

    private static final String TAG = "RaspberryPiConnector";



    private static final RaspberryPiConnector _instance = new RaspberryPiConnector();
    public static RaspberryPiConnector getInstance(){
        return _instance;
    }


    private Client client;
    private MessageProcessor messageProcessor;
    private MessageCallback raspberryMessageCallback;

    public void setup(String ipaddr,int port,MessageCallback callback){
        if(client!=null)
            client.close();
        client = new Client(ipaddr,port);
        messageProcessor = new MessageProcessor();
        raspberryMessageCallback = callback;

        client.setup(new AbstractCallback<Void, String>() {
            @Override
            public void onSuccess(Void result) {
                Log.d(TAG,"connected");
                client.subscribeForEvents(listener);
            }

            @Override
            public void onError(String error) {
                Log.e(TAG,"unable to connect");
            }
        });

    }

    public void close(){
        if(client!=null)
            client.close();
        if(raspberryMessageCallback!=null)
            raspberryMessageCallback.onError(MessageCallback.ERROR_CONNECTION_REFUSED);
    }

    private ClientListener listener = new ClientListener() {
        @Override
        public void onMessage(String message) {
            if(messageProcessor!=null&&raspberryMessageCallback!=null)
                messageProcessor.processMessage(message,raspberryMessageCallback);
            else throw new RuntimeException("Fields not initialized");
        }

        @Override
        public void onClosed(Exception ex) {
            if(raspberryMessageCallback!=null)
                raspberryMessageCallback.onError(MessageCallback.ERROR_CONNECTION_REFUSED);
        }

        @Override
        public void onEnd(Exception ex) {
            if(raspberryMessageCallback!=null)
                raspberryMessageCallback.onError(MessageCallback.ERROR_CONNECTION_REFUSED);
        }
    };

    public void sendLogin(String code){
        Log.d(TAG,"send login");
        if(client!=null&&messageProcessor!=null)
            client.sendMessage(messageProcessor.createMessage(MessageProcessor.MessageType.LOGIN_REQUEST, new String[]{"c", code}));
        else throw new RuntimeException("not initialized");
    }
    public void sendMessage(boolean internalOrPublic,String message){
        if(client!=null&&messageProcessor!=null)
            client.sendMessage(messageProcessor.createMessage(MessageProcessor.MessageType.MESSAGE, new String[]{"p", internalOrPublic?"i":"u"},new String[]{"c",message}));
        else throw new RuntimeException("not initialized");
    }
    public void sendPing(){
        Log.d(TAG,"send ping");
        if(client!=null&&messageProcessor!=null)
            client.sendMessage(messageProcessor.createMessage(MessageProcessor.MessageType.PING));
        else throw new RuntimeException("not initialized");
    }
}
