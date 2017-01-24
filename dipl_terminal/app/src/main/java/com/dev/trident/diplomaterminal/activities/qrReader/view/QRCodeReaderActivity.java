package com.dev.trident.diplomaterminal.activities.qrReader.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.trident.diplomaterminal.Application;
import com.dev.trident.diplomaterminal.R;
import com.dev.trident.diplomaterminal.activities.qrReader.presenter.QRPresenter;
import com.dev.trident.diplomaterminal.activities.qrReader.presenter.QRPresenterImpl;
import com.dev.trident.diplomaterminal.activities.settings.SettingsActivity;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class QRCodeReaderActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler,QRView{

    private static final String TAG = "QRActivity";

    private static final int SETTINGS_REQUEST_CODE = 123;


    private QRPresenter presenter;
    int cameraId = -1;



    private ZBarScannerView mScannerView;
    private CoordinatorLayout background;
    private TextView messageContainer;
    private ImageView settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qrcode_reader);

        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }


        mScannerView = (ZBarScannerView)findViewById(R.id.scannerView);
        background = (CoordinatorLayout)findViewById(R.id.background);
        messageContainer = (TextView)findViewById(R.id.messageContainer);
        settingsButton = (ImageView)mActionBarToolbar.findViewById(R.id.settings);


        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(QRCodeReaderActivity.this, SettingsActivity.class),SETTINGS_REQUEST_CODE);
                settingsButton.setVisibility(View.GONE);
            }
        });

        this.presenter = new QRPresenterImpl(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.

        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.d(TAG, "Camera found");
                cameraId = i;
                break;
            }
        }
        mScannerView.startCamera(cameraId);
        mScannerView.resumeCameraPreview(this);         // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, rawResult.getContents()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);

        presenter.processAccessCode(rawResult.getContents());
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setCodeReadingEnabled(boolean status) {
        if(status){
            background.setBackgroundColor(getResources().getColor(R.color.yellow));
            messageContainer.setText("Scan your document here to request the access");
        } else {
            background.setBackgroundColor(getResources().getColor(R.color.red));
            messageContainer.setText("Terminal disabled");
        }

    }

    @Override
    public void showAccessStatusMessage(boolean status) {
        if(status){
            background.setBackgroundColor(getResources().getColor(R.color.green));
            messageContainer.setText("Access allowed");
        } else {
            background.setBackgroundColor(getResources().getColor(R.color.red));
            messageContainer.setText("Access denied");
        }
        resumeNormalStateHandler.postDelayed(runnable, Application.ACCESS_TIME_IN_MILLIS);
    }

    @Override
    public void showMessage(String message) {
        messageContainer.setText(message);
        resumeNormalStateHandler.postDelayed(runnable,Application.ACCESS_TIME_IN_MILLIS);
    }

    @Override
    public void openAdminSettingsPanel() {
        settingsButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setBlocked(boolean isBlocked) {
        if(isBlocked) {
            messageContainer.setText("Terminal temporary locked");
            mScannerView.stopCamera();
        } else {
            messageContainer.setText("Terminal unlocked");
            mScannerView.setResultHandler(this);
            mScannerView.startCamera(cameraId);
        }

    }

    ProgressDialog dialog;
    @Override
    public void setLoading(boolean value) {
        if(dialog!=null) dialog.cancel();
        if(value)
            dialog = ProgressDialog.show(this, "",
                "Your request is being processed. Please wait...", false);

    }

    ProgressDialog dialogConnection;
    @Override
    public void setConnectionEstablished(boolean value) {
        if(dialogConnection!=null) dialogConnection.cancel();
        if(!value)
            dialogConnection = ProgressDialog.show(this, "",
                    "No connection. The terminal is unavailable...", false);
    }

    @Override
    public void playSound(boolean isPositive) {
        final MediaPlayer mp = MediaPlayer.create(this, isPositive?R.raw.positive:R.raw.negative);
        mp.start();
    }


    Handler resumeNormalStateHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            background.setBackgroundColor(getResources().getColor(R.color.yellow));
            messageContainer.setText("Scan your document here to request the access");
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==SETTINGS_REQUEST_CODE&&resultCode==RESULT_OK){
            presenter.notifySettingsChanged();
        }
    }
}
