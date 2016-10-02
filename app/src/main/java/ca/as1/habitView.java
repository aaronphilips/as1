package ca.as1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.util.UUID;

public class HabitView extends Activity {
    private HabitSetList habitSetList;
    private Habit habit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_view);

        habitSetList =HabitFileIO_Main.HabitFileIO.loadFromFile(this);

        Intent intent = getIntent();
        UUID habitID=UUID.randomUUID();
        if(intent.hasExtra("uuid")){
            habitID=UUID.fromString(intent.getStringExtra("uuid"));

            habit=habitSetList.getHabit(habitID);
            Log.d("GOT HABIT",habitSetList.getHabit(habitID).toString());
        }


        EditText dateEditText = (EditText) findViewById(R.id.habitViewDate);
        final LocalDate date = habit.getDateCreated();
        dateEditText.setText(date.toString(), TextView.BufferType.EDITABLE);






        TextView textView=(TextView)findViewById(R.id.habitViewTitle);
        textView.setText(habit.toString(), TextView.BufferType.EDITABLE);
        Button deleteButton= (Button)findViewById(R.id.deleteHabitButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //http://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-on-android
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                habitSetList.removeHabit(habit.getID());
                                HabitFileIO_Main.HabitFileIO.saveInFile(HabitView.this,habitSetList);
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(HabitView.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        Button addCompletionButton = (Button) findViewById(R.id.addCompletionButton);

        Button changeDateButton = (Button) findViewById(R.id.changeDateButton);
        changeDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                //date picker uses zero index monts
                DatePickerDialog datePickerDialog=new DatePickerDialog(HabitView.this,datePickerListener,date.getYear(), date.getMonthOfYear()-1,date.getDayOfMonth());
                datePickerDialog.setTitle("Select the created date");
                datePickerDialog.show();
            }
        });



    }
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            EditText dateEditText = (EditText) findViewById(R.id.habitViewDate);
            LocalDate date = new LocalDate(year,monthOfYear+1,dayOfMonth);
            dateEditText.setText(date.toString(), TextView.BufferType.EDITABLE);
        }
    };
}
