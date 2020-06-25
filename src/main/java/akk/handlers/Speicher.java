package akk.handlers;

import akk.model.Appointment;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.*;
import java.util.stream.Collectors;



public class Speicher {
    public static final String STR_USER_ID = "UserID";
    public static final String STR_DATE_AND_SUBJECT = "Date + Subject";
    public static final String STR_DATE = "Date";
    public static final String STR_SUBJECT = "Subject";
    private static String tableName = "akkData";
    private AmazonDynamoDB amazonDynamoDB;
    private String userID;

    private static String STREET = "Street";
    private static String PERSON = "Person";


    Speicher(AmazonDynamoDB amazonDynamoDB) {
        this.amazonDynamoDB = amazonDynamoDB;
        //only for local test purpose
        this.userID = "1";
    }
    Speicher(String userID) {
        this.userID = userID;
        amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();
    }

    public boolean save(Appointment appointment) {

        Map<String, AttributeValue> map = new HashMap<>();
        map.put(STR_USER_ID, new AttributeValue(userID));
        map.put(STR_DATE_AND_SUBJECT, new AttributeValue(appointment.getDate() + " " + appointment.getSubject()));
        map.put(STR_DATE, new AttributeValue(appointment.getDate()));
        map.put(STR_SUBJECT, new AttributeValue(appointment.getSubject()));
        if (appointment.getCity() != null) {map.put("City", new AttributeValue(appointment.getCity()));}
        if (appointment.getStreet() != null) {map.put(STREET, new AttributeValue(appointment.getStreet()));}
        if (appointment.getTime() != null) {map.put("Time", new AttributeValue(appointment.getTime()));}
        if (appointment.getPerson() != null) {map.put(PERSON, new AttributeValue(appointment.getPerson()));}
        if (appointment.getReminder() != null) {map.put("Reminder", new AttributeValue(appointment.getReminder().toString()));}
        if (appointment.getNotes() != null) {map.put("Notes", new AttributeValue(appointment.getNotes().toString()));}
        try{
            amazonDynamoDB.putItem(tableName, map);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public List<Appointment> get(String attribute, String value) {
       try {
            ScanRequest scanRequest = new ScanRequest(tableName);
            ScanResult scanResult = amazonDynamoDB.scan(scanRequest);

            List<Map<String,AttributeValue>> list = scanResult.getItems().stream()
                    .filter(x -> (x.get(STR_USER_ID).getS()).equals(userID))
                    .filter(x -> x.get(attribute).getS().equals(value))
                    .collect(Collectors.toList());
            return getAppointments(list);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<Appointment> getAll() {
        try {
            ScanRequest scanRequest = new ScanRequest(tableName);
            ScanResult scanResult = amazonDynamoDB.scan(scanRequest);

            List<Map<String,AttributeValue>> list = scanResult.getItems().stream()
                    .filter(x -> (x.get(STR_USER_ID).getS()).equals(userID))
                    .collect(Collectors.toList());

            return getAppointments(list);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    /*
    zu private zurück
     */
    public static List<Appointment> getAppointments(List<Map<String, AttributeValue>> list) {
        List<Appointment> result = new ArrayList<>();
        List<String> notes;
        for(Map<String,AttributeValue> map : list) {
            notes = null;

            String s = map.get("Notes").getS();
            if (!s.equals("[]")) {
                notes = new ArrayList<>();
                s = s.substring(1,s.length() - 1);
                while (s.contains(",")) {
                    int index = s.indexOf(',');
                    String note = s.substring(0, index);
                    notes.add(note);
                    s = s.substring(index + 2);
                } notes.add(s);
            }

            result.add(new Appointment(map.get(STR_SUBJECT).getS(),
                    map.containsKey("City") ? map.get("City").getS() : null,
                    map.containsKey(STREET) ? map.get(STREET).getS() : null,
                    map.containsKey("Time") ? map.get("Time").getS() : null,
                    map.get(STR_DATE).getS(),
                    map.containsKey(PERSON) ? map.get(PERSON).getS() : null,
                    null, notes));
        }

        return result;
    }

    public String delete(Appointment appointment) {

        DeleteItemRequest deleteItemRequest = new DeleteItemRequest().withTableName(tableName)
                .withKey(new AbstractMap.SimpleEntry<>(STR_USER_ID, new AttributeValue().withS(userID)),
                        new AbstractMap.SimpleEntry<>(STR_DATE_AND_SUBJECT, new AttributeValue(appointment.getDate() + " " + appointment.getSubject())));

        try {
            if (get(STR_DATE_AND_SUBJECT, appointment.getDate() + " " + appointment.getSubject()).isEmpty()) {
                return "Nicht vorhanden";
            }
            amazonDynamoDB.deleteItem(deleteItemRequest);
            return "Gelöscht";
        } catch (Exception e) {
            return null;
        }

    }
    

}
