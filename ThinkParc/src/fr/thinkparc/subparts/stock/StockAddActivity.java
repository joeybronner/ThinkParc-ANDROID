package fr.thinkparc.subparts.stock;

/* ======================================================================== *
 *																			*
 * @filename:		StockAddActivity.java									*
 * @description:	StockAddActivity allows a user to do a stock entry.		*
 *																			*
 * @author(s): 		Joey BRONNER											*
 * @contact(s):		joeybronner@gmail.com									*
 * @creation: 		01/07/2015												*
 * @remarks:		-														*
 * 																			*
 * @rights:			Think-Parc Software ©, 2015.							*
 *																			*
 *																			*
 * Date       | Developer      | Changes description						* 
 * ------------------------------------------------------------------------ *
 * 01/07/2015 | J.BRONNER      | Creation									*
 * ------------------------------------------------------------------------ *
 * JJ/MM/AAAA | ...			   | ...			 							*
 * =========================================================================*/

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
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
	int quantity;
	String id_measurement;
	String driveway;
	String bay;
	String position;
	String locker;
	String rack;
	String id_site;
	String id_typestock;
	String id_part;
	String storehouse;

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
			@Override
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

		quantity = Integer.parseInt(etQuantity.getText().toString());
		id_measurement = getMeasurement(spinnerMeasurements.getSelectedItem().toString());
		driveway = etDriveway.getText().toString();
		bay = etBay.getText().toString();
		position = etPosition.getText().toString();
		locker = etLocker.getText().toString();
		rack = etRack.getText().toString();
		id_site = getSite(spinnerSites.getSelectedItem().toString());
		id_typestock = getTypeStock(spinnerKindStock.getSelectedItem().toString());
		id_part = getPart(spinnerReference.getSelectedItem().toString());
		storehouse = etStoreHouse.getText().toString().trim();

		if (checkStockFieldsBeforeInsertion()) {
			// POST 
			if(UtilitiesHTTP.isConnected((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
				choice = 2;
				new HttpAsyncTask().execute("http://www.think-parc.com/webservice/v1/companies/stocks/addinstock/quanty/" + quantity + "/id_measurement/" + id_measurement + "/driveway/" + driveway + "/bay/" + bay + "/position/" + position + "/locker/" + locker + "/rack/" + rack + "/id_site/" + id_site + "/id_typestock/" + id_typestock + "/id_part/" + id_part + "/storehouse/" + storehouse);
			}
		} else {
			Toast.makeText(this, getResources().getString(R.string.stock_error_fields), Toast.LENGTH_SHORT).show();
		}
	}

	private boolean checkStockFieldsBeforeInsertion() {
		try {

			if (quantity < 1) {
				return false;
			}

			if (driveway.equals("") || driveway.isEmpty()) {
				return false;
			}

			if (bay.equals("") || bay.isEmpty()) {
				return false;
			}

			if (position.equals("") || position.isEmpty()) {
				return false;
			}

			if (locker.equals("") || locker.isEmpty()) {
				return false;
			}

			if (rack.equals("") || rack.isEmpty()) {
				return false;
			}

			if (storehouse.equals("") || storehouse.isEmpty()) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return  false;
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

	@SuppressLint("InflateParams")
	private void showSuccess() {
		// Show success message and redirect
		LayoutInflater inflater = getLayoutInflater();
		View dialoglayout = inflater.inflate(R.layout.success, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setView(dialoglayout);
		builder.setCancelable(false);

		// Close after X seconds
		final AlertDialog success = builder.create();
		success.show();

		// Hide after some seconds
		final Handler handler  = new Handler();
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if (success.isShowing()) {
					success.dismiss();
					Utilities.openView(StockAddActivity.this, HomeActivity.class);
				}
			}
		};

		success.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				handler.removeCallbacks(runnable);
			}
		});

		handler.postDelayed(runnable, 1500);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { /* Nothing */ }

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
					showSuccess();
				}

				progressDialog.dismiss();
			} catch (JSONException e) {
				progressDialog.dismiss();
				e.printStackTrace();
			}
		}
	}
}
