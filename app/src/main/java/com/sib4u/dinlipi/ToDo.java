package com.sib4u.dinlipi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ToDo extends Fragment {
    public ToDo() {
        // Required empty public constructor
    }



    private ImageButton add,delete;
    private EditText editText;
    private ListView listView;
    private  TodoAdapter adapter;
    private List<UserModel> userModelList;
    private  DbHelper db;
    private UserModel userModel;
    private  List<String> a;
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
        show();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=editText.getText().toString().trim();
                if(!s.isEmpty())
                {
                    db.addTodo(new UserModel(-1,s,0));
                }
                editText.setText(null);
                show();
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
                 {userModel=new UserModel(userModelList.get(position).getID2(),userModelList.get(position).getTODO(),0) ;
                  checkedTextView.setChecked(false);
                  boolean b= db.updateToDoAt(userModel);
                 }
                 else
                 {
                     userModel=new UserModel(userModelList.get(position).getID2(),userModelList.get(position).getTODO(),1);
                     checkedTextView.setChecked(true);
                  boolean b= db.updateToDoAt(userModel);
                 }
                 show();
             }
         });
        return root;
    }
    private void show(){
        userModelList=db.getTodo();
        adapter=new TodoAdapter(getContext(),userModelList);
        listView.setAdapter(adapter);
    }

    }
    
