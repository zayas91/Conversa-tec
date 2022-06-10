package com.example.alejandroanzures.conversa_tec;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Clase_Directa extends AppCompatActivity {

    //Variables de TextToVoice
    TextToSpeech TtoVoice;

    //Varaibles VoiceToText
    private static final String TAG = "MyStt3Activity";
    private SpeechRecognizer sr;
    private boolean ClaseIniciada=false;

    //Elementos del layout
    TextView txtvCurrentSpeech;
    //ListView lstvHistorySpeech;
    clasesDB DB;
    FloatingActionButton fabAdd;
    FloatingActionButton fabStartStop;
    FloatingActionButton fabQuestion;
    FloatingActionButton fabTmpQuestion;
    LinearLayout LayoutStartStop;
    LinearLayout LayoutQuestion;
    LinearLayout LayoutTmpQuestion;

    //Animation
    Animation fabOpen;
    Animation fabClose;
    Animation fabRotate;
    Animation fabAntiRotate;

    //Current
    String Actual="";
    ArrayAdapter<String> adapter=null;

    String Pregunta="";

    //Other Variables
    boolean isOpen=false;
    ForegroundColorSpan acento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clase__directa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Elementos del Layout
        txtvCurrentSpeech=(TextView) findViewById(R.id.txtvCurrentSpeech);
        txtvCurrentSpeech.setText(Actual);
        txtvCurrentSpeech.setMovementMethod(new ScrollingMovementMethod());

        //lstvHistorySpeech=(ListView) findViewById(R.id.lstvHistorySpeech);
        //lstvHistorySpeech.setAdapter(adapter);

        //Floating Action Buttons
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabStartStop = (FloatingActionButton) findViewById(R.id.fabStartStop);
        fabQuestion = (FloatingActionButton) findViewById(R.id.fabQuestion);
        fabTmpQuestion = (FloatingActionButton) findViewById(R.id.fabTmpQuestion);
        LayoutStartStop =(LinearLayout)findViewById(R.id.startStopLayout);
        LayoutQuestion =(LinearLayout)findViewById(R.id.questionLayout);
        LayoutTmpQuestion =(LinearLayout)findViewById(R.id.tmpQuestion);

        //Animations
        fabOpen= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fabClose=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        fabRotate=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clock);
        fabAntiRotate=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anti_clock);

        //FAB Actions
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabAddClick();
            }
        });
        fabStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabStartStopClick(view);
            }
        });
        fabQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabQuestionClick();
            }
        });
        fabTmpQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabTmpQuestionClick();
            }
        });

        //Base de Datos
        DB=new clasesDB(this);

        //Voz a Texto
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());

        //Texto a Voz
        TtoVoice=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    TtoVoice.setLanguage(Locale.getDefault());
                }
            }
        });

        //Color
        acento=new ForegroundColorSpan(R.color.colorAccent);

    }

    //Metodos de FAB
    public void fabAddClick()
    {
        if(isOpen)
        {
            //fabQuestion.startAnimation(fabClose);
            LayoutQuestion.startAnimation(fabClose);
            fabQuestion.setClickable(false);
            //fabStartStop.startAnimation(fabClose);
            LayoutStartStop.startAnimation(fabClose);
            fabStartStop.setClickable(false);
            fabAdd.startAnimation(fabAntiRotate);
            isOpen=false;
        }
        else
        {
            //fabQuestion.startAnimation(fabOpen);
            LayoutQuestion.startAnimation(fabOpen);
            fabQuestion.setClickable(true);
            //fabStartStop.startAnimation(fabOpen);
            LayoutStartStop.startAnimation(fabOpen);
            fabStartStop.setClickable(true);
            fabAdd.startAnimation(fabRotate);
            isOpen=true;
        }
    }

    public void fabStartStopClick(View view)
    {
        //fabQuestion.startAnimation(fabClose);
        LayoutQuestion.startAnimation(fabClose);
        fabQuestion.setClickable(false);
        //fabStartStop.startAnimation(fabClose);
        LayoutStartStop.startAnimation(fabClose);
        fabStartStop.setClickable(false);

        if(!ClaseIniciada)
        {
            fabStartStop.setImageResource(R.drawable.ic_stop);
            ClaseIniciada = true;
            Snackbar.make(view, "Iniciando Reconocimiento", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            Reconocimiento();
        }
        else
        {
            fabStartStop.setImageResource(R.drawable.ic_start);
            ClaseIniciada = false;
            Snackbar.make(view, "Deteniendo Reconocimiento", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        fabAdd.startAnimation(fabAntiRotate);
        isOpen=false;

    }

    public void fabQuestionClick()
    {
        //fabQuestion.startAnimation(fabClose);
        LayoutQuestion.startAnimation(fabClose);
        fabQuestion.setClickable(false);
        //fabStartStop.startAnimation(fabClose);
        LayoutStartStop.startAnimation(fabClose);
        fabStartStop.setClickable(false);
        fabAdd.startAnimation(fabAntiRotate);
        isOpen=false;

        InputDialogMainActivityFragment message=new InputDialogMainActivityFragment();
        message.setTitleMessage("","",R.drawable.ic_realizar_pregunta,this);
        message.show(getSupportFragmentManager(),"Información");


        /*try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    public void displayQuestionButton()
    {
        LayoutTmpQuestion.startAnimation(fabOpen);
        fabTmpQuestion.setClickable(true);
    }

    public void fabTmpQuestionClick()
    {

        Toast.makeText(getApplicationContext(), "Realizando Pregunta",Toast.LENGTH_SHORT).show();
        TtoVoice.speak(Pregunta, TextToSpeech.QUEUE_FLUSH, null);
        LayoutTmpQuestion.startAnimation(fabClose);
        fabTmpQuestion.setClickable(false);
        //Añadiendo Pregunta al Current
        //txtvCurrentSpeech

        Actual=Actual+"<P><font color='red'>"+Pregunta+"</font></P>";
        txtvCurrentSpeech.setText(Html.fromHtml(Actual));
    }

    //Metodos de la Clase
    public void Reconocimiento()
    {
        Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Empieza a Hablar");

        if(ClaseIniciada)
        try {
            sr.startListening(i);

            Log.i("111111","11111111");
        }
        catch (ActivityNotFoundException antExc)
        {
            Toast.makeText(Clase_Directa.this, "Su dispositivo no soporta el dictado por voz",Toast.LENGTH_LONG).show();
        }
    }

    //Clase para reconocimiento
    class listener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");
        }
        public void onBeginningOfSpeech()
        {
            Log.d(TAG, "onBeginningOfSpeech");
        }
        public void onRmsChanged(float rmsdB)
        {
            Log.d(TAG, "onRmsChanged");
        }
        public void onBufferReceived(byte[] buffer)
        {
            Log.d(TAG, "onBufferReceived");
        }
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndofSpeech");

        }
        public void onError(int error)
        {
            Log.d(TAG,  "error " +  error);
            //txtvCurrentSpeech.setText("error " + error);
            Toast.makeText(Clase_Directa.this, "Error " +error,Toast.LENGTH_LONG).show();
            Reconocimiento();
        }
        public void onResults(Bundle results)
        {
            String str = new String();
            Log.d(TAG, "onResults " + results);
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++)
            {
                Log.d(TAG, "result " + data.get(i));
                str += data.get(i);
            }

            Actual=Actual+"<P>"+data.get(0).toString()+"</P>";
            txtvCurrentSpeech.setText(Html.fromHtml(Actual));

            Reconocimiento();
        }
        public void onPartialResults(Bundle partialResults)
        {
            Log.d(TAG, "onPartialResults");
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent " + eventType);
        }
    }

    /*public void onActivityResult(int request_code, int result_code, Intent i)
    {
        super.onActivityResult(request_code,result_code,i);
        switch (request_code)
        {
            case 100:
                if(result_code==RESULT_OK && i!=null)
                {
                    ArrayList<String> result =i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtvCurrentSpeech.setText(result.get(0));
                    DB.agregarSpeech(result.get(0));
                    DB.leerSpeech();
                    List<String> speech=DB.leerSpeech();
                    adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,speech);
                    lstvHistorySpeech.setAdapter(adapter);
                }
            break;
        }
    }*/
}
