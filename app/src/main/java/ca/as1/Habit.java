package ca.as1;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashSet;


/**
 * Created by aaron on 9/25/16.
 */
public class Habit {
    private ArrayList<LocalDate> completions;
    private HashSet<DayOfWeek> daysOfWeek;
    private String name;
    private LocalDate dateCreated;


    public ArrayList<LocalDate> getCompletions() {
        return completions;
    }

    public void addCompletions(LocalDate newCompletion) {
        this.completions.add(newCompletion);
    }
    public Habit(String name, LocalDate dateCreated,HashSet<DayOfWeek> dayOfWeeks){
        this.name=name;
        this.dateCreated=dateCreated;
        this.daysOfWeek=dayOfWeeks;
        this.completions=new ArrayList<LocalDate>();

    }
    @Override
    public String toString(){
        return name;
    }
    public boolean completedToday(){

        //TODO compare with today date
        return true;
    }
}
