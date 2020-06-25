package akk.handlers;

import akk.model.Appointment;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class DeleteNoteIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("DeleteNoteIntent"));
    }



    @Override
    public Optional<Response> handle(HandlerInput input) {
        String userID = input.getRequestEnvelope().getSession().getUser().getUserId();
        Speicher speicher = new Speicher(userID);
        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();
        Map<String, Object> requestAttributes = input.getAttributesManager().getRequestAttributes();
        if (requestAttributes != null) {
            if (requestAttributes.get("speicher") != null) {
                speicher = (Speicher) requestAttributes.get("speicher");
            }
        }

        String note = slots.get("Note").getValue();
        String subject = slots.get("Subject").getValue();
        String date = slots.get("Date").getValue();

        try {
            Appointment appointment = speicher.get("Date + Subject", date + " " + subject).get(0);
            List<String> notes = appointment.getNotes();
            List<String> matchingNotes = new ArrayList<>();
            String equalNote = null;
            for (String s : notes) {
                if (s.equals(note)) {
                    equalNote = s;
                } else if (s.contains(note)) {
                    matchingNotes.add(s);
                }
            }
            if (equalNote == null && matchingNotes.isEmpty()) {
                return input.getResponseBuilder()
                        .withSpeech("Für " + note + " wurde keine passende Notiz gefunden")
                        .withShouldEndSession(false)
                        .build();
            } else if (equalNote != null) {
                speicher.delete(appointment);
                    appointment.removeNote(equalNote);
                    speicher.save(appointment);
                        return input.getResponseBuilder()
                                .withSpeech("Die Notiz " + equalNote + " wurde gelöscht")
                                .withShouldEndSession(false)
                                .build();
            } else if (matchingNotes.size() == 1) {
                speicher.delete(appointment);
                appointment.removeNote(matchingNotes.get(0));
                speicher.save(appointment) ;
                return input.getResponseBuilder()
                        .withSpeech("Die Notiz " + matchingNotes.get(0) + " wurde gelöscht")
                        .withShouldEndSession(false)
                        .build();

            }  else {
                StringBuilder response = new StringBuilder( "Es wurden mehrere passende Notizen gefunden, ");
                for (String n : matchingNotes) {
                    response.append(n + ", ");
                } response.delete(response.length()-2, response.length());
                response.replace(response.lastIndexOf(","), response.lastIndexOf(",") + 1, " und");

                return input.getResponseBuilder()
                        .withShouldEndSession(false)
                        .withSpeech(response.toString())
                        .build();
            }

        } catch (Exception e) {

            return input.getResponseBuilder()
                    .withSpeech("Du hast am " + date + " keinen Termin zum " + subject)
                    .withShouldEndSession(false)
                    .build();
        }

    }
}
