package com.dev.trident.diplomaterminal.activities.qrReader.model;

import android.content.Context;

import com.dev.trident.diplomaterminal.Application;
import com.dev.trident.diplomaterminal.protocol.MessageCallback;
import com.dev.trident.diplomaterminal.protocol.MessageProcessor;

/**
 * trident 16.05.16.
 */
public class QRModelImpl implements QRModel {
    private static final int RECONNECT_ATTEMPTS_MAX = 5;

    private int unsuccessfulAttempts = 0;
    private int reconnectAttempts = 0;
    SocketCommunicationTask socketCommunicationTask;
    private MessageProcessor messageProcessor;

    public QRModelImpl(){
        messageProcessor = new MessageProcessor();
    }

    @Override
    public String[] getConnectionSettings(Context context) {
        String[] result = new String[2];
        result[0] = context.getSharedPreferences(Application.PREFS_NAME,Context.MODE_PRIVATE).getString(Application.PREF_IPADDR,"192.168.1.100");
        result[1] = ""+context.getSharedPreferences(Application.PREFS_NAME,Context.MODE_PRIVATE).getInt(Application.PREF_IPPORT,6666);
        return result;
    }

    @Override
    public boolean checkVulnerability(boolean isAccessAllowed) {
        if(!isAccessAllowed) unsuccessfulAttempts++;
        else unsuccessfulAttempts = 0;
        return Application.UNSUCCESSFUL_BEFORE_BLOCK <= unsuccessfulAttempts;
    }

    @Override
    public boolean init(final MessageCallback callback, String ipAddr, int port) {
        if(++reconnectAttempts>RECONNECT_ATTEMPTS_MAX)
            return false;

        if(socketCommunicationTask!=null)
            socketCommunicationTask.cancel(true);

        socketCommunicationTask = new SocketCommunicationTask(ipAddr, port, new SocketCommunicationTask.SocketListener() {
            @Override
            public void onMessage(String message) {
                messageProcessor.processMessage(message,callback);
            }

            @Override
            public void onConnectionLost() {
                callback.onError(MessageCallback.ERROR_CONNECTION_REFUSED);
            }

            @Override
            public void onConnectionOk() {
                callback.onConnectionEstablished();
            }
        });
        socketCommunicationTask.execute();
        return true;
    }

    @Override
    public boolean sendMessage(String message) {
        if(socketCommunicationTask!=null) return socketCommunicationTask.sendMessage(message);
        return false;
    }

    @Override
    public MessageProcessor getMessageCreator() {
        return messageProcessor;
    }
}
