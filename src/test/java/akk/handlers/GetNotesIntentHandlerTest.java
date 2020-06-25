package akk.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GetNotesIntentHandlerTest {


    private GetNotesIntentHandler handler;

    @Before
    public void setup() {
        handler = new GetNotesIntentHandler();
    }
    @Test
    public void testCanHandle() {
        final HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

    @Test
    //nullpointer
    public void testGetNote() {
        final Response response = TestUtil.handleGetNotes(handler);
        System.out.println(response.getOutputSpeech().toString());
        Assert.assertTrue(response.getOutputSpeech().toString().contains("Du hast 2 Notizen: schirm und zwetschgen"));

    }
}
