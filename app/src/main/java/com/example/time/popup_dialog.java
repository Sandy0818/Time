package com.example.time;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class popup_dialog extends AppCompatDialogFragment {


    private EditText aat1;
    private EditText desc;
    Button but1;

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
                String aat = aat1.getText().toString().trim();
                String desc1= desc.getText().toString().trim();



            }
        });

        aat1=(EditText) view.findViewById(R.id.aname);
        desc=(EditText) view.findViewById(R.id.adesc);


        return builder.create();

    }
}
