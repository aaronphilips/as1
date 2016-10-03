package ca.as1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.util.UUID;

public class HabitViewActivity extends Activity {
    private HabitSetList habitSetList;
    private Habit habit;
    private ListView completionsList;
    private ArrayAdapter<LocalDate> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set up views and load from memory
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_view);

        habitSetList =HabitFileIO_Main.HabitFileIO.loadFromFile(this);

        Intent intent = getIntent();
        UUID habitID=UUID.randomUUID();
        if(intent.hasExtra("uuid")){
            habitID=UUID.fromString(intent.getStringExtra("uuid"));

            habit=habitSetList.getHabit(habitID);
        }


        EditText dateEditText = (EditText) findViewById(R.id.habitViewDate);
        final LocalDate date = habit.getDateCreated();
        dateEditText.setText(date.toString(), TextView.BufferType.EDITABLE);

        TextView habitTitleTextView=(TextView)findViewById(R.id.habitViewTitle);
        habitTitleTextView.setText(habit.toString(), TextView.BufferType.EDITABLE);



        TextView shortFormDays=(TextView)findViewById(R.id.availableDays);
        shortFormDays.setText(habit.getShortFormDaysString(), TextView.BufferType.EDITABLE);


        completionsList=(ListView) findViewById(R.id.completionsListView);
        completionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               final LocalDate date= (LocalDate) parent.getItemAtPosition(position);
                //http://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-on-android
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                deleteCompletion(date);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(HabitViewActivity.this);
                builder.setMessage("Delete this completion?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        Button deleteButton= (Button)findViewById(R.id.deleteHabitButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //http://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-on-android
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                deleteHabit();
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(HabitViewActivity.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        Button addCompletionButton = (Button) findViewById(R.id.addCompletionButton);
        addCompletionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                insertCompletion();
            }
        });

        Button changeDateButton = (Button) findViewById(R.id.changeDateButton);
        changeDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                //date picker uses zero index months
                DatePickerDialog datePickerDialog=new DatePickerDialog(HabitViewActivity.this,datePickerListener,date.getYear(), date.getMonthOfYear()-1,date.getDayOfMonth());
                datePickerDialog.setTitle("Select the created date");
                datePickerDialog.show();
            }
        });



    }
    //handle what happens to delete habit from internal memory
    private void deleteHabit() {
        habitSetList.removeHabit(habit.getID());
        HabitFileIO_Main.HabitFileIO.saveInFile(HabitViewActivity.this,habitSetList);
    }
    //handle new completion
    private void insertCompletion() {
        habit.addCompletion(new LocalDate());
        adapter.notifyDataSetChanged();
        TextView completionTextView=(TextView)findViewById(R.id.completionCount);
        completionTextView.setText(Integer.toString(habit.getCompletions().size()), TextView.BufferType.EDITABLE);
    }
    //handle deletion of completion
    private void deleteCompletion(LocalDate date) {
        habit.removeCompletion(date);
        adapter.notifyDataSetChanged();
        TextView completionTextView=(TextView)findViewById(R.id.completionCount);
        completionTextView.setText(Integer.toString(habit.getCompletions().size()), TextView.BufferType.EDITABLE);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            EditText dateEditText = (EditText) findViewById(R.id.habitViewDate);
            LocalDate date = new LocalDate(year,monthOfYear+1,dayOfMonth);
            dateEditText.setText(date.toString(), TextView.BufferType.EDITABLE);
        }
    };
    @Override
    protected void onStart(){
        super.onStart();

//        adapter = new ArrayAdapter<DateTime>(this,
//                R.layout.list_item, habit.getCompletions());
//        completionsList.setAdapter(adapter);


    }
    @Override
    protected void onResume(){
        super.onResume();
        adapter = new ArrayAdapter<LocalDate>(this,
                R.layout.list_item, habit.getCompletions());
        completionsList.setAdapter(adapter);
        TextView completionTextView=(TextView)findViewById(R.id.completionCount);
        completionTextView.setText(Integer.toString(habit.getCompletions().size()), TextView.BufferType.EDITABLE);

    }
    @Override
    protected void onStop(){


        habitSetList.removeHabit(habit.getID());
        habitSetList.addHabit(habit);
        HabitFileIO_Main.HabitFileIO.saveInFile(this,habitSetList);
        super.onStop();
    }

}
