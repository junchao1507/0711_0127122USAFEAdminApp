package com.example.a0711_0127122usafeadminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reginald.editspinner.EditSpinner;

public class UpdateQuizActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private DatabaseReference rootRef, questionRef;
    private ArrayAdapter<String> listAns;
    private Question question;
    private EditSpinner spnAns;
    private EditText edtQues, edtOptA, edtOptB, edtOptC, edtOptD;
    private Button btnAdd, btnAddAnother, btnReturnHome;
    private String ansList[] = {"A", "B", "C", "D"};
    private int maxId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_quiz);

        edtQues = findViewById(R.id.edt_question);
        edtOptA = findViewById(R.id.edt_option_a);
        edtOptB = findViewById(R.id.edt_option_b);
        edtOptC = findViewById(R.id.edt_option_c);
        edtOptD = findViewById(R.id.edt_option_d);
        spnAns = findViewById(R.id.spinner_answer);
        btnAdd = findViewById(R.id.btn_add);
        btnAddAnother = findViewById(R.id.btn_add_another);
        btnReturnHome = findViewById(R.id.btn_return_home);

        // Week Spinner
        listAns = new ArrayAdapter<String>(UpdateQuizActivity.this, android.R.layout.simple_spinner_item, ansList);
        listAns.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAns.setAdapter(listAns);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestion();
            }
        });

        btnAddAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAdd.setVisibility(View.VISIBLE);
                btnAddAnother.setVisibility(View.INVISIBLE);

                edtQues.setText("");
                edtOptA.setText("");
                edtOptB.setText("");
                edtOptC.setText("");
                edtOptD.setText("");
                spnAns.setText("");

                addQuestion();
            }
        });

        btnReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(UpdateQuizActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void addQuestion(){
        String ques = edtQues.getText().toString();
        String optA = edtOptA.getText().toString();
        String optB = edtOptB.getText().toString();
        String optC = edtOptC.getText().toString();
        String optD = edtOptD.getText().toString();
        String ans = spnAns.getText().toString();
        String errorMsg = "This field cannot be left empty!";

        if (TextUtils.isEmpty(ques)) {
            edtQues.setError(errorMsg);
            edtQues.requestFocus();
        } else if (TextUtils.isEmpty(optA)) {
            edtOptA.setError(errorMsg);
            edtOptA.requestFocus();
        } else if (TextUtils.isEmpty(optB)) {
            edtOptB.setError(errorMsg);
            edtOptB.requestFocus();
        } else if (TextUtils.isEmpty(optC)) {
            edtOptC.setError(errorMsg);
            edtOptC.requestFocus();
        } else if (TextUtils.isEmpty(optD)) {
            edtOptD.setError(errorMsg);
            edtOptD.requestFocus();
        } else if (TextUtils.isEmpty(ans)) {
            spnAns.setError(errorMsg);
            spnAns.requestFocus();
        } else {
            rootRef = FirebaseDatabase.getInstance("https://usafe---0127122-a31c2-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
            questionRef = rootRef.child("Question");
            questionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            maxId += 1;
                        }
                    }

                    question = new Question(maxId, ques, optA, optB, optC, optD, ans);
                    questionRef.child(String.valueOf(maxId)).setValue(question).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UpdateQuizActivity.this, "New Lesson Added Successfully!", Toast.LENGTH_SHORT).show();
                                btnAdd.setVisibility(View.INVISIBLE);
                                btnAddAnother.setVisibility(View.VISIBLE);
                                maxId = 1;
                            } else {
                                Toast.makeText(UpdateQuizActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(UpdateQuizActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}