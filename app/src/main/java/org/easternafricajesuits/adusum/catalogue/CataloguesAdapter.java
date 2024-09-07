package org.easternafricajesuits.adusum.catalogue;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.R;
import org.easternafricajesuits.WebViewActivity;
import org.easternafricajesuits.adusum.model.CatalogueModel;

import java.util.List;

import xyz.hasnat.sweettoast.SweetToast;

public class CataloguesAdapter extends RecyclerView.Adapter<CataloguesAdapter.ViewHolder> implements View.OnCreateContextMenuListener {

    private DownloadManager downloadManager;
    private List<CatalogueModel> items;
    private Context context;
    private String selected_file_link;

    public void setSelected_file_link(String selected_file_link) {
        this.selected_file_link = selected_file_link;
    }

    public String getSelected_file_link() {
        return selected_file_link;
    }

    public CataloguesAdapter(Context context, List<CatalogueModel> itemArrayList) {
        this.context = context;
        this.items = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_catalogues, viewGroup, false);
        view.setOnCreateContextMenuListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewCatalogueName.setText(items.get(position).getCatalogue_name());

        holder.textViewCatalogueName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetToast.info(context, "Long press for file actions", 2000);
            }
        });

        holder.textViewCatalogueName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setSelected_file_link(items.get(position).getCatalogue_link());
                return false;
            }
        });
    }

    private void downloadThisfile(String doc_link) {
        doc_link = AllConstants.IMAGE_URL + doc_link;

        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(doc_link);
        SweetToast.info(context, "Download started...", 2000);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference = downloadManager.enqueue(request);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem item_open = menu.add(Menu.NONE, 1, 1, "Open");
        MenuItem item_download = menu.add(Menu.NONE, 2, 2, "Download");
        item_open.setOnMenuItemClickListener(onEditMenu);
        item_download.setOnMenuItemClickListener(onEditMenu);
    }

    private void openfileinwebview(String fileurl) {
        SweetToast.info(context, "Opening Catalogue", 2000);
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("CATALOGUE_NAME", fileurl);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case 1:
                    openfileinwebview(getSelected_file_link());
                    break;
                case 2:
                    downloadThisfile(getSelected_file_link());
                    break;
            }
            return false;
        }
    };

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewCatalogueName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCatalogueName = (TextView) itemView.findViewById(R.id.list_item_catalogue_name);
        }
    }
}
