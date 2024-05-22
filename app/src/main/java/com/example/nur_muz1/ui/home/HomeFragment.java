package com.example.nur_muz1.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.example.nur_muz1.R;
import com.example.nur_muz1.databinding.HomeBinding;
import com.example.nur_muz1.ui.login.Login;
import com.example.nur_muz1.ui.music_play.MusicPlay;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private HomeBinding homeBinding;
    private FirebaseFirestore db;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;

    View recomended_list;
    String name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeBinding = HomeBinding.inflate(inflater, container, false);
        View root = homeBinding.getRoot();

        db = FirebaseFirestore.getInstance();

        TextView textViewUserName = homeBinding.UserName1;

//        db.collection("Music acc")
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        String userName = "";
//                        StringBuilder userData = new StringBuilder();
//                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                            userName = document.getString("name");
//                            userData.append("Name: ").append(userName).append("\n");
//                        }
//                        textViewUserName.setText(userName);
//                    } else {
//                        textViewUserName.setText("Error getting data from Firestore");
//                    }
//                });

        recomended_list = root.findViewById(R.id.recomended_list);
        linearLayout1 = recomended_list.findViewById(R.id.music1);
        linearLayout2 = recomended_list.findViewById(R.id.music2);

        linearLayout1.setOnClickListener(View -> openMusicPlayFragment(1));
        linearLayout2.setOnClickListener(View -> openMusicPlayFragment(2));

        SharedPreferences sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String userName = sharedPref.getString("username", "Unknown User");

        textViewUserName.setText(userName);

        return root;
    }

    private void openMusicPlayFragment(int songId) {
        MusicPlay newFragment = MusicPlay.newInstance(songId);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment_activity_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homeBinding = null;
    }
}
