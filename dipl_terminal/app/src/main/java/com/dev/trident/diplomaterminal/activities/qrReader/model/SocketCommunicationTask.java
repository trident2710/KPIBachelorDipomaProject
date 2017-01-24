package com.dev.trident.diplomaterminal.activities.qrReader.model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * trident 25.05.16.
 */
public class SocketCommunicationTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "QRSocket";

    String dstAddress;
    int dstPort;

    Socket socket = null;
    DataInputStream in;
    DataOutputStream out;
    SocketListener listener;

    public interface SocketListener {
        void onMessage(String message);
        void onConnectionLost();
        void onConnectionOk();
    }


    public SocketCommunicationTask(String addr, int port,SocketListener listener){
        dstAddress = addr;
        dstPort = port;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        try {
            Log.d(TAG,"init");
            InetAddress ipAddress = InetAddress.getByName(dstAddress);
            socket = new Socket(ipAddress, dstPort);


            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            in = new DataInputStream(sin);
            out = new DataOutputStream(sout);

            Log.d(TAG,"connection established");
            listener.onConnectionOk();
            String line;
            try {
                while (true) {
                    line = in.readUTF();
                    Log.d(TAG,"on message "+line);
                    listener.onMessage(line);
                }
            } catch (Exception ex){
                ex.printStackTrace();
                Log.d(TAG,"error while reading messages");
            }

        } catch (Exception ex){
            Log.d(TAG,"error socket");
            ex.printStackTrace();

        }
        Log.d(TAG,"connection lost");
        listener.onConnectionLost();
        try {
            socket.close();
        } catch (Exception e){

        }

        return null;
    }

    public boolean sendMessage(String message) {
        try {
            Log.d(TAG,"send message: "+message);
            out.writeUTF(message);
            out.flush();
            return true;
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }


}
