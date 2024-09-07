package org.easternafricajesuits.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.R;
import org.easternafricajesuits.models.EventsModel;

import java.util.List;

public class FragEventsAdapter extends RecyclerView.Adapter<FragEventsAdapter.EventsViewHolder> {

    private List<EventsModel> eventsModel;
    private Context context;
    private int rowLayout;

    public FragEventsAdapter(List<EventsModel> eventsModel, Context context, int rowLayout) {
        this.eventsModel = eventsModel;
        this.context = context;
        this.rowLayout = rowLayout;
    }
    public List<EventsModel> getEventsModel() {
        return eventsModel;
    }

    public void setEventsModel(List<EventsModel> eventsModel) {
        this.eventsModel = eventsModel;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getRowLayout() {
        return rowLayout;
    }

    public void setRowLayout(int rowLayout) {
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);

        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
        if (eventsModel.size() > 0) {
            holder.frag_event_content.setText(eventsModel.get(position).getEventName());

            if (eventsModel.get(position).getEventImage() == null) {
                if (eventsModel.get(position).getEventType().equals("birthday")) {
                    holder.frag_event_content.append(", SJ.");
//                    if ((eventsModel.get(position).getEventImage() != null)) {
                    Glide.with(FragEventsAdapter.this.context)
                            .load(R.drawable.icons8birthday64)
                            .into(holder.frag_event_image);
//                    }
                } else if (eventsModel.get(position).getEventType().equals("feast")) {
                    Glide.with(FragEventsAdapter.this.context)
                            .load(R.drawable.icons8breadandrye100purple)
                            .into(holder.frag_event_image);
                } else {
                    // it's a General Event
                    Glide.with(FragEventsAdapter.this.context)
                            .load(R.drawable.icons8normalevent96)
                            .into(holder.frag_event_image);
                }
            } else {
                if ((eventsModel.get(position).getEventImage() != null)) {
                    Glide.with(FragEventsAdapter.this.context)
                            .load(AllConstants.IMAGE_URL + eventsModel.get(position).getEventImage())
                            .placeholder(R.drawable.loadingdots)
                            .into(holder.frag_event_image);
                }
            }


    //        holder.frag_event_location.setText(eventsModel.get(position).getEventLocation());
            holder.frag_event_day.setText(eventsModel.get(position).getEventDay());
            holder.frag_event_name.setText(eventsModel.get(position).getEventMonth());
            holder.frag_event_description.setText(eventsModel.get(position).getEventDescription());
        }
    }

    @Override
    public int getItemCount() {
        return eventsModel.size();
    }


    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout events_frag_linearlayout;

//        TextView frag_event_location;
        ShapeableImageView frag_event_image;

        TextView frag_event_day;
        TextView frag_event_name;
        TextView frag_event_content;
        ReadMoreTextView frag_event_description;

        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);

            events_frag_linearlayout = itemView.findViewById(R.id.events_frag_linearlayout);

//            frag_event_location = itemView.findViewById(R.id.frag_event_location);
            frag_event_image = itemView.findViewById(R.id.frag_event_image);
            frag_event_day = itemView.findViewById(R.id.frag_event_day);
            frag_event_name = itemView.findViewById(R.id.frag_event_month);
            frag_event_content = itemView.findViewById(R.id.frag_event_name);
            frag_event_description = itemView.findViewById(R.id.frag_event_description);
        }
    }

}
