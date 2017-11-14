package com.example.saajidh.solutionseeker;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class change_password extends AppCompatActivity {

    String old_pwd;
    String new_pwd;
    FirebaseDatabase firebasedb;
    DatabaseReference myRef;
    DatabaseReference UTableRef;
    DatabaseReference UserRef;
    String UserID;
    ArrayList<String> userinfo;
    String userpwd;

    AlertDialog.Builder dlgalert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        dlgalert = new AlertDialog.Builder(change_password.this);

        UserID = getIntent().getExtras().getString("SID");

        firebasedb = FirebaseDatabase.getInstance();
        myRef = firebasedb.getReference();
        UTableRef = myRef.child("Users");
        UserRef = UTableRef.child(UserID);

        final EditText oldpwd = (EditText) findViewById(R.id.oldpassword);
        final EditText newpwd = (EditText) findViewById(R.id.newpassword);
        Button pwdsubmit = (Button) findViewById(R.id.pwdsubmit);
        userinfo = new ArrayList<>();

        pwdsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_pwd = oldpwd.getEditableText().toString();
                new_pwd = newpwd.getEditableText().toString();

                UserRef.addListenerForSingleValueEvent(
                        new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                    userinfo.add(String.valueOf(dsp.getValue()));
                                }

                                userpwd = userinfo.get(10);

                                if(!userpwd.equals(old_pwd))
                                {
                                    oldpwd.setError("Pasword doesn't match!");
                                    return;
                                }
                                else
                                {
                                    if(new_pwd.length()>4) {
                                        UserRef.child("Password").setValue(new_pwd);
                                        Intent i = new Intent(change_password.this, MainActivity.class);
                                        i.putExtra("stdid",UserID);
                                        startActivity(i);
                                        Toast.makeText(change_password.this, "Password has set successflly!", Toast.LENGTH_SHORT).show();

                                    }
                                    else if(new_pwd.equals(""))
                                    {
                                        newpwd.setError("Password cannot be empty!");
                                        return;
                                    }
                                    else
                                    {
                                        newpwd.setError(getString(R.string.error_invalid_password));
                                        return;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //handle databaseError
                            }
                        });
            }
        });
    }
}
