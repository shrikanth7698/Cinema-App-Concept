package com.shrikanthravi.cinemaappconcept.data;

/**
 * Created by shrikanthravi on 01/03/18.
 */

public class MovieTime {
    String time;
    int totalSeats;
    int availableSeats;
    boolean selected=false;

    public MovieTime(String time, int totalSeats, int availableSeats) {
        this.time = time;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
