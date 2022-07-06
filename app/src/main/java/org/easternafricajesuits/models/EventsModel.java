package org.easternafricajesuits.models;

import com.google.gson.annotations.SerializedName;

public class EventsModel {

    @SerializedName("event_name")
    private String eventName;

    @SerializedName("event_location")
    private String eventLocation;

    @SerializedName("event_day")
    private String eventDay;

    @SerializedName("event_month")
    private String eventMonth;

    @SerializedName("event_year")
    private String eventYear;

    @SerializedName("event_time")
    private String eventTime;

    public EventsModel(String eventName, String eventLocation, String eventDay, String eventMonth, String eventYear, String eventTime) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDay = eventDay;
        this.eventMonth = eventMonth;
        this.eventYear = eventYear;
        this.eventTime = eventTime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDay() {
        return eventDay;
    }

    public void setEventDay(String eventDay) {
        this.eventDay = eventDay;
    }

    public String getEventMonth() {
        return eventMonth;
    }

    public void setEventMonth(String eventMonth) {
        this.eventMonth = eventMonth;
    }

    public String getEventYear() {
        return eventYear;
    }

    public void setEventYear(String eventYear) {
        this.eventYear = eventYear;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

}
