package org.easternafricajesuits.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.R;
import org.easternafricajesuits.databinding.HomeFragmentAboutUsBinding;

public class HomeFragmentAboutus extends Fragment {

    private HomeFragmentAboutUsBinding binding;

    public HomeFragmentAboutus() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentAboutUsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.linkFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(AllConstants.facebooklink);
            }
        });

        binding.linkYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(AllConstants.youtubelink);
            }
        });

        binding.linkTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(AllConstants.twitterlink);
            }
        });

        binding.linkWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(AllConstants.websitelink);
            }
        });

        binding.linkInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(AllConstants.instagramlink);
            }
        });
    }

    private void openLink(String strLink) {
        Uri uri = Uri.parse(strLink);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        startActivity(intent);
    }
}
