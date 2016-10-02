package ca.as1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashSet;

public class AddHabitActivity extends Activity {
    private HabitSetList habitSetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        Button add_habitButton = (Button) findViewById(R.id.addHabitButton);
        Button setDateButoon =(Button)findViewById(R.id.setDateButton);
        EditText dateEditText = (EditText) findViewById(R.id.dateCreatedInput);

        final LocalDate date = new LocalDate();
        dateEditText.setText(date.toString(), TextView.BufferType.EDITABLE);
        habitSetList =HabitFileIO_Main.HabitFileIO.loadFromFile(this);


        add_habitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);


                //Tweet newTweet = new NormalTweet(text);

                //newTweet.getMessage();

                //habitSetList.add(newTweet);

                //adapter.notifyDataSetChanged();
                addHabit();
                HabitFileIO_Main.HabitFileIO.saveInFile(AddHabitActivity.this, habitSetList);
                finish();
            }
        });

        setDateButoon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                //date picker uses zero index monts
                DatePickerDialog datePickerDialog=new DatePickerDialog(AddHabitActivity.this,datePickerListener,date.getYear(), date.getMonthOfYear()-1,date.getDayOfMonth());
                datePickerDialog.setTitle("Select the created date");
                datePickerDialog.show();
            }
        });
    }
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            EditText dateEditText = (EditText) findViewById(R.id.dateCreatedInput);
            LocalDate date = new LocalDate(year,monthOfYear+1,dayOfMonth);
            dateEditText.setText(date.toString(), TextView.BufferType.EDITABLE);
        }
    };

    private Habit buildHabit(){
        HashSet<DayOfWeek> selectedDays=getSelectedDays(getCheckboxes());
        String habitName=((EditText) findViewById((R.id.habitNameInput))).getText().toString();

        String dateEditText = ((EditText) findViewById(R.id.dateCreatedInput)).getText().toString();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate dt = dtf.parseLocalDate(dateEditText);


        Habit habit = new Habit(habitName,dt,selectedDays);

        return habit;
    }

    private void addHabit(){
        Habit newhabit=buildHabit();
        habitSetList.addHabit(newhabit);
    }
    private void saveHabits(){

    }

    private HashSet<DayOfWeek> getSelectedDays(ArrayList<CheckBox> checkBoxes){
        HashSet<DayOfWeek> selectedDays = new HashSet<DayOfWeek>();

        for(CheckBox chk:checkBoxes){
            if(chk.getId()==R.id.checkBox_MON&&chk.isChecked()) selectedDays.add(DayOfWeek.MONDAY);
            if(chk.getId()==R.id.checkBox_TUE&&chk.isChecked()) selectedDays.add(DayOfWeek.TUESDAY);
            if(chk.getId()==R.id.checkBox_WED&&chk.isChecked()) selectedDays.add(DayOfWeek.WEDNESDAY);
            if(chk.getId()==R.id.checkBox_THU&&chk.isChecked()) selectedDays.add(DayOfWeek.THURSDAY);
            if(chk.getId()==R.id.checkBox_FRI&&chk.isChecked()) selectedDays.add(DayOfWeek.FRIDAY);
            if(chk.getId()==R.id.checkBox_SAT&&chk.isChecked()) selectedDays.add(DayOfWeek.SATURDAY);
            if(chk.getId()==R.id.checkBox_SUN&&chk.isChecked()) selectedDays.add(DayOfWeek.SUNDAY);

        }
        return selectedDays;
    }

    private ArrayList<CheckBox>getCheckboxes(){
        ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
        ViewGroup viewGroup =(ViewGroup)getWindow().getDecorView();
        findCheckBoxes(viewGroup,checkBoxes);
        return checkBoxes;
    }
    private static void findCheckBoxes(ViewGroup viewGroup,ArrayList<CheckBox> checkBoxes){
        for (int i = 0, N = viewGroup.getChildCount(); i < N; i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                findCheckBoxes((ViewGroup) child, checkBoxes);
            } else if (child instanceof CheckBox) {
                checkBoxes.add((CheckBox) child);
            }
        }
    }
    //private DayOfWeek getDays(Array<>)
}
