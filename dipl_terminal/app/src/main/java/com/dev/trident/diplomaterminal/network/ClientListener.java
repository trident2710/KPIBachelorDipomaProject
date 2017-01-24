package com.dev.trident.diplomaterminal.network;

/**
 * trident 16.05.16.
 */
public interface ClientListener {

    void onMessage(String message);
    void onClosed(Exception ex);
    void onEnd(Exception ex);
}
