package com.sib4u.dinlipi;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context ctx;
    private List<UserModel> list;
    private List<String> Date=new ArrayList<>();
    private List<String> Title=new ArrayList<>();
    private List<String> Note=new ArrayList<>();
    public CustomAdapter(Context ctx, List<UserModel> list) {
        this.ctx = ctx;
        this.list = list;
        for(UserModel cm:list)
        {  Date.add(cm.getDATE());
            Title.add(cm.getTITLE());
            Note.add(cm.getNOTE());
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(R.layout.list_style,null);
        TextView date,note,title;
        date=convertView.findViewById(R.id.listDateId);
        title=convertView.findViewById(R.id.listTitleId);
        note=convertView.findViewById(R.id.listNoteId);

        String titleText=Title.get(position),dateText=Date.get(position),noteText=Note.get(position);
        date.setText(dateText);
        note.setText(noteText);
        title.setText(titleText);
        return convertView;
    }
}
