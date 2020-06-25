package akk.handlers;

import akk.model.Appointment;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;


public class DeleteAppointmentIntent implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("DeleteAppointmentHandler"));
    }


    @Override
    public Optional<Response> handle(HandlerInput input) {

        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();
        String userId = input.getRequestEnvelope().getSession().getUser().getUserId();


        Appointment appointment = new Appointment(slots.get("SubjectToDelete").getValue(), null, null, null, slots.get("DateToDelete").getValue(),
                null, null, null);
        //Abfrage ob überhaupt das appointment vorhanden ist vor dem löschen
        Speicher speicher = new Speicher(userId);


        Map<String, Object> requestAttributes = input.getAttributesManager().getRequestAttributes();
        if ((requestAttributes != null) &&(requestAttributes.get("speicher") != null)) {
                speicher = (Speicher) requestAttributes.get("speicher");
        }
        String s = speicher.delete(appointment);
        if (s.equals("Gelöscht")) {
            return input.getResponseBuilder()
                    .withSpeech(appointment.toString() + " wurde gelöscht")
                    .withSimpleCard("AKK", appointment.toString() + " wurde gelöscht")
                    .withShouldEndSession(false)
                    .build();
        }
        return input.getResponseBuilder()
                .withSpeech("Es ist leider ein Fehler aufgetreten, der Termin konnte nicht gelöscht werden")
                .withShouldEndSession(false)
                .build();

    }
}

