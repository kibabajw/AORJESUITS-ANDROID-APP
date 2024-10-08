package org.easternafricajesuits.fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import org.easternafricajesuits.R;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.databinding.HomeFragmentPrayersBinding;
import org.easternafricajesuits.models.Thoughts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.sweettoast.SweetToast;

public class HomeFragmentPrayers extends Fragment {

    private HomeFragmentPrayersBinding binding;
    private Spinner spinnerPrayerMonth = null;
    private Spinner spinnerPrayerDay = null;
    private String selectedDay = "";
    private String selectedMonth = "";
    private DateFormat currenDay;
    private DateFormat currentMonth;

    private Date date;

    private String[] monthsarray = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };


    public HomeFragmentPrayers() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentPrayersBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerPrayerDay = binding.spinnerPrayerDay;
        spinnerPrayerMonth = binding.spinnerPrayerMonth;

        // Deal with dates and months
        currenDay = new SimpleDateFormat("d");
        currentMonth = new SimpleDateFormat("MMMM");
        date = new Date();

        String thisDay = currenDay.format(date);
        String thisMonth = currentMonth.format(date).toLowerCase();


        // Set up spinner for days
        ArrayAdapter<CharSequence> adapterDays = ArrayAdapter.createFromResource(getContext(), R.array.days, R.layout.support_simple_spinner_dropdown_item);
        adapterDays.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerPrayerDay.setAdapter(adapterDays);

        for (int i = 0; i < adapterDays.getCount(); ++i) {
            if (String.valueOf(adapterDays.getItem(i)).equals(thisDay)) {
                spinnerPrayerDay.setSelection(i);
                selectedDay = String.valueOf(adapterDays.getItem(i));
            }
        }

        spinnerPrayerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDay = String.valueOf(parent.getItemAtPosition(position));
                fetchprayer(selectedDay, selectedMonth);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Set up spinner for month
        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(getContext(), R.array.months_array, R.layout.support_simple_spinner_dropdown_item);
        adapterMonth.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerPrayerMonth.setAdapter(adapterMonth);

        for (int j = 0; j < adapterMonth.getCount(); ++j) {
            if (String.valueOf(adapterMonth.getItem(j)).toLowerCase().equals(thisMonth)) {
                spinnerPrayerMonth.setSelection(j);
                selectedMonth = String.valueOf(adapterMonth.getItem(j));
            }
        }

        spinnerPrayerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth = String.valueOf(parent.getItemAtPosition(position));
                fetchprayer(selectedDay, selectedMonth);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fetchprayer(selectedDay, selectedMonth);

    }

    private void fetchprayer(String day, String month) {
        Call<Thoughts> call = RetrofitClient.getInstance().getApi().getThoughts(day, monthsarray[spinnerPrayerMonth.getSelectedItemPosition()]);
        call.enqueue(new Callback<Thoughts>() {
            @Override
            public void onResponse(Call<Thoughts> call, Response<Thoughts> response) {
                if (response.code() == 200) {
                    binding.txtprayermonth.setText(response.body().getThoughtmonth());
                    binding.txtprayerday.setText(response.body().getThoughtday());
                    binding.txtprayercontent.setText(response.body().getThoughtitem());
                }
            }

            @Override
            public void onFailure(Call<Thoughts> call, Throwable t) {
                SweetToast.error(getContext(), "Could not fetch prayers", 3000);
            }
        });
    }

}
