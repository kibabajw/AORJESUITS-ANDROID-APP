package org.easternafricajesuits.fragments;

import android.Manifest;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import org.easternafricajesuits.R;
import org.easternafricajesuits.adapters.NewsAdapter;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.databinding.HomeFragmentNewsBinding;
import org.easternafricajesuits.models.NewsModel;
import org.easternafricajesuits.models.NewsReceivedModel;
import org.easternafricajesuits.rest.EndPoints;
import org.easternafricajesuits.utils.Client;
import org.easternafricajesuits.utils.NetworkChangeListener;
import org.easternafricajesuits.utils.PaginationScrollListener;
import org.easternafricajesuits.utils.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentNews extends Fragment {

    private HomeFragmentNewsBinding binding;
    private NetworkChangeListener mReceiver = new NetworkChangeListener();

    private static final int PERMISSIONS_INTERNET_REQUEST_CODE = 11;
    private RecyclerView newsRecyclerView;
    private RecyclerView.Adapter newsAdapter;
    private List<NewsModel> newsModel = new ArrayList<>();
    private CoordinatorLayout scrollView;

    // start
    NewsAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView rv;
    ProgressBar progressBar;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 10;
    private int currentPage = PAGE_START;
    private Client client;

    // end
    private Context context;
    public HomeFragmentNews() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentNewsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scrollView = binding.homefragnewsRootLayout;
        newsRecyclerView = binding.homeFragNewsRecyclerView;
        // start ----------

        rv = binding.homeFragNewsRecyclerView;
        progressBar = binding.fraghomenewsProgressbar;
        adapter = new NewsAdapter(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);

        rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (checkNetwork() == true) {
                            loadNextPage(adapter.getItemCount());
                        } else {
                            Toast.makeText(getContext(), "You seem to be offline", Toast.LENGTH_LONG).show();
                        }
                    }
                }, 500);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        // end ------------

        if (checkNetwork() == true) {
            loadFirstPage(0);
//            askforpermission();
        } else {
            Toast.makeText(getContext(), "You seem to be offline", Toast.LENGTH_LONG).show();
        }
    }

    private void loadFirstPage(int firstID) {
        Call<NewsReceivedModel> call = RetrofitClient.getInstance().getApi().getNews(firstID);
        call.enqueue(new Callback<NewsReceivedModel>() {
            @Override
            public void onResponse(Call<NewsReceivedModel> call, Response<NewsReceivedModel> response) {
                if (response.code() == 200) {
                    List<NewsModel> results = fetchResults(response);
                    progressBar.setVisibility(View.GONE);
                    adapter.addAll(results);

                    if (currentPage <= TOTAL_PAGES) {
                        adapter.addLoadingFooter();
                    } else {
                        isLastPage = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsReceivedModel> call, Throwable t) {
            }
        });
    }

    private void loadNextPage(int nextID) {
        Call<NewsReceivedModel> call = RetrofitClient.getInstance().getApi().getNews(nextID);
        call.enqueue(new Callback<NewsReceivedModel>() {
            @Override
            public void onResponse(Call<NewsReceivedModel> call, Response<NewsReceivedModel> response) {
                if (response.code() == 200) {
                    adapter.removeLoadingFooter();
                    isLoading = false;

                    List<NewsModel> results = fetchResults(response);
                    adapter.addAll(results);

                    if (results.size() == 0) {
                        progressBar.setVisibility(View.GONE);
                    }

                    if (currentPage != TOTAL_PAGES) {
                        adapter.addLoadingFooter();
                    } else {
                        isLastPage = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsReceivedModel> call, Throwable t) {
            }
        });
    }

    private List<NewsModel> fetchResults(Response<NewsReceivedModel> response) {
        NewsReceivedModel receivedModel = response.body();
        return receivedModel.getNewsModel();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public boolean checkNetwork(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void askforpermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                loadtheNews();
            } else {
                requestInternetPermission();
            }
        } else {
            loadtheNews();
        }
    }

    private void requestInternetPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.INTERNET)) {
            new AlertDialog.Builder(getActivity()).setTitle("Internet permission")
                    .setMessage("The application requires internet permission, click Ok to allow")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission.INTERNET }, PERMISSIONS_INTERNET_REQUEST_CODE);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission.INTERNET }, PERMISSIONS_INTERNET_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_INTERNET_REQUEST_CODE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadtheNews();
            } else {
                Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadtheNews() {
        Call<NewsReceivedModel> call = RetrofitClient.getInstance().getApi().getNews(0);

        call.enqueue(new Callback<NewsReceivedModel>() {
            @Override
            public void onResponse(Call<NewsReceivedModel> call, Response<NewsReceivedModel> response) {
                if (response.code() == 200) {
                    newsModel.clear();
                    newsModel.addAll(response.body().getNewsModel());
                    newsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NewsReceivedModel> call, Throwable t) {
            }
        });

    }

    private BroadcastReceiver mLocalReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isConnected = intent.getBooleanExtra(NetworkChangeListener.EXTRA_IS_CONNECTED, false);
            if (!isConnected) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Notification notification = new NotificationCompat.Builder(context, "channel101")
                            .setSmallIcon(R.drawable.ic_wifi_off)
                            .setContentTitle("Internet connection")
                            .setContentText("You seem to be offline")
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .build();
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                    notificationManagerCompat.notify(0, notification);
                } else {
                        Snackbar.make(scrollView, "You seem to be offline", Snackbar.LENGTH_LONG).show();
                }
            } else {
//                loadtheNews();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        getActivity().registerReceiver(mReceiver, filter);

        IntentFilter sendFilter = new IntentFilter(NetworkChangeListener.NOTIFY_NETWORK_CHANGE);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mLocalReceiver, sendFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mReceiver);
    }

}
