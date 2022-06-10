package com.example.alejandroanzures.conversa_tec;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Alejandro Anzures on 07/11/2017.
 */

public class InputDialogMainActivityFragment extends DialogFragment  {
    String Title="",Content="";
    int ico=0;
    AppCompatActivity padre;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View dialog=inflater.inflate(R.layout.question_dialog, null);

        final EditText tmp=(EditText) dialog.findViewById(R.id.dialogPregunta);
        builder.setView(dialog)
                .setIcon(ico)
                // Add action buttons
                .setPositiveButton("Solicitar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        ((Clase_Directa)padre).Pregunta=tmp.getText().toString();
                        ((Clase_Directa)padre).displayQuestionButton();
                        try {
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(padre.getApplicationContext(), notification);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }

    public void setTitleMessage(String Title, String Content, int ico, AppCompatActivity padre)
    {
        this.ico=ico;
        this.padre=padre;
    }
}

