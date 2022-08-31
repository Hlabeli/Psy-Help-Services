package za.ac.uj.pyshelp;

public class EventList {

    String date, disc, duration, title, venue;

    public EventList() {
    }

    public EventList(String date, String disc, String duration, String title, String venue) {
        this.date = date;
        this.disc = disc;
        this.duration = duration;
        this.title = title;
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
