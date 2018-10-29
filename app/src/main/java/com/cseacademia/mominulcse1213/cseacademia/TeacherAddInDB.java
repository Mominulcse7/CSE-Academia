package com.cseacademia.mominulcse1213.cseacademia;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherAddInDB extends AppCompatActivity {
    private EditText tName, tDesig, tEmail, tPhone;
    private Button tSubmitBtn;
    DatabaseReference tDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_add_in_db);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tName=(EditText) findViewById(R.id.tname);
        tDesig=(EditText) findViewById(R.id.tdesignation);
        tEmail=(EditText) findViewById(R.id.temail);
        tPhone=(EditText) findViewById(R.id.tphone);
        tSubmitBtn=(Button) findViewById(R.id.tsubmit);

        tDB= FirebaseDatabase.getInstance().getReference("Teachers");

        tSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline())
                {
                    addTeacherInfo();
                }
                else
                {
                    noInternetConnection();
                }
            }
        });
    }

    private void addTeacherInfo() {

        String tNameData=tName.getText().toString();
        String tDesigData=tDesig.getText().toString();
        String tEmailData=tEmail.getText().toString();
        String tPhoneData=tPhone.getText().toString();

        if (!TextUtils.isEmpty(tNameData) && !TextUtils.isEmpty(tDesigData))
        {
            String id=tDB.push().getKey();
            TeacherConstructor teacher= new TeacherConstructor(id, tNameData, tDesigData,tEmailData,tPhoneData);
            tDB.child(id).setValue(teacher);
            Toast.makeText(this, "Teacher Info added", Toast.LENGTH_LONG).show();
            clearTeacher();

        }
        else
        {
            Toast.makeText(this, "Please Write Teacher Name and Designation", Toast.LENGTH_LONG).show();
        }
    }

    private void clearTeacher() {
        tName.setText("");
        tDesig.setText("");
        tEmail.setText("");
        tPhone.setText("");
    }

    private void noInternetConnection() {
        AlertDialog.Builder alert=new AlertDialog.Builder(TeacherAddInDB.this);
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

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
