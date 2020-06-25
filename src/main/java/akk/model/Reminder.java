package main.java.akk.model;

public class Reminder {

    int hourBefore;
    int minBefore;
    String note;

    public Reminder(int hourBefore, int minBefore, String note) {
        this.hourBefore = hourBefore;
        this.minBefore = minBefore;
        this.note = note;
    }

    public int getHourBefore() {
        return hourBefore;
    }

    public void setHourBefore(int hourBefore) {
        this.hourBefore = hourBefore;
    }

    public int getMinBefore() {
        return minBefore;
    }

    public void setMinBefore(int minBefore) {
        this.minBefore = minBefore;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
