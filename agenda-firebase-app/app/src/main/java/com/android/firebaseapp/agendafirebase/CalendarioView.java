package com.android.firebaseapp.agendafirebase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.net.sip.SipSession;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.firebaseapp.agendafirebase.resources.EventoDecorador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarioView extends Activity {

    private Date startDate;
    private Date endDate;
    private ArrayList<Date> listaDeDatas;
    private ArrayList<CalendarDay> datasFormatColor = new ArrayList<CalendarDay>();
    private MaterialCalendarView calendarView;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarioReference = databaseReference.child("usuario");
    private UsuarioModel usuarioModel = new UsuarioModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_view);

        calendarView = (MaterialCalendarView) findViewById(R.id.calendarioID);
        usuarioReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dia1 = "";
                String dia2 = "";
                String dia3 = "";
                String a = getIntent().getStringExtra("userUid");
                if (dataSnapshot.child(a).child("dia1").getValue() != null) {
                    dia1 = dataSnapshot.child(a).child("dia1").getValue().toString();
                }
                if (dataSnapshot.child(a).child("dia2").getValue() != null) {
                    dia2 = dataSnapshot.child(a).child("dia2").getValue().toString();
                }
                if (dataSnapshot.child(a).child("dia2").getValue() != null) {
                    dia3 = dataSnapshot.child(a).child("dia3").getValue().toString();
                }
                String dataInicio = "01/01/2018";
                String dataFim = "01/01/2022";
                startDate = new Date();
                endDate   = new Date();
                //Formatando as datas
                usuarioModel.formatarData(startDate,dataInicio);
                usuarioModel.formatarData(endDate, dataFim);
                //Populando ArrayList Datas
                usuarioModel.listaDeDatas(startDate,endDate,listaDeDatas);
                //Populando o Array de calendarDay com datas especificas
                usuarioModel.comparaDatas(datasFormatColor,listaDeDatas,dia1);
                usuarioModel.comparaDatas(datasFormatColor,listaDeDatas,dia2);
                usuarioModel.comparaDatas(datasFormatColor,listaDeDatas,dia3);
                //Metodo para marca datas no calendario
                usuarioModel.decoraCalendario(calendarView,datasFormatColor);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Ouve um erro na comunicacao com o servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

