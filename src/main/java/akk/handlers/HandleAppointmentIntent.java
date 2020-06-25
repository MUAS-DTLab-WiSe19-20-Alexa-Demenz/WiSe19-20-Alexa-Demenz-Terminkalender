package akk.handlers;

import akk.model.Appointment;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.*;


import java.util.*;

import static com.amazon.ask.request.Predicates.intentName;



public class HandleAppointmentIntent implements RequestHandler {

    public static final String STR_EINEN = ", einen ";
    public static final String STR_UND = " und ";
    public static final String STR_SUBJECT = "Subject";

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("HandleAppointmentIntent"));
    }



    @Override
    public Optional<Response> handle(HandlerInput input) {
        String userId = input.getRequestEnvelope().getSession().getUser().getUserId();

        Request request = input.getRequestEnvelope().getRequest();
            IntentRequest intentRequest = (IntentRequest) request;
            Intent intent = intentRequest.getIntent();
            Map<String, Slot> slots = intent.getSlots();
            StringBuilder appointmentsToSay = new StringBuilder();
            Speicher speicher = new Speicher(userId);

            Map<String, Object> requestAttributes = input.getAttributesManager().getRequestAttributes();
            if ((requestAttributes != null) && (requestAttributes.get("speicher") != null)){
                speicher = (Speicher) requestAttributes.get("speicher");
            }

            boolean withNotes = slots.get("WithNotes").getValue().equals("ja");
                if (slots.get("Date").getValue() != null) {
                    if (!(speicher.get("Date", slots.get("Date").getValue()).isEmpty())) {
                        appointmentsToSay.append("Du hast am ").append(slots.get("Date").getValue()).append(" einen ");
                        for (Appointment appointment : speicher.get("Date", slots.get("Date").getValue())) {
                            appointment.setDate(null);
                            notes(appointmentsToSay, withNotes, appointment);
                        }
                        appointmentsToSay.delete(appointmentsToSay.length() - 8, appointmentsToSay.length());
                        int index = appointmentsToSay.lastIndexOf(", einen Termin für");
                        if (index != -1) {
                            appointmentsToSay.replace(index, index + 1, STR_UND);
                        }
                    } else {
                        appointmentsToSay.append("Du hast am ").append(slots.get("Date").getValue()).append(" keine Termine");
                    }
                } else if (slots.get(STR_SUBJECT).getValue() != null) {
                    if (!(speicher.get(STR_SUBJECT, slots.get(STR_SUBJECT).getValue()).isEmpty())) {
                        appointmentsToSay.append("Du hast zum ").append(slots.get(STR_SUBJECT).getValue()).append(" einen ");
                        for (Appointment appointment : speicher.get(STR_SUBJECT, slots.get(STR_SUBJECT).getValue())) {
                            appointment.setSubject(null);
                            notes(appointmentsToSay, withNotes, appointment);
                            int index = appointmentsToSay.indexOf("für");
                            appointmentsToSay.delete(index, index + 8);
                        }
                        appointmentsToSay.delete(appointmentsToSay.length() - 8, appointmentsToSay.length());
                        int index = appointmentsToSay.lastIndexOf(",");
                        if (index != -1) {
                            appointmentsToSay.replace(index, index + 1, STR_UND);
                        }
                    } else {
                        appointmentsToSay.append("Du hast keinen Termin zum ").append(slots.get(STR_SUBJECT).getValue());
                    }
                } else {
                    if(!speicher.getAll().isEmpty()) {
                        appointmentsToSay.append("Du hast einen ");
                        for (Appointment appointment : speicher.getAll()) {
                            appointmentsToSay.append(appointment.toString());
                            appointmentsToSay.append(STR_EINEN);
                        }
                        appointmentsToSay.delete(appointmentsToSay.length() - 8, appointmentsToSay.length());
                        int index = appointmentsToSay.lastIndexOf(",");
                        if (index != -1) {
                            appointmentsToSay.replace(index, index + 1, STR_UND);
                        }

                    } else {
                        appointmentsToSay.append("Du hast keine Termine gespeichert");
                    }
                }

                return input.getResponseBuilder()
                        .withSpeech(appointmentsToSay.toString())
                        .withShouldEndSession(false)
                        .build();
    }

    private void notes(StringBuilder appointmentsToSay, boolean withNotes, Appointment appointment) {
        appointmentsToSay.append(appointment.toString());

        if (withNotes) {
            if (appointment.getNotes().isEmpty()) {
                appointmentsToSay.append(" keine Notizen ");
            } else {
                appointmentsToSay.append(" Notizen: ");

                for (String note : appointment.getNotes()) {
                    appointmentsToSay.append(note);
                    appointmentsToSay.append(" , ");
                }
                appointmentsToSay.delete(appointmentsToSay.length() - 3, appointmentsToSay.length());
                if (appointment.getNotes().size() > 1) {
                    appointmentsToSay.replace(appointmentsToSay.lastIndexOf(","), appointmentsToSay.lastIndexOf(",") + 1, STR_UND);
                }
            }
        }

        appointmentsToSay.append(STR_EINEN);
    }
}