package akk;

public class PhrasesAndConstants {

    private PhrasesAndConstants() {
        throw new IllegalStateException("Utility class");
    }


    public static final String CARD_TITLE = "akk";

    //    public static final String WELCOME = "Hallo. Ich bin dein Kalender. Wir werden Termine für dich verwalten und dich an sie erinnern.";
    public static final String HELP = "Du kannst \"Trage einen Termin ein\" sagen, um einen Termin einzutragen, " +
            "\"Lösche einen Termin\", um einen Termin zu löschen, \"Ändere einen Termin \", um einen Termin zu ändern, " +
            "\"Trage eine Notiz ein\", um eine Notiz einzutragen, \"Lösche eine Notiz\", um eine Notiz zu löschen, " +
            "\"Welche Notizen habe ich\", um die Notizen zu einem Termin ausgeben zu lassen, " +
            "\"Welche Termine habe ich am \", um die Termine eines bestimmten Tages abzufragen, " +
            "\"Welche Termine habe ich zum\", um die Termine mit einem bestimmten Betreff abzufragen\", " +
            "\"Hilfe\", um diese Hilfe abzufragen\", und \"Stop\", um den Skill zu beenden";
    //    public static final String HELP_REPROMPT = "Bitte sag deinen Termin.";
    public static final String CANCEL_AND_STOP = "Auf Wiedersehen";
    public static final String FALLBACK = "Tut mir leid, das weiss ich nicht. Sage einfach Hilfe.";


    //Slots
    public static final String SUBJECT = "Subject";
    public static final String DATE = "Date";
    public static final String STREET = "Street";
    public static final String CITY = "City";
    public static final String TIME = "Time";
    public static final String PERSON = "Person";
    public static final String REMINDER = "Reminder";
    public static final String NOTE = "Note";

    public static final String SUBJECT_TO_DELETE = "SubjectToDelete";
    public static final String DATE_TO_DELETE = "DateToDelete";
    /*public static final String STREET_TO_DELETE = "StreetToDelete";
    public static final String CITY_TO_DELETE = "CityToDelete";
    public static final String TIME_TO_DELETE = "TimeToDelete";
    public static final String PERSON_TO_DELETE = "PersonToDelete";
    public static final String REMINDER_TO_DELETE = "ReminderToDelete";
    public static final String NOTE_TO_DELETE = "NoteToDelete";*/
    public static final String ATTRIBUTE_TO_CHANGE = "AttributeToChange";
    public static final String NEW_VALUE = "newValue";
    public static final String WITH_NOTES = "WithNotes";

}