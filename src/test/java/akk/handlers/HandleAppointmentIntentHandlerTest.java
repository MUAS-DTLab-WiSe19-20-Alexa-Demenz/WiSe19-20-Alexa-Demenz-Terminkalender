package akk.handlers;

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


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


public class HandleAppointmentIntentHandlerTest {
    private HandleAppointmentIntent handler;


    @Before
    public void setup() throws Throwable {
        handler = new HandleAppointmentIntent();


    }

    @Test
    public void testCanHandle() {
        final HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));

    }

    @Test
    public void testNotHandle() {
        final Response response = TestUtil.standardTestForHandle(handler);
        assertTrue(response.getOutputSpeech().toString().contains("Du hast am "  + "2019-12-22 keine Termine"));
    }

    @Test
    public void testHandleDate() {
        final Response response = TestUtil.handleRequestHandler(handler);
        assertTrue(response.getOutputSpeech().toString().contains(LocalDate.now().toString()));
        //schlägt an einem tag fehl
        System.out.println(response.getOutputSpeech().toString());
        assertFalse(response.getOutputSpeech().toString().contains("2019-10-22"));
    }
    @Test
    public void testHandleSubject() {
        final Response response = TestUtil.handleRequestHandlerSubject(handler);
        System.out.println(response.getOutputSpeech().toString());
        assertTrue(response.getOutputSpeech().toString().contains("hausarzt"));
    }

    @Test
    public void testHandleBothNull() {
        final Response response = TestUtil.handleRequestHandlerBothNull(handler);
        assertTrue(response.getOutputSpeech().toString().contains("Du hast einen"));
    }

}