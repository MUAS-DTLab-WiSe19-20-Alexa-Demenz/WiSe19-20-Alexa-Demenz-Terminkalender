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
import org.junit.*;
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

//ohne Datenbank wahrscheinlich Exceptions - später prüfen
public class SpeicherTest {
    Appointment appointment1 = new Appointment("hausarzt", "nein", "nein", "00:00", "2019-12-22", "nein", null, null);
    Appointment appointment2 = new Appointment("arzt", "nein", "nein", "00:00", "2019-12-22", "nein", null, null);
    Appointment appointment3 = new Appointment("einkauf", "nein", "nein", "00:00", "2019-12-21", "nein", null, null);



    @Test
    public void testSave() {
        Speicher speicher = TestUtil.getSpeicher();
        assertTrue(speicher.save(appointment1));
    }

    @Test
    public void testGet() {
        Speicher speicher = TestUtil.getSpeicher();
        speicher.save(appointment2);
        speicher.save(appointment3);
        List<Appointment> date = speicher.get("Date", "2019-12-22");
        for (Appointment a: date) {
            assertTrue(a.getDate().equals("2019-12-22"));
        }

        assertFalse(speicher.getAll().get(1).equals(appointment2));
        assertTrue(speicher.getAll().get(1).toString().equals(appointment2.toString()));
        assertTrue(speicher.get("Subject", appointment2.getSubject()).get(0).toString().equals(appointment2.toString()));


    }
    @Test
    public void testDelete() {
        Speicher speicher = TestUtil.getSpeicher();
        speicher.save(appointment3);
        assertTrue(speicher.getAll().size() == 1);
        assertTrue(speicher.delete(appointment3).equals("Gelöscht"));
        assertTrue(speicher.delete(appointment3).equals("Nicht vorhanden"));
        assertTrue(speicher.getAll().size() == 0);

    }




}
