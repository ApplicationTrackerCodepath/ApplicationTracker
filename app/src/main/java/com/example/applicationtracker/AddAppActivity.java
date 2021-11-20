package com.example.applicationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.applicationtracker.models.Application;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AddAppActivity extends AppCompatActivity {

    public static final String TAG = "AddAppActivity";
    private EditText etCompName;
    private EditText etJobTitle;
    private EditText etStatus;
    private CalendarView cvDate;
    private EditText etNotes;
    private Button btnSubmit;
    long appDate;

    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_app);

        etCompName = findViewById(R.id.etCompName);
        etJobTitle = findViewById(R.id.etJobTitle);
        etStatus = findViewById(R.id.etStatus);
        cvDate = findViewById(R.id.cvDate);
        etNotes = findViewById(R.id.etNotes);
        btnSubmit = findViewById(R.id.btnSubmit);




        cvDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Calendar c = Calendar.getInstance();
                c.set(i, i1, i2);
                appDate = c.getTimeInMillis();
            }
        });

        //queryApplication();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CompName = etCompName.getText().toString();
                String JobTitle = etJobTitle.getText().toString();
                String sStatus = etStatus.getText().toString();
                int status = Integer.parseInt(sStatus);
                String Notes = etNotes.getText().toString();
                Date date = new Date(appDate);
                if(CompName.isEmpty() || JobTitle.isEmpty()){
                    Toast.makeText(AddAppActivity.this, "Company Name and Job Title cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }



                ParseUser currentUser = ParseUser.getCurrentUser();
                saveApplication(CompName, JobTitle, currentUser, status, Notes, date);
            }
        });

    }

    private void saveApplication(String compName, String jobTitle, ParseUser currentUser, int status, String notes, Date date) {
        Application application = new Application();
        application.setCompName(compName);
        application.setJobTitle(jobTitle);
        application.setUser(currentUser);
        application.setStatus(status);
;       application.setDateApplied(date);
        application.setNotes(notes);
        application.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(AddAppActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG, "post save was successful!!");
                etCompName.setText("");
                etJobTitle.setText("");
                etStatus.setText("");
                etNotes.setText("");
            }
        });
    }

    private void queryApplication() {
        ParseQuery<Application> query = ParseQuery.getQuery(Application.class);
        query.include(Application.KEY_USER);
        query.findInBackground(new FindCallback<Application>() {
            @Override
            public void done(List<Application> applications, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for(Application application : applications){
                    Log.i(TAG, "Application:" + application.getCompName() + ", username: " + application.getUser().getUsername());
                }
            }
        });
    }
}