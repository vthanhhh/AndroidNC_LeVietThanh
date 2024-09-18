package com.example.bai2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvThread1, tvThread2, tvThread3;
    private Button btnThread1, btnThread2, btnThread3;

    private boolean isThread1Running = false;
    private boolean isThread2Running = false;
    private boolean isThread3Running = false;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Thread thread1, thread2, thread3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tvThread1 = findViewById(R.id.tvThread1);
        tvThread2 = findViewById(R.id.tvThread2);
        tvThread3 = findViewById(R.id.tvThread3);

        btnThread1 = findViewById(R.id.btnThread1);
        btnThread2 = findViewById(R.id.btnThread2);
        btnThread3 = findViewById(R.id.btnThread3);

        // Thread 1:
        btnThread1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isThread1Running) {
                    isThread1Running = false;
                    btnThread1.setText("Start");
                } else {
                    isThread1Running = true;
                    btnThread1.setText("Stop");
                    startThread1();
                }
            }
        });
        // Thread 2:
        btnThread2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isThread2Running) {
                    isThread2Running = false;
                    btnThread2.setText("Start");
                } else {
                    isThread2Running = true;
                    btnThread2.setText("Stop");
                    startThread2();
                }
            }
        });

        // Thread 3:
        btnThread3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isThread3Running) {
                    isThread3Running = false;
                    btnThread3.setText("Start");
                } else {
                    isThread3Running = true;
                    btnThread3.setText("Stop");
                    startThread3();
                }
            }
        });
    }
    private void startThread1() {
        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                while (isThread1Running) {
                    final int randomNum = 50 + random.nextInt(51); // Sinh số ngẫu nhiên từ 50-100
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvThread1.setText(String.valueOf(randomNum));
                        }
                    });
                    try {
                        Thread.sleep(1000); // Dừng 1 giây
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread1.start();
    }

    private void startThread2() {
        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int oddNumber = 1;
                while (isThread2Running) {
                    final int currentOdd = oddNumber;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvThread2.setText(String.valueOf(currentOdd));
                        }
                    });
                    oddNumber += 2; //
                    try {
                        Thread.sleep(2500); // Dừng 2.5 giây
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread2.start();
    }

    private void startThread3() {
        thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                int number = 0;
                while (isThread3Running) {
                    final int currentNumber = number;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvThread3.setText(String.valueOf(currentNumber));
                        }
                    });
                    number++; //
                    try {
                        Thread.sleep(2000); // Dừng 2 giây
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread3.start();
    }
}