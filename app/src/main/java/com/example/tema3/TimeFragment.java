package com.example.tema3;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class TimeFragment extends Fragment {
    TextView selectedTime, selectedData;
    Button chooseTime, chooseData,createAlarm;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    String amPm, titleTodo;
    int year, month, day,hourF,minuteF,dayF,monthF,yearF;
    Calendar calendar;

    public TimeFragment(String name){
        this.titleTodo = name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.time_fragment,container,false);
        chooseTime = view.findViewById(R.id.button_time);
        selectedTime = view.findViewById(R.id.textView_time);
        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(getContext(), R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        selectedTime.setText(String.format("%02d:%02d", hourOfDay, minute) +" "+ amPm);
                        hourF = hourOfDay; minuteF = minute;

                    }
                },0,0,false);
                timePickerDialog.show();
            }
        });

        chooseData = view.findViewById(R.id.button_date);
        selectedData = view.findViewById(R.id.textView_date);
        chooseData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog =  new DatePickerDialog(getContext(),R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedData.setText(Integer.toString(dayOfMonth)+'/'+Integer.toString(month+1)+'/'+Integer.toString(year));
                        dayF = dayOfMonth; monthF = month; yearF = year;

                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        createAlarm = view.findViewById(R.id.button_alarm);
        createAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar thatDay = Calendar.getInstance();
                thatDay.set(Calendar.MINUTE,minuteF);
                thatDay.set(Calendar.HOUR_OF_DAY,hourF);
                thatDay.set(Calendar.DAY_OF_MONTH,dayF);
                thatDay.set(Calendar.MONTH,monthF);
                thatDay.set(Calendar.YEAR,yearF);
                Calendar today = Calendar.getInstance();
                long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
                ((MainActivity)getActivity()).startAlert(titleTodo,diff);

            }
        });

        return view;
    }

}
