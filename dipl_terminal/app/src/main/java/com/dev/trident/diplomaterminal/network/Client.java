package com.dev.trident.diplomaterminal.network;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.dev.trident.diplomaterminal.other.AbstractCallback;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.callback.DataCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * trident 16.05.16.
 */
public class Client {

    private static final String TAG = "QRClient";

    private String host;
    private int port;

    private AsyncSocket socket;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void setup(final AbstractCallback<Void,String> callback) {
        Log.d(TAG,"setup");
        AsyncServer.getDefault().connectSocket(new InetSocketAddress(host, port), new ConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, final AsyncSocket socket) {
                Log.d(TAG,"connection completed");
                Client.this.socket = socket;
                if(ex!=null)
                    callback.onError(ex.getMessage());
                else
                    callback.onSuccess(null);
            }
        });
    }

    public void subscribeForEvents(ClientListener listener){
        handleConnectCompleted(listener,socket);
    }

    public boolean sendMessage(String message){
        try {
            Util.writeAll(socket, message.getBytes(), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {
                }
            });
            return true;
        } catch (Exception ex){
            return false;
        }

    }

    public boolean close(){
        try {
            socket.close();
            return true;
        } catch (Exception ex){
            return false;
        }

    }

    private void handleConnectCompleted(final ClientListener listener, final AsyncSocket socket) {

        socket.setDataCallback(new DataCallback() {
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                listener.onMessage(new String(bb.getAllByteArray()));
            }
        });

        socket.setClosedCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                listener.onClosed(ex);
            }
        });

        socket.setEndCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                listener.onEnd(ex);
            }
        });
    }
}
