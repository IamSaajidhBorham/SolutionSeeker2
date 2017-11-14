package com.example.saajidh.solutionseeker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ViewUser extends AppCompatActivity {

    String sid;
    String userid;

    DatabaseReference dbRef;
    DatabaseReference UTableRef;
    DatabaseReference URef;

    String Student = "";
    String dccn1 = "";
    String ipe = "";
    String mit = "";
    String cf = "";
    String st1 = "";
    String dbms1 = "";
    String ita = "";
    String fcs = "";

    TextView TheStudent;
    TextView DCCN1;
    TextView IPE;
    TextView MIT;
    TextView CF;
    TextView ST1;
    TextView DBMS1;
    TextView ITA;
    TextView FCS;
    Button Message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        sid = getIntent().getExtras().getString("SID");
        userid = getIntent().getExtras().getString("UserID");

        dbRef = FirebaseDatabase.getInstance().getReference();
        UTableRef = dbRef.child("Users");
        URef = UTableRef.child(sid);

        TheStudent = (TextView) findViewById(R.id.txtviewStudentName);
        DCCN1 = (TextView) findViewById(R.id.txtviewDCCN1);
        IPE = (TextView) findViewById(R.id.txtviewIPE);
        MIT = (TextView) findViewById(R.id.txtviewMIT);
        CF = (TextView) findViewById(R.id.txtviewCF);
        ST1 = (TextView) findViewById(R.id.txtviewST1);
        DBMS1 = (TextView) findViewById(R.id.txtviewDBMS1);
        ITA = (TextView) findViewById(R.id.txtviewITA);
        FCS = (TextView) findViewById(R.id.txtviewFCS);
        Message = (Button) findViewById(R.id.btnMessage);

        URef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map test1 = (Map) dataSnapshot.getValue();
                Student = (String) test1.get("Name");
                sid = (String) test1.get("SID");

                dccn1 = (String) test1.get("DCCN-I");
                ipe = (String) test1.get("IPE");
                mit = (String) test1.get("MIT");
                cf = (String) test1.get("CF");
                st1 = (String) test1.get("ST-I");
                dbms1 = (String) test1.get("DBMS-I");
                ita = (String) test1.get("ITA");
                fcs = (String) test1.get("FCS");

                TheStudent.setText(Student);
                DCCN1.setText(dccn1);
                IPE.setText(ipe);
                MIT.setText(mit);
                CF.setText(cf);
                ST1.setText(st1);
                DBMS1.setText(dbms1);
                ITA.setText(ita);
                FCS.setText(fcs);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewUser.this, addPost.class);
                i.putExtra("viewedid",sid);
                i.putExtra("stdid",userid);
                addPost.fromViewUser=true;
                addPost.fromMainActivity=false;
                startActivity(i);
            }
        });

    }
}
