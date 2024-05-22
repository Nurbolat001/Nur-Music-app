package com.example.nur_muz1.ui.splash;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nur_muz1.R;
import com.example.nur_muz1.ui.welcome.Welcome;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //скрываем нижнюю панел
        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); //Появляется поверх игры и исчезает
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();


        VideoView videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.free7; // Замените your_video на имя вашего видеофайла
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.start();

        // Переход к основной активности после завершения воспроизведения видео
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Intent intent = new Intent(SplashScreenActivity.this, Welcome.class);
                startActivity(intent);
            }
        });
    }

}
