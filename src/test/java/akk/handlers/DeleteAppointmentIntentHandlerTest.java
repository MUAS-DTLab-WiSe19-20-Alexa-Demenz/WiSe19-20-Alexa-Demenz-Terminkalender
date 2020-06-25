package akk.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class DeleteAppointmentIntentHandlerTest {
    private DeleteAppointmentIntent handler;

    @Before
    public void setup() {
        handler = new DeleteAppointmentIntent();
    }
    @Test
    public void testCanHandle() {
        final HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

    @Test
    public void testStandard() {
        final Response response = TestUtil.standardTestForHandleDelete(handler);
        assertTrue(response.getOutputSpeech().toString().contains("Es ist leider ein Fehler aufgetreten, der Termin konnte nicht gelöscht werden"));
    }

   @Test
    public void deleteTest() {
        final Response response = TestUtil.deleteRequestHandle(handler);
        assertTrue(response.getOutputSpeech().toString().contains("wurde gelöscht"));
    }


}
