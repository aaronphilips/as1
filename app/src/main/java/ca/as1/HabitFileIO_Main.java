package ca.as1;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.joda.time.LocalDate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Aaron Philips on 9/28/2016.
 * file to load and save from internal memory
 * uses gson
 */

public class HabitFileIO_Main {
    private static final String FILENAME = "file.sav";


    public static class HabitFileIO{
        public static HabitSetList loadFromFile(Context context) {
            ArrayList<Habit> habitList= new ArrayList<Habit>();
            HabitSetList habitSetList=new HabitSetList();
            try {
                FileInputStream fis = context.openFileInput(FILENAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));

                final GsonBuilder builder = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateSerializer());

                final Gson gson = builder.create();

                //code taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt sept 22nd
                Type listType=new TypeToken<ArrayList<Habit>>(){}.getType();
                habitList = gson.fromJson(in,listType);
                habitSetList=new HabitSetList(habitList);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                //throw new RuntimeException();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                throw new RuntimeException();
            }
            return habitSetList;
        }
        public static void saveInFile(Context context, HabitSetList habitSetList) {
            try {

                FileOutputStream fos = context.openFileOutput(FILENAME,0);
                OutputStreamWriter writer = new OutputStreamWriter(fos);
                final GsonBuilder builder = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateSerializer());

                final Gson gson = builder.create();
                gson.toJson(habitSetList.getHabitArrayList(),writer);
                writer.flush();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                throw new RuntimeException();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                throw new RuntimeException();
            }
        }
    }


}
