package com.dev.trident.diplomaterminal.activities.qrReader.view;

import android.content.Context;

/**
 * trident 16.05.16.
 */
public interface QRView {
    Context getContext();
    void setCodeReadingEnabled(boolean status);
    void showAccessStatusMessage(boolean status);
    void showMessage(String message);
    void openAdminSettingsPanel();
    void setBlocked(boolean isBlocked);
    void setLoading(boolean value);
    void setConnectionEstablished(boolean value);
    void playSound(boolean isPositive);

}
