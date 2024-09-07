package org.easternafricajesuits.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.easternafricajesuits.MainActivity;
import org.easternafricajesuits.adusum.AdusumAccountActivity;
import org.easternafricajesuits.adusum.AdusumLoginActivity;
import org.easternafricajesuits.adusum.AdusumregisterActivity;
import org.easternafricajesuits.adusum.ProbationActivity;
import org.easternafricajesuits.adusum.databases.AdusumAccountSQLHelper;
import org.easternafricajesuits.adusum.databases.AdusumDatabaseContract;
import org.easternafricajesuits.adusum.shukran.ShukranActivity;
import org.easternafricajesuits.databinding.FragmentHomeLibraryBinding;
import org.easternafricajesuits.jesuitjargon.JesuitJargonActivity;
import org.easternafricajesuits.spiritualexercises.SpiritualExercisesActivity;
import org.easternafricajesuits.thepilgrim.ThePilgrimActivity;

import xyz.hasnat.sweettoast.SweetToast;


public class HomeFragmentLibrary extends Fragment {

    private FragmentHomeLibraryBinding binding;
    private AdusumAccountSQLHelper mAdusumAccountSQLHelper;
    public HomeFragmentLibrary() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdusumAccountSQLHelper = new AdusumAccountSQLHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeLibraryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonOpenSpiritualExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SpiritualExercisesActivity.class));
            }
        });

        binding.buttonOpenThePilgrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ThePilgrimActivity.class));
            }
        });

        binding.buttonOpenJesuitJargon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), JesuitJargonActivity.class));
            }
        });

        binding.buttonOpenPeriodicals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ShukranActivity.class));
            }
        });
    }
}