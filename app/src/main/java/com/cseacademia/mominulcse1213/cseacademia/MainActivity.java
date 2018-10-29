package com.cseacademia.mominulcse1213.cseacademia;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference databasePosts;
    List<PostConstructor> posts;
    ListView listViewPosts;
    DatabaseReference databaseVerifiedUser;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        mAuth=FirebaseAuth.getInstance();
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawerlayoutiD);
        mToggle= new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        databasePosts = FirebaseDatabase.getInstance().getReference("Posts");
        databaseVerifiedUser = FirebaseDatabase.getInstance().getReference("VerifiedUsers");

        listViewPosts = (ListView) findViewById(R.id.list);

        posts = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigation=(NavigationView) findViewById(R.id.navigationviewiD);
        navigation.setNavigationItemSelectedListener(this);
    }

    //Right navigation menu notification btn implementation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.notificationbtn_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onStart() {
        super.onStart();

        databaseVerifiedUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot reguserSnapshot : dataSnapshot.getChildren()) {

                    UserRegistrationConstractor reguseR = reguserSnapshot.getValue(UserRegistrationConstractor.class);

                    if (!reguseR.getMacAddress().isEmpty()){
                        final String uSessioN=reguseR.getUserSession().toString();
                        final String uSessioN2="20"+uSessioN;

                        databasePosts.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                posts.clear();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    PostConstructor post = postSnapshot.getValue(PostConstructor.class);

                                    if(post.getPostSession().toString().equals("Common")|| post.getPostSession().toString().equals("common"))

                                        posts.add(post);
                                }
                                PostList postAdapter = new PostList(MainActivity.this, posts);
                                listViewPosts.setAdapter(postAdapter);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    else {
                        Toast.makeText(MainActivity.this,"No new Notice/ You Don't Have access",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        if(item.getItemId()==R.id.notificationbtnid)
        {
            if(isOnline()){
                Intent intent=new Intent(MainActivity.this,PostShow.class);
                startActivity(intent);
            }else{
                noInternetConnection();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.adminlogin) {
            if(isOnline()){
                Intent loginIntent= new Intent(MainActivity.this, AdminLogin.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
            }
            else{
                noInternetConnection();
            }
        }
        if(item.getItemId()==R.id.usersignup) {
            if(isOnline()){
                Intent loginIntent= new Intent(MainActivity.this, UserRegistration.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
            }
            else{
                noInternetConnection();
            }
        }

        if(item.getItemId()==R.id.cseweb)
        {
            if(isOnline()){
                Intent intent=new Intent(MainActivity.this,CSE_Web.class);
                startActivity(intent);
            }else{
                noInternetConnection();
            }
        }

        if(item.getItemId()==R.id.teachercontacts)
        {
            Intent intent=new Intent(MainActivity.this,TeacherContactsShow.class);
            startActivity(intent);
        }

        if(item.getItemId()==R.id.studentcontacts)
        {
            Intent intent=new Intent(MainActivity.this,CR_Contacts.class);
            startActivity(intent);
        }

        if(item.getItemId()==R.id.about_dept)
        {
            Intent intent=new Intent(MainActivity.this,AboutDept.class);
            startActivity(intent);
        }

        if(item.getItemId()==R.id.about_developers)
        {
            Intent intent=new Intent(MainActivity.this,AboutDeveloper.class);
            startActivity(intent);
        }

        if(item.getItemId()==R.id.log_out)
        {
            mAuth.signOut();
            Toast.makeText(MainActivity.this, "SignOut ...", Toast.LENGTH_SHORT).show();

        }

        return true;
    }

    //For No Internet Alert
    private void noInternetConnection() {
        AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
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