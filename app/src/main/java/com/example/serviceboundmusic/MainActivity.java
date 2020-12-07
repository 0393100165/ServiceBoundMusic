package com.example.serviceboundmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.serviceboundmusic.MyService.MyBinder;

public class MainActivity extends AppCompatActivity{

    private MyService myService;
    private boolean isBound = false;
    private ServiceConnection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPlay = (Button) findViewById(R.id.btnPlay);
        Button btnStop = (Button) findViewById(R.id.btnStop);
        //con 2 phim nua k biet lam`
        // Khởi tạo ServiceConnection
        connection = new ServiceConnection() {

            // Phương thức này được hệ thống gọi khi kết nối tới service bị lỗi
            @Override
            public void onServiceDisconnected(ComponentName name) {

                isBound = false;
            }

            // Phương thức này được hệ thống gọi khi kết nối tới service thành công
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyBinder binder = (MyBinder) service;
                myService = binder.getService(); // lấy đối tượng MyService
                isBound = true;
            }
        };

        // Khởi tạo intent
        final Intent intent =
                new Intent(MainActivity.this,
                MyService.class);


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay.setBackgroundResource(R.drawable.ic_stop);
                btnStop.setBackgroundResource(R.drawable.ic_stop);
                    // Bắt đầu một service sủ dụng bind
                bindService(intent, connection, Context.BIND_AUTO_CREATE);
                // Đối thứ ba báo rằng Service sẽ tự động khởi tạo
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnStop.setBackgroundResource(R.drawable.ic_pause);

                // Nếu Service đang hoạt động;
                if(isBound){
                    // Tắt Service
                    unbindService(connection);
                    isBound = false;
                }
            }
        });

//        btFast.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // nếu service đang hoạt động
//                if(isBound){
//                    // tua bài hát
//                    myService.fastForward();
//                }else{
//                    Toast.makeText(MainActivity.this,
//                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        findViewById(R.id.btStart).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(isBound){
//                    // tua bài hát
//                    myService.fastStart();
//                }else{
//                    Toast.makeText(MainActivity.this,
//                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }
}