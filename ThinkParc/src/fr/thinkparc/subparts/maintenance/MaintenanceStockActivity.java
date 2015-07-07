package fr.thinkparc.subparts.maintenance;

/* ======================================================================== *
 *																			*
 * @filename:		MaintenanceStockActivity.java							*
 * @description:	This activity retrieves all references in stock.		*
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
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import fr.thinkparc.app.R;
import fr.thinkparc.obj.Part;
import fr.thinkparc.util.Constants;
import fr.thinkparc.util.Utilities;
import fr.thinkparc.util.UtilitiesHTTP;

public class MaintenanceStockActivity extends ListActivity {

	String id_vehicle;
	List<Part> parts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintenance_stock);
		
		// Load values
		Intent myIntent = getIntent();
		id_vehicle = myIntent.getStringExtra("id_vehicle");
		
		// Here, load all parts (reference)
		if(UtilitiesHTTP.isConnected((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
			new HttpAsyncTask().execute("http://think-parc.com/webservice/v1/companies/" + Constants.ID_COMPANY + "/reporting/parts/all");
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
		
		String[] partsArray = new String[parts.size()];
		for (int i=0 ; i < partsArray.length ; i++) {
			partsArray[i] = parts.get(i).getReference();
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, partsArray) {
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
	
	@SuppressLint("InflateParams") @Override
	protected void onListItemClick(ListView l, View v, final int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		// Show alert for quantity
		LayoutInflater li = LayoutInflater.from(MaintenanceStockActivity.this);
		final View promptView = li.inflate(R.layout.prompt, null);
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MaintenanceStockActivity.this);
		alertDialogBuilder.setView(promptView);
		
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton(getResources().getString(R.string.ok), 
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							EditText etQuantity = (EditText) promptView.findViewById(R.id.etQuantity);
							String reference = parts.get(position).getReference();
							int quantity = Integer.parseInt(etQuantity.getText().toString());
							if (quantity > 0) {
								Utilities.openView(MaintenanceStockActivity.this, MaintenanceSiteActivity.class, id_vehicle, reference, quantity);
							} else {
								
							}
						}
					})
			.setNegativeButton(getResources().getString(R.string.cancel), 
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();	
						}
					});
		
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(MaintenanceStockActivity.this, null, getResources().getString(R.string.loading), true, false);
		}
		
		@Override
		protected String doInBackground(String... urls) {
			return UtilitiesHTTP.GET(urls[0]);
		}
		@Override
		protected void onPostExecute(String result) {
			try {
				getAllReferences(result);
				progressDialog.dismiss();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
