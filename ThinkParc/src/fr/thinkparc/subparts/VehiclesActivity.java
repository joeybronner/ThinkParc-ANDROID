package fr.thinkparc.subparts;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import fr.thinkparc.app.R;
import fr.thinkparc.obj.Vehicle;
import fr.thinkparc.util.Constants;
import fr.thinkparc.util.UtilitiesHTTP;

public class VehiclesActivity extends Activity implements OnItemSelectedListener {

	//EditText etResponse;
	Spinner spinnerVehicles;
	List<Vehicle> vehicles;
	TextView tvSerialNr_val;
	TextView tvBuyingDate_val;
	TextView tvCirculationDate_val;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vehicles);

		// References for UI elements
		spinnerVehicles = (Spinner) findViewById(R.id.spinnerVehicles);
		tvSerialNr_val = (TextView) findViewById(R.id.tvSerialNr_val);
		tvBuyingDate_val = (TextView) findViewById(R.id.tvBuyingDate_val);
		tvCirculationDate_val = (TextView) findViewById(R.id.tvCirculationDate_val);
		
		// Spinner click listener
		spinnerVehicles.setOnItemSelectedListener(this);

		if(UtilitiesHTTP.isConnected((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
			// call AsynTask to perform network operation on separate thread
			new HttpAsyncTask().execute("http://think-parc.com/webservice/v1/companies/" + Constants.ID_COMPANY + "/vehicles/all");
		}

	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Get the selected vehicle
        Vehicle v = (Vehicle) parent.getItemAtPosition(position);
        
        // Set vehicle values
        tvSerialNr_val.setText(v.getNr_serial());
        tvBuyingDate_val.setText(v.getBuyingprice());
        tvCirculationDate_val.setText(v.getDate_entryservice());
    }
	
	@Override
	public void onNothingSelected(AdapterView<?> parent) { /* Not used. */ }

	private void getAllVehicles(String r) throws JSONException {
		// Spinner Drop down elements
		vehicles = new ArrayList<Vehicle>();
		
		JSONArray jsonarray = new JSONArray(r);
		for (int i = 0; i < jsonarray.length(); i++) {
		    JSONObject jsonobject = jsonarray.getJSONObject(i);
		    Vehicle v = new Vehicle();
		    v.setNr_plate(jsonobject.getString("nr_plate"));
		    v.setNr_serial(jsonobject.getString("nr_serial"));
		    v.setBuyingprice(jsonobject.getString("date_buy"));
		    v.setDate_entryservice(jsonobject.getString("date_entryservice"));
		    vehicles.add(v);
		}

		// Creating adapter for spinner
		ArrayAdapter<Vehicle> dataAdapter = new ArrayAdapter<Vehicle>(this, android.R.layout.simple_spinner_item, vehicles);

		// Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// attaching data adapter to spinner
		spinnerVehicles.setAdapter(dataAdapter);
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			return UtilitiesHTTP.GET(urls[0]);
		}
		@Override
		protected void onPostExecute(String result) {
			try {
				getAllVehicles(result);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}


	/*
	 * ActionBar elements
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.vehicles, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_gotowebsite) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
