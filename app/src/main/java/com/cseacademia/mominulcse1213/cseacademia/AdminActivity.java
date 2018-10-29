package com.cseacademia.mominulcse1213.cseacademia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addNewPost, addNewTeacher, addCRInfo, editPost, editTeacher, editCRInfo, editUserInfo, editRegUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        addNewPost=(Button) findViewById(R.id.adminaddpost);
        addNewTeacher=(Button) findViewById(R.id.adminaddteacher);
        addCRInfo=(Button) findViewById(R.id.adminaddcr);
        editPost=(Button) findViewById(R.id.admineditpost);
        editTeacher=(Button) findViewById(R.id.admineditteacher);
        editCRInfo=(Button) findViewById(R.id.admineditcr);
        editUserInfo=(Button) findViewById(R.id.adminverifyuser);
        editRegUserInfo=(Button) findViewById(R.id.adminupdelreguser);

        addNewPost.setOnClickListener(this);
        addNewTeacher.setOnClickListener(this);
        addCRInfo.setOnClickListener(this);
        editPost.setOnClickListener(this);
        editTeacher.setOnClickListener(this);
        editCRInfo.setOnClickListener(this);
        editUserInfo.setOnClickListener(this);
        editRegUserInfo.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.adminmainmenuicon,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent loginIntent= new Intent(AdminActivity.this, MainActivity.class);
            startActivity(loginIntent);
        }
        if (item.getItemId() == R.id.menusettingsicon) {
            Intent loginIntent= new Intent(AdminActivity.this, MainActivity.class);
            startActivity(loginIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.adminaddpost)
        {
                Intent intent=new Intent(AdminActivity.this,PostAddInDB.class);
                startActivity(intent);
        }
        if(v.getId()==R.id.adminaddteacher)
        {
            Intent intent=new Intent(AdminActivity.this,TeacherAddInDB.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.adminaddcr)
        {
            Toast.makeText(AdminActivity.this, "Under Development", Toast.LENGTH_SHORT).show();
        }
        if(v.getId()==R.id.admineditpost)
        {
            Intent intent=new Intent(AdminActivity.this,PostUpdateDelete.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.admineditteacher)
        {
            Intent intent=new Intent(AdminActivity.this,TeacherUpdateDelete.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.admineditcr)
        {
            Toast.makeText(AdminActivity.this, "Under Development", Toast.LENGTH_SHORT).show();
        }

        if(v.getId()==R.id.adminverifyuser)
        {
            Intent intent=new Intent(AdminActivity.this,UserVerify.class);
            startActivity(intent);
        }

        if(v.getId()==R.id.adminupdelreguser)
        {
            Intent intent=new Intent(AdminActivity.this,VerifiedUserUpdateDelete.class);
            startActivity(intent);
        }

    }
}
