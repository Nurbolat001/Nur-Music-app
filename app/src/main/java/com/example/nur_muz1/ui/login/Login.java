package com.example.nur_muz1.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nur_muz1.MainActivity;
import com.example.nur_muz1.R;
import com.example.nur_muz1.databinding.LoginBinding;
import com.example.nur_muz1.ui.registertions.Registertions;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Login extends AppCompatActivity {

    EditText editText;
    EditText editText2;

    ImageButton imageButton;
    CheckBox checkBox;

    LoginBinding loginBinding;

    FirebaseFirestore db;

    String name;

    String password;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        loginBinding = LoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        db = FirebaseFirestore.getInstance();

        imageButton = findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registertions.class);
                startActivity(intent);
            }
        });


        editText = loginBinding.loginname;
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideNavigationBar();
                } else {
                    showNavigationBar();
                }
            }
        });
        getSupportActionBar().hide();

        editText2 = loginBinding.editTextTextPassword;
        editText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Получаем ссылку на imageButton2
                    View imageButton2 = findViewById(R.id.imageButton2);
                    // Передаем фокус на imageButton2
                    showNavigationBar();
                    return true;
                }
                return false;
            }
        });

        imageButton = loginBinding.imageButton2;

        imageButton.setOnClickListener(View -> {
            String name = editText.getText().toString();
            String pass = editText2.getText().toString();

            loginUser(name,pass);
        });

    }


    private void loginUser(String name, String password) {
        db.collection("Music acc")
                .whereEqualTo("name", name)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String userPassword = document.getString("password");
                                if (userPassword.equals(password)) {
                                    Log.d(TAG, "Авторизация успешна для пользователя " + name);
                                    Toast.makeText(Login.this, "Авторизация успешна", Toast.LENGTH_LONG).show();
                                    SharedPreferences sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("username", name);
                                    editor.apply();
                                    startActivity(new Intent(this, MainActivity.class));
                                } else {
                                    Log.d(TAG, "Неверный пароль для пользователя " + name);
                                    Toast.makeText(Login.this, "Неверное имя пользователя или пароль", Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            Log.d(TAG, "Пользователь с именем " + name + " не найден");
                            Toast.makeText(Login.this, "Пользователь с указанным именем не найден", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.w(TAG, "Ошибка при проверке авторизации пользователя", task.getException());
                        Toast.makeText(Login.this, "Ошибка при проверке авторизации пользователя", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void hideNavigationBar() {
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void showNavigationBar() {
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

}