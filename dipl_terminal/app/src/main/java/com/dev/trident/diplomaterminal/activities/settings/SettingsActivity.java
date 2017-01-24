package com.dev.trident.diplomaterminal.activities.settings;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.trident.diplomaterminal.Application;
import com.dev.trident.diplomaterminal.R;
import com.stealthcopter.networktools.Ping;
import com.stealthcopter.networktools.ping.PingResult;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @Bind(R.id.ipAddress)
    EditText ipAddress;
    @Bind(R.id.ipPort)
    EditText ipPort;
    @Bind(R.id.btnTestConnection)
    Button btnTestConnection;
    @Bind(R.id.save)
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        restoreValuesFromPrefs();
    }

    @OnClick(R.id.btnTestConnection)
    void onTestClick(){
        if(validateValues()){
            // Asynchronously
            try {
                Ping.onAddress(ipAddress.getText().toString()).setTimeOutMillis(1000).setTimes(1).doPing(new Ping.PingListener() {
                    @Override
                    public void onResult(final PingResult pingResult) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!pingResult.isReachable()){
                                    Toast.makeText(SettingsActivity.this,"Host is not reachable",Toast.LENGTH_SHORT).show();
                                } else Toast.makeText(SettingsActivity.this,"Host is reachable",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onFinished() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btnTestConnection.setText("Try again");
                            }
                        });
                    }
                });
            } catch (Exception ex){

            }
        }


    }

    @OnClick(R.id.save)
    void onSaveClick(){
        if(validateValues())
            saveValues();
        setResult(RESULT_OK,null);
        finish();
    }


    private void restoreValuesFromPrefs(){
        SharedPreferences sharedPreferences = getSharedPreferences(Application.PREFS_NAME,MODE_PRIVATE);
        ipAddress.setText(sharedPreferences.getString(Application.PREF_IPADDR,""));
        ipPort.setText(""+sharedPreferences.getInt(Application.PREF_IPPORT,0));
    }

    private boolean validateValues(){
        if(ipAddress.getText().equals("")||!Patterns.IP_ADDRESS.matcher(ipAddress.getText().toString()).matches()){
            Toast.makeText(this,"Please, insert correct ip address",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(ipPort.getText().equals("")){
            try {
                Integer.parseInt(ipPort.getText().toString());
            } catch (Exception ex){
                Toast.makeText(this,"Please, insert correct port number (between 1 and 65536",Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }

    private void saveValues(){
        SharedPreferences sharedPreferences = getSharedPreferences(Application.PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Application.PREF_IPADDR,ipAddress.getText().toString());
        editor.putInt(Application.PREF_IPPORT,Integer.parseInt(ipPort.getText().toString()));
        editor.apply();
        Toast.makeText(this,"Successfully saved!",Toast.LENGTH_SHORT).show();
    }
}
