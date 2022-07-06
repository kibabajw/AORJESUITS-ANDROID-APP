package org.easternafricajesuits.adusum;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.R;
import org.easternafricajesuits.adusum.constitution.ConstitutionActivity;
import org.easternafricajesuits.adusum.model.AdusumDocuments;

import java.util.List;

public class AdusumAdapter extends RecyclerView.Adapter<AdusumAdapter.ViewHolder> {
    private DownloadManager downloadManager;

    private List<AdusumDocuments> items;
    private Context context;

    public AdusumAdapter(Context applicationContext, List<AdusumDocuments> itemArrayList) {
        this.context = applicationContext;
        this.items = itemArrayList;
    }

    @NonNull
    @Override
    public AdusumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adusum_list_brother, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdusumAdapter.ViewHolder viewHolder, int i) {
        viewHolder.docname.setText(items.get(i).getDoc_name());

        viewHolder.docname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadThisfile(AllConstants.IMAGE_URL + items.get(i).getDoc_file());
            }
        });
    }

    private void downloadThisfile(String doc_link) {
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(doc_link);
        Toast.makeText(context, "Download started", Toast.LENGTH_SHORT).show();
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference = downloadManager.enqueue(request);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView docname;

        public ViewHolder(View view) {
            super(view);
            docname = (TextView) view.findViewById(R.id.list_item_doc_name);
        }

    }


}
