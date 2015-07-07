package fr.thinkparc.subparts.stock;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import fr.thinkparc.app.HomeActivity;
import fr.thinkparc.app.R;
import fr.thinkparc.obj.Measurement;
import fr.thinkparc.obj.Part;
import fr.thinkparc.obj.Site;
import fr.thinkparc.util.Constants;
import fr.thinkparc.util.Utilities;
import fr.thinkparc.util.UtilitiesHTTP;

public class StockAddActivity extends Activity implements OnItemSelectedListener {

	Spinner spinnerReference;
	Spinner spinnerSites;
	Spinner spinnerKindStock;
	Spinner spinnerMeasurements;
	List<Part> parts;
	List<Site> sites;
	List<Measurement> measurements;
	Button btAdd;
	EditText etQuantity;
	EditText etMagasinNr;
	EditText etDriveway;
	EditText etBay;
	EditText etRack;
	EditText etLocker;
	EditText etPosition;
	EditText etStoreHouse;
	int choice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_add);

		// References for UI elements
		spinnerReference = (Spinner) findViewById(R.id.spinnerReference);
		spinnerSites = (Spinner) findViewById(R.id.spinnerSites);
		spinnerKindStock = (Spinner) findViewById(R.id.spinnerKinds);
		spinnerMeasurements = (Spinner) findViewById(R.id.spinnerMeasurements);
		etQuantity = (EditText) findViewById(R.id.etQuantity);
		etMagasinNr = (EditText) findViewById(R.id.etMagasinNr);
		etDriveway = (EditText) findViewById(R.id.etDriveway);
		etBay = (EditText) findViewById(R.id.etBay);
		etRack = (EditText) findViewById(R.id.etRack);
		etLocker = (EditText) findViewById(R.id.etLocker);
		etPosition = (EditText) findViewById(R.id.etPosition);
		etStoreHouse = (EditText) findViewById(R.id.etStoreHourse);

		// Spinner click listener
		spinnerReference.setOnItemSelectedListener(this);

		// Button Add listener
		btAdd = (Button) findViewById(R.id.btAdd);
		btAdd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				addStock();
			}
		});

		if(UtilitiesHTTP.isConnected((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
			choice = 1;
			new HttpAsyncTask().execute(
					"http://think-parc.com/webservice/v1/companies/" + Constants.ID_COMPANY + "/reporting/parts/all", 
					"http://think-parc.com/webservice/v1/companies/" + Constants.ID_COMPANY + "/sites", 
					"http://think-parc.com/webservice/v1/companies/stocks/measurements");
		}
	}

	private void addStock() {
		// 11 values
		String quantity = etQuantity.getText().toString();
		String id_measurement = getMeasurement(spinnerMeasurements.getSelectedItem().toString());
		String driveway = etDriveway.getText().toString();
		String bay = etBay.getText().toString();
		String position = etPosition.getText().toString();
		String locker = etLocker.getText().toString();
		String rack = etRack.getText().toString();
		String id_site = getSite(spinnerSites.getSelectedItem().toString());
		String id_typestock = getTypeStock(spinnerKindStock.getSelectedItem().toString());
		String id_part = getPart(spinnerReference.getSelectedItem().toString());
		String storehouse = etStoreHouse.getText().toString();

		// POST 
		if(UtilitiesHTTP.isConnected((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
			choice = 2;
			new HttpAsyncTask().execute("http://www.think-parc.com/webservice/v1/companies/stocks/addinstock/quanty/" + quantity + "/id_measurement/" + id_measurement + "/driveway/" + driveway + "/bay/" + bay + "/position/" + position + "/locker/" + locker + "/rack/" + rack + "/id_site/" + id_site + "/id_typestock/" + id_typestock + "/id_part/" + id_part + "/storehouse/" + storehouse);
		}
	}

	private String getSite(String site) {
		for (Site s : sites) {
			if (s.getName().equals(site)) {
				return s.getId_site(); 
			}
		}
		return null;
	}
	
	private String getMeasurement(String measurement) {
		for (Measurement m : measurements) {
			if (m.getMeasurement().equals(measurement)) {
				return m.getId_measurement(); 
			}
		}
		return null;
	}

	private String getPart(String reference) {
		for (Part p : parts) {
			if (p.getReference().equals(reference)) {
				return p.getId_part(); 
			}
		}
		return null;
	}

	private String getTypeStock(String kind) {
		if (kind.equals("En Stock")) {
			return "1";
		} else {
			return "2";
		}
	}

	private void getAllReferences(String r) throws JSONException {
		// Spinner Drop down elements
		parts = new ArrayList<Part>();

		JSONArray jsonarray = new JSONArray(r);
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobject = jsonarray.getJSONObject(i);
			Part p = new Part();
			p.setId_part(jsonobject.getString("id_part"));
			p.setReference(jsonobject.getString("reference"));
			parts.add(p);
		}

		// Creating adapter for spinner
		ArrayAdapter<Part> dataAdapter = new ArrayAdapter<Part>(this, android.R.layout.simple_spinner_item, parts);

		// Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinnerReference.setAdapter(dataAdapter);
	}
	
	private void getAllMeasurements(String r) throws JSONException  {
		// Spinner Drop down elements
		measurements = new ArrayList<Measurement>();

		JSONArray jsonarray = new JSONArray(r);
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobject = jsonarray.getJSONObject(i);
			Measurement m = new Measurement();
			m.setId_measurement(jsonobject.getString("id_measurement"));
			m.setMeasurement(jsonobject.getString("measurement"));
			m.setSymbol(jsonobject.getString("symbol"));
			measurements.add(m);
		}

		// Creating adapter for spinner
		ArrayAdapter<Measurement> dataAdapter = new ArrayAdapter<Measurement>(this, android.R.layout.simple_spinner_item, measurements);

		// Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinnerMeasurements.setAdapter(dataAdapter);
	}

	private void getAllSites(String r) throws JSONException {
		// Spinner Drop down elements
		sites = new ArrayList<Site>();

		JSONArray jsonarray = new JSONArray(r);
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobject = jsonarray.getJSONObject(i);
			Site s = new Site();
			s.setId_site(jsonobject.getString("id_site"));
			s.setName(jsonobject.getString("name"));
			sites.add(s);
		}

		// Creating adapter for spinner
		ArrayAdapter<Site> dataAdapter = new ArrayAdapter<Site>(this, android.R.layout.simple_spinner_item, sites);

		// Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinnerSites.setAdapter(dataAdapter);
	}

	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// Get the selected vehicle
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) { /* Not used. */ }

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(StockAddActivity.this, null, getResources().getString(R.string.loading), true, false);
		}

		@Override
		protected String doInBackground(String... urls) {
			switch (choice) {
			case 1:
				String req1 = UtilitiesHTTP.GET(urls[0]);
				String req2 = UtilitiesHTTP.GET(urls[1]);
				String req3 = UtilitiesHTTP.GET(urls[2]);
				String req = req1 + "<split>" + req2 + "<split>" + req3;
				return req;
			case 2:
				return UtilitiesHTTP.POST(urls[0]);
			}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			try {
				switch (choice) {
				case 1:
					String[] r = result.split("<split>");
					String r1 = r[0];
					String r2 = r[1];
					String r3 = r[2];
					getAllReferences(r1);
					getAllSites(r2);
					getAllMeasurements(r3);
					break;
				case 2:
					Utilities.openView(StockAddActivity.this, HomeActivity.class);
				}

				progressDialog.dismiss();
			} catch (JSONException e) {
				progressDialog.dismiss();
				e.printStackTrace();
			}
		}
	}
}
