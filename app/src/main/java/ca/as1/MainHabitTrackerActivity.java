package ca.as1;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainHabitTrackerActivity extends Activity {

	//private static final String FILENAME = "file.sav";
	//private EditText bodyText;
	private ListView oldHabitList;
	private ArrayList<Habit> habitList = new ArrayList<Habit>();
	private ArrayAdapter<Habit> adapter;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		habitList=HabitFileIO_Main.HabitFileIO.loadFromFile(this);
		//bodyText = (EditText) findViewById(R.id.body);
		Button add_habitButton = (Button) findViewById(R.id.add_habit);
		oldHabitList = (ListView) findViewById(R.id.oldHabitList);
		Button switchDayButton = (Button) findViewById(R.id.clear);
		switchDayButton.setText(R.string.switch_day);
		add_habitButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				//Tweet newTweet = new NormalTweet(text);
				//newTweet.getMessage();
				//habitList.add(newTweet);
				gotoAddHabitActivity();
				adapter.notifyDataSetChanged();
				HabitFileIO_Main.HabitFileIO.saveInFile(MainHabitTrackerActivity.this, habitList);
			}
		});
		switchDayButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
//				adapter.clear();
//				habitList.clear();
				switchDay();

			}
		});
		oldHabitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Habit entry = (Habit) parent.getItemAtPosition(position);
				Log.d("GOT HABIT",entry.toString());
			}
		});


	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		adapter = new ArrayAdapter<Habit>(this,
				R.layout.list_item, habitList);
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
//			habitList = gson.fromJson(in,listType);
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
//			gson.toJson(habitList,writer);
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
		Intent intent = new Intent(this, MainHabitTrackerActivity.class);
		//intent.putExtra()
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
		startActivity(intent);
	}

	public void gotoAddHabitActivity() {
		Intent intent = new Intent(this, AddHabitActivity.class);
		startActivity(intent);
	}



}