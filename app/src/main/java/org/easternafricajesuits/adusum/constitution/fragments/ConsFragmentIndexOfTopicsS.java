package org.easternafricajesuits.adusum.constitution.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.easternafricajesuits.R;
import org.easternafricajesuits.databinding.ConsFragmentIndexOfTopicsSBinding;

public class ConsFragmentIndexOfTopicsS extends Fragment {

    private ConsFragmentIndexOfTopicsSBinding binding;

    public ConsFragmentIndexOfTopicsS() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ConsFragmentIndexOfTopicsSBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.justifiedTextviewIndexTopicS.setText(R.string.str_cons_index_of_topics_s_def);
        binding.justifiedTextviewIndexTopicSCont.setText(R.string.str_cons_index_of_topics_s_def_cont);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
