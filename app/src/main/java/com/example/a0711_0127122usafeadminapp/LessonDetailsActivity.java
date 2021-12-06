package com.example.a0711_0127122usafeadminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;
import java.util.HashMap;

public class LessonDetailsActivity extends AppCompatActivity {
    private DatabaseReference rootRef, lessonRef, seatRef;
    private ArrayAdapter<String> listSeats;
    private ArrayList<String> seatNo = new ArrayList<>();;
    private TextView tvLessonId, tvModuleName, tvDayDate, tvTime, tvLocation, tvSubjectLecturer, tvSeatsAvailable;
    private Button btnDelete, btnBack;

    private String lessonId = "";
    private String moduleName = "";
    private String dayDate = "";
    private String time = "";
    private String location = "";
    private String lecturer = "";
    private String seatsAvailable = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_details);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setElevation(0);

        tvLessonId = findViewById(R.id.tv_lesson_id_details);
        tvModuleName = findViewById(R.id.tv_module_name_details);
        tvDayDate = findViewById(R.id.tv_day_date_details);
        tvTime = findViewById(R.id.tv_time_details);
        tvLocation = findViewById(R.id.tv_location_details);
        tvSeatsAvailable = findViewById(R.id.tv_seat_details);
        tvSubjectLecturer = findViewById(R.id.tv_lecturer);
        tvSeatsAvailable = findViewById(R.id.tv_seat_details);
        btnDelete = findViewById(R.id.btn_delete);
        btnBack = findViewById(R.id.btn_back);


        // Get text from ViewLessonsActivity.java
        Bundle extras = getIntent().getExtras();
        lessonId = extras.getString("lessonId");
        moduleName = extras.getString("moduleName");
        dayDate = extras.getString("dayDate");
        time = extras.getString("time");
        location = extras.getString("location");
        lecturer = extras.getString("lecturer");
        seatsAvailable = extras.getString("capacity");


        // Set text
        tvLessonId.setText(lessonId);
        tvModuleName.setText(moduleName);
        tvDayDate.setText(dayDate);
        tvTime.setText(time);
        tvLocation.setText(location);
        tvSubjectLecturer.setText(lecturer);
        tvSeatsAvailable.setText(seatsAvailable);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(LessonDetailsActivity.this)
                    .setTitle("DELETE A LESSON")
                    .setMessage("Are you sure to delete this lesson?" +
                            "\nLesson Id: " + lessonId +
                            "\nModule Name: " + moduleName +
                            "\nDate: " + dayDate +
                            "\nTime: " + time)
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HashMap UpdateLesson = new HashMap();
                            UpdateLesson.put("active", "false");
                            lessonRef = FirebaseDatabase.getInstance("https://usafe---0127122-a31c2-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Lesson");
                            lessonRef.child(lessonId).updateChildren(UpdateLesson).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful()){
                                        new AlertDialog.Builder(LessonDetailsActivity.this)
                                                .setTitle("DELETE SUCCEEDED.")
                                                .setMessage("Delete Succeeded. You will be redirected back to the Lesson Page.")
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent intent = new Intent(LessonDetailsActivity.this, ViewLessonsActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                    }else{
                                        Toast.makeText(LessonDetailsActivity.this, "An error occurred. Try again later.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).setNegativeButton("Close", null).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LessonDetailsActivity.this, ViewLessonsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}