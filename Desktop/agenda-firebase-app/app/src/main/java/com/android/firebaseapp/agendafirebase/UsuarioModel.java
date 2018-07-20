package com.android.firebaseapp.agendafirebase;

import android.app.Activity;
import android.util.Log;
import android.widget.CalendarView;
import com.android.firebaseapp.agendafirebase.resources.EventoDecorador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

public class UsuarioModel extends Activity {
    public void criarUsuario(DatabaseReference dr, Usuario usuario,int cod){
        dr.child(""+cod).setValue(usuario);
    }

    public ArrayList<CalendarDay> comparaDatas(ArrayList<CalendarDay> calendarDays, ArrayList<Date> listaDeDatas, String dia){
        String segunda = "segunda";
        String terca = "terca";
        String quarta = "quarta";
        String quinta = "quinta";
        String sexta = "sexta";
        String sabado = "sabado";

        Calendar calendar = Calendar.getInstance();
        for(int i = 0;i < listaDeDatas.size(); i++ ){
            try {
                Date data = listaDeDatas.get(i);
                calendar.setTime(data);
                if (dia.equals(segunda)) {
                    chegaSeg(calendar, calendarDays);
                } else if (dia.equals(terca)) {
                    chegaTer(calendar, calendarDays);
                } else if (dia.equals(quarta)) {
                    chegaQuar(calendar, calendarDays);
                } else if (dia.equals(quinta)) {
                    chegaQui(calendar, calendarDays);
                } else if (dia.equals(sexta)) {
                    chegaSex(calendar, calendarDays);
                } else if (dia.equals(sabado)) {
                    chegaSab(calendar, calendarDays);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return calendarDays;
    }

    public ArrayList<Date> listaDeDatas(Date startDate, Date endDate, ArrayList<Date> datas ){
      Calendar  calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate)){
            Date date = calendar.getTime();
            datas.add(date);
            calendar.add(Calendar.DATE, 1);
        }

        return datas;
    }

    public Date formatarData(Date date,String data){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public String recuperaDadosUsuario( String usuario, DatabaseReference dr){
        dr.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
              Log.i("dados" ,dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return usuario;
    }
    public void decoraCalendario(MaterialCalendarView calendarView , ArrayList<CalendarDay> datasFormatColor){
        if (datasFormatColor != null) {
            Collection<CalendarDay> calendarDays;
            Collection<CalendarDay> calendarDaysReColor;
            Calendar cal = Calendar.getInstance();
            calendarDays = datasFormatColor;
            int myColor1 = R.color.colorPrimaryDark;
            calendarView.addDecorators(new EventoDecorador(myColor1, calendarDays));

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> datasFormatColor2 = new ArrayList<CalendarDay>();
            for(int i = 0; i < datasFormatColor.size(); i++){
                Date date = datasFormatColor.get(i).getDate();
                if(date.before(cal.getTime())){

                    calendar.setTime(date);
                    CalendarDay calendarDay = CalendarDay.from(calendar);
                    datasFormatColor2.add(calendarDay);
                }
            }
            calendarDaysReColor = datasFormatColor2;
            int myColor = R.color.colorAccent;
            calendarView.addDecorator(new EventoDecorador(myColor, calendarDaysReColor));
        }
    }
    public ArrayList<CalendarDay>   chegaSeg(Calendar data, ArrayList<CalendarDay> date) {
        if (data.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY ) {
            int year = data.get(Calendar.YEAR);
            int month = data.get(Calendar.MONTH);
            int day = data.get(Calendar.DATE);
            CalendarDay calendarDay = CalendarDay.from(data);
            date.add(calendarDay);
        }
        return date;
    }
    public ArrayList<CalendarDay>   chegaTer(Calendar data,ArrayList<CalendarDay> date) {
        if (data.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY ) {
            int year = data.get(Calendar.YEAR);
            int month = data.get(Calendar.MONTH);
            int day = data.get(Calendar.DATE);
            CalendarDay calendarDay = CalendarDay.from(data);
            date.add(calendarDay);
        }
        return date;
    }
    public ArrayList<CalendarDay>   chegaQuar(Calendar data,ArrayList<CalendarDay> date) {
        if (data.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            int year = data.get(Calendar.YEAR);
            int month = data.get(Calendar.MONTH);
            int day = data.get(Calendar.DATE);
            CalendarDay calendarDay = CalendarDay.from(data);
            date.add(calendarDay);
        }
        return date;
    }
    public ArrayList<CalendarDay>   chegaQui(Calendar data,ArrayList<CalendarDay> date) {
        if (data.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            int year = data.get(Calendar.YEAR);
            int month = data.get(Calendar.MONTH);
            int day = data.get(Calendar.DATE);
            CalendarDay calendarDay = CalendarDay.from(data);
            date.add(calendarDay);
        }
        return date;
    }
    public ArrayList<CalendarDay>   chegaSex(Calendar data,ArrayList<CalendarDay> date) {
        if (data.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            int year = data.get(Calendar.YEAR);
            int month = data.get(Calendar.MONTH);
            int day = data.get(Calendar.DATE);
            CalendarDay calendarDay = CalendarDay.from(data);
            date.add(calendarDay);
        }
        return date;
    }
    public ArrayList<CalendarDay>   chegaSab(Calendar data,ArrayList<CalendarDay> date) {
        if (data.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            int year = data.get(Calendar.YEAR);
            int month = data.get(Calendar.MONTH);
            int day = data.get(Calendar.DATE);
            CalendarDay calendarDay = CalendarDay.from(data);
            date.add(calendarDay);
        }
        return date;
    }
public ArrayList<CalendarDay> coloriUmaData(Calendar data, ArrayList<CalendarDay> date){
            int year = data.get(Calendar.YEAR);
            int month = data.get(Calendar.MONTH);
            int day = data.get(Calendar.DATE);
            data.set(year, month, day);
            CalendarDay calendarDay = CalendarDay.from(data);
            date.add(calendarDay);
            return date;
        }
}