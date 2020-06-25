package akk.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class LaunchRequestHandlerTest {

    private LaunchRequestHandler handler;

    @Before
    public void setup() {
        handler = new LaunchRequestHandler();
    }

    @Test
    public void testCanHandle() {
        final HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

    @Test
    public void testHandle() throws Throwable {
        final Response response = TestUtil.launchRequestHandle(handler);
        assertTrue(response.getOutputSpeech().toString().contains("Du hast heute einen"));

    }



}
