package com.cseacademia.mominulcse1213.cseacademia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class AdminLogin extends AppCompatActivity {
    private EditText adminemaiL, adminpassworD;
    private Button adminloginbuttoN;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabaseUsers ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        mAuth=FirebaseAuth.getInstance();
        mProgress=new ProgressDialog(this);

        adminemaiL=(EditText) findViewById(R.id.usernameadmin);
        adminpassworD=(EditText) findViewById(R.id.passwordadmin);
        adminloginbuttoN=(Button) findViewById(R.id.userloginbtn);

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null)
                {
                    Intent mainintent=new Intent(AdminLogin.this,AdminActivity.class);
                    startActivity(mainintent);
                }
            }
        };

        adminloginbuttoN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void startSignIn() {
        String emaiL=adminemaiL.getText().toString();
        String passworD=adminpassworD.getText().toString();


        if (TextUtils.isEmpty(emaiL) || TextUtils.isEmpty(passworD) ) {
            Toast.makeText(AdminLogin.this, "Write Email & Password", Toast.LENGTH_LONG).show();

        }
        else
        {
            mProgress.setMessage("Checking Login...");
            mProgress.show();
            mAuth.signInWithEmailAndPassword(emaiL, passworD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        mProgress.dismiss();
                        Toast.makeText(AdminLogin.this, "UserName Or Password is Wrong", Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        Toast.makeText(AdminLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent loginIntent= new Intent(AdminLogin.this, AdminActivity.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);
                    }
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            Intent loginIntent= new Intent(AdminLogin.this, MainActivity.class);
            startActivity(loginIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
