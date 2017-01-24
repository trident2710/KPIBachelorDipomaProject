package com.dev.trident.diplomaterminal.activities.qrReader.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.IntegerRes;
import android.util.Log;

import com.dev.trident.diplomaterminal.Application;
import com.dev.trident.diplomaterminal.activities.qrReader.model.QRModel;
import com.dev.trident.diplomaterminal.activities.qrReader.model.QRModelImpl;
import com.dev.trident.diplomaterminal.activities.qrReader.view.QRView;
import com.dev.trident.diplomaterminal.network.RaspberryPiConnector;
import com.dev.trident.diplomaterminal.protocol.MessageCallback;
import com.dev.trident.diplomaterminal.protocol.MessageProcessor;

/**
 * trident 16.05.16.
 */
public class QRPresenterImpl implements QRPresenter{

    private static final String TAG = "QRPresenterImpl";

    QRView view;
    QRModel model;


    Handler handler = new Handler();
    Runnable lockRunnable = new Runnable() {
        @Override
        public void run() {
            view.setCodeReadingEnabled(false);
        }
    };

    String lastCode;

    public QRPresenterImpl(QRView view){
        this.view = view;
        this.model = new QRModelImpl();
        init();
    }

    @Override
    public void processAccessCode(String code) {
        Log.d(TAG,"process access code");
        view.setBlocked(true);
        view.setLoading(true);
        lastCode = code;
        model.sendMessage(model.getMessageCreator().createMessage(MessageProcessor.MessageType.LOGIN_REQUEST,new String[][]{{"c",code}}));
    }

    @Override
    public void notifySettingsChanged() {
        init();
    }

    private void init(){
        String[] settings = model.getConnectionSettings(view.getContext());
        if(settings.length == 2&&!settings[0].equals("")&&Integer.parseInt(settings[1])!=0){
            Log.d(TAG,"setup: "+settings[0]+" "+settings[1]);
            model.init(raspberryCallback,settings[0],Integer.parseInt(settings[1]));
        } else view.setCodeReadingEnabled(false);
    }

    MessageCallback raspberryCallback = new MessageCallback() {

        @Override
        public void onLoginAnswer(final int code) {
            view.setLoading(false);
            //view.setBlocked(false);
            ((Activity)view).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    view.setBlocked(false);
                    if(code == MessageCallback.ACCESS_ALLOWED || code == MessageCallback.UNLOCK_FOR_ADMIN){
                        model.checkVulnerability(true);
                        Log.d(TAG,"access allowed");
                        view.showAccessStatusMessage(true);
                        view.playSound(true);
                    } else {
                        Log.d(TAG,"access rejected");
                        view.showAccessStatusMessage(false);
                        view.playSound(false);
                        if(model.checkVulnerability(false)){
                            view.showMessage("Terminal has been blocked due to unsuccessfull attempts");
                            view.setBlocked(true);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    view.setBlocked(false);
                                }
                            }, Application.BLOCK_INTERVAL_IN_MILLIS);
                        }
                    }
                }
            });
        }


        @Override
        public void onMessage(int code, String content) {
            view.setLoading(false);
//            if(code == MessageCallback.MESSAGE_FOR_USER)
//                view.showMessage(content);
//            if(code == MessageCallback.MESSAGE_INTERNAL){
//                Log.d(TAG,"Message Arrived: "+content);
//            }
        }

        @Override
        public void onAction(int code) {
            view.setLoading(false);
            Log.d(TAG,"received action");
//            if(code == MessageCallback.LOCK_SYSTEM)
//                view.setCodeReadingEnabled(false);
//            if(code == MessageCallback.UNLOCK_SYSTEM)
//                view.setCodeReadingEnabled(true);
//            if(code ==MessageCallback.UNLOCK_FOR_ADMIN)
//                view.openAdminSettingsPanel();
        }

        @Override
        public void onPing(int code) {
            view.setLoading(false);
            Log.d(TAG,"received ping");
//            handler.removeCallbacks(lockRunnable);
//            handler.postDelayed(lockRunnable,1000);
//
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    RaspberryPiConnector.getInstance().sendPing();
//                }
//            },100);

        }

        @Override
        public void onError(int code) {
            view.setLoading(false);
            Log.d(TAG,"received error");
            if(code == ERROR_CONNECTION_REFUSED){
                view.playSound(false);
                ((Activity)view).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG,"here");
                        if(!reconnecting){
                            //view.setBlocked(true);
                            view.setConnectionEstablished(false);
                            reconnectHandler.postDelayed(reconnectRunnable,Application.RECONNECT_TIME_IN_MILLIS);
                            reconnecting = true;
                        }


                    }
                });

                Log.d(TAG,"connection refused");
            }
            if(code == ERROR_OTHER){
                view.playSound(false);
                view.showMessage("Unable to process your request. The connection with the server is missing");
            }
        }

        @Override
        public void onConnectionEstablished() {
            reconnectHandler.removeCallbacks(reconnectRunnable);
            reconnecting = false;
            ((Activity)view).runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //view.setBlocked(false);
                    view.setConnectionEstablished(true);
                }
            });

            Log.d(TAG,"connection established");
        }
    };

    boolean reconnecting = false;
    Handler reconnectHandler = new Handler();
    Runnable reconnectRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG,"reconnect run");
            try {
                String[] s = model.getConnectionSettings((Context)view);
                model.init(raspberryCallback,s[0],Integer.parseInt(s[1]));
            } catch (Exception ex){
                Log.d(TAG,"unable to reconnect");
                ex.printStackTrace();
            }
            reconnectHandler.postDelayed(reconnectRunnable,Application.RECONNECT_TIME_IN_MILLIS);
        }
    };
}
