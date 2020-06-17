package com.sib4u.dinlipi.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.sib4u.dinlipi.R;
import com.sib4u.dinlipi.UserDetail;

public class SlideshowFragment extends Fragment {
    Switch aSwitch;
    private Button saveButton, cancel;
    private EditText userName, oldPass, newPass;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {

        }
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        aSwitch = root.findViewById(R.id.switch1);
        oldPass = root.findViewById(R.id.editText3);
        newPass = root.findViewById(R.id.editText4);
        userName = root.findViewById(R.id.editText2);
        saveButton = root.findViewById(R.id.button2);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = userName.getText().toString().trim(),
                        n = newPass.getText().toString().trim(),
                        o = oldPass.getText().toString().trim(),
                        p = new UserDetail(getContext()).load("passWordKey");
                if (u.isEmpty())
                    Toast.makeText(getContext(), "enter an user name", Toast.LENGTH_SHORT).show();
                else if (o.isEmpty())
                    Toast.makeText(getContext(), "enter your old password", Toast.LENGTH_SHORT).show();
                else if (n.isEmpty())
                    Toast.makeText(getContext(), "enter a new password", Toast.LENGTH_SHORT).show();
                else if (p.equals(o)) {
                    new UserDetail(getContext()).save(u, n);
                    Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                    oldPass.setText("");
                    newPass.setText("");
                    userName.setText("");
                    getActivity().onBackPressed();
                } else
                    Toast.makeText(getContext(), "wrong password", Toast.LENGTH_SHORT).show();
            }
        });
        cancel = root.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPass.setText("");
                newPass.setText("");
                userName.setText("");
                getActivity().onBackPressed();
            }
        });
        if (new UserDetail(getContext()).isDarkMood()) {
            aSwitch.setChecked(true);
        } else {
            aSwitch.setChecked(false);
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new UserDetail(getContext()).setDarkMode(true);
                    reset();
                } else {
                    new UserDetail(getContext()).setDarkMode(false);
                    reset();
                }
            }
        });
        return root;
    }

    public void reset() {
        startActivity(new Intent(getContext(), getActivity().getClass()));
        getActivity().finish();
    }
}
