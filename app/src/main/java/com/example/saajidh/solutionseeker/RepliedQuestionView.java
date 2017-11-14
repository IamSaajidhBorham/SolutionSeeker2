package com.example.saajidh.solutionseeker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class RepliedQuestionView extends AppCompatActivity {

    public DatabaseReference dbRef;
    public DatabaseReference RTableRef;
    public DatabaseReference QTableRef;
    ArrayList<String> replytable;
    String arr;
    String[] parts;
    String p1;
    String p2;
    String answer;
    int rate;
    String itemclick1;
    String selectedRID;
    int rated;
    String p3;
    String p4;
    String word;
    ArrayList<String> arraylist;
    ArrayList<String> finalarraylist;

    ArrayList<String> replies;
    ArrayAdapter myadapter;
    ListView myreplylist;
    Button editmyquestion;
    AlertDialog.Builder popDialog;
    RatingBar rating;

    public void ShowDialog()
    {
        popDialog = new AlertDialog.Builder(this);
        rating = new RatingBar(this, null, android.R.attr.ratingBarStyleIndicator);
        rating.setNumStars(9);
        rating.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
        rating.setMax(0);
        rating.setStepSize((float) 1.0);
        rating.setIsIndicator(false);
        rating.setRating(Float.parseFloat(String.valueOf(rated)));

        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("Rate!");
        popDialog.setView(rating);

        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        rate = Math.round(rating.getRating());
                        RTableRef.child(selectedRID).child("Rating").setValue(String.valueOf(rate));
                        Toast.makeText(RepliedQuestionView.this, "Successfully rated!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                });

        // Button Cancel
        popDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        popDialog.create();
        popDialog.show();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replied_question_view);

        dbRef = FirebaseDatabase.getInstance().getReference();
        RTableRef = dbRef.child("Replies");
        QTableRef = dbRef.child("Question");

        final String val = getIntent().getExtras().getString("thequestion");
        final String qid = getIntent().getExtras().getString("theqid");
        String uid = getIntent().getExtras().getString("theuserid");

        TextView the_question = (TextView) findViewById(R.id.textView20);
        the_question.setText(val);

        myreplylist = (ListView) findViewById(R.id.replylist);
        editmyquestion = (Button) findViewById(R.id.btnEditQuestion);

        RTableRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override

                    public void onDataChange(DataSnapshot dataSnapshot) {
                        replytable = new ArrayList<>();
                        replies = new ArrayList<>();
                        arraylist = new ArrayList<>();
                        finalarraylist = new ArrayList<>();

                        //Get map of users in datasnapshot
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            replytable.add(String.valueOf(dsp.getValue())); //add result into array list
                        }

                        for (int i =0; i<replytable.size(); i++)
                        {
                            arr = replytable.get(i);
                            parts = arr.split(",");
                            p1 = parts[2].split("=")[1];
                            if(p1.equals(qid))
                            {
                                p2 = parts[3].split("=")[1];
                                answer = p2.split("\\}")[0];
                                p3 = parts[0].split("=")[1];
                                p4 = parts[1].split("=")[1];
                                word = p3+":"+p4;
                                arraylist.add(word);
                                replies.add(answer);
                            }
                        }

                        //bubble sort algorithm
                        for (int i = (arraylist.size() - 1); i >= 0; i--)
                        {
                            for (int j = 1; j <= i; j++)
                            {
                                String item1 = arraylist.get(j-1);
                                String item2 = arraylist.get(j);
                                int val1 = Integer.parseInt(item1.split(":")[1]);
                                int val2 = Integer.parseInt(item2.split(":")[1]);
                                if (val1 < val2)
                                {
                                    String temp = item1;
                                    arraylist.set(j-1, arraylist.get(j));
                                    arraylist.set(j, temp);
                                }
                            }
                        }

                        for(int k=0;k<arraylist.size();k++)
                        {
                            String id = arraylist.get(k).split(":")[0];
                            for (int i =0; i<replytable.size(); i++)
                            {
                                arr = replytable.get(i);
                                parts = arr.split(",");
                                p1 = parts[0].split("=")[1];
                                p2 = parts[3].split("=")[1];
                                p3 = p2.split("\\}")[0];
                                if(id.equals(p1))
                                {
                                    finalarraylist.add(p3);
                                }
                            }
                        }

                        myadapter = new ArrayAdapter (RepliedQuestionView.this, android.R.layout.simple_list_item_1, finalarraylist);
                        myreplylist.setAdapter(myadapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        editmyquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RepliedQuestionView.this, editMyQuestion.class);
                i.putExtra("thequestion", val);
                i.putExtra("theqid", qid);
                startActivity(i);
            }
        });

        myreplylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemclick1 = (String) parent.getItemAtPosition(position);
                RTableRef.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {
                                replies = new ArrayList<>();

                                //Get map of users in datasnapshot
                                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                    replytable.add(String.valueOf(dsp.getValue())); //add result into array list
                                }

                                for (int i =0; i<replytable.size(); i++)
                                {
                                    arr = replytable.get(i);
                                    parts = arr.split(",");
                                    p1 = parts[2].split("=")[1];
                                    if(p1.equals(qid))
                                    {
                                        p2 = parts[3].split("=")[1];
                                        answer = p2.split("\\}")[0];
                                        if(answer.equals(itemclick1))
                                        {
                                            selectedRID = parts[0].split("=")[1];
                                            rated = Integer.parseInt(parts[1].split("=")[1]);
                                            ShowDialog();
                                        }
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
