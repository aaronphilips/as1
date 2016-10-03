package ca.as1;

import android.text.TextUtils;
import android.util.Log;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    private String shortFormDaysString;

    public ArrayList<LocalDate> getCompletions() {
        return completions;
    }

    public void addCompletions(LocalDate newCompletion) {
        this.completions.add(newCompletion);
    }

    public void removeCompletion(LocalDate completion){
        if(completions.contains(completion)){
            completions.remove(completion);
        }
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
        this.shortFormDaysString = buildShortFormDaysString();

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
    public HashSet<DayOfWeek> getDaysOfWeek(){
        return daysOfWeek;
    }

    public String getShortFormDaysString(){
        return this.shortFormDaysString;
    }
    //http://stackoverflow.com/questions/33802971/alternative-for-string-join-in-android
    //didn't know about the android way of doing this and textutils
    private String buildShortFormDaysString(){
        ArrayList<String> strings= new ArrayList<String>();
        ArrayList<DayOfWeek> dayOfWeeks=new ArrayList<DayOfWeek>(this.getDaysOfWeek());
        Collections.sort(dayOfWeeks);
        for(DayOfWeek dayOfWeek:dayOfWeeks ){
            Log.d("GOT HABIT",dayOfWeek.toString());
            strings.add(dayOfWeek.toString().substring(0,3));
        }

        return TextUtils.join(", " ,strings);
    }

}
