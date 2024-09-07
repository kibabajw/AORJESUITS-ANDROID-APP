package org.easternafricajesuits.adusum;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.R;
import org.easternafricajesuits.WebViewActivity;
import org.easternafricajesuits.adusum.catalogue.CatalogueActivity;
import org.easternafricajesuits.adusum.constitution.ConstitutionActivity;
import org.easternafricajesuits.adusum.model.AdusumDocuments;

import java.util.List;

import xyz.hasnat.sweettoast.SweetToast;

public class AdusumAdapter extends RecyclerView.Adapter<AdusumAdapter.ViewHolder> implements View.OnCreateContextMenuListener {
    private DownloadManager downloadManager;

    private List<AdusumDocuments> items;
    private Context context;

    private String selected_file_link;
    private void setSelected_file_link(String selected_file_link) {
        this.selected_file_link = selected_file_link;
    }

    public String getSelected_file_link() {
        return selected_file_link;
    }

    public AdusumAdapter(Context applicationContext, List<AdusumDocuments> itemArrayList) {
        this.context = applicationContext;
        this.items = itemArrayList;
    }

    @NonNull
    @Override
    public AdusumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adusum_list_brother, viewGroup, false);
        view.setOnCreateContextMenuListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdusumAdapter.ViewHolder viewHolder, int i) {
        viewHolder.docname.setText(items.get(i).getDoc_name());

        /*viewHolder.docname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                downloadThisfile(AllConstants.IMAGE_URL + items.get(i).getDoc_file());

                showactionmenu(AllConstants.IMAGE_URL + items.get(i).getDoc_file());
            }
        });*/

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetToast.info(context, "Long press for file actions", 2000);
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setSelected_file_link(items.get(i).getDoc_file());
                return false;
            }
        });
    }


    private void downloadThisfile(String doc_link) {
        doc_link = AllConstants.IMAGE_URL + doc_link;

        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(doc_link);
        SweetToast.info(context, "Download started", 2000);
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
        SweetToast.info(context, "Opening document", 2000);

        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("CATALOGUE_NAME", fileurl);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1:
                        // open file in web view
                        openfileinwebview(getSelected_file_link());
                        break;

                    case 2:
                        // download file to device
                        downloadThisfile(getSelected_file_link());
                        break;
                }
                return true;
            }
    };



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
