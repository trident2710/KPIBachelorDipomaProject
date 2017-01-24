package com.dev.trident.diplomaterminal.activities.qrReader.model;

import android.content.Context;

import com.dev.trident.diplomaterminal.protocol.MessageCallback;
import com.dev.trident.diplomaterminal.protocol.MessageProcessor;

/**
 * trident 16.05.16.
 */
public interface QRModel {
    String[] getConnectionSettings(Context context);
    boolean checkVulnerability(boolean isAccessAllowed);
    boolean init(MessageCallback callback,String ipAddr,int port);
    boolean sendMessage(String message);
    MessageProcessor getMessageCreator();
}
