package org.easternafricajesuits.models;

import com.google.gson.annotations.SerializedName;

public class Thoughts {

    @SerializedName("prayer_month")
    private String thoughtmonth;
    @SerializedName("prayer_day")
    private String thoughtday;
    @SerializedName("prayer_item")
    private String thoughtitem;

    public Thoughts(String thoughtmonth, String thoughtday, String thoughtitem) {
        this.thoughtmonth = thoughtmonth;
        this.thoughtday = thoughtday;
        this.thoughtitem = thoughtitem;
    }

    public String getThoughtmonth() {
        return thoughtmonth;
    }

    public String getThoughtday() {
        return thoughtday;
    }

    public String getThoughtitem() {
        return thoughtitem;
    }
}
