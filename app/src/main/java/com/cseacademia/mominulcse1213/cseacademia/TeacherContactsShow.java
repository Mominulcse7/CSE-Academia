package com.cseacademia.mominulcse1213.cseacademia;

    import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.MenuItem;
        import android.widget.Button;
        import android.widget.ListView;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;

public class TeacherContactsShow extends AppCompatActivity {
    DatabaseReference databaseTeachers;
    List<TeacherConstructor> teachers;
    ListView listViewTeachers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_contacts_show);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        databaseTeachers = FirebaseDatabase.getInstance().getReference("Teachers");

        listViewTeachers = (ListView) findViewById(R.id.teacherlist);

        teachers = new ArrayList<>();

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
        databaseTeachers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                teachers.clear();
                for (DataSnapshot teacherSnapshot : dataSnapshot.getChildren()) {
                    TeacherConstructor teacher = teacherSnapshot.getValue(TeacherConstructor.class);
                    teachers.add(teacher);
                }
                TeacherList teacherAdapter = new TeacherList(TeacherContactsShow.this, teachers);
                listViewTeachers.setAdapter(teacherAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
