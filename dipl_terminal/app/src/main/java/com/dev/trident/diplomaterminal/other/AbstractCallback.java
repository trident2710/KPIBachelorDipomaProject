package com.dev.trident.diplomaterminal.other;

/**
 * trident 16.05.16.
 */
public interface AbstractCallback<T,K> {
    void onSuccess(T result);
    void onError(K error);
}
