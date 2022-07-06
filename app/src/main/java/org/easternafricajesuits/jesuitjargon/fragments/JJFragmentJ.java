package org.easternafricajesuits.jesuitjargon.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.easternafricajesuits.R;
import org.easternafricajesuits.databinding.JjFragmentIBinding;
import org.easternafricajesuits.databinding.JjFragmentJBinding;

public class JJFragmentJ extends Fragment {

    private JjFragmentJBinding binding;

    public JJFragmentJ() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = JjFragmentJBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
}
