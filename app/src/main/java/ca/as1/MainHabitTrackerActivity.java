package ca.as1;

import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.LocalDate;

public class MainHabitTrackerActivity extends Activity {

	private ListView oldHabitList;
	private HabitSetList habitSetList = new HabitSetList();
	private ArrayAdapter<Habit> adapter;
	private DayOfWeek day;
	private int dayInt= new LocalDate().getDayOfWeek();
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		habitSetList =HabitFileIO_Main.HabitFileIO.loadFromFile(this);



		Intent intent= getIntent();
		if(intent.hasExtra("dayVal")){

			dayInt= Integer.parseInt(intent.getStringExtra("dayVal"));

		}

		TextView habitTitle = (TextView) findViewById(R.id.habitTitle);
		habitTitle.setText("All Habits", TextView.BufferType.EDITABLE);

		if (dayInt>0){
			Log.d("GOT HABIT","actually day");
			 day= Helper.Functions.getDayfromInt(dayInt);
			habitTitle.setText(day.toString(), TextView.BufferType.EDITABLE);
		}

		Button add_habitButton = (Button) findViewById(R.id.add_habit);
		oldHabitList = (ListView) findViewById(R.id.oldHabitList);

		Button switchDayButton = (Button) findViewById(R.id.clear);

		add_habitButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				gotoAddHabitActivity();
				adapter.notifyDataSetChanged();
				HabitFileIO_Main.HabitFileIO.saveInFile(MainHabitTrackerActivity.this, habitSetList);
			}
		});
		switchDayButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				switchDay();

			}
		});
		oldHabitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Habit entry = (Habit) parent.getItemAtPosition(position);
				gotoHabitView(entry.getID());
			}
		});


	}
	@Override
	protected  void onResume(){

		habitSetList =HabitFileIO_Main.HabitFileIO.loadFromFile(this);
		adapter.notifyDataSetChanged();
		oldHabitList.invalidate();
		adapter = new ArrayAdapter<Habit>(this,
				R.layout.list_item, habitSetList.getHabitArrayList());
		if(dayInt>0){
			adapter = new ArrayAdapter<Habit>(this,
					R.layout.list_item, habitSetList.getHabitArrayList(Helper.Functions.getDayfromInt(dayInt)));
		}

		oldHabitList.setAdapter(adapter);

		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		adapter = new ArrayAdapter<Habit>(this,
				R.layout.list_item, habitSetList.getHabitArrayList());
		oldHabitList.setAdapter(adapter);
	}

	public void switchDay() {
		//http://stackoverflow.com/questions/6286847/how-do-i-create-an-android-spinner-as-a-popup
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setTitle("Choose Day to Check Habits");
		String[] types = {"All","Monday", "Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
		b.setItems(types, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
				String dayVal=Integer.toString(which);

				gotoDayActivity(dayVal);
			}

		});

		b.show();



	}
	public void gotoDayActivity(String dayVal){
		Intent intent = new Intent(this, MainHabitTrackerActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
		intent.putExtra("dayVal",dayVal);
		startActivity(intent);
	}

	public void gotoAddHabitActivity() {
		Intent intent = new Intent(this, AddHabitActivity.class);
		startActivity(intent);
	}

	public void gotoHabitView(UUID uuid){
		Intent intent = new Intent(MainHabitTrackerActivity.this,HabitView.class);
		intent.putExtra("uuid",uuid.toString());
		startActivity(intent);
	}



}