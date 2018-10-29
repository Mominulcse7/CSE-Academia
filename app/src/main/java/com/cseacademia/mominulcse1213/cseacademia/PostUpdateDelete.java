package com.cseacademia.mominulcse1213.cseacademia;

    import android.content.Intent;
    import android.support.v7.app.AlertDialog;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.view.LayoutInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ListView;
    import android.widget.Toast;

    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    import java.util.ArrayList;
    import java.util.List;

public class PostUpdateDelete extends AppCompatActivity {
    DatabaseReference databasePosts;
    List<PostConstructor> posts;
    ListView listViewPosts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_update_delete);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        //getting the reference of Posts node
        databasePosts = FirebaseDatabase.getInstance().getReference("Posts");
        listViewPosts = (ListView) findViewById(R.id.list);
        posts = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listViewPosts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                PostConstructor postCon = posts.get(i);
                showUpdateDeleteDialog(postCon.getPostId(), postCon.getPostSession(), postCon.getPostIngTime(),
                        postCon.getPostTimeOut(), postCon.getPostDes());
                return true;
            }
        });
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
        //attaching value event listener
        databasePosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
               posts.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting post
                    PostConstructor post = postSnapshot.getValue(PostConstructor.class);
                    //adding post to the list
                    posts.add(post);
                }

                //creating adapter
                PostList postAdapter = new PostList(PostUpdateDelete.this, posts);
                //attaching adapter to the listview
                listViewPosts.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private boolean updatePost(String id, String session, String intime, String outtime, String des){
        DatabaseReference dR=FirebaseDatabase.getInstance().getReference("Posts").child(id);
        PostConstructor postConstructor= new PostConstructor(id, session, intime, outtime, des);
        dR.setValue(postConstructor);
        Toast.makeText(getApplicationContext(), "Post Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deletePost(String id) {
        //getting the specified Post reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Posts").child(id);
        //removing Post
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Post Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

    private void showUpdateDeleteDialog(final String postId, String postSession,
                                        String postIngTime, String postTimeOut,
                                        String postDes) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.post_update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextSession = (EditText) dialogView.findViewById(R.id.postspinnersession);
        final EditText editTextPostIngTime = (EditText) dialogView.findViewById(R.id.postingtime);
        final EditText editTextPostOutTime = (EditText) dialogView.findViewById(R.id.postouttime);
        final EditText editTextPostDes = (EditText) dialogView.findViewById(R.id.postdes);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.postupdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.postdelete);

        editTextSession.setText(postSession);
        editTextPostIngTime.setText(postIngTime);
        editTextPostOutTime.setText(postTimeOut);
        editTextPostDes.setText(postDes);

        dialogBuilder.setTitle("Update Post Here...");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String session = editTextSession.getText().toString().trim();
                String intime = editTextPostIngTime.getText().toString();
                String outtime = editTextPostOutTime.getText().toString();
                String postdes = editTextPostDes.getText().toString();

                if (!TextUtils.isEmpty(session)) {
                    updatePost(postId, session, intime, outtime,postdes);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePost(postId);
                b.dismiss();

            }
        });
    }

}
