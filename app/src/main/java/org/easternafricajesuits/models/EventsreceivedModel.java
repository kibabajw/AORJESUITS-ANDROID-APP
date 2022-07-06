package org.easternafricajesuits.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventsreceivedModel {


    @SerializedName("events")
    private List<EventsModel> eventsModel;

    public List<EventsModel> getEventsModel() {
        return eventsModel;
    }

    public void setEventsModel(List<EventsModel> eventsModel) {
        this.eventsModel = eventsModel;
    }

}
