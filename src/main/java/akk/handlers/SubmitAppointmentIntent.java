package akk.handlers;

import akk.model.Appointment;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.*;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;



public class SubmitAppointmentIntent implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("SubmitAppointmentIntent"));
    }



    @Override
    public Optional<Response> handle(HandlerInput input) {
        String userId = input.getRequestEnvelope().getSession().getUser().getUserId();

        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();

            Appointment appointment = new Appointment(slots.get("Subject").getValue(),
                    slots.get("City").getValue().equals("nein") ? null : slots.get("City").getValue(),
                    slots.get("Street").getValue().equals("nein") ? null : slots.get("Street").getValue(),
                    slots.get("Time").getValue().equals("00:00") ? null : slots.get("Time").getValue(),
                    slots.get("Date").getValue(),
                    slots.get("Person").getValue().equals("nein") ? null : slots.get("Person").getValue(),
                    null, null);
        Speicher speicher = new Speicher(userId);

        Map<String, Object> requestAttributes = input.getAttributesManager().getRequestAttributes();
        if ((requestAttributes != null) && (requestAttributes.get("speicher") != null)) {
                speicher = (Speicher) requestAttributes.get("speicher");
        }

            if (speicher.save(appointment)) {
                return input.getResponseBuilder()
                        .withSpeech(appointment.toString() + " wurde erstellt")
                        .withSimpleCard("AKK", appointment.toString() + " wurde erstellt")
                        .withShouldEndSession(false)
                        .withReprompt("Wenn du eine Notiz machen willst hinzufügen willst dann sage es jetzt")
                        .build();
            } return input.getResponseBuilder()
                .withSpeech("Es ist leider ein Fehler aufgetreten, der Termin konnte nicht gespeichert werden")
                .withShouldEndSession(false)
                .build();

    }
}