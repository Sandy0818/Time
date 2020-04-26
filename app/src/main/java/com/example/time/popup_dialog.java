package com.example.time;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class popup_dialog extends AppCompatDialogFragment {


    private EditText aat1;
    private EditText desc;
    EditText sel_date;
    private EditText aatdate;
    Calendar myCalendar;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button but1;

    private CallBackListener callBackListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_popup_dialog, container, true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("AAT", "Reached onActivityCreated");
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (getActivity() instanceof CallBackListener)
            callBackListener = (CallBackListener) getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("AAT", "Reached onViewCreated");

        Button addbtn = view.findViewById(R.id.addbtn);
        Button canbtn = view.findViewById(R.id.cancelbtn);
        sel_date = view.findViewById(R.id.dateslct);

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                updateLabel(dayOfMonth, monthOfYear + 1, year);
            }

        };

        sel_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //aatdate = (DatePicker) view.findViewById(R.id.aat2);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                putData(getView());

                if (callBackListener != null) {
                    callBackListener.onCallBack();
                }
            }
        });

        canbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBackListener != null) {
                    getActivity().getFragmentManager().popBackStack();
                    callBackListener.onCallBack();
                }
            }
        });
    }

    private void updateLabel(int date, int month, int yr) {
        /*String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        sel_date.setText(sdf.format(myCalendar.getTime()));*/
        String slc_date = String.format("%02d-%02d-%04d", date, month, yr);
        sel_date.setText(slc_date);
    }

    public void putData(View view)
    {
        final EditText w_name = (EditText) view.findViewById(R.id.aname);
        final EditText w_desc = (EditText) view.findViewById(R.id.adesc);
        aatdate = (EditText) view.findViewById(R.id.dateslct);

        final String name = w_name.getText().toString();
        final String desc = w_desc.getText().toString();
        String a_date = aatdate.getText().toString();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final HashMap<String, Object> aat = new HashMap<>();

        aat.put("title", name);
        aat.put("desc", desc);
        aat.put("date", a_date);

        try {
            db.collection("users").document("user4").collection("aat list").document(name)
                    .set(aat)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("AAT", aat.toString());
                            //Toast.makeText(getActivity().getBaseContext(), "workshop added to database", Toast.LENGTH_SHORT).show();

                        }
                    });

            db.collection("users").document("user4").collection("Title").document("Title_d")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            //String wshop_list = "";
                            String aat_list = (String) documentSnapshot.get("AAT");
                            final Map<String, Object> aat_obj = new HashMap<>();
                            //Toast.makeText(, aat_list.toString(), Toast.LENGTH_LONG).show();

                            if(!documentSnapshot.exists())
                            {
                                //Toast.makeText(com.example.time.syllabus.this, "Document Title_d doesn't exist", Toast.LENGTH_SHORT).show();
                                aat_list = name;
                                aat_obj.put("AAT", aat_list);

                                db.collection("users").document("user4").collection("Title").document("Title_d")
                                        .set(aat_obj)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                               // Toast.makeText(getApplicationContext(), "Title_d created", Toast.LENGTH_SHORT).show();
                                                Log.d("aat", aat_obj.toString());
                                            }
                                        });
                            }

                            if(aat_list == null || aat_list.isEmpty())
                            {
                                aat_list = name;
                            }

                            else {
                                aat_list = aat_list.concat(", " + name);
                            }

                                aat_obj.put("AAT", aat_list);

                                db.collection("users").document("user4").collection("Title").document("Title_d")
                                        .update(aat_obj)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Toast.makeText(getApplicationContext(), "Updated subject title list", Toast.LENGTH_SHORT).show();
                                                Log.d("aat", aat_obj.toString());
                                            }
                                        });

                        }
                    });
        }
        catch (Exception e)
        {

        }

    }

}