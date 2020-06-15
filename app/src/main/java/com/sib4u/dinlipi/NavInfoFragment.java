package com.sib4u.dinlipi;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

public class NavInfoFragment extends Fragment {

    public NavInfoFragment() {
        // Required empty public constructor
    }
ImageView linkDin,github,inst;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nav_info, container, false);
        github=root.findViewById(R.id.imageView2);
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.github.com/SIB61"));
                startActivity(intent);
            }
        });
        linkDin=root.findViewById(R.id.imageView3);
        linkDin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/md-sabit-islam-bhuiya-55a7601ab"));
                startActivity(intent);
            }
        });
        inst=root.findViewById(R.id.imageView4);
        inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/sabit__001"));
                startActivity(intent);
            }
        });
       return root;
    }
}
