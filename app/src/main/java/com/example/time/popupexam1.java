package com.example.time;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class popupexam1 extends AppCompatDialogFragment {


    private EditText examet1;
    //private EditText desc;
    //private DatePicker aatdate;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    //Button but1;

    //Onaataddedlistener callback;
    //public void setOnaataddedlistener(Onaataddedlistener callback){
      //  this.callback=callback;
    //}

    //public interface Onaataddedlistener{
      //  public void onaatadded();
    //}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.popupexam1,null);

        builder.setView(view).setTitle("enter").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String examsub =examet1.getText().toString().trim();
                //String desc1= desc.getText().toString().trim();

               // final String exam_list;

                //int date = aatdate.getDayOfMonth();
                //int month = aatdate.getMonth() + 1;
                //int year = aatdate.getYear();
                //Date aat_date = new GregorianCalendar(year, month, date).getTime();

                HashMap<String, Object> examsub_obj = new HashMap<>();
                examsub_obj.put("Title", examsub);
                //aat_obj.put("Descp", desc1);
                //aat_obj.put("Date", aat_date);

                try {
                    db.collection("users").document("user1").collection("exams list").document(examsub)
                            .set(examsub_obj)
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
                                    String exam_list = (String) documentSnapshot.get("EXAMS");
                                    //Toast.makeText(, aat_list.toString(), Toast.LENGTH_LONG).show();

                                    if(!exam_list.equals(null)) {
                                        exam_list = exam_list.concat(", " + examsub);
                                    }
                                    else
                                        exam_list = examsub;

                                    Map<String, Object> subj = new HashMap<>();
                                    subj.put("EXAMS", exam_list);

                                    final String finalexam_list = exam_list;
                                    db.collection("users").document("user1").collection("Title").document("Title_d")
                                            .update(subj)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    try {
                                                        dismiss();
                                                        //callback.onaatadded();
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

        examet1=(EditText) view.findViewById(R.id.examet1);
        //desc=(EditText) view.findViewById(R.id.adesc);

        //aatdate = (DatePicker) view.findViewById(R.id.aat2);


        return builder.create();

    }
}
