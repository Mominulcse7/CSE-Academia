package com.cseacademia.mominulcse1213.cseacademia;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegistration extends AppCompatActivity implements View.OnClickListener {
    private EditText uName, uRoll, uSession, uPhone, uEmail;
    private Button signUp;
    DatabaseReference databaseUserReg;
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


        uName=(EditText) findViewById(R.id.username );
        uRoll=(EditText) findViewById(R.id.userroll );
        uSession=(EditText) findViewById(R.id.usersession );
        uPhone=(EditText) findViewById(R.id.userphone );
        uEmail=(EditText) findViewById(R.id.useremail );
        signUp=(Button) findViewById(R.id.usersignupbtn);
        databaseUserReg = FirebaseDatabase.getInstance().getReference("UserPrimaryReg");

        signUp.setOnClickListener(this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View v) {

        if (isOnline())
        {
            addUserPrimary();
        }
        else
        {
            noInternetConnection();
        }

    }

    private void addUserPrimary() {
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        String macAddress = info.getMacAddress();

        String userName= uName.getText().toString();
        String userRoll= uRoll.getText().toString();
        String userSession= uSession.getText().toString();
        String userPhone= uPhone.getText().toString();
        String userEmail= uEmail.getText().toString();

        if (!TextUtils.isEmpty(userName) &&!TextUtils.isEmpty(userRoll)
                &&!TextUtils.isEmpty(userSession) &&!TextUtils.isEmpty(userPhone) )
        {
            String id=databaseUserReg.push().getKey();
            UserRegistrationConstractor userreg=new UserRegistrationConstractor(id, userName,
                    userRoll, userSession, userPhone, userEmail, macAddress );
            databaseUserReg.child(id).setValue(userreg);
            Toast.makeText(this, "User info added. Wait for Admin Response...", Toast.LENGTH_LONG).show();
            clearUserReg();

            Intent intent=new Intent(UserRegistration.this, MainActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(UserRegistration.this, "Enter Name, Roll, Session, Phone... ",Toast.LENGTH_SHORT).show();
        }
    }

    private void clearUserReg() {
        uName.setText("");
        uRoll.setText("");
        uSession.setText("");
        uPhone.setText("");
        uEmail.setText("");
    }


    private void noInternetConnection() {
        AlertDialog.Builder alert=new AlertDialog.Builder(UserRegistration.this);
        alert.setMessage("Please Turn your Data Connection");
        alert.setCancelable(false);
        alert.setIcon(R.drawable.alert);
        alert.setTitle("Hello User");
        alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog=alert.create();
        alertDialog.show();
    }

    //Check net is connected or Not

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }   }
}
