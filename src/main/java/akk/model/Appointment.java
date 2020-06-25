package akk.model;

import java.util.ArrayList;
import java.util.List;


public class Appointment {



    private String subject;
    private String city;
    private String street;
    private String time;
    private String date;
    private String person;
    private main.java.akk.model.Reminder reminder;
    //private List<Note> notes;
    private List<String> notes;



    public Appointment(String subject, String city, String street, String time, String date, String person,main.java.akk.model.Reminder reminder, List</*Note*/String> notes) {
        this.subject = subject;
        this.city = city;
        this.street = street;
        this.time = time;
        this.date = date;
        this.person = person;
        this.reminder = reminder;
        this.notes = notes;
        if (notes == null) {
            this.notes = new ArrayList<>();
        }

    }
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public main.java.akk.model.Reminder getReminder() {
        return reminder;
    }

    public void setReminder(main.java.akk.model.Reminder reminder) {
        this.reminder = reminder;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void addNote(String note) {
        notes.add(note);
    }
    public void removeNote(String note) {
        notes.remove(note);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Termin für " + getSubject());
        if (getPerson() != null) {
            builder.append( " von " + getPerson());
        }
        if (getCity() != null) {
            builder.append(" in " + getCity());
        }
        if (getStreet() != null) {
            builder.append(" Straße " + getStreet());
        }
        if (getDate() != null) {
            builder.append(" am " + getDate());
        }
        if (getTime() != null) {
            builder.append(" um " + getTime());
        }
        return builder.toString();
    }
}