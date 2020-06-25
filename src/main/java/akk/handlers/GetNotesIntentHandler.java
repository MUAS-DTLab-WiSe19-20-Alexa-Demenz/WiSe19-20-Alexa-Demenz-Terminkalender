package akk.handlers;

import akk.model.Appointment;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class GetNotesIntentHandler implements RequestHandler {

    private static String SUBJECT = "Subject";

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("GetNotesIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String userId = input.getRequestEnvelope().getSession().getUser().getUserId();

        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();
        Speicher speicher = new Speicher(userId);
        Map<String, Object> requestAttributes = input.getAttributesManager().getRequestAttributes();
        if (requestAttributes != null && requestAttributes.get("speicher") != null) {
            speicher = (Speicher) requestAttributes.get("speicher");

        }
        try {
            Appointment appointment = speicher.get("Date + Subject",
                    slots.get("Date").getValue() + " " + slots.get(SUBJECT).getValue()).get(0);
            if (appointment.getNotes().isEmpty()) {
                return input.getResponseBuilder()
                        .withShouldEndSession(false)
                        .withSpeech("Du hast keine Notizen für den Termin am " + slots.get("Date").getValue() + " zum " + slots.get(SUBJECT).getValue() + " gespeichert")
                        .build();
            }
            StringBuilder response = new StringBuilder("Du hast " + (appointment.getNotes().size() == 1 ? "eine Notiz: " : appointment.getNotes().size() + " Notizen: "));
            for (String note : appointment.getNotes()) {
                response.append(note + ", ");
            } response.delete(response.length() - 2, response.length());
            if (response.indexOf(",") != -1) {
                response.replace(response.lastIndexOf(","), response.lastIndexOf(", ") + 1, " und");
            }
            return input.getResponseBuilder()
                    .withShouldEndSession(false)
                    .withSpeech(response.toString())
                    .build();
        } catch (Exception e) {
            return input.getResponseBuilder()
                    .withShouldEndSession(false)
                    .withSpeech("Du hast keinen Termin am " + slots.get("Date").getValue() + " zum " + slots.get(SUBJECT).getValue())
                    .build();
        }
    }
}
