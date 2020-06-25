package akk.handlers;

import akk.model.Appointment;
import com.amazon.ask.AlexaSkill;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import akk.PhrasesAndConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


public class SubmitAppointmentIntentHandlerTest {
    private SubmitAppointmentIntent handler;



    @Before
    public void setup() throws Throwable {
        handler = new SubmitAppointmentIntent();


    }

    @Test
    public void testCanHandle() {
        final HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));

    }

   @Test
    public void testHandleNotSaved() {
        final Response response = TestUtil.standardNotHandled(handler);
        assertTrue(response.getOutputSpeech().toString().contains("Es ist leider ein Fehler aufgetreten, der Termin konnte nicht gespeichert werden"));
    }


   @Test
    public void testSpeicherSave() throws Throwable {
        final Response response = TestUtil.submitRequestHandle(handler);
        assertTrue(response.getOutputSpeech().toString().contains(LocalDate.now().toString()));

    }





}
