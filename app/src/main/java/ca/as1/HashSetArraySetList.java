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
 * Created solely as a wrapper class to abstract indexing from a set of habit
 */
public class HashSetArraySetList{
    private HashMap<UUID,Habit> habitHashMap;
    //private ArrayList<Habit> habitArrayList;

    public HashSetArraySetList(){
        this.habitHashMap= new HashMap<UUID, Habit>();

    }
    public Habit getHabit(UUID id){
        return habitHashMap.get(id);
    }
    public void removeHabit(UUID id){
        habitHashMap.remove(id);
    }
    public ArrayList<Habit> getHabitArrayList(){
        ArrayList<Habit>ret_list= new ArrayList<Habit>();
        for(Map.Entry<UUID,Habit> pair:habitHashMap.entrySet()){
            ret_list.add(pair.getValue());
        }
        Collections.sort(ret_list);
        return ret_list;
    }
//    private ArrayList<Habit> convertToArraylist(){
//        //for(Entry)
//        return new ArrayList<Habit>();
//    }



}
