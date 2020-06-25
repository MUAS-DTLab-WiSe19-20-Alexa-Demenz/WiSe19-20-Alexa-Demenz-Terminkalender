package akk.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import main.java.akk.model.Reminder;
import org.junit.Test;
import org.junit.*;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

public class AppointmentTest {
    ArrayList<String> list = new ArrayList<>();

    Appointment testAppointment = new Appointment("Arzt", "Muenchen", "Lohtstrasse", "13:00",
            "2019-11-20", "Bruder", null, list);

    public void validateSettersAndGetters() throws Exception{


        PojoClass activityPojo = PojoClassFactory.getPojoClass(Appointment.class);

        Validator validator = ValidatorBuilder.create()
                // Lets make sure that we have a getter and a setter for every field defined.
                .with(new SetterMustExistRule()).with(new GetterMustExistRule())

                // Lets also validate that they are behaving as expected
                .with(new SetterTester()).with(new GetterTester()).build();

        // Start the Test
        validator.validate(activityPojo);
    }

    @Test
    public void testToString() {
        Assert.assertTrue(testAppointment.toString().equals("Termin für Arzt von Bruder in Muenchen Straße Lohtstrasse am 2019-11-20 um 13:00"));
    }

    @Test
    public void testSetting() {
        testAppointment.setSubject("einkauf");
        Assert.assertTrue(testAppointment.getSubject().equals("einkauf"));
        testAppointment.setCity("Holzkirchen");
        Assert.assertTrue(testAppointment.getCity().equals("Holzkirchen"));
        testAppointment.setStreet("erlkamerstrasse");
        Assert.assertTrue(testAppointment.getStreet().equals("erlkamerstrasse"));
        testAppointment.setTime("14:00");
        Assert.assertTrue(testAppointment.getTime().equals("14:00"));
        testAppointment.setDate("2019-12-24");
        Assert.assertTrue(testAppointment.getDate().equals("2019-12-24"));
        testAppointment.setPerson("schwester");
        Assert.assertTrue(testAppointment.getPerson().equals("schwester"));
        Reminder reminder = new Reminder(2, 2, "schirm");
        testAppointment.setReminder(reminder);
        Assert.assertTrue(testAppointment.getReminder().equals(reminder));
        testAppointment.addNote("schirm");
        Assert.assertTrue(testAppointment.getNotes().get(0).equals("schirm"));
        testAppointment.removeNote("schirm");
        Assert.assertTrue(testAppointment.getNotes().size() == 0);

    }
}
