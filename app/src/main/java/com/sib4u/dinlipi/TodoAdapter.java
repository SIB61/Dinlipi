package com.sib4u.dinlipi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.List;

public class TodoAdapter extends BaseAdapter {
    private Context ctx;
    private List<UserModel> userModelList;

    public TodoAdapter(Context ctx, List<UserModel> userModelList) {
        this.ctx = ctx;
        this.userModelList = userModelList;
    }

    @Override
    public int getCount() {
        return userModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(R.layout.row_design,null);
        CheckedTextView checkedTextView=convertView.findViewById(R.id.checkboxTextID);
        checkedTextView.setText("-> "+userModelList.get(position).getTODO());
        checkedTextView.setChecked(userModelList.get(position).isChecked()==1);
        return convertView;
    }
}
