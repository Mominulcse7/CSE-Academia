package com.cseacademia.mominulcse1213.cseacademia;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AboutDeveloper extends AppCompatActivity implements View.OnClickListener {
    private ImageView devShare, devFacebook, devEmail,devCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_developer);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        devShare=(ImageView) findViewById(R.id.devshare);
        devFacebook=(ImageView) findViewById(R.id.devfacebook);
        devEmail=(ImageView) findViewById(R.id.devemail);
        devCall=(ImageView) findViewById(R.id.devcall);
        devShare.setOnClickListener(this);
        devFacebook.setOnClickListener(this);
        devEmail.setOnClickListener(this);
        devCall.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.devshare)
        {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/type");
            String subject="CSE Dept. App";
            String body="This app is very usefull for CSE dept. Students.\ncom.example.ripon.cse_iu1 ";
            intent.putExtra(Intent.EXTRA_SUBJECT,subject);
            intent.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(intent, "share with"));
        }
        if(v.getId()==R.id.devcall)
        {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            String temp = "tel:" + "01718056487";
            intent.setData(Uri.parse(temp));
            startActivity(intent);
        }

        if(v.getId()==R.id.devfacebook)
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/Siddiqui.mominul/"));
            startActivity(browserIntent);
        }

        if(v.getId()==R.id.devemail)
        {
            Intent intent=new Intent(Intent.ACTION_SEND);
            String[] recipients={"mominulcse7@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT,"");
            intent.putExtra(Intent.EXTRA_TEXT,"");
            intent.setType("text/html");
            intent.setPackage("com.google.android.gm");
            startActivity(Intent.createChooser(intent, "Send mail"));
        }
    }
}
