package com.example.time;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class project_title_popup extends AppCompatDialogFragment {

    private CallBackListener callBackListener;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_project_title_popup, container, true);
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

        Button addbtn = view.findViewById(R.id.add_exam);
        Button canbtn = view.findViewById(R.id.cancel_exam);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                putData(getView());

                if (callBackListener != null) {
                    getActivity().getFragmentManager().popBackStack();
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

    public void putData(View v)
    {
        EditText exam_name = v.findViewById(R.id.exam_title);
        final String title = exam_name.getText().toString();

        db.collection("users").document("user1").collection("Title").document("Title_d")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String exam_list =(String) documentSnapshot.get("Projects");

                        if(exam_list == null || exam_list.isEmpty())
                            exam_list = title;
                        else
                            exam_list = exam_list.concat(", " + title);

                        HashMap<String, Object> proj = new HashMap<>();
                        proj.put("Projects", exam_list);

                        db.collection("users").document("user1").collection("Title").document("Title_d")
                                .update(proj)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                    }
                });
    }
}
