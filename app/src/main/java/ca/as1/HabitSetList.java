package ca.as1;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Aaron Philips on 9/28/2016.
 * Created solely as a wrapper class to abstract indexing from a set of habit.
 * I wanted the behaviour of a set, but to easily get arraylists, for adapters
 * Also wanted arraylist depending on day I wanted to view
 */
public class HabitSetList {
    private HashMap<UUID,Habit> habitHashMap;
    //private ArrayList<Habit> habitArrayList;

    public HabitSetList(ArrayList<Habit> inputHabitList){
        this.habitHashMap= new HashMap<UUID, Habit>();
        for(Habit habit:inputHabitList){
            habitHashMap.put(habit.getID(),habit);
        }
    }
    public HabitSetList(){
        this.habitHashMap= new HashMap<UUID, Habit>();
    }
    public Habit getHabit(UUID id){
        return habitHashMap.get(id);
    }

    public void removeHabit(UUID id){
        habitHashMap.remove(id);
    }

    public void addHabit(Habit habit){habitHashMap.put(habit.getID(),habit);}

    public ArrayList<Habit> getHabitArrayList(){
        ArrayList<Habit>ret_list= new ArrayList<Habit>();
        for(Map.Entry<UUID,Habit> pair:habitHashMap.entrySet()){
            ret_list.add(pair.getValue());
        }
        Collections.sort(ret_list);
        return ret_list;
    }
    //overloaded constructor for specific day view
    public ArrayList<Habit> getHabitArrayList(DayOfWeek dayOfWeek){
        ArrayList<Habit>ret_list= new ArrayList<Habit>();
        for(Map.Entry<UUID,Habit> pair:habitHashMap.entrySet()){
            if(pair.getValue().getDaysOfWeek().contains(dayOfWeek)){
                ret_list.add(pair.getValue());
            }
        }
        Collections.sort(ret_list);
        return ret_list;
    }
//


}
