package com.dev.trident.diplomaterminal.protocol;

/**
 * trident 16.05.16.
 */
public interface MessageCallback {
    int ACCESS_ALLOWED=             0b000000000001;
    int ACCESS_DENIED=              0b000000000010;
    int MESSAGE_INTERNAL=           0b000000000100;
    int MESSAGE_FOR_USER=           0b000000001000;
    int LOCK_SYSTEM =               0b000000010000;
    int UNLOCK_SYSTEM =             0b000000100000;
    int SYSTEM_CORRECT =            0b000001000000;
    int SYSTEM_INCORRECT =          0b000010000000;
    int ERROR_SYNTAX =              0b000100000000;
    int ERROR_OTHER =               0b001000000000;
    int ERROR_CONNECTION_REFUSED =  0b010000000000;
    int UNLOCK_FOR_ADMIN =          0b100000000000;

    void onLoginAnswer(int code);
    void onMessage(int code, String content);
    void onAction(int code);
    void onPing(int code);
    void onError(int code);
    void onConnectionEstablished();
}
