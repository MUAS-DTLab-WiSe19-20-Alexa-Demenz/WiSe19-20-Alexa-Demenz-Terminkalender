package akk.handlers;

import akk.PhrasesAndConstants;
import akk.model.Appointment;
import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.response.ResponseBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ChangeAppointmentIntentHandlerTest {
    @ClassRule
    public static final DatabaseUtil util = new DatabaseUtil();
    static Appointment appointment1 = new Appointment("hausarzt", "nein", "nein", "00:00", LocalDate.now().toString(), "nein", null, new ArrayList<>());
    static String[] times = {"ein uhr", "zwei uhr", "drei uhr", "vier uhr", "fünf uhr", "sechs uhr", "sieben uhr", "acht uhr",
    "neun uhr", "zehn uhr", "elf uhr", "zwölf uhr", "dreizehn uhr", "vierzehn uhr", "fünfzehn uhr", "sechzehn uhr",
    "siebzehn uhr", "achtzehn uhr", "neunzehn uhr", "zwanzig uhr", "einundzwanzig uhr", "zweiundzwanzig uhr",
    "dreiundzwanzig uhr", "vierundzwanziguhr"};
    static String[] timeInNumbers = {"01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "9:00",
    "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
    "22:00", "23:00", "24:00"};
    public static HandlerInput mockHandlerChange(String subject, String city, String time, String date,
                                                      String attritubeToChange, String newValue,
                                                      Map<String, Object> requestAttributes) {
        final AttributesManager attributesManagerMock = Mockito.mock(AttributesManager.class);
        when(attributesManagerMock.getRequestAttributes()).thenReturn(requestAttributes);

        // Mock Slots
        final RequestEnvelope requestEnvelopeMock = RequestEnvelope.builder()
                .withRequest(IntentRequest.builder()
                        .withIntent(Intent.builder()
                                .putSlotsItem(PhrasesAndConstants.SUBJECT, Slot.builder()
                                        .withName(PhrasesAndConstants.SUBJECT)
                                        .withValue(subject)
                                        .build())
                                .putSlotsItem(PhrasesAndConstants.DATE, Slot.builder()
                                        .withName(PhrasesAndConstants.DATE)
                                        .withValue(date)
                                        .build())
                                .putSlotsItem(PhrasesAndConstants.TIME, Slot.builder()
                                        .withName(PhrasesAndConstants.TIME)
                                        .withValue(time)
                                        .build())
                                .putSlotsItem(PhrasesAndConstants.CITY, Slot.builder()
                                        .withName(PhrasesAndConstants.CITY)
                                        .withValue(city)
                                        .build())
                                .putSlotsItem(PhrasesAndConstants.ATTRIBUTE_TO_CHANGE, Slot.builder()
                                        .withName(PhrasesAndConstants.ATTRIBUTE_TO_CHANGE)
                                        .withValue(attritubeToChange)
                                        .build())
                                .putSlotsItem(PhrasesAndConstants.NEW_VALUE, Slot.builder()
                                        .withName(PhrasesAndConstants.NEW_VALUE)
                                        .withValue(newValue)
                                        .build())
                                .build())
                        .build()).
                        withSession(Session.builder().withSessionId("1")
                                .withUser(User.builder().withUserId("1").build())
                                .build())
                .build();



        // Mock Handler input attributes
        final HandlerInput input = Mockito.mock(HandlerInput.class);
        when(input.getAttributesManager()).thenReturn(attributesManagerMock);
        when(input.getResponseBuilder()).thenReturn(new ResponseBuilder());
        when(input.getRequestEnvelope()).thenReturn(requestEnvelopeMock);

        return input;
    }

    private ChangeAppointmentIntentHandler handler;

    @Before
    public void setup() {
        handler = new ChangeAppointmentIntentHandler();
    }
    @Test
    public void testCanHandle() {
        final HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

    @Test
    public void testChangeTime() {
        final Response response = TestUtil.handleRequestChangeAvailable(handler);
        System.out.println(response.getOutputSpeech().toString());
        Assert.assertTrue(response.getOutputSpeech().toString().contains("02:00"));

    }

    @Test
    public void testChangeWithNo() {
        final Response response = TestUtil.handleRequestChangeWithNo(handler);
        System.out.println(response.getOutputSpeech().toString());
        Assert.assertTrue(response.getOutputSpeech().toString().contains(LocalDate.now().toString() + " geändert"));

    }

    @Test
    public void testChangeDate() {
        final Response response = TestUtil.handleRequestChangeNotChangable(handler);

        Assert.assertTrue(response.getOutputSpeech().toString().contains("dumbo dumbo"));

    }

    @Test
    public void testChangeDateToTomorrow() {
        final Response response = TestUtil.handleRequestChangeToTomorrow(handler);
        Assert.assertTrue(response.getOutputSpeech().toString().contains(LocalDate.now().plusDays(1).toString()));
    }

    @Test
    public void testAllTimes() {
        for (int i = 0; i< times.length - 1; i++) {
            final Response response = ChangeAppointmentIntentHandlerTest.handleRequestChangeTimes(handler, i);
            System.out.println(response.getOutputSpeech().toString());
            Assert.assertTrue(response.getOutputSpeech().toString().contains(timeInNumbers[i]));
        }

    }

    public static Response handleRequestChangeTimes(RequestHandler handler, int index) {
        Speicher speicher = getSpeicher();
        speicher.save(appointment1);
        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("speicher", speicher);
        final HandlerInput inputMock = TestUtil.mockHandlerInputChange("hausarzt", "nein",  "00:00", LocalDate.now().toString(),
                "time",times[index], requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();
        assertNotNull(response.getOutputSpeech());
        return response;
    }
    public static Speicher getSpeicher() {
        AmazonDynamoDB testDB = null;
        try {
            util.before();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        testDB = util.getAmazonDynamoDB();

        Speicher speicher = new Speicher(testDB);
        DynamoDB db = new DynamoDB(testDB);
        Table table = db.createTable("akkData", Arrays.asList(new KeySchemaElement("UserID", KeyType.HASH),
                new KeySchemaElement("Date + Subject", KeyType.RANGE)), Arrays.asList
                        (new AttributeDefinition("UserID", ScalarAttributeType.S),
                                new AttributeDefinition("Date + Subject", ScalarAttributeType.S)),
                new ProvisionedThroughput(10L, 10L));

        return speicher;
    }



}
