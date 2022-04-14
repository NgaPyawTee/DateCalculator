package com.homework.datecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    private Button birthBtn, todayBtn, calBtn;
    private TextView textView;
    private DatePickerDialog.OnDateSetListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        birthBtn = findViewById(R.id.btn_birth);
        todayBtn = findViewById(R.id.btn_today);
        calBtn = findViewById(R.id.btn_cal);
        textView = findViewById(R.id.tv_date_difference);

        birthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BirthBtnClick();
            }
        });

        todayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodayBtnClick();
            }
        });

        calBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalculateDate();
            }
        });


        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String s = day + "/" + month + "/" + year;
                birthBtn.setText(s);
            }
        };
    }

    private void BirthBtnClick() {
        Calendar calendar = Calendar.getInstance();
        int birthYear = calendar.get(Calendar.YEAR);
        int birthMonth = calendar.get(Calendar.MONTH);
        int birthDay = calendar.get(Calendar.DAY_OF_MONTH);

        /*Calendar c = new GregorianCalendar(birthYear, birthMonth, 0);
        SimpleDateFormat sf = new SimpleDateFormat("dd");
        Date date = c.getTime();
        int i = Integer.parseInt(sf.format(date));
        birthBtn.setText(String.valueOf(i));*/

        DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, listener, birthYear, birthMonth, birthDay);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void TodayBtnClick() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy");
        String todayDate = simpleDateFormat.format(Calendar.getInstance().getTime());
        todayBtn.setText(todayDate);
    }

    private void CalculateDate() {
        String s1 = birthBtn.getText().toString();
        String s2 = todayBtn.getText().toString();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date1 = simpleDateFormat.parse(s1);
            Date date2 = simpleDateFormat.parse(s2);

            long startDate = date1.getTime();
            long endDate = date2.getTime();

            //long startDate = simpleDateFormat.parse(s1).getTime();
            //long endDate = simpleDateFormat.parse(s2).getTime();

            if (startDate <= endDate) {
                Period period = new Period(startDate, endDate, PeriodType.yearMonthDay());
                int perYear = period.getYears();
                int perMonth = period.getMonths();
                int perDay = period.getDays();

                textView.setText(perDay + " Days " + perMonth + " Months " + perYear + " Years ");
            } else {
                Toast.makeText(getApplicationContext(), "Start date shouldn't larger than end date!", Toast.LENGTH_SHORT).show();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}