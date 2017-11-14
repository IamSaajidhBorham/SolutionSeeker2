package com.example.saajidh.solutionseeker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class addPost extends AppCompatActivity {

    private static final String TAG = "Add Post";

    public DatabaseReference dbRef;

    Button btn_postSubmit;
    EditText theQuestion;
    Integer count = 0;
    String question;
    String category;
    public DatabaseReference QTableRef;
    public DatabaseReference UTableRef;
    public DatabaseReference IPETableRef;
    public DatabaseReference CFTableRef;
    public DatabaseReference DCCN1TableRef;
    public DatabaseReference DBMS1TableRef;
    public DatabaseReference FCSTableRef;
    public DatabaseReference ITATableRef;
    public DatabaseReference ST1TableRef;
    ArrayList<String> sid;
    ArrayList<String> skill;
    ArrayList<String> eligiblelist;
    String key;
    String skillval;
    String sidval;
    int val;
    public static String id;
    String list ="";
    ArrayList<String> qids;
    Integer maxqid;
    ArrayList<String> theList;
    Integer subjectCount = 0;
    ArrayList<String> ipelist;
    ArrayList<String> cflist;
    ArrayList<String> dccn1list;
    ArrayList<String> dbms1list;
    ArrayList<String> fcslist;
    ArrayList<String> italist;
    ArrayList<String> st1list;
    ArrayList<Integer> subjectcounts;
    Integer maxsubjectcount;
    Integer maxsubjectkey;
    String theSubject;
    Boolean clear = true;
    public static boolean fromViewUser=false;
    public static boolean fromMainActivity=false;
    String sendingto;


    public String CategoryIdentify()
    {
        String q = theQuestion.getEditableText().toString();
        subjectcounts = new ArrayList<Integer>();
        maxsubjectcount = 0;
        maxsubjectkey = -1;
        clear = true;

        Integer count1 = SubjectCount(ipelist, q);
        subjectcounts.add(count1);

        Integer count2 = SubjectCount(cflist, q);
        if(count2>0)
        {
            if(count2==count1)
            {
                clear = false;
            }
        }
        subjectcounts.add(count2);

        Integer count3 = SubjectCount(dccn1list, q);
        if(count3>0)
        {
            if(count3==count1 || count3==count2)
            {
                clear = false;
            }
        }
        subjectcounts.add(count3);

        Integer count4 = SubjectCount(dbms1list, q);
        if(count4>0)
        {
            if(count4==count1 || count4==count2 || count4==count3)
            {
                clear = false;
            }
        }
        subjectcounts.add(count4);

        Integer count5 = SubjectCount(fcslist, q);
        if(count5>0)
        {
            if(count5==count1 || count5==count2 || count5==count3 || count5==count4)
            {
                clear = false;
            }
        }
        subjectcounts.add(count5);

        Integer count6 = SubjectCount(italist, q);
        if(count6>0)
        {
            if(count6==count1 || count6==count2 || count6==count3 || count6==count4 || count6==count5)
            {
                clear = false;
            }
        }
        subjectcounts.add(count6);

        Integer count7 = SubjectCount(st1list, q);
        if(count7>0)
        {
            if(count7==count1 || count7==count2 || count7==count3 || count7==count4 || count7==count5 || count7==count6)
            {
                clear = false;
            }
        }
        subjectcounts.add(count7);

        if(clear==false)
        {
            return null;
        }

        for (int g=0; g<subjectcounts.size();g++)
        {
            Integer value = subjectcounts.get(g);
            if(value>maxsubjectcount) {
                maxsubjectcount = value;
                maxsubjectkey = g;
            }
        }

        if(maxsubjectkey==0)
        {
            theSubject = "IPE";
        }
        else if(maxsubjectkey==1)
        {
            theSubject = "CF";
        }
        else if(maxsubjectkey==2)
        {
            theSubject = "DCCN-I";
        }
        else if(maxsubjectkey==3)
        {
            theSubject = "DBMS-I";
        }
        else if(maxsubjectkey==4)
        {
            theSubject = "FCS";
        }
        else if(maxsubjectkey==5)
        {
            theSubject = "ITA";
        }
        else if(maxsubjectkey==6)
        {
            theSubject = "ST-I";
        }
        else
        {
            theSubject = "";
        }

        if(maxsubjectcount==0)
        {
            return null;
        }
        else
        {
            return theSubject;
        }

    }

    /**
     *
     * @param TermList is the subject terminology list.
     * @param Question is the question that user posting.
     * @return this method will returns integer value, which is the respected subject count.
     * this method is to calculate the each subject count, so the identifying the category of the question is can be done accurately
     * and in a proper way.
     */
    public Integer SubjectCount(ArrayList<String> TermList, String Question)
    {
        subjectCount = 0;
        theList = new ArrayList<>();
        String testQuestion = Question;
        String[] questionlist = testQuestion.split("\\W+");
        Integer count = 0;
        Integer key = 0;
        Integer check = 0;
        Boolean complete = false;
        for(String s : TermList)
        {
            theList.add(s);

        }

        for(int i=0;i<theList.size();i++)
        {
            count = 0;
            String element = theList.get(i);
            String[] elementsplit = element.split("\\W+");
            if(elementsplit.length>1)
            {
                for(int j=0;j<elementsplit.length;j++)
                {
                    Boolean isEqual = false;
                    for(int m=0; m<questionlist.length;m++)
                    {
                        if(isEqual==true)
                        {
                            continue;
                        }
                        else
                        {
                            if(questionlist[m].equals(elementsplit[j]))
                            {
                                if(j>0)
                                {
                                    check = key;
                                    check++;

                                    if(m==check)
                                    {
                                        count++;
                                    }
                                    else
                                    {
                                        count--;
                                    }
                                }
                                else
                                {
                                    count++;
                                }
                                isEqual = true;
                                key = m;
                            }
                            else
                            {
                                isEqual = false;
                            }
                        }
                    }
                    if(count==elementsplit.length)
                    {
                        complete = true;
                        subjectCount++;
                    }
                }
            }
            else
            {
                for (int n=0;n<questionlist.length;n++)
                {
                    if(questionlist[n].equals(theList.get(i)))
                    {
                        complete = true;
                        count++;
                        subjectCount++;
                    }
                }

            }
        }

        return subjectCount;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        id = getIntent().getExtras().getString("stdid");

        dbRef = FirebaseDatabase.getInstance().getReference();
        QTableRef = dbRef.child("Question");
        UTableRef = dbRef.child("Users");
        IPETableRef = dbRef.child("IPE");
        CFTableRef = dbRef.child("CF");
        DCCN1TableRef = dbRef.child("DCCN-I");
        DBMS1TableRef = dbRef.child("DBMS-I");
        FCSTableRef = dbRef.child("FCS");
        ITATableRef = dbRef.child("ITA");
        ST1TableRef = dbRef.child("ST-I");

        btn_postSubmit = (Button) findViewById(R.id.btnPost);
        theQuestion = (EditText) findViewById(R.id.thequestion);


        if(fromMainActivity==true)
        {
            btn_postSubmit.setText("Post");

        }
        else if(fromViewUser==true)
        {
            sendingto = getIntent().getExtras().getString("viewedid");
            btn_postSubmit.setText("Send");

        }



        IPETableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ipelist = new ArrayList<String>();
                for (DataSnapshot dsp : dataSnapshot.getChildren())
                {
                    ipelist.add(dsp.getValue().toString());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(addPost.this, databaseError.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        CFTableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                cflist = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren())
                {
                    cflist.add(dsp.getValue().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(addPost.this, databaseError.toString(),Toast.LENGTH_SHORT).show();

            }
        });

        DCCN1TableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dccn1list= new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren())
                {
                    dccn1list.add(dsp.getValue().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DBMS1TableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dbms1list = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren())
                {
                    dbms1list.add(dsp.getValue().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FCSTableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                fcslist = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren())
                {
                    fcslist.add(dsp.getValue().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ITATableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                italist = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren())
                {
                    italist.add(dsp.getValue().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ST1TableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                st1list = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren())
                {
                    st1list.add(dsp.getValue().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        QTableRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override

                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        qids = new ArrayList<>();
                        maxqid = 0;

                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            Map test1 = (Map) dsp.getValue();
                            qids.add((String) test1.get("QID"));
                        }

                        for(int i=0;i<qids.size();i++)
                        {
                            String sid = qids.get(i);
                            int val = Integer.parseInt(sid);
                            if(val>maxqid)
                            {
                                maxqid = val;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        btn_postSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question = theQuestion.getEditableText().toString().replace(",",";");
                count=maxqid+1;

                final String[] idetify = new String[1];

                final String output = CategoryIdentify();


                AlertDialog.Builder myBuilder = new AlertDialog.Builder(addPost.this);
                myBuilder.setTitle(R.string.Q_Category_Dialog_Title);

                if(output==null)
                {
                    myBuilder.setMessage("The category of the question couldn't identify. Please enter question again!");
                    myBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                }
                else
                {
                    category = output;
                    myBuilder.setMessage("Is this is the category of the Question "+category+"?");
                    myBuilder.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            UTableRef.addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override

                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            //Get map of users in datasnapshot

                                            sid = new ArrayList<>();
                                            skill = new ArrayList<>();
                                            eligiblelist = new ArrayList<>();

                                            for (DataSnapshot dsp : dataSnapshot.getChildren()){
                                                Map singleUser = (Map) dsp.getValue();
                                                skill.add((String) singleUser.get(category));
                                            }

                                            for (DataSnapshot dsp : dataSnapshot.getChildren()){
                                                Map singleUser = (Map) dsp.getValue();
                                                sid.add((String) singleUser.get("SID"));
                                            }

                                            for (int a=0; a<skill.size(); a++)
                                            {
                                                skillval = skill.get(a);
                                                sidval = sid.get(a);

                                                if(skillval == null)
                                                {
                                                    skillval = "0";
                                                }
                                                if(sidval == null)
                                                {
                                                    sidval = "";
                                                }

                                                val = 3;
                                                if(Integer.parseInt(skillval) >= val && !sidval.equals(id))
                                                {
                                                    eligiblelist.add(sidval);
                                                }
                                            }
                                            for(int k=0;k<eligiblelist.size();k++)
                                            {
                                                list = list + eligiblelist.get(k) + ":";
                                            }

                                            dbRef.child("Question");
                                            DatabaseReference dbchild = dbRef.child("Question");
                                            key = count.toString();
                                            dbchild.child(key);
                                            DatabaseReference dbchild_child = dbchild.child(key);
                                            dbchild_child.child("QID").setValue(key);
                                            dbchild_child.child("Question").setValue(question);
                                            dbchild_child.child("Category").setValue(category);
                                            dbchild_child.child("SID").setValue(id);
                                            if(fromViewUser==true)
                                            {
                                                dbchild_child.child("SendingTo").setValue(sendingto);
                                            }
                                            else if(fromMainActivity==true)
                                            {
                                                dbchild_child.child("SendingTo").setValue(list);
                                            }

                                            Intent i = new Intent(addPost.this, MainActivity.class);
                                            i.putExtra("stdid",id);
                                            addPost.this.startActivity(i);
                                            Toast.makeText(addPost.this, "Post is successfully added.",Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            //handle databaseError
                                        }

                                    });

                    /*

                    */
                            dialog.dismiss();

                        }
                    });
                    myBuilder.setNegativeButton("No!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            AlertDialog.Builder myBuilder1 = new AlertDialog.Builder(addPost.this);
                            myBuilder1.setTitle(R.string.Q_Category_Dialog_Title);
                            myBuilder1.setMessage("The category of the question couldn't identify. Please enter question again!");
                            myBuilder1.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertDialog1 = myBuilder1.create();
                            alertDialog1.show();
                            dialog.dismiss();
                        }
                    });
                }

                AlertDialog alertDialog = myBuilder.create();
                alertDialog.show();
            }
        });



        Log.d(TAG, "onCreate: Started.");
    }

}
