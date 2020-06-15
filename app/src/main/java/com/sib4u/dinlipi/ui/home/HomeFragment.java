package com.sib4u.dinlipi.ui.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sib4u.dinlipi.CustomAdapter;
import com.sib4u.dinlipi.DbHelper;
import com.sib4u.dinlipi.R;
import com.sib4u.dinlipi.UserModel;
import com.sib4u.dinlipi.WriteActivity;

public class HomeFragment extends Fragment {
    FloatingActionButton button;
    ListView listView;
    CustomAdapter adapter;
    DbHelper dbHelper;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

      /// homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
       button=root.findViewById(R.id.fab);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               intentTo(new UserModel(" "," "," ",-1));
           }
       });

       listView=root.findViewById(R.id.listViewID);
        showList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intentTo(new DbHelper(getContext()).get(position));
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int at=position;
                new AlertDialog.Builder(getContext())
                        .setTitle("DELETE")
                        .setIcon(R.drawable.delete_icon)
                        .setMessage("do you want to delete this?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DbHelper dbHelper=new DbHelper(getContext());
                                boolean deleted=dbHelper.deleteAt(at);
                                if (deleted) {
                                    showList();
                                } else {
                                    Toast.makeText(getContext(), "failed"+at, Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                return true;
            }
        });
        return root;
    }

    private void intentTo(UserModel userModel) {

        Intent intent=new Intent(getContext(), WriteActivity.class);
        intent.putExtra("date",userModel.getDATE());
        intent.putExtra("title",userModel.getTITLE());
        intent.putExtra("note",userModel.getNOTE());
        intent.putExtra("id",userModel.getID());
        startActivityForResult(intent, 1);
    }

    private void showList() {
        dbHelper=new DbHelper(getContext());
        adapter=new CustomAdapter(getContext(),dbHelper.getAll());
        listView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==Activity.RESULT_OK)
        {
           if(data.getBooleanExtra("success",false))
             showList();
        }
    }
}
