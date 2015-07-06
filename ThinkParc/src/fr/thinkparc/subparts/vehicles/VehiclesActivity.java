package fr.thinkparc.subparts.vehicles;

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
	TextView tvBrand_val;
	TextView tvModel_val;
	TextView tvMileage_val;
	TextView tvEnergy_val;
	TextView tvState_val;
	TextView tvCategory_val;
	TextView tvBuyingPrice_val;
	TextView tvEquipments_val;
	TextView tvSite_val;
	TextView tvCommentary_val;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vehicles);

		// References for UI elements
		spinnerVehicles = (Spinner) findViewById(R.id.spinnerVehicles);
		tvSerialNr_val = (TextView) findViewById(R.id.tvSerialNr_val);
		tvBuyingDate_val = (TextView) findViewById(R.id.tvBuyingDate_val);
		tvCirculationDate_val = (TextView) findViewById(R.id.tvCirculationDate_val);
		tvBrand_val = (TextView) findViewById(R.id.tvBrand_val);
		tvModel_val = (TextView) findViewById(R.id.tvModel_val);
		tvMileage_val = (TextView) findViewById(R.id.tvMileage_val);
		tvEnergy_val = (TextView) findViewById(R.id.tvEnergy_val);
		tvState_val = (TextView) findViewById(R.id.tvState_val);
		tvCategory_val = (TextView) findViewById(R.id.tvCategory_val);
		tvBuyingPrice_val = (TextView) findViewById(R.id.tvBuyingPrice_val);
		tvEquipments_val = (TextView) findViewById(R.id.tvEquipments_val);
		tvSite_val = (TextView) findViewById(R.id.tvSite_val);
		tvCommentary_val = (TextView) findViewById(R.id.tvCommentary_val);
		
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
        tvBuyingDate_val.setText(v.getDate_buy());
        tvCirculationDate_val.setText(v.getDate_entryservice());
        tvBrand_val.setText(v.getBrand());
        tvModel_val.setText(v.getModel());
        tvMileage_val.setText(v.getMileage());
        tvEnergy_val.setText(v.getEnergy());
        tvState_val.setText(v.getState());
        tvCategory_val.setText(v.getCategory());
        tvBuyingPrice_val.setText(v.getBuyingprice() + " " + v.getSymbol());
        tvEquipments_val.setText(v.getEquipments());
        tvSite_val.setText(v.getSite());
        tvCommentary_val.setText(v.getCommentary());
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
		    v.setDate_buy(jsonobject.getString("date_buy"));
		    v.setDate_entryservice(jsonobject.getString("date_entryservice"));
		    v.setBrand(jsonobject.getString("brand"));
		    v.setModel(jsonobject.getString("model"));
		    v.setMileage(jsonobject.getString("mileage"));
		    v.setEnergy(jsonobject.getString("energy"));
		    v.setState(jsonobject.getString("state"));
		    v.setCategory(jsonobject.getString("category"));
		    v.setBuyingprice(jsonobject.getString("buyingprice"));
		    v.setSymbol(jsonobject.getString("symbol"));
		    v.setEquipments(jsonobject.getString("equipments"));
		    v.setSite(jsonobject.getString("name"));
		    v.setCommentary(jsonobject.getString("commentary"));
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
}
