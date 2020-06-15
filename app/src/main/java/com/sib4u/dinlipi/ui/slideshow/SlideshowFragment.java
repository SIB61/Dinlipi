package com.sib4u.dinlipi.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.sib4u.dinlipi.R;
import com.sib4u.dinlipi.UserDetail;

public class SlideshowFragment extends Fragment {
    private Button saveButton,cancel;
    private EditText userName,oldPass,newPass;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        oldPass=root.findViewById(R.id.editText3);
        newPass=root.findViewById(R.id.editText4);
        userName=root.findViewById(R.id.editText2);
        saveButton =root.findViewById(R.id.button2);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u=userName.getText().toString().trim(),
                        n=newPass.getText().toString().trim(),
                        o=oldPass.getText().toString().trim(),
                        p=new UserDetail(getContext()).load("passWordKey");
                if(u.isEmpty())
                    Toast.makeText(getContext(),"enter an user name",Toast.LENGTH_SHORT).show();
               else if(o.isEmpty())
                    Toast.makeText(getContext(),"enter your old password",Toast.LENGTH_SHORT).show();
               else if(n.isEmpty())
                    Toast.makeText(getContext(),"enter a new password",Toast.LENGTH_SHORT).show();
               else if(p.equals(o))
                {
                    new UserDetail(getContext()).save(u, n);
                    Toast.makeText(getContext(),"Updated",Toast.LENGTH_SHORT).show();
                    oldPass.setText("");
                    newPass.setText("");
                    userName.setText("");
                }
               else
                Toast.makeText(getContext(),"wrong password",Toast.LENGTH_SHORT).show();
            }
        });
        cancel=root.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPass.setText("");
                newPass.setText("");
                userName.setText("");
            }
        });
        return root;
    }
}
