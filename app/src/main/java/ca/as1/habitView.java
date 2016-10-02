package ca.as1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

public class HabitView extends Activity {
    private HabitSetList habitSetList;
    private Habit habit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_view);

        habitSetList=HabitFileIO_Main.HabitFileIO.loadFromFile(this);
        Intent intent = getIntent();
        UUID habitID=UUID.randomUUID();
        if(intent.hasExtra("uuid")){
            habitID=UUID.fromString(intent.getStringExtra("uuid"));

            habit=habitSetList.getHabit(habitID);
            Log.d("GOT HABIT",habitSetList.getHabit(habitID).toString());
        }
        TextView textView=(TextView)findViewById(R.id.habitViewTitle);
        textView.setText(habit.toString(), TextView.BufferType.EDITABLE);
        Button deleteButton= (Button)findViewById(R.id.deleteHabitButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
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


    }
}
