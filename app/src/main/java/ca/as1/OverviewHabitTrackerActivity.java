package ca.as1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class OverviewHabitTrackerActivity extends Activity {

	private static final String FILENAME = "file.sav";
	private EditText bodyText;
	private ListView oldHabitList;
	private ArrayList<Habit> habitList =new ArrayList<Habit>();
	private ArrayAdapter<Habit> adapter;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		loadFromFile();
		//bodyText = (EditText) findViewById(R.id.body);
		//Button saveButton = (Button) findViewById(R.id.save);
		oldHabitList = (ListView) findViewById(R.id.oldHabitList);
		Button clearButton= (Button) findViewById(R.id.clear);
		clearButton.setText(R.string.switch_day);
//		saveButton.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				setResult(RESULT_OK);
//
//
//				//Tweet newTweet = new NormalTweet(text);
//
//				//newTweet.getMessage();
//
//				//habitList.add(newTweet);
//				adapter.notifyDataSetChanged();
//				saveInFile();
//			}
//		});
		clearButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){
				setResult(RESULT_OK);
				adapter.clear();
				habitList.clear();
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

	private void loadFromFile() {

		try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			Gson gson =new Gson();
			//code taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt sept 22nd
			Type listType=new TypeToken<ArrayList<Habit>>(){}.getType();
			habitList = gson.fromJson(in,listType);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Hey \n Looks like this is the first time you used Aaron Philips' habit tracker")
					.setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							//do things
						}
					});
			AlertDialog alert = builder.create();
			alert.show();
			//throw new RuntimeException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}
	
	private void saveInFile() {
		try {

			FileOutputStream fos = openFileOutput(FILENAME,0);
			OutputStreamWriter writer = new OutputStreamWriter(fos);
			Gson gson = new Gson();
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