package org.easternafricajesuits.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.frag_event_day.setText(eventsModel.get(position).getEventDay());
        holder.frag_event_name.setText(eventsModel.get(position).getEventMonth());
        holder.frag_event_content.setText(eventsModel.get(position).getEventName());
    }

    @Override
    public int getItemCount() {
        return eventsModel.size();
    }


    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout events_frag_linearlayout;
        TextView frag_event_day;
        TextView frag_event_name;
        TextView frag_event_content;

        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);

            events_frag_linearlayout = itemView.findViewById(R.id.events_frag_linearlayout);
            frag_event_day = itemView.findViewById(R.id.frag_event_day);
            frag_event_name = itemView.findViewById(R.id.frag_event_month);
            frag_event_content = itemView.findViewById(R.id.frag_event_name);
        }
    }

}
