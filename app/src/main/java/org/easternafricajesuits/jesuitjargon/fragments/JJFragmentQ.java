package org.easternafricajesuits.jesuitjargon.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.easternafricajesuits.databinding.JjFragmentQBinding;

public class JJFragmentQ extends Fragment {

    private JjFragmentQBinding binding;

    public JJFragmentQ() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = JjFragmentQBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
}
