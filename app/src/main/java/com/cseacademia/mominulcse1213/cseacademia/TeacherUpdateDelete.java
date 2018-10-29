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

public class TeacherUpdateDelete extends AppCompatActivity {
    DatabaseReference databaseTeachers;
    List<TeacherConstructor> teachers;
    ListView listViewTeachers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_update_delete);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        //getting the reference of Posts node
        databaseTeachers = FirebaseDatabase.getInstance().getReference("Teachers");
        listViewTeachers = (ListView) findViewById(R.id.teacherupdellist);
        teachers = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listViewTeachers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TeacherConstructor teacherCon = teachers.get(i);
                showUpdateDeleteDialog(teacherCon.getTeacherId(), teacherCon.getTeacherName(),
                        teacherCon.getTeacherDesignation(),
                        teacherCon.getTeacherEmail(), teacherCon.getTeacherPhone());
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
        databaseTeachers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous teacher list
                teachers.clear();

                //iterating through all the nodes
                for (DataSnapshot teacherSnapshot : dataSnapshot.getChildren()) {
                    //getting teacher
                    TeacherConstructor teacher = teacherSnapshot.getValue(TeacherConstructor.class);
                    //adding teacher to the list
                    teachers.add(teacher);
                }

                //creating adapter
                TeacherList teacherAdapter = new TeacherList(TeacherUpdateDelete.this, teachers);
                //attaching adapter to the listview
                listViewTeachers.setAdapter(teacherAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private boolean updateTeacher(String id, String name, String designation, String email, String phone){
        DatabaseReference dR=FirebaseDatabase.getInstance().getReference("Teachers").child(id);
        TeacherConstructor teacherConstructor= new TeacherConstructor(id, name, designation, email, phone);
        dR.setValue(teacherConstructor);
        Toast.makeText(getApplicationContext(), "Teacher Info Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteTeacher(String id) {
        //getting the specified teacher reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Teachers").child(id);
        //removing teacher
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Teacher Info Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

    private void showUpdateDeleteDialog(final String teacherId, String name, String designation, String email, String phone) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.teacher_update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextname = (EditText) dialogView.findViewById(R.id.editteachername);
        final EditText editTextdesignation = (EditText) dialogView.findViewById(R.id.editteacherdesignation);
        final EditText editTextemail = (EditText) dialogView.findViewById(R.id.editteacheremail);
        final EditText editTextphone = (EditText) dialogView.findViewById(R.id.editteacherphone);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.teacherupdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.teacherdelete);

        editTextname.setText(name);
        editTextdesignation.setText(designation);
        editTextemail.setText(email);
        editTextphone.setText(phone);

        dialogBuilder.setTitle("Update Teacher Info Here...");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextname.getText().toString().trim();
                String designation = editTextdesignation.getText().toString();
                String email = editTextemail.getText().toString();
                String phone = editTextphone.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(designation) ){
                    updateTeacher(teacherId, name, designation, email, phone);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTeacher(teacherId);
                b.dismiss();

            }
        });
    }

}
