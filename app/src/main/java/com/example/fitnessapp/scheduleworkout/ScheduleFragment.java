package com.example.fitnessapp.scheduleworkout;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.app.PendingIntent;
import android.app.TimePickerDialog;

import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;



import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.DatePicker;

import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.fitnessapp.R;

import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

public class ScheduleFragment extends Fragment {

    // Notification ID.
    private static final int NOTIFICATION_ID = 0;
    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    private NotificationManager mNotificationManager;






    private Button addTrip;

    private TextView time_view;
    private TextView date_view;
    private Button time_button;
    private Button date_button;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_schedule, container, false);






        addTrip = layout.findViewById(R.id.addTrip);

        time_button = layout.findViewById(R.id.time_button);
        date_button = layout.findViewById(R.id.date_button);

        time_view = layout.findViewById(R.id.time_view);
        time_view.setInputType(InputType.TYPE_NULL);
        date_view = layout.findViewById(R.id.date_view);
        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date_view.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });
        time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                timePicker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                time_view.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                timePicker.show();
            }
        });

        mNotificationManager = (NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);


        final AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(ALARM_SERVICE);

        addTrip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent notifyIntent = new Intent(v.getContext(), AlarmReceiver.class);
                notifyIntent.putExtra("Title", "Time to workout!");

                final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                        (v.getContext(), NOTIFICATION_ID, notifyIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);


                if (alarmManager != null) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                            ParseTime().getTime(), notifyPendingIntent);
                    Toast.makeText(getActivity(), "An alarm to display notification for your workout to was created!",
                            Toast.LENGTH_SHORT).show();
                }


                time_view.setText("");
                date_view.setText("");


            }
        });

        createNotificationChannel();
        return layout;
    }

    private Date ParseTime()
    {
        int day = datePicker.getDatePicker().getDayOfMonth();
        int month = datePicker.getDatePicker().getMonth();
        int year =  datePicker.getDatePicker().getYear();
        String[] hourAndminutes = time_view.getText().toString().split(":");
        int hour = Integer.parseInt(hourAndminutes[0]);
        int minutes = Integer.parseInt(hourAndminutes[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager =
                (NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "To dos notifications",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}

