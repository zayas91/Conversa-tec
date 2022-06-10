package com.example.alejandroanzures.conversa_tec;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Alejandro Anzures on 06/11/2017.
 */

public class InfoDialogMainActivityFragment extends DialogFragment {

    String Title="",Content="";
    int ico=0;
    boolean Open=true;
    AppCompatActivity padre;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage(Content)
                .setTitle(Title)
                .setIcon(ico)
                .setPositiveButton("OK",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id){
                        if(Open)
                        {
                            Intent clase_directa=new Intent(padre,Clase_Directa.class);
                            startActivity(clase_directa);
                        }
                    }
                });
        return builder.create();
    }

    public void setTitleMessage(String Title, String Content, int ico, AppCompatActivity padre, boolean Open)
    {
        this.Title=Title;
        this.Content=Content;
        this.ico=ico;
        this.padre=padre;
        this.Open= Open;
    }
}
