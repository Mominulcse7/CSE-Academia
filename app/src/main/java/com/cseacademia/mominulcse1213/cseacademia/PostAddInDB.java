package com.cseacademia.mominulcse1213.cseacademia;

        import android.app.DatePickerDialog;
        import android.content.Intent;
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

        import java.text.SimpleDateFormat;
        import java.util.Calendar;

public class PostAddInDB extends AppCompatActivity {
    EditText postOutTime, postDescription, postSession;
    TextView postIngTime;
    Button postSubmitBtn;
    DatabaseReference databasePosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add_in_db);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy  -  hh:mm a");
        String formattedDate = df.format(c.getTime());

        postSession=(EditText) findViewById(R.id.postspinnersession);
        postIngTime=(TextView) findViewById(R.id.postingtime);
        postOutTime=(EditText) findViewById(R.id.postouttime);
        postDescription=(EditText) findViewById(R.id.postdes);
        postSubmitBtn=(Button) findViewById(R.id.postsubmit);

        postIngTime.setText(formattedDate);

        //getting the reference of Posts node
        databasePosts = FirebaseDatabase.getInstance().getReference("Posts");

        postSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPost();
            }
        });

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
    private void addPost() {
        //getting the values to save
        String session=postSession.getText().toString();
        String postin = postIngTime.getText().toString();
        String postend=postOutTime.getText().toString();
        String postdes = postDescription.getText().toString();

        //checking if the value is provided
        if (!TextUtils.isEmpty(postdes) && !TextUtils.isEmpty(session) ) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Post
            String id = databasePosts.push().getKey();

            //creating an Post Object
            PostConstructor post = new PostConstructor(id, session, postin, postend, postdes);

            //Saving the Post
            databasePosts.child(id).setValue(post);
            Toast.makeText(this, "Post added", Toast.LENGTH_LONG).show();

            clearPost();

            Intent intent=new Intent(PostAddInDB.this, AdminActivity.class);
            startActivity(intent);

        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please Write Post Title/Session and Post Description", Toast.LENGTH_LONG).show();
        }
    }

    private void clearPost() {
        postSession.setText("");
        postIngTime.setText("");
        postOutTime.setText("");
        postDescription.setText("");


    }

}
