package akk.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import net.bytebuddy.asm.Advice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CreateNoteIntentHandlerTest {


    private CreateNoteIntentHandler handler;

    @Before
    public void setup() {
        handler = new CreateNoteIntentHandler();
    }
    @Test
    public void testCanHandle() {
        final HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

    @Test
    //nullpointer
    public void testRecognizeNote() {
        final Response response = TestUtil.handleCreateNoteRecognize(handler);
        System.out.println(response.getOutputSpeech().toString());
        Assert.assertTrue(response.getOutputSpeech().toString().contains("Notiz wurde erfolgreich erstellt"));

    }


    @Test
    //nullpointer
    public void testNoteNotSaved() {
        final Response response = TestUtil.handleNoteNot(handler);
        System.out.println(response.getOutputSpeech().toString());
        Assert.assertTrue(response.getOutputSpeech().toString().contains(LocalDate.now().toString() + " keinen Termin"));

    }
}
