package com.sib4u.dinlipi.ui.gallery;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.sib4u.dinlipi.CustomAdapter;
import com.sib4u.dinlipi.DbHelper;
import com.sib4u.dinlipi.R;
import com.sib4u.dinlipi.UserModel;
import com.sib4u.dinlipi.WriteActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class GalleryFragment extends Fragment  {
    public GalleryFragment()
    {

    }
    private CalendarView calendarView;
    private  ListView gotoListView;
    private String date;
    private List<UserModel> list;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        gotoListView=root.findViewById(R.id.gotoListViewID);
        calendarView=root.findViewById(R.id.calenderID);
        String cd=new SimpleDateFormat("dd MMM, yyyy").format(Calendar.getInstance().getTime());
        list=new DbHelper(getContext()).gotoDate(cd);
        gotoListView.setAdapter(new CustomAdapter(getContext(),list));
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date=dayOfMonth+" "+new WriteActivity().months[month]+", "+year;
                list=new DbHelper(getContext()).gotoDate(date);
                gotoListView.setAdapter(new CustomAdapter(getContext(),list));
            }
        });

        gotoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // intentTo(list.get(position));
                intentTo(list.get(position));
            }
        });
        return root;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode== Activity.RESULT_OK)
        {
            if(data.getBooleanExtra("success",false)) {
                list=new DbHelper(getContext()).gotoDate(date);
                gotoListView.setAdapter(new CustomAdapter(getContext(),list));
            }
        }
    }
    private void intentTo(UserModel userModel) {
        Intent intent=new Intent(getContext(), WriteActivity.class);
        intent.putExtra("date",userModel.getDATE());
        intent.putExtra("title",userModel.getTITLE());
        intent.putExtra("note",userModel.getNOTE());
        intent.putExtra("id",userModel.getID());
        startActivityForResult(intent,1);
    }
}
