package com.example.saajidh.solutionseeker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class edit_profile extends AppCompatActivity {

    public DatabaseReference dbRef;
    DatabaseReference UTableRef;
    DatabaseReference UserRef;


    EditText username;
    EditText useremail;
    CheckBox y1;
    CheckBox y2;
    Spinner DCCN1Spinner;
    Spinner IPESpinner;
    Spinner MITSpinner;
    Spinner CFSpinner;
    Spinner ST1Spinner;
    Spinner DBMS1Spinner;
    Spinner ITASpinner;
    Spinner FCSSpinner;
    Button done;
    String sid;
    String name;
    String attendingyr;
    String email;
    String dccn1;
    String ipe;
    String mit;
    String cf;
    String st1;
    String dbms1;
    String ita;
    String fcs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        sid = getIntent().getExtras().getString("SID");

        dbRef = FirebaseDatabase.getInstance().getReference();
        UTableRef = dbRef.child("Users");
        UserRef = UTableRef.child(sid);


        name = getIntent().getExtras().getString("Name");
        attendingyr = getIntent().getExtras().getString("AttendingYear");
        email = getIntent().getExtras().getString("Email");
        dccn1 = getIntent().getExtras().getString("DCCN1");
        ipe = getIntent().getExtras().getString("IPE");
        mit = getIntent().getExtras().getString("MIT");
        cf = getIntent().getExtras().getString("CF");
        st1 = getIntent().getExtras().getString("ST1");
        dbms1 = getIntent().getExtras().getString("DBMS1");
        ita = getIntent().getExtras().getString("ITA");
        fcs = getIntent().getExtras().getString("FCS");

        username = (EditText) findViewById(R.id.uname);
        y1 = (CheckBox) findViewById(R.id.y1);
        y2 = (CheckBox) findViewById(R.id.y2);
        useremail = (EditText) findViewById(R.id.uemail);

        //if the 2nd year check box is selected, it will get unchecked.
        y1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                y2.setChecked(false);
                y1.setChecked(b);
            }
        });

        //if the 1st year check box is selected, it will get unchecked.
        y2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                y1.setChecked(false);
                y2.setChecked(b);
            }
        });

        DCCN1Spinner = (Spinner) findViewById(R.id.dccn1spinner);
        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(edit_profile.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.subjects));
        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DCCN1Spinner.setAdapter(myAdapter1);

        IPESpinner = (Spinner) findViewById(R.id.ipespinner);
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(edit_profile.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.subjects));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        IPESpinner.setAdapter(myAdapter2);

        MITSpinner = (Spinner) findViewById(R.id.mitspinner);
        ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>(edit_profile.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.subjects));
        myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MITSpinner.setAdapter(myAdapter3);

        CFSpinner = (Spinner) findViewById(R.id.cfspinner);
        ArrayAdapter<String> myAdapter4 = new ArrayAdapter<String>(edit_profile.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.subjects));
        myAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CFSpinner.setAdapter(myAdapter4);

        ST1Spinner = (Spinner) findViewById(R.id.st1spinner);
        ArrayAdapter<String> myAdapter5 = new ArrayAdapter<String>(edit_profile.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.subjects));
        myAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ST1Spinner.setAdapter(myAdapter5);

        DBMS1Spinner = (Spinner) findViewById(R.id.dbms1spinner);
        ArrayAdapter<String> myAdapter6 = new ArrayAdapter<String>(edit_profile.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.subjects));
        myAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DBMS1Spinner.setAdapter(myAdapter6);

        ITASpinner = (Spinner) findViewById(R.id.itaspinner);
        ArrayAdapter<String> myAdapter7 = new ArrayAdapter<String>(edit_profile.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.subjects));
        myAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ITASpinner.setAdapter(myAdapter7);

        FCSSpinner = (Spinner) findViewById(R.id.fcsspinner);
        ArrayAdapter<String> myAdapter8 = new ArrayAdapter<String>(edit_profile.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.subjects));
        myAdapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FCSSpinner.setAdapter(myAdapter8);

        done = (Button) findViewById(R.id.editdone);

        username.setText(name);
        useremail.setText(email);
        if(attendingyr.equals("1st Year"))
        {
            y1.setChecked(true);
            y2.setChecked(false);
        }
        if(attendingyr.equals("2nd Year"))
        {
            y1.setChecked(false);
            y2.setChecked(true);
        }

        DCCN1Spinner.setSelection(Integer.parseInt(dccn1)-1);
        IPESpinner.setSelection(Integer.parseInt(ipe)-1);
        MITSpinner.setSelection(Integer.parseInt(mit)-1);
        CFSpinner.setSelection(Integer.parseInt(cf)-1);
        ST1Spinner.setSelection(Integer.parseInt(st1)-1);
        DBMS1Spinner.setSelection(Integer.parseInt(dbms1)-1);
        ITASpinner.setSelection(Integer.parseInt(ita)-1);
        FCSSpinner.setSelection(Integer.parseInt(fcs)-1);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = username.getEditableText().toString();
                if(y1.isChecked())
                {
                    attendingyr = "1st Year";
                }
                if (y2.isChecked())
                {
                    attendingyr = "2nd Year";
                }
                email = useremail.getEditableText().toString();
                dccn1 = DCCN1Spinner.getSelectedItem().toString();
                ipe = IPESpinner.getSelectedItem().toString();
                mit = MITSpinner.getSelectedItem().toString();
                cf = CFSpinner.getSelectedItem().toString();
                st1 = ST1Spinner.getSelectedItem().toString();
                dbms1 = DBMS1Spinner.getSelectedItem().toString();
                ita = ITASpinner.getSelectedItem().toString();
                fcs = FCSSpinner.getSelectedItem().toString();

                UserRef.child("Name").setValue(name);
                UserRef.child("Attending Year").setValue(attendingyr);
                UserRef.child("Email Address").setValue(email);
                UserRef.child("DCCN-I").setValue(dccn1);
                UserRef.child("IPE").setValue(ipe);
                UserRef.child("MIT").setValue(mit);
                UserRef.child("CF").setValue(cf);
                UserRef.child("ST-I").setValue(st1);
                UserRef.child("DBMS-I").setValue(dbms1);
                UserRef.child("ITA").setValue(ita);
                UserRef.child("FCS").setValue(fcs);

                finish();
            }
        });

    }
}
