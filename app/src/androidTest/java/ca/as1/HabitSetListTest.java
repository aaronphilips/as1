package ca.as1;

import android.test.ActivityInstrumentationTestCase2;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Aaron Philips on 10/2/2016.
 */
public class HabitSetListTest extends ActivityInstrumentationTestCase2 {
    public HabitSetListTest() {
        super(HabitSetList.class);
    }

    public void testAddHabit(){
        HashSet<DayOfWeek> dayOfWeeks=new HashSet<DayOfWeek>();
        dayOfWeeks.add(DayOfWeek.MONDAY);
        dayOfWeeks.add(DayOfWeek.FRIDAY);
        Habit testHabit= new Habit("Test Habit", new LocalDate(),dayOfWeeks);
        HabitSetList habitSetList= new HabitSetList();
        habitSetList.addHabit(testHabit);
        assertEquals(1,habitSetList.getHabitArrayList().size());
    }
    public void testGetHabit(){
        HashSet<DayOfWeek> dayOfWeeks=new HashSet<DayOfWeek>();
        dayOfWeeks.add(DayOfWeek.MONDAY);
        dayOfWeeks.add(DayOfWeek.FRIDAY);
        Habit testHabit= new Habit("Test Habit", new LocalDate(),dayOfWeeks);
        HabitSetList habitSetList= new HabitSetList();
        habitSetList.addHabit(testHabit);
        assertEquals(testHabit,habitSetList.getHabit(testHabit.getID()));
    }
    public void testRemoveHabit(){
        HashSet<DayOfWeek> dayOfWeeks=new HashSet<DayOfWeek>();
        dayOfWeeks.add(DayOfWeek.MONDAY);
        dayOfWeeks.add(DayOfWeek.FRIDAY);
        Habit testHabit= new Habit("Test Habit", new LocalDate(),dayOfWeeks);
        dayOfWeeks.add(DayOfWeek.SUNDAY);
        Habit testHabit2= new Habit("Test Habit2",new LocalDate().plusDays(10),dayOfWeeks);
        dayOfWeeks.add(DayOfWeek.SATURDAY);
        Habit testHabit3= new Habit("Test Habit2",new LocalDate().plusDays(10),dayOfWeeks);
        HabitSetList habitSetList= new HabitSetList();
        habitSetList.addHabit(testHabit);
        habitSetList.addHabit(testHabit2);
        habitSetList.addHabit(testHabit3);
        habitSetList.removeHabit(testHabit2.getID());
        assertEquals(2,habitSetList.getHabitArrayList().size());
    }

    public void testGetHabitArrayList(){
        HashSet<DayOfWeek> dayOfWeeks=new HashSet<DayOfWeek>();
        dayOfWeeks.add(DayOfWeek.MONDAY);
        dayOfWeeks.add(DayOfWeek.FRIDAY);
        Habit testHabit= new Habit("Test Habit", new LocalDate(),dayOfWeeks);

        HashSet<DayOfWeek> dayOfWeeks2=new HashSet<DayOfWeek>();
        dayOfWeeks2.add(DayOfWeek.MONDAY);
        dayOfWeeks2.add(DayOfWeek.FRIDAY);
        dayOfWeeks2.add(DayOfWeek.SUNDAY);
        Habit testHabit2= new Habit("Test Habit2",new LocalDate().plusDays(10),dayOfWeeks2);

        HashSet<DayOfWeek> dayOfWeeks3=new HashSet<DayOfWeek>();
        dayOfWeeks3.add(DayOfWeek.MONDAY);
        dayOfWeeks3.add(DayOfWeek.FRIDAY);
        dayOfWeeks3.add(DayOfWeek.SUNDAY);
        dayOfWeeks3.add(DayOfWeek.SATURDAY);
        Habit testHabit3= new Habit("Test Habit2",new LocalDate().plusDays(10),dayOfWeeks3);

        HabitSetList habitSetList= new HabitSetList();
        habitSetList.addHabit(testHabit);
        habitSetList.addHabit(testHabit2);
        habitSetList.addHabit(testHabit3);
        assertEquals(2,habitSetList.getHabitArrayList(DayOfWeek.SUNDAY).size());

    }


}
