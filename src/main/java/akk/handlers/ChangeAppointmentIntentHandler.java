package akk.handlers;

import akk.model.Appointment;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;


public class ChangeAppointmentIntentHandler implements RequestHandler {

    private static String NEW_VALUE = "newValue";

    @Override
    public boolean canHandle(HandlerInput input) {

        return input.matches(intentName("ChangeAppointmentIntent"));
    }


    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String,String> time = new HashMap<>();
        time.put("null uhr", null);
        time.put("ein uhr", "01:00");
        time.put("zwei uhr", "02:00");
        time.put("drei uhr", "03:00");
        time.put("vier uhr", "04:00");
        time.put("fünf uhr", "05:00");
        time.put("sechs uhr", "06:00");
        time.put("sieben uhr", "07:00");
        time.put("acht uhr", "08:00");
        time.put("neun uhr", "09:00");
        time.put("zehn uhr", "10:00");
        time.put("elf uhr", "11:00");
        time.put("zwölf uhr", "12:00");
        time.put("dreizehn uhr", "13:00");
        time.put("vierzehn uhr", "14:00");
        time.put("fünfzehn uhr", "15:00");
        time.put("sechzehn uhr", "16:00");
        time.put("siebzehn uhr", "17:00");
        time.put("achtzehn uhr", "18:00");
        time.put("neunzehn uhr", "19:00");
        time.put("zwanzig uhr", "20:00");
        time.put("einundzwanzig uhr", "21:00");
        time.put("zweiundzwanzig uhr", "22:00");
        time.put("dreiundzwanzig uhr", "23:00");
        time.put("vierundzwanzig uhr", "24:00");
        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();
        String userID = input.getRequestEnvelope().getSession().getUser().getUserId();
        Speicher speicher = new Speicher(userID);
        Map<String, Object> requestAttributes = input.getAttributesManager().getRequestAttributes();
        if (requestAttributes != null) {
            if (requestAttributes.get("speicher") != null) {
                speicher = (Speicher) requestAttributes.get("speicher");
            }
        }

        //schlecht zu testen, getMethode gibt mir schon error zurück
        String date = slots.get("Date").getValue();
        String subject = slots.get("Subject").getValue();
        String newValue = "";
        try {
            Appointment appointment = speicher.get("Date + Subject", date + " " + subject).get(0);
            Appointment old = new Appointment(appointment.getSubject(), appointment.getCity(), appointment.getStreet(), appointment.getTime(), appointment.getDate(), appointment.getPerson(), null, null);

            String attributeToChange = slots.get("AttributeToChange").getValue();
            newValue = slots.get(NEW_VALUE).getValue();
            if (newValue.equals("nein")
                    && !(attributeToChange.equals("subject") || attributeToChange.equals("betreff") || attributeToChange.equals("titel"))
                    && !(attributeToChange.equals("date") || attributeToChange.equals("datum") || attributeToChange.equals("tag"))) {
                newValue = "";
            }


            switch (attributeToChange) {
                case "subject":
                case "betreff":
                case "titel":
                    appointment.setSubject(slots.get(NEW_VALUE).getValue());
                    break;
                case "date":
                case "tag":
                case "datum":
                    switch (newValue) {
                        case "heute":
                            newValue = LocalDate.now().toString();
                            break;
                        case "morgen":
                            newValue = LocalDate.now().plusDays(1).toString();
                            break;
                        case "übermorgen":
                            newValue = LocalDate.now().plusDays(2).toString();
                            break;
                        default:
                            return input.getResponseBuilder()
                                    .withSpeech("Das Datum kann leider nur auf heute, morgen oder übermorgen geändert werden")
                                    .withShouldEndSession(false)
                                    .build();
                    }
                    appointment.setDate(newValue);
                    break;
                case "city":
                case "stadt":
                case "ort":
                    appointment.setCity(newValue);
                    break;
                case "street":
                case "straße":
                case "platz":
                    appointment.setStreet(newValue);
                    break;
                case "person":
                    appointment.setPerson(newValue);
                    break;
                case "zeit":
                case "time":
                case "uhrzeit":
                    String tmp = time.get(newValue);
                    appointment.setTime(tmp);
                    break;
                default:
                    return input.getResponseBuilder()
                            .withSpeech(attributeToChange + " " + slots.get(NEW_VALUE).getValue())
                            .withShouldEndSession(false)
                            .build();
            }

            //kommt wohl nie dahin
            if (speicher.delete(old).equals("Nicht vorhanden")) {
                return input.getResponseBuilder()
                        .withSpeech("Termin konnte nicht geändert werden")
                        .withShouldEndSession(false)
                        .build();
            }

            if (!(speicher.save(appointment))) {
                speicher.save(old);
                return input.getResponseBuilder()
                        .withSpeech("Der Termin konnte nicht geändert werden")
                        .withShouldEndSession(false)
                        .build();
            }

            return input.getResponseBuilder()
                    .withSpeech("Du hast den Termin zu " + appointment.toString() + " geändert")
                    .withShouldEndSession(false)
                    .build();

        } catch (Exception e) {
            return input
                    .getResponseBuilder()
                    .withSpeech("Du hast am " + date + " keinen Termin zum " + subject)
                    .withShouldEndSession(false)
                    .build();
        }
    }




}
