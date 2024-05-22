package com.example.nur_muz1.ui.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.nur_muz1.databinding.UserBinding;

public class User extends Fragment {

    private UserBinding userBinding;

    TextView textViewUserName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);


        userBinding = UserBinding.inflate(inflater, container, false);
        View root = userBinding.getRoot();
        textViewUserName = userBinding.userName2;
        SharedPreferences sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String userName = sharedPref.getString("username", "Unknown User");

        textViewUserName.setText(userName);

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        userBinding = null;
    }

}
