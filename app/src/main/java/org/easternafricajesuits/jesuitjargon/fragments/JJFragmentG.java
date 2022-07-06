package org.easternafricajesuits.jesuitjargon.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.easternafricajesuits.databinding.JjFragmentGBinding;

public class JJFragmentG extends Fragment {

    private JjFragmentGBinding binding;

    public JJFragmentG() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = JjFragmentGBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
}
