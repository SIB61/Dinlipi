package com.sib4u.dinlipi;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class ToDo extends Fragment {
    public ToDo() {
        // Required empty public constructor
    }


   private Calendar calendar;
    private ImageButton add,delete;
    private EditText editText;
    private ListView listView;
    private  TodoAdapter adapter;
    private List<UserModel> userModelList;
    private  DbHelper db;
    private UserModel userModel;
    private  List<String> a;
    private int i=0;
    private String s;
    private  String TIME;
    private  ImageButton DateButton;
    int y=0,m,d;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_to_do, container, false);

        a=new ArrayList<>();
        add=root.findViewById(R.id.imageButton);
        delete=root.findViewById(R.id.imageButton2);
        db=new DbHelper(getContext());
        listView=root.findViewById(R.id.todo);
        editText=root.findViewById(R.id.addFieldID);
        DateButton=root.findViewById(R.id.imageButton3);
        show();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Calendar calendar=Calendar.getInstance();
                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            }
                        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();*/
                 s=editText.getText().toString().trim();
                if(!s.isEmpty())
                {
                    if(y!=0)
                    showTimePicker();
                    else
                    {
                        db.addTodo(new UserModel(-1,s,0,"off"));
                        show();
                        editText.setText(null);
                    }
                }

            }
        });



        DateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean b=db.deleteTodo();
               show();
            }
        });
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 CheckedTextView checkedTextView=view.findViewById(R.id.checkboxTextID);
                 if(checkedTextView.isChecked())
                 {userModel=new UserModel(userModelList.get(position).getID2(),userModelList.get(position).getTODO(),0,userModelList.get(position).getTIME()) ;
                  checkedTextView.setChecked(false);
                  boolean b= db.updateToDoAt(userModel);
                 }
                 else
                 {
                     userModel=new UserModel(userModelList.get(position).getID2(),userModelList.get(position).getTODO(),1,userModelList.get(position).getTIME());
                     checkedTextView.setChecked(true);
                     boolean b= db.updateToDoAt(userModel);
                 }
             }
         });
        return root;
    }
    private void show(){
        userModelList=db.getTodo();
        adapter=new TodoAdapter(getContext(),userModelList);
        listView.setAdapter(adapter);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c)
    {
        AlarmManager alarmManager= (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(getContext(),Alarmreciever.class);
        intent.putExtra("message",s);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(getContext(),new DbHelper(getContext()).get2(userModelList.size()-1).getID2()+1,intent,0);
        if(c.before(Calendar.getInstance()))
            c.add(Calendar.DATE,1);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }
    private void cancelAlarm(int i)
    {
        AlarmManager alarmManager= (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(getContext(),Alarmreciever.class);
        PendingIntent pendingIntent =PendingIntent.getBroadcast(getContext(),i,intent,0);
        alarmManager.cancel(pendingIntent);
    }

    private void showTimePicker() {
        calendar=Calendar.getInstance();
        TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, final int hourOfDay, final int minute) {
                        Calendar c=Calendar.getInstance();
                        c.set(y,m,d,hourOfDay,minute,0);
                        SimpleDateFormat dateFormat=new SimpleDateFormat("h:mm a, EEE, MMM dd");
                        Date date =c.getTime();
                        TIME=dateFormat.format(date);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            if(new UserDetail(getContext()).getReminder())
                            startAlarm(c);
                            db.addTodo(new UserModel(-1,s,0,TIME));
                            show();
                            editText.setText(null);
                        }
                    }
                },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),false);
        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                db.addTodo(new UserModel(-1,s,0,"off"));
                show();
                editText.setText(null);
            }
        });
        timePickerDialog.show();
    }

     public void showDatePicker(){
         calendar=Calendar.getInstance();
         DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),
                 new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        y=year;
                        m=month;
                        d=dayOfMonth;
                     }
                 },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
         datePickerDialog.show();
     }
    }
    
