package com.example.alejandroanzures.conversa_tec;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    AppCompatActivity padre=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Clics largos a Image Buttos para mostrar información
        ImageButton btnProfesor=(ImageButton)findViewById(R.id.btnProfesor);
        btnProfesor.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                InfoDialogMainActivityFragment message=new InfoDialogMainActivityFragment();
                message.setTitleMessage(getString(R.string.profesor),getString(R.string.profesor_desc),R.drawable.ic_profesor,padre,false);
                message.show(getSupportFragmentManager(),"Información");
                return true;
            }
        });
        ImageButton btnUnirseClase=(ImageButton)findViewById(R.id.btnUnirseClase);
        btnUnirseClase.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                InfoDialogMainActivityFragment message=new InfoDialogMainActivityFragment();
                message.setTitleMessage(getString(R.string.unirse_a_clase),getString(R.string.unirse_a_clase_desc),R.drawable.ic_unirse_clase,padre,false);
                message.show(getSupportFragmentManager(),"Información");
                return true;
            }
        });
        ImageButton btnClaseDirecta=(ImageButton)findViewById(R.id.btnClaseDirecta);
        btnClaseDirecta.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                InfoDialogMainActivityFragment message=new InfoDialogMainActivityFragment();
                message.setTitleMessage(getString(R.string.clase_directa),getString(R.string.clase_directa_desc),R.drawable.ic_clase_directa,padre,false);
                message.show(getSupportFragmentManager(),"Información");
                return true;
            }
        });
        ImageButton btnAjustes=(ImageButton)findViewById(R.id.btnAjustes);
        btnAjustes.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                InfoDialogMainActivityFragment message=new InfoDialogMainActivityFragment();
                message.setTitleMessage(getString(R.string.ajustes),getString(R.string.ajustes_desc),R.drawable.ic_configuracion,padre,false);
                message.show(getSupportFragmentManager(),"Información");
                return true;
            }
        });

        //Clics cortos para funcionamiento
        /*btnProfesor.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                InfoDialogMainActivityFragment message=new InfoDialogMainActivityFragment();
                message.setTitleMessage(getString(R.string.profesor),getString(R.string.profesor_desc),R.drawable.ic_profesor);
                message.show(getSupportFragmentManager(),"Información");
                return true;
            }
        });
        btnUnirseClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputDialogMainActivityFragment message=new InputDialogMainActivityFragment();
                message.setTitleMessage(getString(R.string.unirse_a_clase),getString(R.string.unirse_a_clase_desc),R.drawable.ic_unirse_clase,padre);
                message.show(getSupportFragmentManager(),"Información");
            }
        });*/
        btnClaseDirecta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfoDialogMainActivityFragment message=new InfoDialogMainActivityFragment();
                message.setTitleMessage(getString(R.string.ajustes),getString(R.string.ajustes_desc),R.drawable.ic_configuracion,padre,true);
                message.show(getSupportFragmentManager(),"Información");
            }
        });
        /*
        btnAjustes.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                InfoDialogMainActivityFragment message=new InfoDialogMainActivityFragment();
                message.setTitleMessage(getString(R.string.ajustes),getString(R.string.ajustes_desc),R.drawable.ic_configuracion);
                message.show(getSupportFragmentManager(),"Información");
                return true;
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
