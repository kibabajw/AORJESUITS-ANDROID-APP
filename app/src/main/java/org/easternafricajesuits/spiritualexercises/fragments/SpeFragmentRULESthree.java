package org.easternafricajesuits.spiritualexercises.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.easternafricajesuits.R;
import org.easternafricajesuits.databinding.SpeFragmentRulesThreeBinding;

public class SpeFragmentRULESthree extends Fragment {
private SpeFragmentRulesThreeBinding binding;
    public SpeFragmentRULESthree() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SpeFragmentRulesThreeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
}
