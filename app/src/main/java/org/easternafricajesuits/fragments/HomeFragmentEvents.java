package org.easternafricajesuits.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.easternafricajesuits.R;
import org.easternafricajesuits.adapters.FragEventsAdapter;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.databinding.HomeFragmentEventsBinding;
import org.easternafricajesuits.models.EventsModel;
import org.easternafricajesuits.models.EventsreceivedModel;
import org.easternafricajesuits.utils.NetworkChangeListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.sweettoast.SweetToast;

public class HomeFragmentEvents extends Fragment {

    private HomeFragmentEventsBinding binding;
    private Spinner spinnerMonths = null;
    private Spinner spinnerYears = null;
    private DateFormat currentMonth;
    private DateFormat currentYear;
    private DateFormat currentDay;
    private Date date;

    private RecyclerView eventsRecyclerView;
    private RecyclerView.Adapter eventsAdapter;
    private List<EventsModel> eventsModel = new ArrayList<>();

    private String selectedMonth = "";
    private String selectedYear = "";
    private String thisYear = null;
    private String thisMonth = null;
    private DateFormat timenow;
    private LinearLayout empty_view_events;

    private NetworkChangeListener mReceiver = new NetworkChangeListener();
    private int selectioncounter = 0;

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

    public HomeFragmentEvents() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentEventsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        empty_view_events = binding.emptyViewEvents;

        currentMonth = new SimpleDateFormat("MMMM");
        currentYear = new SimpleDateFormat("yyyy");
        currentDay = new SimpleDateFormat("d");

        date = new Date();

        // set month-text and month-digit
        thisYear = currentYear.format(date);
        thisMonth = currentMonth.format(date).toLowerCase();
        String today = currentDay.format(date);

        // end set month-text and month-digit

        eventsRecyclerView = binding.homeFragEventsRecyclerview;
        eventsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventsAdapter = new FragEventsAdapter(eventsModel, getContext(), R.layout.events_frag_recycler_item);
        eventsRecyclerView.setAdapter(eventsAdapter);


        // setup Spinner for months
        spinnerMonths = binding.spinnerEventsMonth;
        ArrayAdapter<CharSequence> adapterMonths = ArrayAdapter.createFromResource(getContext(), R.array.months_array, R.layout.support_simple_spinner_dropdown_item);
        adapterMonths.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerMonths.setAdapter(adapterMonths);

        for (int i = 0; i < adapterMonths.getCount(); ++i) {
            if (String.valueOf(adapterMonths.getItem(i)).toLowerCase().equals(thisMonth)) {
                spinnerMonths.setSelection(i);
                selectedMonth = String.valueOf(adapterMonths.getItem(i));
            }
        }

        spinnerMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth = String.valueOf(parent.getItemAtPosition(position));
                if (selectioncounter >= 2) {
//                    fetchFullmonthEvents(selectedMonth, selectedYear);
                    fetchFullmonthEvents(monthsarray[spinnerMonths.getSelectedItemPosition()], selectedYear);
                } else {
                    ++selectioncounter;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // spinner for years
        spinnerYears = binding.spinnerEventsYear;
        ArrayAdapter<CharSequence> adapterYears = ArrayAdapter.createFromResource(getContext(), R.array.years_array, R.layout.support_simple_spinner_dropdown_item);
        adapterYears.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerYears.setAdapter(adapterYears);

        for (int j = 0; j < adapterYears.getCount(); ++j) {
            if (String.valueOf(adapterYears.getItem(j)).equals(thisYear)) {
                spinnerYears.setSelection(j);
                selectedYear = String.valueOf(adapterYears.getItem(j));
            }
        }

        spinnerYears.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = String.valueOf(parent.getItemAtPosition(position));
                if (selectioncounter >= 2) {
//                    fetchFullmonthEvents(selectedMonth, selectedYear);
                    fetchFullmonthEvents(monthsarray[spinnerMonths.getSelectedItemPosition()], selectedYear);
                } else {
                    ++selectioncounter;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        fetchEvents(thisMonth, thisYear);
        fetchEvents(monthsarray[spinnerMonths.getSelectedItemPosition()], selectedYear);
//        prayers(selectedMonth, selectedYear);
    }

    /*private void prayers(String selectedMonth, String selectedYear) {
        Call<Apostleship> call = RetrofitClient.getInstance().getApi().getApostleship(selectedMonth, selectedYear);
        call.enqueue(new Callback<Apostleship>() {
            @Override
            public void onResponse(Call<Apostleship> call, Response<Apostleship> response) {
                if (response.code() == 200) {

                    if (getContext() != null) {
                        binding.txtApostleshipName.setText(response.body().getApostlename());
                        binding.txtApostleshipContent.setText(response.body().getApostleitem());
                    }

                }
            }

            @Override
            public void onFailure(Call<Apostleship> call, Throwable t) {
//                Toast.makeText(getContext(), "An error occured", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void fetchEvents(String whichMonth, String whichYear) {
        Call<EventsreceivedModel> call = RetrofitClient.getInstance().getApi().getEvents(whichMonth, whichYear);

        call.enqueue(new Callback<EventsreceivedModel>() {
            @Override
            public void onResponse(Call<EventsreceivedModel> call, Response<EventsreceivedModel> response) {
                if (response.code() == 200) {
                    empty_view_events.setVisibility(View.GONE);

                    eventsModel.clear();
                    eventsModel.addAll(response.body().getEventsModel());
                    eventsAdapter.notifyDataSetChanged();
                } else {
                    eventsModel.clear();
                    eventsAdapter.notifyDataSetChanged();
                    empty_view_events.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<EventsreceivedModel> call, Throwable t) {
                SweetToast.error(getContext(), getString(R.string.sorry_an_error_occured), 3500);
            }
        });
    }

    private void fetchFullmonthEvents(String whichMonth, String whichYear) {
        Call<EventsreceivedModel> call = RetrofitClient.getInstance().getApi().getFullmonthevents(whichMonth, whichYear);

        call.enqueue(new Callback<EventsreceivedModel>() {
            @Override
            public void onResponse(Call<EventsreceivedModel> call, Response<EventsreceivedModel> response) {
                if (response.code() == 200) {
                    empty_view_events.setVisibility(View.GONE);

                    eventsModel.clear();
                    eventsModel.addAll(response.body().getEventsModel());
                    eventsAdapter.notifyDataSetChanged();
                } else {
                    eventsModel.clear();
                    eventsAdapter.notifyDataSetChanged();
                    empty_view_events.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<EventsreceivedModel> call, Throwable t) {
                SweetToast.error(getContext(), getString(R.string.sorry_an_error_occured), 3500);
            }
        });
    }
    private BroadcastReceiver mLocalReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isConnected = intent.getBooleanExtra(NetworkChangeListener.EXTRA_IS_CONNECTED, false);
            if (!isConnected) {

            } else {
//                prayers(selectedMonth, selectedYear);
//                fetchEvents(selectedMonth, selectedYear);
                fetchEvents(monthsarray[spinnerMonths.getSelectedItemPosition()], selectedYear);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        getActivity().registerReceiver(mReceiver, filter);

        IntentFilter secondFilter = new IntentFilter(NetworkChangeListener.NOTIFY_NETWORK_CHANGE);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mLocalReceiver, secondFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mReceiver);
    }
}
