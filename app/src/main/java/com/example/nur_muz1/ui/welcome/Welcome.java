package com.example.nur_muz1.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.nur_muz1.MainActivity;
import com.example.nur_muz1.R;
import com.example.nur_muz1.databinding.StartScreenBinding;
import com.example.nur_muz1.ui.login.Login;

public class Welcome extends AppCompatActivity {

    private TextView textView;
    private StartScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //скрываем нижнюю панел
        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        binding = StartScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        Button btnGoToSecAct = binding.button1;

        btnGoToSecAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Login.class);
                startActivity(intent);
            }
        });

        textView = binding.textView;
        SpannableString spannableString = new SpannableString("Слушайте музыку уже сейчас — от ваших любимых треков до самых горячих хитов!");
        int color = ContextCompat.getColor(this, R.color.welcome_title);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(color);
        ForegroundColorSpan colorSpan3 = new ForegroundColorSpan(color);
        spannableString.setSpan(colorSpan, 16, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(colorSpan2, 20, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(colorSpan3, 62, 76, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }
}
