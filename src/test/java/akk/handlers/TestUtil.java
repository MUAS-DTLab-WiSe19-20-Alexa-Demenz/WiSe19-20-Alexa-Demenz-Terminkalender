package akk.handlers;

import akk.model.Appointment;
import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.model.interfaces.system.SystemState;
import com.amazon.ask.response.ResponseBuilder;
import akk.PhrasesAndConstants;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class TestUtil {
    static ArrayList<String> list = new ArrayList<>();

    static Appointment appointment1 = new Appointment("hausarzt", "nein", "nein", "00:00", LocalDate.now().toString(), "nein", null, new ArrayList<>());
    static Appointment appointment2 = new Appointment("einkauf", "nein", "nein", "00:00", LocalDate.now().toString(), "nein", null, list);
    static Appointment appointment3 = new Appointment("einkauf", "nein", "nein", "00:00", "2019-10-22", "nein", null, new ArrayList<>());
    @ClassRule
    public static final DatabaseUtil util = new DatabaseUtil();
    //Speicher nicht umbedingt notwendig als Attribut
    public static HandlerInput mockHandlerInputSubmit(String subject, String city, String street, String time,
                                                      String date, String person, String reminder, String note,
                                                      Speicher speicher, Map<String, Object> requestAttributes) {
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
                                .putSlotsItem(PhrasesAndConstants.STREET, Slot.builder()
                                        .withName(PhrasesAndConstants.STREET)
                                        .withValue(street)
                                        .build())
                                .putSlotsItem(PhrasesAndConstants.PERSON, Slot.builder()
                                        .withName(PhrasesAndConstants.PERSON)
                                        .withValue(person)
                                        .build())
                                .putSlotsItem(PhrasesAndConstants.REMINDER, Slot.builder()
                                        .withName(PhrasesAndConstants.REMINDER)
                                        .withValue(reminder)
                                        .build())
                                .putSlotsItem(PhrasesAndConstants.NOTE, Slot.builder()
                                        .withName(PhrasesAndConstants.NOTE)
                                        .withValue(note)
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

    public static HandlerInput mockHandlerInputChange(String subject, String city, String time, String date,
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

    public static HandlerInput mockHandlerInputDelete(String subject, String city, String street,
                                                      String time, String date, String person, String reminder,
                                                      String note, Speicher speicher, Map<String, Object> requestAttributes) {
        final AttributesManager attributesManagerMock = Mockito.mock(AttributesManager.class);
        when(attributesManagerMock.getRequestAttributes()).thenReturn(requestAttributes);

        // Mock Slots
        final RequestEnvelope requestEnvelopeMock = RequestEnvelope.builder()
                .withRequest(IntentRequest.builder()
                        .withIntent(Intent.builder()
                                .putSlotsItem(PhrasesAndConstants.SUBJECT_TO_DELETE, Slot.builder()
                                        .withName(PhrasesAndConstants.SUBJECT_TO_DELETE)
                                        .withValue(subject)
                                        .build())
                                .putSlotsItem(PhrasesAndConstants.DATE_TO_DELETE, Slot.builder()
                                        .withName(PhrasesAndConstants.DATE_TO_DELETE)
                                        .withValue(date)
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

    //später
    public static HandlerInput mockHandlerInputReminder(String subject, String city, String street,
                                                      String time, String date, String person, String reminder,
                                                      String note, Speicher speicher, Map<String, Object> requestAttributes) {
        final AttributesManager attributesManagerMock = Mockito.mock(AttributesManager.class);
        when(attributesManagerMock.getRequestAttributes()).thenReturn(requestAttributes);

        // Mock Slots
        final RequestEnvelope requestEnvelopeMock = RequestEnvelope.builder()
                .withRequest(IntentRequest.builder()
                        .withIntent(Intent.builder()
                                .putSlotsItem(PhrasesAndConstants.SUBJECT_TO_DELETE, Slot.builder()
                                        .withName(PhrasesAndConstants.SUBJECT_TO_DELETE)
                                        .withValue(subject)
                                        .build())
                                .putSlotsItem(PhrasesAndConstants.DATE_TO_DELETE, Slot.builder()
                                        .withName(PhrasesAndConstants.DATE_TO_DELETE)
                                        .withValue(date)
                                        .build())
                                .build())
                        .build()).
                        withContext(Context.builder()
                                .withSystem(SystemState.builder().
                                        withUser(User.builder().
                                                withPermissions(Permissions.builder()
                                                        .build())
                                                .build())
                                        .build())
                                .build())
                .build();



        // Mock Handler input attributes
        final HandlerInput input = Mockito.mock(HandlerInput.class);
        when(input.getAttributesManager()).thenReturn(attributesManagerMock);
        when(input.getResponseBuilder()).thenReturn(new ResponseBuilder());
        when(input.getRequestEnvelope()).thenReturn(requestEnvelopeMock);

        return input;
    }
    public static HandlerInput mockHandlerInputHandle(String subject, String city, String street,
                                                      String time, String date, String person,
                                                      String reminder, String note, String withNotes,
                                                      Speicher speicher, Map<String, Object> requestAttributes) {
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
                                .putSlotsItem(PhrasesAndConstants.WITH_NOTES, Slot.builder()
                                        .withName(PhrasesAndConstants.WITH_NOTES)
                                        .withValue(withNotes)
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
    public static HandlerInput mockHandlerInputNote(String subject, String city, String street,
                                                      String time, String date, String person,
                                                      String reminder, String notes,
                                                      Speicher speicher, Map<String, Object> requestAttributes) {
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
                                .putSlotsItem(PhrasesAndConstants.NOTE, Slot.builder()
                                        .withName(PhrasesAndConstants.NOTE)
                                        .withValue(notes)
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

    public static Response handleCreateNoteRecognize(RequestHandler handler) {
        Speicher speicher = getSpeicher();
        speicher.save(appointment1);
        speicher.save(appointment2);
        speicher.save(appointment3);
        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("speicher", speicher);
        final HandlerInput inputMock = TestUtil.mockHandlerInputNote("hausarzt", null, null, null, LocalDate.now().toString(),
                null, null, null, speicher, requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();


        assertNotEquals("Test", response.getReprompt());
        assertNotNull(response.getOutputSpeech());
        return response;
    }

    public static Response handleNoteNot(RequestHandler handler) {
        Speicher speicher = getSpeicher();
        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("speicher", speicher);
        final HandlerInput inputMock = TestUtil.mockHandlerInputNote("hausarzt", null, null, null, LocalDate.now().toString(),
                null, null, null, speicher, requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();


        assertNotEquals("Test", response.getReprompt());
        assertNotNull(response.getOutputSpeech());
        return response;
    }
    public static Response handleGetNotes(RequestHandler handler) {
        Speicher speicher = getSpeicher();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("schirm");
        arrayList.add("zwetschgen");
        Appointment appointment = new Appointment("einkauf", "nein", "nein", "00:00", LocalDate.now().toString(), "nein", null, arrayList);
        speicher.save(appointment);
        speicher.save(appointment3);
        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("speicher", speicher);
        final HandlerInput inputMock = TestUtil.mockHandlerInputNote("einkauf", null, null, null, LocalDate.now().toString(),
                null, null, null, speicher, requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();


        assertNotEquals("Test", response.getReprompt());
        assertNotNull(response.getOutputSpeech());
        return response;
    }

    public static Response handleRequestHandler(RequestHandler handler) {
        Speicher speicher = getSpeicher();
        speicher.save(appointment1);
        speicher.save(appointment2);
        speicher.save(appointment3);
        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("speicher", speicher);
        final HandlerInput inputMock = TestUtil.mockHandlerInputHandle("hausarzt","nein", "nein", "00:00", LocalDate.now().toString(), "nein", null, null, "ja", speicher,  requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();

        //assertFalse(response.getShouldEndSession());
        assertNotEquals("Test", response.getReprompt());
        assertNotNull(response.getOutputSpeech());
        return response;
    }
    public static Response handleDeleteNotesNotFound(RequestHandler handler) {
        Speicher speicher = getSpeicher();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("schirm");
        arrayList.add("zwetschgen");
        Appointment appointment = new Appointment("einkauf", "nein", "nein", "00:00", LocalDate.now().toString(), "nein", null, arrayList);
        speicher.save(appointment);
        speicher.save(appointment3);
        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("speicher", speicher);
        ArrayList<String> toDelete = new ArrayList<>();
        toDelete.add("essen");

        final HandlerInput inputMock = TestUtil.mockHandlerInputNote("einkauf", null, null, null, LocalDate.now().toString(),
                null, null, toDelete.toString(), speicher, requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();


        assertNotEquals("Test", response.getReprompt());
        assertNotNull(response.getOutputSpeech());
        return response;
    }
    public static Response handleDeleteNotesFound(RequestHandler handler) {
        Speicher speicher = getSpeicher();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("schirm");
        arrayList.add("zwetschgen");
        Appointment appointment = new Appointment("einkauf", "nein", "nein", "00:00", LocalDate.now().toString(), "nein", null, arrayList);
        speicher.save(appointment);
        speicher.save(appointment3);
        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("speicher", speicher);
        ArrayList<String> toDelete = new ArrayList<>();
        toDelete.add("schirm");


        final HandlerInput inputMock = TestUtil.mockHandlerInputNote("einkauf", null, null, null, LocalDate.now().toString(),
                null, null, "schirm", speicher, requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();


        assertNotEquals("Test", response.getReprompt());
        assertNotNull(response.getOutputSpeech());
        return response;
    }
    public static Response handleDeleteNotePartlyEqual(RequestHandler handler) {
        Speicher speicher = getSpeicher();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("schirm nicht vergessen");
        arrayList.add("zwetschgen");
        Appointment appointment = new Appointment("einkauf", "nein", "nein", "00:00", LocalDate.now().toString(), "nein", null, arrayList);
        speicher.save(appointment);
        speicher.save(appointment3);
        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("speicher", speicher);
        ArrayList<String> toDelete = new ArrayList<>();
        toDelete.add("schirm");


        final HandlerInput inputMock = TestUtil.mockHandlerInputNote("einkauf", null, null, null, LocalDate.now().toString(),
                null, null, "schirm", speicher, requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();


        assertNotEquals("Test", response.getReprompt());
        assertNotNull(response.getOutputSpeech());
        return response;
    }

    public static Response handleNotInStorage(RequestHandler handler) {
        Speicher speicher = getSpeicher();
        ArrayList<String> arrayList = new ArrayList<>();

        Appointment appointment = new Appointment("einkauf", "nein", "nein", "00:00", LocalDate.now().toString(), "nein", null, arrayList);

        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("speicher", speicher);
        ArrayList<String> toDelete = new ArrayList<>();
        toDelete.add("schirm");


        final HandlerInput inputMock = TestUtil.mockHandlerInputNote("einkauf", null, null, null, LocalDate.now().toString(),
                null, null, "schirm", speicher, requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();


        assertNotEquals("Test", response.getReprompt());
        assertNotNull(response.getOutputSpeech());
        return response;
    }


    public static Response handleRequestHandlerSubject(RequestHandler handler) {
        Speicher speicher = getSpeicher();
        speicher.save(appointment1);
        speicher.save(appointment2);
        speicher.save(appointment3);
        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("speicher", speicher);
        System.out.println("vor mock");
        final HandlerInput inputMock = TestUtil.mockHandlerInputHandle("hausarzt","nein", "nein", "00:00", null, "nein", null, null, "ja",speicher, requestAttributes);
        System.out.println("nach mock");
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        System.out.println("nach assert");
        final Response response = res.get();

        assertNotNull(response.getOutputSpeech());
        return response;
    }
    public static Response handleRequestHandlerBothNull(RequestHandler handler) {
        Speicher speicher = getSpeicher();
        speicher.save(appointment1);
        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("speicher", speicher);
        final HandlerInput inputMock = TestUtil.mockHandlerInputHandle(null,"nein", "nein", "00:00", null, "nein", null, null, "ja",null, requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();

        assertNotNull(response.getOutputSpeech());
        return response;
    }

    public static Response standardTestForHandle(RequestHandler handler) {
        final HandlerInput inputMock = TestUtil.mockHandlerInputHandle("hausarzt","nein", "nein", "00:00", "2019-12-22", "nein", null, null, "ja",null, null);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();

        //assertFalse(response.getShouldEndSession());
        assertNotEquals("Test", response.getReprompt());
        assertNotNull(response.getOutputSpeech());
        return response;
    }
    public static Response standardNotHandled(RequestHandler handler) {
        final HandlerInput inputMock = TestUtil.mockHandlerInputSubmit("hausarzt","nein", "nein", "00:00", "2019-12-22", "nein", null, null, null, null);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();

        //assertFalse(response.getShouldEndSession());
        assertNotEquals("Test", response.getReprompt());
        assertNotNull(response.getOutputSpeech());
        return response;
    }

    public static Response standardReminder(RequestHandler handler) {
        final HandlerInput inputMock = TestUtil.mockHandlerInputReminder(null,null, null, null, null, null, null, null, null, null);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();

        //assertFalse(response.getShouldEndSession());
        assertNotEquals("Test", response.getReprompt());
        assertNotNull(response.getOutputSpeech());
        return response;
    }
    public static Response handleRequestChangeAvailable(RequestHandler handler) {
        Speicher speicher = getSpeicher();
        speicher.save(appointment1);
        speicher.save(appointment2);
        speicher.save(appointment3);
        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("speicher", speicher);
        final HandlerInput inputMock = TestUtil.mockHandlerInputChange("hausarzt", "nein",  "00:00", LocalDate.now().toString(),
                "time", "zwei uhr", requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();
        assertNotNull(response.getOutputSpeech());
        return response;
    }

    public static Response handleRequestChangeWithNo(RequestHandler handler) {
        Speicher speicher = getSpeicher();
        speicher.save(appointment1);
        speicher.save(appointment2);
        speicher.save(appointment3);
        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("speicher", speicher);
        final HandlerInput inputMock = TestUtil.mockHandlerInputChange("hausarzt", "nein",  "00:00", LocalDate.now().toString(),
                "time", "nein", requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();
        assertNotNull(response.getOutputSpeech());
        return response;
    }
    public static Response handleRequestChangeDate(RequestHandler handler) {
        Speicher speicher = getSpeicher();
        speicher.save(appointment1);
        speicher.save(appointment2);
        speicher.save(appointment3);
        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("speicher", speicher);
        final HandlerInput inputMock = TestUtil.mockHandlerInputChange("hausarzt", "nein",  "00:00", LocalDate.now().toString(),
                "date", LocalDate.now().plusDays(4).toString(), requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();
        assertNotNull(response.getOutputSpeech());
        return response;
    }

    public static Response handleRequestChangeNotChangable(RequestHandler handler) {
        Speicher speicher = getSpeicher();
        speicher.save(appointment1);
        speicher.save(appointment2);
        speicher.save(appointment3);
        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("speicher", speicher);
        final HandlerInput inputMock = TestUtil.mockHandlerInputChange("hausarzt", "nein",  "00:00", LocalDate.now().toString(),
                "dumbo", "dumbo", requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();
        assertNotNull(response.getOutputSpeech());
        return response;
    }
    public static Response handleRequestChangeToTomorrow(RequestHandler handler) {
        Speicher speicher = getSpeicher();
        speicher.save(appointment1);
        speicher.save(appointment2);
        speicher.save(appointment3);
        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("speicher", speicher);
        final HandlerInput inputMock = TestUtil.mockHandlerInputChange("hausarzt", "nein",  "00:00", LocalDate.now().toString(),
                "date", "morgen", requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();
        assertNotNull(response.getOutputSpeech());
        return response;
    }



    public static Response standardTestForHandleDelete(RequestHandler handler) {
        final HandlerInput inputMock = TestUtil.mockHandlerInputDelete("hausarzt","nein", "nein", "00:00", "2019-12-22", "nein", null, null, null, null);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();

        //assertFalse(response.getShouldEndSession());
        assertNotEquals("Test", response.getReprompt());
        assertNotNull(response.getOutputSpeech());
        return response;
    }


    public static Response launchRequestHandle(RequestHandler handler) throws Throwable {


        Speicher speicher = TestUtil.getSpeicher();
        final Map<String, Object> requestAttributes = new HashMap<>();
        //Appointment, dass immer auf den heutigen Tag ausgerichtet ist, damit wird
        Appointment appointment1 = new Appointment("hausarzt", "nein", "nein", "00:00", LocalDate.now().toString(), "nein", null, null);

        speicher.save(appointment1);
        requestAttributes.put("speicher", speicher);
        final HandlerInput inputMock = TestUtil.mockHandlerInputSubmit(null,null, null, null, null, null, null, null, speicher, requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);
        assertTrue(res.isPresent());
        final Response response = res.get();
        return response;

    }
    public static Response submitRequestHandle(RequestHandler handler) throws Throwable {


        Speicher speicher = TestUtil.getSpeicher();
        final Map<String, Object> requestAttributes = new HashMap<>();
        //Appointment, dass immer auf den heutigen Tag ausgerichtet ist, damit wird
        Appointment appointment1 = new Appointment("hausarzt", "nein", "nein", "00:00", LocalDate.now().toString(), "nein", null, null);

        requestAttributes.put("speicher", speicher);
        final HandlerInput inputMock = TestUtil.mockHandlerInputSubmit("hausarzt", "nein", "nein", "00:00", LocalDate.now().toString(), "nein", null, null, speicher, requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);
        assertTrue(res.isPresent());
        final Response response = res.get();
        return response;

    }

    public static Response deleteRequestHandle(RequestHandler handler) {
        Speicher speicher = TestUtil.getSpeicher();
        final Map<String, Object> requestAttributes = new HashMap<>();
        Appointment appointment1 = new Appointment("hausarzt", "nein", "nein", "00:00", LocalDate.now().toString(), "nein", null, null);
        speicher.save(appointment1);
        requestAttributes.put("speicher", speicher);
        final HandlerInput inputMock = TestUtil.mockHandlerInputDelete("hausarzt", "nein", "nein", "00:00", LocalDate.now().toString(), "nein", null, null, speicher, requestAttributes);
        final Optional<Response> res = handler.handle(inputMock);
        assertTrue(res.isPresent());
        final Response response = res.get();
        return response;

    }
 static Response sessionEndedTestForHandle(RequestHandler handler) {
        final HandlerInput inputMock = TestUtil.mockHandlerInputSubmit(null,null, null, null, null, null, null, null, null, null);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();
        assertTrue(response.getShouldEndSession());
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
