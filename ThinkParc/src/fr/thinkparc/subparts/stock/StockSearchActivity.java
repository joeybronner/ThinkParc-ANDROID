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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import fr.thinkparc.app.R;
import fr.thinkparc.obj.Part;
import fr.thinkparc.obj.Site;
import fr.thinkparc.util.Constants;
import fr.thinkparc.util.Utilities;
import fr.thinkparc.util.UtilitiesHTTP;

public class StockSearchActivity extends Activity {

	Spinner spinnerReference;
	Spinner spinnerSites;
	Button btSearch;
	List<Part> parts;
	List<Site> sites;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_search);

		// References for UI elements
		spinnerReference = (Spinner) findViewById(R.id.spinnerReference);
		spinnerSites = (Spinner) findViewById(R.id.spinnerSites);

		// Button Add listener
		btSearch = (Button) findViewById(R.id.btSearch);
		btSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String id_site = getSite(spinnerSites.getSelectedItem().toString());
				String id_part = getPart(spinnerReference.getSelectedItem().toString());
				Utilities.openView(StockSearchActivity.this, StockSearchResultActivity.class, id_part, id_site);
			}
		});

		if(UtilitiesHTTP.isConnected((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
			new HttpAsyncTask().execute(
					"http://think-parc.com/webservice/v1/companies/" + Constants.ID_COMPANY + "/reporting/parts/all", 
					"http://think-parc.com/webservice/v1/companies/" + Constants.ID_COMPANY + "/sites");
		}
	}
	
	private String getPart(String reference) {
		for (Part p : parts) {
			if (p.getReference().equals(reference)) {
				return p.getId_part(); 
			}
		}
		return null;
	}
	
	private String getSite(String site) {
		for (Site s : sites) {
			if (s.getName().equals(site)) {
				return s.getId_site(); 
			}
		}
		return null;
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


	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(StockSearchActivity.this, null, getResources().getString(R.string.loading), true, false);
		}

		@Override
		protected String doInBackground(String... urls) {
			String req1 = UtilitiesHTTP.GET(urls[0]);
			String req2 = UtilitiesHTTP.GET(urls[1]);
			String req = req1 + "<split>" + req2;
			return req;
		}
		@Override
		protected void onPostExecute(String result) {
			try {
				String[] r = result.split("<split>");
				String r1 = r[0];
				String r2 = r[1];
				getAllReferences(r1);
				getAllSites(r2);
				progressDialog.dismiss();
			} catch (JSONException e) {
				progressDialog.dismiss();
				e.printStackTrace();
			}
		}
	}
}
