package com.sib4u.dinlipi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WriteActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener
{

  public final String[] months={
          "Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep",
          "Oct","Nov","Dec"
  };
 private TextView dateView;
 private EditText TitleView,NoteView;
 private FloatingActionButton savePageButton,homeButton;
 private  int p_id;
 UserDetail us;
UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(new UserDetail(this).isDarkMood()==true)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_write);
        dateView=findViewById(R.id.dateID);
        NoteView=findViewById(R.id.noteViewID);
        TitleView=findViewById(R.id.titleVIewID);
        dateView.setOnClickListener(this);
        homeButton=findViewById(R.id.homeButtonId);
        homeButton.setOnClickListener(this);
        savePageButton=findViewById(R.id.savePageID);
        us=new UserDetail(WriteActivity.this);
        userModel=us.LoadWritten();

        p_id=getIntent().getIntExtra("id",-1);
        if(p_id!=-1)
        {
          dateView.setText(getIntent().getStringExtra("date").trim());
          TitleView.setText(getIntent().getStringExtra("title").trim());
          NoteView.setText(getIntent().getStringExtra("note").trim());
        }
        else
        {
            if(userModel.getNOTE().trim().isEmpty()&&userModel.getTITLE().trim().isEmpty()) {
                dateView.setText(new SimpleDateFormat("dd MMM, yyyy").format(Calendar.getInstance().getTime()));
            }
            else
            {
               dateView.setText(userModel.getDATE());
               TitleView.setText(userModel.getTITLE());
               NoteView.setText(userModel.getNOTE());
            }
        }
        savePageButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId() ){
            case R.id.dateID:
                DialogFragment datePicker=new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date_picker");
                break;

            case R.id.savePageID:
                us.SaveWritten(new UserModel(" "," "," ",0));
                String date=dateView.getText().toString(),title=TitleView.getText().toString().trim(),
                        note=NoteView.getText().toString().trim();
                    if (date.isEmpty())
                        Toast.makeText(WriteActivity.this, "enter a date", Toast.LENGTH_SHORT).show();
                    else if (title.isEmpty())
                        Toast.makeText(WriteActivity.this, "write a title", Toast.LENGTH_SHORT).show();
                    else if (note.isEmpty())
                        Toast.makeText(WriteActivity.this, "write a note", Toast.LENGTH_SHORT).show();
                    else {
                        UserModel userModel = new UserModel(date, title, note,p_id);
                        DbHelper dbHelper = new DbHelper(WriteActivity.this);
                        if (p_id == -1) {
                            boolean added= dbHelper.addNote(userModel);
                            if (added) {
                                back(true);
                            } else {
                                Toast.makeText(WriteActivity.this, "failed", Toast.LENGTH_SHORT).show();
                                back(false);
                            }
                        }
                        else
                        {
                            boolean updated=dbHelper.updateAt(userModel);
                            if(updated) {
                                back(true);
                            }
                            else
                            {
                                Toast.makeText(WriteActivity.this, "failed", Toast.LENGTH_SHORT).show();
                                back(false);
                            }
                        }
                    }
                break;
            case R.id.homeButtonId:
                onBackPressed();
        }
    }
    @Override
    public void onBackPressed() {
        if(p_id==-1) {
            us.SaveWritten(new UserModel(dateView.getText().toString(), TitleView.getText().toString().trim()
                    , NoteView.getText().toString().trim(), 0));
        }
        back(false);
    }
    private void back(boolean b) {
        Intent in = new Intent();
        in.putExtra("success", b);
        setResult(Activity.RESULT_OK, in);
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String date=dayOfMonth+" "+months[month]+", "+year;
             dateView.setText(date);
    }

}
