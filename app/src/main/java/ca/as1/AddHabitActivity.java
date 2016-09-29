package ca.as1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

public class AddHabitActivity extends Activity {
    private ArrayList<Habit> habitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        Button add_habitButton = (Button) findViewById(R.id.add_habit);
        habitList=HabitFileIO_Main.HabitFileIO.loadFromFile(this);
        add_habitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);


                //Tweet newTweet = new NormalTweet(text);

                //newTweet.getMessage();

                //habitList.add(newTweet);

                //adapter.notifyDataSetChanged();
                HabitFileIO_Main.HabitFileIO.saveInFile(AddHabitActivity.this, habitList);
            }
        });
    }
    private Habit buildHabit{

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
