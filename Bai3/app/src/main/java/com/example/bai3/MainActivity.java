package com.example.bai3;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private EditText etMinutes;
    private Button btnSetAlarm, btnStopAlarm;
    private Vibrator vibrator;

    private Handler handler = new Handler();
    private Runnable alarmRunnable;
    private boolean isAlarmSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = findViewById(R.id.timePicker);
        etMinutes = findViewById(R.id.etMinutes);
        btnSetAlarm = findViewById(R.id.btnSetAlarm);
        btnStopAlarm = findViewById(R.id.btnStopAlarm);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAlarmSet) {
                    setAlarm();
                } else {
                    Toast.makeText(MainActivity.this, "Alarm already set!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnStopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarm();
            }
        });
    }

    private void setAlarm() {
        // Lấy thời gian từ TimePicker
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String inputMinutes = etMinutes.getText().toString();

        if (inputMinutes.isEmpty()) {
            Toast.makeText(this, "Please enter minutes", Toast.LENGTH_SHORT).show();
            return;
        }

        int durationMinutes = Integer.parseInt(inputMinutes);


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        long alarmTimeInMillis = calendar.getTimeInMillis() - System.currentTimeMillis();

        if (alarmTimeInMillis > 0) {
            isAlarmSet = true;
            Toast.makeText(this, "Alarm set for " + hour + ":" + minute, Toast.LENGTH_SHORT).show();

            // Đặt runnable để bắt đầu rung khi đến giờ
            alarmRunnable = new Runnable() {
                @Override
                public void run() {
                    startVibration(durationMinutes * 60 * 1000);
                }
            };

            // Hẹn giờ rung
            handler.postDelayed(alarmRunnable, alarmTimeInMillis);
        } else {
            Toast.makeText(this, "Invalid time selected!", Toast.LENGTH_SHORT).show();
        }
    }

    private void startVibration(long durationMillis) {
        long[] pattern = {0, 1000, 1000};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect effect = VibrationEffect.createWaveform(pattern, 0);
            vibrator.vibrate(effect);
        } else {
            vibrator.vibrate(pattern, 0);
        }


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopAlarm();
            }
        }, durationMillis);
    }

    private void stopAlarm() {
        if (isAlarmSet) {
            handler.removeCallbacks(alarmRunnable);  // Hủy báo thức
            vibrator.cancel();  // Dừng rung
            isAlarmSet = false;
            Toast.makeText(this, "Alarm stopped!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No alarm to stop!", Toast.LENGTH_SHORT).show();
        }
    }
}
