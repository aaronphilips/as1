package ca.as1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

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
 */

public class HabitFileIO_Main {
    private static final String FILENAME = "file.sav";


    public static class HabitFileIO{
        public static ArrayList<Habit> loadFromFile(Context context) {
            ArrayList<Habit> habitList= new ArrayList<Habit>();
            try {
                FileInputStream fis = context.openFileInput(FILENAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));

                final GsonBuilder builder = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateSerializer());

                final Gson gson = builder.create();

                //code taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt sept 22nd
                Type listType=new TypeToken<ArrayList<Habit>>(){}.getType();
                habitList = gson.fromJson(in,listType);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                //throw new RuntimeException();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                throw new RuntimeException();
            }
            return habitList;
        }
        public static void saveInFile(Context context, ArrayList<Habit> habitList) {
            try {

                FileOutputStream fos = context.openFileOutput(FILENAME,0);
                OutputStreamWriter writer = new OutputStreamWriter(fos);
                final GsonBuilder builder = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateSerializer());

                final Gson gson = builder.create();
                gson.toJson(habitList,writer);
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
