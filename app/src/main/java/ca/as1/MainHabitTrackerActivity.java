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

	//private static final String FILENAME = "file.sav";
	//private EditText bodyText;
	private ListView oldHabitList;
	private HabitSetList habitSetList = new HabitSetList();
	private ArrayAdapter<Habit> adapter;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		int dayInt= new LocalDate().getDayOfWeek();
		Log.d("GOT HABIT",Integer.toString(dayInt));
		Intent intent= getIntent();
		if(intent.hasExtra("dayVal")){
			String dayVal=intent.getStringExtra("dayVal");

			dayInt= Integer.parseInt(dayVal);
			Log.d("GOT HABIT",Integer.toString(dayInt));
		}


		DayOfWeek day= Helper.Functions.getDayfromInt(dayInt);

		TextView habitTitle = (TextView) findViewById(R.id.habitTitle);
		habitTitle.setText(day.toString(), TextView.BufferType.EDITABLE);
		habitSetList =HabitFileIO_Main.HabitFileIO.loadFromFile(this);

		Button add_habitButton = (Button) findViewById(R.id.add_habit);

		oldHabitList = (ListView) findViewById(R.id.oldHabitList);

		Button switchDayButton = (Button) findViewById(R.id.clear);

		add_habitButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				//Tweet newTweet = new NormalTweet(text);
				//newTweet.getMessage();
				//habitSetList.add(newTweet);
				gotoAddHabitActivity();
				adapter.notifyDataSetChanged();
				HabitFileIO_Main.HabitFileIO.saveInFile(MainHabitTrackerActivity.this, habitSetList);
			}
		});
		switchDayButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
//				adapter.clear();
//				habitSetList.clear();
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

//	private void loadFromFile() {
//
//		try {
//			FileInputStream fis = openFileInput(FILENAME);
//			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
//			Gson gson =new Gson();
//			//code taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt sept 22nd
//			Type listType=new TypeToken<ArrayList<Habit>>(){}.getType();
//			habitSetList = gson.fromJson(in,listType);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setMessage("Hey \n Looks like this is the first time you used Aaron Philips' habit tracker")
//					.setCancelable(false)
//					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialog, int id) {
//							//do things
//						}
//					});
//			AlertDialog alert = builder.create();
//			alert.show();
//			//throw new RuntimeException();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			throw new RuntimeException();
//		}
//	}

//	private void saveInFile() {
//		try {
//
//			FileOutputStream fos = openFileOutput(FILENAME,0);
//			OutputStreamWriter writer = new OutputStreamWriter(fos);
//			Gson gson = new Gson();
//			gson.toJson(habitSetList,writer);
//			writer.flush();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			throw new RuntimeException();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			throw new RuntimeException();
//		}
//	}

	public void switchDay() {
		//http://stackoverflow.com/questions/6286847/how-do-i-create-an-android-spinner-as-a-popup
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setTitle("Choose Day to Check Habits");
		String[] types = {"Monday", "Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
		b.setItems(types, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
				String dayVal="";
				switch(which){
					case 0:
						dayVal="1";
						break;
					case 1:
						dayVal="2";
						break;
					case 2:
						dayVal="3";
						break;
					case 3:
						dayVal="4";
						break;
					case 4:
						dayVal="5";
						break;
					case 5:
						dayVal="6";
						break;
					case 6:

						dayVal="7";
						break;
				}

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