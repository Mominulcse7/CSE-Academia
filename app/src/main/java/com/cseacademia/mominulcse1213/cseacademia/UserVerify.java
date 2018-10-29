package com.cseacademia.mominulcse1213.cseacademia;

import android.app.ProgressDialog;
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

    public class UserVerify extends AppCompatActivity {
                DatabaseReference databaseUserReg;
                List<UserRegistrationConstractor> regusers;
                ListView listViewRegUsers;
        private ProgressDialog mProgress;
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_user_verify);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    mProgress=new ProgressDialog(this);

                    databaseUserReg = FirebaseDatabase.getInstance().getReference("UserPrimaryReg");
                    listViewRegUsers = (ListView) findViewById(R.id.userreglist);
                    regusers = new ArrayList<>();

                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);

                    listViewRegUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                            UserRegistrationConstractor reguser = regusers.get(i);
                            showAllowRejectDialog(reguser.getId(), reguser.getUserName(), reguser.getUserRoll(),
                                    reguser.getUserSession(), reguser.getUserPhone(),reguser.getUserEmail(), reguser.getMacAddress() );
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
                    databaseUserReg.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            regusers.clear();

                            for (DataSnapshot reguserSnapshot : dataSnapshot.getChildren()) {
                                UserRegistrationConstractor reguseR = reguserSnapshot.getValue(UserRegistrationConstractor.class);
                                regusers.add(reguseR);
                            }

                            UserRegistrationList regUserAdapter = new UserRegistrationList(UserVerify.this, regusers);
                            listViewRegUsers.setAdapter(regUserAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                private boolean userAllow(String id, String userName, String userRoll, String userSession, String userPhone, String userEmail, String macAddress ){
                    DatabaseReference dR=FirebaseDatabase.getInstance().getReference("VerifiedUsers").child(id);
                    UserRegistrationConstractor userConstructor= new UserRegistrationConstractor(id, userName, userRoll,
                            userSession, userPhone,userEmail, macAddress );

                    dR.setValue(userConstructor);
                    return true;
                }

                private boolean userReject(String id) {
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("UserPrimaryReg").child(id);
                    dR.removeValue();

                    return true;
                }

                private void showAllowRejectDialog(final String id, String userName,
                                                    String userRoll, String userSession, String userPhone,
                                                    String userEmail, String macAddress ) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.user_reg_allow_dialog, null);
                    dialogBuilder.setView(dialogView);

                    final EditText editTextName = (EditText) dialogView.findViewById(R.id.username);
                    final EditText editTextRoll = (EditText) dialogView.findViewById(R.id.userroll);
                    final EditText editTextSession = (EditText) dialogView.findViewById(R.id.usersession);
                    final EditText editTextPhone = (EditText) dialogView.findViewById(R.id.userphone);
                    final EditText editTextEmail = (EditText) dialogView.findViewById(R.id.useremail);
                    final EditText editTextMacAddress = (EditText) dialogView.findViewById(R.id.usermac);

                    final Button buttonAllow = (Button) dialogView.findViewById(R.id.userallow);
                    final Button buttonReject = (Button) dialogView.findViewById(R.id.userreject);

                    editTextName.setText(userName);
                    editTextRoll.setText(userRoll);
                    editTextSession.setText(userSession);
                    editTextPhone.setText(userPhone);
                    editTextEmail.setText(userEmail);
                    editTextMacAddress.setText(macAddress);

                    dialogBuilder.setTitle("Allow User Here...");
                    final AlertDialog b = dialogBuilder.create();
                    b.show();

                    buttonAllow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String uname = editTextName.getText().toString().trim();
                            String uroll = editTextRoll.getText().toString();
                            String usession = editTextSession.getText().toString();
                            String uphone = editTextPhone.getText().toString();
                            String uemail = editTextEmail.getText().toString();
                            String umac = editTextMacAddress.getText().toString();

                            if (!TextUtils.isEmpty(umac)) {
                                userAllow(id, uname, uroll, usession, uphone,uemail, umac);
                                userReject(id);
                                Toast.makeText(getApplicationContext(), "User Added", Toast.LENGTH_LONG).show();
                                b.dismiss();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "To Verify Mac Address required...", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    buttonReject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            userReject(id);
                            Toast.makeText(getApplicationContext(), "User Rejected", Toast.LENGTH_LONG).show();
                            b.dismiss();

                        }
                    });
                }

            }
