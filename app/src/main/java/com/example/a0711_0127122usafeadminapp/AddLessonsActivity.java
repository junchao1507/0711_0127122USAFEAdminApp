package com.example.a0711_0127122usafeadminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reginald.editspinner.EditSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddLessonsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatabaseReference rootRef, lessonRef;
    private ArrayAdapter<String> listWeeks, listDays, listLocs, listModNames, listModes, listLecturers;
    private EditText edtDate, edtStart, edtEnd;
    private Button btnAdd, btnAddAnother, btnReturnHome;
    private EditSpinner spnWeek, spnDay, spnLocation, spnModName, spnMode, spnLecturer;
    private Lesson lesson;
    private Calendar c;
//    private SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MMM-yy");
//    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    private String weekList[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private String dayList[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private String locList[] = {"GF-LTR-06", "GF-LTR-07", "GF-LTR-08", "04-lab-01A", "04-lab-01B"};
    private String modNameList[] = {"XBDS3024: Image Processing & Computer Vision", "XBDS3034: Natural Language Processing","XBFP3010: Final Year Project 1", "XBMC3024: Mobile Programming & Screen Design 2"};
    private String modeList[] = {"Lecture", "Tutorial"};
    private String lecturerList[] = {"Dr. Law Foong Li", "Mr. Phua Yeong Tsann", "Ms. Siti Fazillah Binti Shamsuddin"};
    private int maxId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lessons);

        spnWeek = findViewById(R.id.spinner_week);
        spnDay = findViewById(R.id.spinner_day);
        edtDate = findViewById(R.id.edt_date);
        spnLocation = findViewById(R.id.spn_location);
        edtStart = findViewById(R.id.edt_start);
        edtEnd = findViewById(R.id.edt_end);
        spnModName = findViewById(R.id.spn_mod_name);
        spnMode = findViewById(R.id.spn_mode);
        spnLecturer = findViewById(R.id.spn_lecturer);
        btnAdd = findViewById(R.id.btn_add);
        btnAddAnother = findViewById(R.id.btn_add_another);
        btnReturnHome = findViewById(R.id.btn_return_home);


        // Week Spinner
        listWeeks = new ArrayAdapter<String>(AddLessonsActivity.this, android.R.layout.simple_spinner_item, weekList);
        listWeeks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnWeek.setAdapter(listWeeks);

        // Day Spinner
        listDays = new ArrayAdapter<String>(AddLessonsActivity.this, android.R.layout.simple_spinner_item, dayList);
        listDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDay.setAdapter(listDays);

        // Location Spinner
        listLocs = new ArrayAdapter<String>(AddLessonsActivity.this, android.R.layout.simple_spinner_item, locList);
        listLocs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLocation.setAdapter(listLocs);

        // Module Name Spinner
        listModNames = new ArrayAdapter<String>(AddLessonsActivity.this, android.R.layout.simple_spinner_item, modNameList);
        listModNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnModName.setAdapter(listModNames);

        // Mode Spinner
        listModes = new ArrayAdapter<String>(AddLessonsActivity.this, android.R.layout.simple_spinner_item, modeList);
        listModes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMode.setAdapter(listModes);


        // Lecturer Spinner
        listLecturers = new ArrayAdapter<String>(AddLessonsActivity.this, android.R.layout.simple_spinner_item, lecturerList);
        listLecturers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLecturer.setAdapter(listLecturers);


        edtDate.setInputType(InputType.TYPE_NULL);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONDAY);
                int year = c.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(AddLessonsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                edtDate.setText(time);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });

        edtStart.setInputType(InputType.TYPE_NULL);
        edtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int min = c.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(AddLessonsActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
                                String amPm = "";
                                if (hourOfDay >= 12) {
                                    amPm = " PM";
                                    if(hourOfDay > 12) {
                                        hourOfDay -= 12;
                                    }
                                } else {
                                    amPm = " AM";
                                }
                                edtStart.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                            }
                        }, hour, min, false);
                timePicker.show();
            }
        });

        edtEnd.setInputType(InputType.TYPE_NULL);
        edtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int min = c.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(AddLessonsActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
                                String amPm = "";
                                if (hourOfDay >= 12) {
                                    amPm = " PM";
                                    if(hourOfDay > 12) {
                                        hourOfDay -= 12;
                                    }
                                } else {
                                    amPm = " AM";
                                }
                                edtEnd.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                            }
                        }, hour, min, false);
                timePicker.show();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLesson();
            }
        });

        btnAddAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAdd.setVisibility(View.VISIBLE);
                btnAddAnother.setVisibility(View.INVISIBLE);
                spnWeek.setText("");
                spnDay.setText("");
                edtDate.setText("");
                spnLocation.setText("");
                edtStart.setText("");
                edtEnd.setText("");
                spnModName.setText("");
                spnMode.setText("");
                spnLecturer.setText("");
                addLesson();
            }
        });

        btnReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(AddLessonsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void addLesson(){
        String week = spnWeek.getText().toString();
        String day = spnDay.getText().toString();
        String date = edtDate.getText().toString();
        String location = spnLocation.getText().toString();
        String startTime = edtStart.getText().toString();
        String endTime = edtEnd.getText().toString();
        String modName = spnModName.getText().toString();
        String mode = spnMode.getText().toString();
        String lecturer = spnLecturer.getText().toString();
        String errorMsg = "This field cannot be left empty!";

        if (TextUtils.isEmpty(week)) {
            spnWeek.setError(errorMsg);
            spnWeek.requestFocus();
        } else if (TextUtils.isEmpty(day)) {
            spnDay.setError(errorMsg);
            spnDay.requestFocus();
        } else if (TextUtils.isEmpty(date)) {
            edtDate.setError(errorMsg);
            edtDate.requestFocus();
        } else if (TextUtils.isEmpty(location)) {
            spnLocation.setError(errorMsg);
            spnLocation.requestFocus();
        } else if (TextUtils.isEmpty(startTime)) {
            edtStart.setError(errorMsg);
            edtStart.requestFocus();
        } else if (TextUtils.isEmpty(endTime)) {
            edtEnd.setError(errorMsg);
            edtEnd.requestFocus();
        } else if (TextUtils.isEmpty(modName)) {
            spnModName.setError(errorMsg);
            spnModName.requestFocus();
        } else if (TextUtils.isEmpty(mode)) {
            spnMode.setError(errorMsg);
            spnMode.requestFocus();
        } else if (TextUtils.isEmpty(lecturer)) {
            spnLecturer.setError(errorMsg);
            spnLecturer.requestFocus();
        } else {
            rootRef = FirebaseDatabase.getInstance("https://usafe---0127122-a31c2-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
            lessonRef = rootRef.child("Lesson");
            lessonRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    String capacity = "";
                    String loc = spnLocation.getText().toString().substring(3, 6);
                    if(snapshot.exists()) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            maxId += 1;
                        }
                    }

                    if(loc.equals("LTR")){
                        capacity = "50";
                    }
                    else{
                        capacity = "15";
                    }

                    lesson = new Lesson(Integer.toString(maxId), week, location, capacity, day, date, startTime, endTime, modName, mode, lecturer, "true");
                    lessonRef.child(String.valueOf(maxId)).setValue(lesson).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddLessonsActivity.this, "New Lesson Added Successfully!", Toast.LENGTH_SHORT).show();
                                btnAdd.setVisibility(View.INVISIBLE);
                                btnAddAnother.setVisibility(View.VISIBLE);
                                maxId = 1;
                            } else {
                                Toast.makeText(AddLessonsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AddLessonsActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
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