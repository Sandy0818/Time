package com.example.time;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class popup_dialog extends AppCompatDialogFragment {


    private EditText aat1;
    private EditText desc;
    private DatePicker aatdate;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button but1;

    Onaataddedlistener callback;
    public void setOnaataddedlistener(Onaataddedlistener callback){
        this.callback=callback;
    }

    public interface Onaataddedlistener{
        public void onaatadded();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.activity_popup_dialog,null);

        builder.setView(view).setTitle("enter").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String aat = aat1.getText().toString().trim();
                String desc1= desc.getText().toString().trim();

                final String aat_list2;

                int date = aatdate.getDayOfMonth();
                int month = aatdate.getMonth() + 1;
                int year = aatdate.getYear();
                Date aat_date = new GregorianCalendar(year, month, date).getTime();

                HashMap<String, Object> aat_obj = new HashMap<>();
                aat_obj.put("Title", aat);
                aat_obj.put("Descp", desc1);
                aat_obj.put("Date", aat_date);

                try {
                    db.collection("users").document("user1").collection("aat list").document(aat)
                            .set(aat_obj)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Toast.makeText(this., "AAT added to database", Toast.LENGTH_SHORT).show();

                                }
                            });

                    db.collection("users").document("user1").collection("Title").document("Title_d")
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    String aat_list = (String) documentSnapshot.get("AAT");
                                    //Toast.makeText(, aat_list.toString(), Toast.LENGTH_LONG).show();

                                    if(!aat_list.equals(null)) {
                                        aat_list = aat_list.concat(", " + aat);
                                    }
                                    else
                                        aat_list = aat;

                                    Map<String, Object> subj = new HashMap<>();
                                    subj.put("AAT", aat_list);

                                    final String finalAat_list = aat_list;
                                    db.collection("users").document("user1").collection("Title").document("Title_d")
                                            .update(subj)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    try {
                                                        dismiss();
                                                        callback.onaatadded();
                                                    }
                                                    catch (NullPointerException e){}

                                                    //Toast.makeText(getContext(), "Updated aat title list" + finalAat_list, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                }
                catch (NullPointerException e)
                {

                }

            }
        });

        aat1=(EditText) view.findViewById(R.id.aname);
        desc=(EditText) view.findViewById(R.id.adesc);

        aatdate = (DatePicker) view.findViewById(R.id.aat2);


        return builder.create();

    }
}
