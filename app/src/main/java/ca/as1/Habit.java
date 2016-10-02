package ca.as1;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;


/**
 * Created by aaron on 9/25/16.
 */
public class Habit implements UniquelyIdentifiable, Comparable<Habit> {
    private ArrayList<LocalDate> completions;
    private HashSet<DayOfWeek> daysOfWeek;
    private String name;
    private LocalDate dateCreated;
    private UUID habitID;


    public ArrayList<LocalDate> getCompletions() {
        return completions;
    }

    public void addCompletions(LocalDate newCompletion) {
        this.completions.add(newCompletion);
    }


    public Habit(String name, LocalDate dateCreated,HashSet<DayOfWeek> daysOfWeek){
        if(daysOfWeek.size()<1){
            throw new IllegalArgumentException();
        }
        this.name=name;
        this.dateCreated=dateCreated;
        this.daysOfWeek=daysOfWeek;
        this.completions=new ArrayList<LocalDate>();
        this.habitID=UUID.randomUUID();


    }

    @Override
    public String toString(){
        return name;
    }
    public boolean completedToday(){

        //TODO compare with today date
        return true;
    }

    public UUID getID() {
        return habitID;
    }

    public int compareTo(Habit other){
        int retVal= this.dateCreated.compareTo(other.dateCreated);
        if(retVal==0){
            retVal=this.name.compareToIgnoreCase(other.name);
        }
        return retVal;
    }
    public LocalDate getDateCreated(){
        return this.dateCreated;
    }
}
