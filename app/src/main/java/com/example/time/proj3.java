package com.example.time;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class proj3 extends AppCompatActivity {
    TextView proj3tv1;
    String st;
    EditText proj3et1;
    List<String> toDoList;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj3);
        proj3tv1=findViewById(R.id.proj3tv1);
        st=getIntent().getExtras().getString("name");
        proj3tv1.setText(st);

        toDoList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<>(this,R.layout.list_view_layout,toDoList);
        listView=findViewById(R.id.proj3list);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView=(TextView) view;
                textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });


        proj3et1=findViewById(R.id.proj3et1);
    }
    public void addItemToList(View view){
        toDoList.add(proj3et1.getText().toString());
        arrayAdapter.notifyDataSetChanged();
        proj3et1.setText(" ");
    }
}

