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

public class DeleteNoteIntentHandlerTest {


    private DeleteNoteIntentHandler handler;

    @Before
    public void setup() {
        handler = new DeleteNoteIntentHandler();
    }
    @Test
    public void testCanHandle() {
        final HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

    @Test
    public void testDeleteNotFound() {
        final Response response = TestUtil.handleDeleteNotesNotFound(handler);
        System.out.println(response.getOutputSpeech().toString());
        Assert.assertTrue(response.getOutputSpeech().toString().contains("Für [essen] wurde keine passende Notiz gefunden"));

    }

    @Test
    //nullpointer
    public void testDeleteFound() {
        final Response response = TestUtil.handleDeleteNotesFound(handler);
        Assert.assertTrue(response.getOutputSpeech().toString().contains("Die Notiz schirm wurde gelöscht"));

    }

    @Test
    //nullpointer
    public void testNotInStorage() {
        final Response response = TestUtil.handleNotInStorage(handler);
        Assert.assertTrue(response.getOutputSpeech().toString().contains(LocalDate.now() + " keinen Termin"));

    }

    @Test
    //nullpointer
    public void testDeleteNotPartlyEqual() {
        final Response response = TestUtil.handleDeleteNotePartlyEqual(handler);
        System.out.println(response.getOutputSpeech().toString());
        Assert.assertTrue(response.getOutputSpeech().toString().contains("Die Notiz schirm nicht vergessen wurde gelöscht"));

    }

}
