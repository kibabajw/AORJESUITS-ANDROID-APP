package org.easternafricajesuits.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.archivum.ArchivumModel;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.databinding.FragmentHistoricalBinding;
import org.easternafricajesuits.models.HeadersModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.sweettoast.SweetToast;

public class HistoricalFragment extends Fragment {

    private TextView textViewTitle;
    private TextView tv_historical_body;
    private ShapeableImageView shapeableImageViewHistoricalImage;
    private WebView webViewVideo;
    private Button btn_prev;
    private Button btn_next;
    private ProgressDialog dialog;
    private FragmentHistoricalBinding binding;
    private Integer historical_item_id;
    public HistoricalFragment() {
        // Required empty public constructor
    }

    private void setHistorical_item_id(Integer historical_item_id) {
        this.historical_item_id = historical_item_id;
    }

    private Integer getHistorical_item_id() {
        return historical_item_id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoricalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        textViewTitle = binding.historicalTopTitle;
        tv_historical_body = binding.historicalBody;
        shapeableImageViewHistoricalImage = binding.imgHistoricalImage;
        webViewVideo = binding.historicalWebview;

        dialog = new ProgressDialog(getContext());

        btn_prev = binding.btnHistoricalPrev;
        btn_next = binding.btnHistoricalNext;

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();

                fetcharchivum(getHistorical_item_id() - 1);
            }

        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();

                fetcharchivum(getHistorical_item_id() + 1);
            }
        });

        return view;
    }

    private void showdialog() {
        dialog.setMessage("Please wait...");
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetcharchivum(0);
    }

    private void fetcharchivum(int itemid) {
        Call<ArchivumModel> call = RetrofitClient.getInstance().getApi().getArchivum(String.valueOf(itemid));
        call.enqueue(new Callback<ArchivumModel>() {
            @Override
            public void onResponse(Call<ArchivumModel> call, Response<ArchivumModel> response) {
                int resCode = response.code();

                if (resCode == 200) {
                    if (getContext() != null) {
                        setHistorical_item_id(response.body().getId());

                        if (getHistorical_item_id() == 1) {
                            btn_next.setVisibility(View.VISIBLE);
                        } else if (getHistorical_item_id() == 2) {
                            btn_prev.setVisibility(View.VISIBLE);
                            btn_next.setVisibility(View.GONE);
                        } else if (getHistorical_item_id() > 2) {
                            btn_prev.setVisibility(View.VISIBLE);
                            btn_next.setVisibility(View.VISIBLE);
                        }

                        textViewTitle.setText(response.body().getTitle());
                        tv_historical_body.setText(response.body().getBody());

//                        Log.i("CHEKIPICS", AllConstants.IMAGE_URL + response.body().getPicture());

                        Glide.with(getContext())
                                .load(AllConstants.IMAGE_URL + response.body().getPicture())
                                .centerCrop()
                                .into(shapeableImageViewHistoricalImage);

                        webViewVideo.loadData(response.body().getVideo(), "text/html", "utf-8");
                        webViewVideo.getSettings().setJavaScriptEnabled(true);
                        webViewVideo.setWebChromeClient(new WebChromeClient());

                        dialog.dismiss();
                    }
                } else {
                    SweetToast.info(getContext(), "No items found at the moment", 3000);
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ArchivumModel> call, Throwable t) {
                SweetToast.error(getContext(), "Sorry, we could not fetch Archivum items", 2000);
                dialog.dismiss();
            }
        });
    }

    private void fetchhistorical() {
        Call<HeadersModel> call = RetrofitClient.getInstance().getApi().getHistorical();
        call.enqueue(new Callback<HeadersModel>() {
            @Override
            public void onResponse(Call<HeadersModel> call, Response<HeadersModel> response) {
                if (response.code() == 200) {

                    if (getContext() != null) {
                        setHistorical_item_id(response.body().getItem_id());

                        textViewTitle.setText(response.body().getPageheading());
                        tv_historical_body.setText(response.body().getPagesubtitle());

                        Glide.with(getContext())
                                .load(AllConstants.IMAGE_URL + response.body().getPageimage())
                                .centerCrop()
                                .into(shapeableImageViewHistoricalImage);

                        webViewVideo.loadData(response.body().getPagetitle(), "text/html", "utf-8");
                        webViewVideo.getSettings().setJavaScriptEnabled(true);
                        webViewVideo.setWebChromeClient(new WebChromeClient());
                    }

                }
            }

            @Override
            public void onFailure(Call<HeadersModel> call, Throwable t) {
                Toast.makeText(getContext(), "An error occured", Toast.LENGTH_SHORT).show();
            }
        });
    }
}