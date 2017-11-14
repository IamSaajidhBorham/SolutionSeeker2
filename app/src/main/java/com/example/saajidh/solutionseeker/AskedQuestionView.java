package com.example.saajidh.solutionseeker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AskedQuestionView extends AppCompatActivity {

    public DatabaseReference dbRef;
    public DatabaseReference RTableRef;
    DatabaseReference QTableRef;
    ArrayList<String> ridset = new ArrayList<>();
    ArrayList<String> finalrids = new ArrayList<>();
    Integer maxrid = 0;
    String myreply;
    public Integer rid;
    String arr;
    String[] parts;
    String p1;
    ArrayList<String> questionlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asked_question_view);

        dbRef = FirebaseDatabase.getInstance().getReference();
        RTableRef = dbRef.child("Replies");
        QTableRef = dbRef.child("Question");

        questionlist = new ArrayList<>();

        TextView the_question = (TextView) findViewById(R.id.theQuestion);
        final EditText thereply = (EditText) findViewById(R.id.theReply);
        Button reply = (Button) findViewById(R.id.Reply);
        final String val = getIntent().getExtras().getString("thequestion");
        final String qid = getIntent().getExtras().getString("theqid");
        the_question.setText(val);

        dbRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override

                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.hasChild("Replies")) {
                            rid = 1;
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError

                    }
                });

        RTableRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override

                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            ridset.add(String.valueOf(dsp.getValue())); //add result into array list
                        }

                        for (int i =0; i<ridset.size(); i++)
                        {
                            arr = ridset.get(i);
                            parts = arr.split(",");
                            p1 = parts[0].split("=")[1];
                            finalrids.add(p1);
                        }

                        for(int i=0;i<finalrids.size();i++)
                        {
                            String sid = finalrids.get(i);
                            int val1 = Integer.parseInt(sid);
                            if(val1>maxrid)
                            {
                                maxrid = val1;
                            }
                        }
                        rid = maxrid + 1;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myreply = thereply.getText().toString();

                dbRef.child("Replies");
                DatabaseReference dbChild1 = dbRef.child("Replies");
                dbChild1.child(rid.toString());
                DatabaseReference dbchildchild1 = dbChild1.child(rid.toString());
                dbchildchild1.child("ReplyID").setValue(rid.toString());
                dbchildchild1.child("Reply").setValue(myreply);
                dbchildchild1.child("QID").setValue(qid);
                Toast.makeText(AskedQuestionView.this, "Done.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}
