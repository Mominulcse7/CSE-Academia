package com.cseacademia.mominulcse1213.cseacademia;

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

public class VerifiedUserUpdateDelete extends AppCompatActivity {
    DatabaseReference regUserDB;
    List<UserRegistrationConstractor>regUsers;
    ListView listViewRegUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified_user_update_delete);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        regUserDB= FirebaseDatabase.getInstance().getReference("VerifiedUsers");
        listViewRegUser=(ListView) findViewById(R.id.verifieduserlist);
        regUsers=new ArrayList<>();

        listViewRegUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                UserRegistrationConstractor userCon=regUsers.get(position);
                ShowUpdateDelDilalog(userCon.getId(), userCon.getUserName(), userCon.getUserRoll(), userCon.getUserSession(),
                        userCon.getUserPhone(), userCon.getUserEmail(),userCon.getMacAddress() );

                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        regUserDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                regUsers.clear();
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren())
                {
                    UserRegistrationConstractor user=userSnapshot.getValue(UserRegistrationConstractor.class);
                    regUsers.add(user);
                }

                UserRegistrationList userAdapter=new UserRegistrationList(VerifiedUserUpdateDelete.this, regUsers);
                listViewRegUser.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void ShowUpdateDelDilalog(final String id, String userName, String userRoll, String userSession,
                                      String userPhone, String userEmail, String macAddress) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.reg_user_update_delete_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.username);
        final EditText editTextRoll = (EditText) dialogView.findViewById(R.id.userroll);
        final EditText editTextSession = (EditText) dialogView.findViewById(R.id.usersession);
        final EditText editTextMobile = (EditText) dialogView.findViewById(R.id.userphone);
        final EditText editTextEmail = (EditText) dialogView.findViewById(R.id.useremail);
        final EditText editTextMac = (EditText) dialogView.findViewById(R.id.usermac);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.userupdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.userdelete);

        editTextName.setText(userName);
        editTextRoll.setText(userRoll);
        editTextSession.setText(userSession);
        editTextMobile.setText(userPhone);
        editTextEmail.setText(userEmail);
        editTextMac.setText(macAddress);

        dialogBuilder.setTitle("Update UserInfo Here...");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String roll = editTextRoll.getText().toString();
                String session = editTextSession.getText().toString();
                String mobile = editTextMobile.getText().toString();
                String email = editTextEmail.getText().toString();
                String mac = editTextMac.getText().toString();

                if (!TextUtils.isEmpty(session))
                {
                    updateRegUser(id, name, roll, session, mobile, email, mac);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRegUser(id);
                b.dismiss();

            }
        });
    }

    private boolean deleteRegUser(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("VerifiedUsers").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "RegUser Deleted", Toast.LENGTH_LONG).show();
        return true;
    }


    private boolean updateRegUser(String id, String name, String roll, String session, String mobile, String email, String mac) {

        DatabaseReference db=FirebaseDatabase.getInstance().getReference("VerifiedUsers").child(id);
        UserRegistrationConstractor userCon=new UserRegistrationConstractor(id,name, roll, session,mobile,email, mac);
        db.setValue(userCon);
        Toast.makeText(getApplicationContext(), "User Info Updated", Toast.LENGTH_LONG).show();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
