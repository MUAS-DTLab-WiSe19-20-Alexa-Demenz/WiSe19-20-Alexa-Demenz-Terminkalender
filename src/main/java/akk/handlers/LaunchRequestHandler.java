/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package akk.handlers;

import akk.model.Appointment;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.amazon.ask.response.ResponseBuilder;

import static com.amazon.ask.request.Predicates.requestType;

public class LaunchRequestHandler implements RequestHandler {


    @Override
    public boolean canHandle(HandlerInput input) {

        return input.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String userId = input.getRequestEnvelope().getSession().getUser().getUserId();



        ResponseBuilder responseBuilder = input.getResponseBuilder();

        Speicher speicher;

        //Für Tests
        //Wenn RequestAttribute vorhanden sind kommen sie von TestUtils
        //Im Speicher sind dann bereits Beispiel Daten vorhanden
        Map<String, Object> requestAttributes = input.getAttributesManager().getRequestAttributes();
        if (requestAttributes.get("speicher") != null) {
            speicher = (Speicher) requestAttributes.get("speicher");
        } else {
            speicher = new Speicher(userId);
        }

        List<Appointment> all = speicher.getAll();

        for (Appointment appointment : all) {
            LocalDate ld = LocalDate.parse(appointment.getDate());
            if (ld.isBefore(LocalDate.now())) {
                speicher.delete(appointment);
            }
        }

        StringBuilder appointmentsToSay = new StringBuilder();
        if (!(speicher.get("Date", LocalDate.now().toString()).isEmpty())) {
            appointmentsToSay.append("Du hast heute einen ");
            for (Appointment appointment : speicher.get("Date", LocalDate.now().toString())) {
                appointment.setDate(null);
                appointmentsToSay.append(appointment.toString());
                appointmentsToSay.append(", einen ");
            }

            appointmentsToSay.delete(appointmentsToSay.length() -8, appointmentsToSay.length());
            int index = appointmentsToSay.lastIndexOf(",");
            if (index != -1) {
                appointmentsToSay.replace(index, index + 1, " und ");
            }
        } else {
            appointmentsToSay.append("Du hast heute keine Termine ");
        }

        String speechText = appointmentsToSay.toString();
        responseBuilder.withSimpleCard("AKK", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .withReprompt("Wenn du noch einen Termin eintragen oder ändern willst, tue es jetzt");

        return responseBuilder.build();
    }
}