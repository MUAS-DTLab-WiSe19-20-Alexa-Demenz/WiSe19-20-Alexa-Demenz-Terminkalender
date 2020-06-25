package akk.handlers;

import akk.model.Appointment;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.*;
import com.amazon.ask.model.services.*;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class CreateNoteIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("CreateNoteIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String userID = input.getRequestEnvelope().getSession().getUser().getUserId();
        Speicher speicher = new Speicher(userID);
        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
        Map<String, Slot> slots = intentRequest.getIntent().getSlots();
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
            Appointment appointment = speicher.get(Speicher.STR_DATE_AND_SUBJECT, date + " " + subject).get(0);
        if (appointment == null) {
            return input.getResponseBuilder()
                    .withShouldEndSession(false)
                    .withSpeech("Du hast am " + date + "keinen Termin zum " + subject)
                    .build();
        }
         appointment.addNote(note);

       if (speicher.delete(appointment).equals("Gelöscht")) {

            if (speicher.save(appointment)) {
                return input.getResponseBuilder()
                        .withSpeech("Notiz wurde erfolgreich erstellt")
                        .withSimpleCard("Neue Notiz", note)
                        .withShouldEndSession(false)
                        .build();
            }
        }

        return input.getResponseBuilder()
                .withSpeech("Es ist leider ein Fehler aufgetreten, Notiz konnte nicht gespeichert werden")
                .withShouldEndSession(false)
                .build();

    } catch (Exception e) {
        return input.getResponseBuilder()
                .withShouldEndSession(false)
                .withSpeech("Du hast am " + date + " keinen Termin zum " + subject)
                .build();
    }
        }
}
