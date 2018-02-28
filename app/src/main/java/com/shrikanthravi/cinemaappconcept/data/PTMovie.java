package com.shrikanthravi.cinemaappconcept.data;

/**
 * Created by shrikanthravi on 28/02/18.
 */

public class PTMovie {

    String type;
    String url;

    public PTMovie(String type, String url) {
        this.type = type;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
