package com.dev.trident.diplomaterminal.activities.qrReader.presenter;

/**
 * trident 16.05.16.
 */
public interface QRPresenter {
    void processAccessCode(String code);
    void notifySettingsChanged();
}
