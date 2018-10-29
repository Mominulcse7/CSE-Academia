package com.cseacademia.mominulcse1213.cseacademia;

    import android.content.Intent;
    import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.MenuItem;
        import android.widget.ListView;
    import android.widget.Toast;

    import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;

public class PostShow extends AppCompatActivity {
    DatabaseReference databasePosts;
    DatabaseReference databaseVerifiedUser;

    List<PostConstructor> posts;
    ListView listViewPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_show);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        //getting the reference of Posts node
        databasePosts = FirebaseDatabase.getInstance().getReference("Posts");
        databaseVerifiedUser = FirebaseDatabase.getInstance().getReference("VerifiedUsers");

        listViewPosts = (ListView) findViewById(R.id.list);

        posts = new ArrayList<>();

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

                                    if(post.getPostSession().toString().equals(uSessioN)|| post.getPostSession().toString().equals(uSessioN2))

                                    posts.add(post);
                                }
                                PostList postAdapter = new PostList(PostShow.this, posts);
                                listViewPosts.setAdapter(postAdapter);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    else {
                        Toast.makeText(PostShow.this,"No new Notice/ You Don't Have access",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


}
