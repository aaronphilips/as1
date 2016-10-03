package ca.as1;

import android.test.ActivityInstrumentationTestCase2;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by Aaron Philips on 10/2/2016.
 */
public class HabitTest extends ActivityInstrumentationTestCase2 {
    public HabitTest() {
        super(Habit.class);
    }
    public void testGetDaysOfWeek(){
        HashSet<DayOfWeek> dayOfWeeks=new HashSet<DayOfWeek>();
        dayOfWeeks.add(DayOfWeek.MONDAY);
        dayOfWeeks.add(DayOfWeek.FRIDAY);
        Habit testHabit= new Habit("Test Habit", new LocalDate(),dayOfWeeks);

        HashSet<DayOfWeek> dayOfWeeks2=new HashSet<DayOfWeek>();
        dayOfWeeks2.add(DayOfWeek.MONDAY);
        dayOfWeeks2.add(DayOfWeek.FRIDAY);
        assertEquals(dayOfWeeks2,testHabit.getDaysOfWeek());
    }
    public void testGetDateCreated(){
        HashSet<DayOfWeek> dayOfWeeks=new HashSet<DayOfWeek>();
        dayOfWeeks.add(DayOfWeek.MONDAY);
        dayOfWeeks.add(DayOfWeek.FRIDAY);
        Habit testHabit= new Habit("Test Habit", new LocalDate(),dayOfWeeks);
        assertEquals(new LocalDate(),testHabit.getDateCreated());
    }
    public void testCompareTo(){
        HashSet<DayOfWeek> dayOfWeeks=new HashSet<DayOfWeek>();
        dayOfWeeks.add(DayOfWeek.MONDAY);
        dayOfWeeks.add(DayOfWeek.FRIDAY);
        Habit testHabit= new Habit("Test Habit", new LocalDate(),dayOfWeeks);
        dayOfWeeks.add(DayOfWeek.SUNDAY);
        Habit testHabit2= new Habit("Test Habit2",new LocalDate().plusDays(10),dayOfWeeks);
        ArrayList<Habit> habits=new ArrayList<Habit>();
        habits.add(testHabit2);
        habits.add(testHabit);
        Collections.sort(habits);
        assertEquals(habits.get(0),testHabit);
        assertEquals(habits.get(1),testHabit2);
    }
    public void testAddCompletion(){
        HashSet<DayOfWeek> dayOfWeeks=new HashSet<DayOfWeek>();
        dayOfWeeks.add(DayOfWeek.MONDAY);
        dayOfWeeks.add(DayOfWeek.FRIDAY);
        Habit testHabit= new Habit("Test Habit", new LocalDate(),dayOfWeeks);
        LocalDate date=new LocalDate();
        testHabit.addCompletion(date);
        assertEquals(date,testHabit.getCompletions().get(0));


    }
    public void testRemoveCompletion(){
        HashSet<DayOfWeek> dayOfWeeks=new HashSet<DayOfWeek>();
        dayOfWeeks.add(DayOfWeek.MONDAY);
        dayOfWeeks.add(DayOfWeek.FRIDAY);
        Habit testHabit= new Habit("Test Habit", new LocalDate(),dayOfWeeks);
        LocalDate date=new LocalDate();
        testHabit.addCompletion(date);
        testHabit.removeCompletion(date);
        assertEquals(0,testHabit.getCompletions().size());

    }
    public void testGetShortFormDaysString(){
        HashSet<DayOfWeek> dayOfWeeks=new HashSet<DayOfWeek>();
        dayOfWeeks.add(DayOfWeek.MONDAY);
        dayOfWeeks.add(DayOfWeek.FRIDAY);
        Habit testHabit= new Habit("Test Habit", new LocalDate(),dayOfWeeks);
        assertEquals("Mon, Fri",testHabit.getShortFormDaysString());
    }
}
