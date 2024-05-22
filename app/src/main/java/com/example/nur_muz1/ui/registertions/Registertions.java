package com.example.nur_muz1.ui.registertions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nur_muz1.ui.login.Login;
import com.example.nur_muz1.databinding.RegisterBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registertions extends AppCompatActivity {


    static final String TAG = "MainActivity";
    ImageButton imageButton;

    ImageButton registerbtn;

    RegisterBinding registerBinding;

    EditText editName;

    EditText editEmail;

    EditText editPassword;

    FirebaseFirestore db;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        registerBinding = RegisterBinding.inflate(getLayoutInflater());
        setContentView(registerBinding.getRoot());


        getSupportActionBar().hide();


        imageButton = registerBinding.imageButton4;
        editName = registerBinding.registername;
        editEmail = registerBinding.editTextTextEmailAddress;
        editPassword = registerBinding.editTextTextPassword;

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registertions.this, Login.class);
                startActivity(intent);
            }
        });

        registerbtn = registerBinding.registerbtn;

        registerbtn.setOnClickListener(View -> {
            String n = editName.getText().toString();
            String e = editEmail.getText().toString();
            String p = editPassword.getText().toString();
            db = FirebaseFirestore.getInstance();
            addUser(n,e,p);
        });

    }

    private void addUser(String name, String email, String pass) {
        db.collection("Music acc")
                .whereEqualTo("name", name)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().isEmpty()) {
                            addData(name, email, pass);
                            Log.d(TAG, "Пользователь успешно создан");
                            Toast.makeText(Registertions.this, "Пользователь успешно создан", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(this, Login.class));
                        } else {
                            Log.d(TAG, "Пользователь уже существует");
                            Toast.makeText(Registertions.this, "Пользователь с именем " + name + " уже существует", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.w(TAG, "Ошибка при проверке существования пользователя", task.getException());
                        Toast.makeText(Registertions.this, "Ошибка при проверке существования пользователя", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void addData(String name, String email, String pass) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("email", email);
        userData.put("password", pass);

        db.collection("Music acc")
                .document(name)
                .set(userData)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "Данные успешно добавлены в документ с ID: " + documentReference);
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Ошибка при добавлении данных в документ", e);
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