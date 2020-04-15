package com.example.time;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class add_workshop extends AppCompatDialogFragment {

    private CallBackListener callBackListener;

    private int mYear, mMonth, mDay, mHour, mMinute;
    String date_time;
    EditText date_time_slct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.activity_add_workshop, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("workshop", "Reached onActivityCreated");
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (getActivity() instanceof CallBackListener)
            callBackListener = (CallBackListener) getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("workshop", "Reached onViewCreated");

        Button addbtn = (Button) view.findViewById(R.id.addbutton);
        Button canbtn = (Button) view.findViewById(R.id.cancelbutton);

        date_time_slct = view.findViewById(R.id.slct_date_time);

        date_time_slct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                putData(getView());
                if(callBackListener != null)
                {
                    getActivity().getFragmentManager().popBackStack();
                    callBackListener.onCallBack();
                }
            }
        });

        canbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBackListener != null)
                {
                    getActivity().getFragmentManager().popBackStack();
                    callBackListener.onCallBack();
                }
            }
        });
    }

    public void putData(View view)
    {
        final EditText w_name = view.findViewById(R.id.w_name);
        final EditText w_loc = view.findViewById(R.id.w_location);
        final EditText w_desc = view.findViewById(R.id.w_descp);
        final EditText w_date_time = view.findViewById(R.id.slct_date_time);

        final String name = w_name.getText().toString();
        final String loc = w_loc.getText().toString();
        final String desc = w_desc.getText().toString();
        final String date_time = w_date_time.getText().toString();

        String split[] = date_time.split("\\s+");
        String w_date = split[0];
        String w_time = split[1];

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final HashMap<String, Object> works = new HashMap<>();

        works.put("title", name);
        works.put("descp", desc);
        works.put("location", loc);
        works.put("date", w_date);
        works.put("time", w_time);

        try {
            db.collection("users").document("user3").collection("workshop list").document(name)
                    .set(works)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("WSHOP", works.toString());
                            //Toast.makeText(getActivity().getBaseContext(), "workshop added to database", Toast.LENGTH_SHORT).show();

                        }
                    });

            db.collection("users").document("user1").collection("Title").document("Title_d")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            //String wshop_list = "";
                            String wshop_list = (String) documentSnapshot.get("Workshop");
                            //Toast.makeText(, aat_list.toString(), Toast.LENGTH_LONG).show();

                            if(!wshop_list.equals(null)) {
                                wshop_list = wshop_list.concat(", " + name);
                            }
                            else
                                wshop_list = name;

                            Map<String, Object> work_list = new HashMap<>();
                            work_list.put("Workshop", wshop_list);

                            db.collection("users").document("user1").collection("Title").document("Title_d")
                                    .update(work_list)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    });
                        }
                    });
        }
        catch (Exception e)
        {

        }

    }

    private void datePicker(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        //*************Call Time Picker Here ********************
                        timePicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void timePicker(){

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = (hourOfDay <= 12) ? hourOfDay : (hourOfDay - 12);
                        mMinute = minute;

                        date_time_slct.setText(date_time + " " + String.format("%02d:%02d", hourOfDay, mMinute));
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
