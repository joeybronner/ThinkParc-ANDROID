package fr.thinkparc.subparts.maintenance;

/* ======================================================================== *
 *																			*
 * @filename:		MaintenanceActivity.java								*
 * @description:	This activity allows a user to add some part on a		*
 * 					current vehicle maintenance								*
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import fr.thinkparc.app.R;
import fr.thinkparc.obj.Maintenance;
import fr.thinkparc.util.Constants;
import fr.thinkparc.util.Utilities;
import fr.thinkparc.util.UtilitiesHTTP;

public class MaintenanceActivity extends ListActivity {

	ArrayList<Maintenance> maintenances;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintenance);

		// Here, load all vehicles in maintenance
		if(UtilitiesHTTP.isConnected((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
			new HttpAsyncTask().execute("http://think-parc.com/webservice/v1/companies/" + Constants.ID_COMPANY +  "/maintenance/vehiclesundermaintenance");
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	
		// Get id_vehicle
		String id_vehicle = maintenances.get(position).getId_vehicle().toString();
		
		// Here, load the id_maintenance
		Utilities.openView(MaintenanceActivity.this, MaintenanceStockActivity.class, id_vehicle);
	}
	
	private void getAllVehiclesInMaintenance(String r) throws JSONException {
		// Spinner Drop down elements
		maintenances = new ArrayList<Maintenance>();
		
		JSONArray jsonarray = new JSONArray(r);
		for (int i = 0; i < jsonarray.length(); i++) {
		    JSONObject jsonobject = jsonarray.getJSONObject(i);
		    Maintenance m = new Maintenance();
		    m.setId_vehicle(jsonobject.getString("id_vehicle"));
		    m.setNr_plate(jsonobject.getString("nr_plate"));
		    maintenances.add(m);
		}
		
		String[] maintenancesArray = new String[maintenances.size()];
		for (int i=0 ; i < maintenancesArray.length ; i++) {
			maintenancesArray[i] = maintenances.get(i).getNr_plate();
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, maintenancesArray) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView text = (TextView) view.findViewById(android.R.id.text1);
				text.setTextColor(getResources().getColor(R.color.white));
				return view;
			}
		};
		setListAdapter(adapter);
		ListView lv = getListView();
		lv.setBackgroundColor(getResources().getColor(R.color.black_transparancy));
	}
	
	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(MaintenanceActivity.this, null, getResources().getString(R.string.loading), true, false);
		}
		
		@Override
		protected String doInBackground(String... urls) {
			return UtilitiesHTTP.GET(urls[0]);
		}
		@Override
		protected void onPostExecute(String result) {
			try {
				getAllVehiclesInMaintenance(result);
				progressDialog.dismiss();
			} catch (JSONException e) {
				progressDialog.dismiss();
				e.printStackTrace();
			}
		}
	}
}
