package fr.thinkparc.subparts.maintenance;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import fr.thinkparc.app.HomeActivity;
import fr.thinkparc.app.R;
import fr.thinkparc.obj.StockElements;
import fr.thinkparc.util.Constants;
import fr.thinkparc.util.CustomAdapter;
import fr.thinkparc.util.Utilities;
import fr.thinkparc.util.UtilitiesHTTP;

public class MaintenanceSiteActivity extends Activity {

	ListView list;
	CustomAdapter adapter;
	public ArrayList<StockElements> stockElements = new ArrayList<StockElements>();
	String id_vehicle;
	String reference;
	String quantity;
	String id_maintenance;
	String id_stock;
	int choice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintenance_site);

		// Load values
		Intent myIntent = getIntent();
		reference = myIntent.getStringExtra("reference");
		quantity = myIntent.getStringExtra("quantity");
		id_vehicle = myIntent.getStringExtra("id_vehicle");
		// Here, load all available stock for a reference
		if(UtilitiesHTTP.isConnected((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
			choice = 1;
			new HttpAsyncTask().execute("http://think-parc.com/webservice/v1/companies/" + Constants.ID_COMPANY + "/maintenance/stock/" + reference + "/quantity/" + quantity);
		}
	}

	/* Get Stock available parts */
	private void getStockAvailable(String r) throws JSONException {
		JSONArray jsonarray = new JSONArray(r);
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobject = jsonarray.getJSONObject(i);
			final StockElements stock = new StockElements();
			stock.setIdStock(jsonobject.getString("id_stock"));
			stock.setSiteName(jsonobject.getString("name"));
			stock.setQuantity(jsonobject.getString("quanty"));
			stock.setRack(jsonobject.getString("rack"));
			stock.setPosition(jsonobject.getString("position"));
			stock.setBay(jsonobject.getString("bay"));
			// Only add location if stock is available (> 0)
			if (Integer.parseInt(jsonobject.getString("quanty")) > 0 ) {
				stockElements.add(stock);
			}
		}

		list = (ListView) findViewById(R.id.list);

		/* Create custom adapter for stocks values */
		adapter=new CustomAdapter(this, stockElements,getResources());
		list.setAdapter(adapter);
	}

	private void getMaintenanceDetail(String r) throws JSONException {
		JSONArray jsonarray = new JSONArray(r);
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobject = jsonarray.getJSONObject(i);
			id_maintenance = jsonobject.getString("id_maintenance");
		}

		if (Integer.parseInt(id_maintenance) > 0) {
			// Add part(s) to maintenance
			if(UtilitiesHTTP.isConnected((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
				choice = 3;
				new HttpAsyncTask().execute("http://www.think-parc.com/webservice/v1/companies/" + Constants.ID_COMPANY + "/maintenance/" + id_maintenance + "/stock/" + id_stock + "/quantity/" + quantity);
			}
		}
	}

	/* Update stock available values */
	private void updateStockValue() {
		if(UtilitiesHTTP.isConnected((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
			choice = 4;
			new HttpAsyncTask().execute("http://www.think-parc.com/webservice/v1/companies/" + Constants.ID_COMPANY + "/stock/" +  id_stock + "/quantity/" + quantity);
		}
	}

	@SuppressLint("InflateParams")
	private void showSuccess() {
		// Show success message and redirect
		LayoutInflater inflater = getLayoutInflater();
		View dialoglayout = inflater.inflate(R.layout.success, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setView(dialoglayout);
		builder.setCancelable(false);
		//builder.show();
		
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
		            Utilities.openView(MaintenanceSiteActivity.this, HomeActivity.class);
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

	public void onItemClick(int position) {
		id_stock = stockElements.get(position).getIdStock();

		// Retrieve id_maintenance
		if(UtilitiesHTTP.isConnected((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
			choice = 2;
			new HttpAsyncTask().execute("http://think-parc.com/webservice/v1/companies/" + Constants.ID_COMPANY + "/maintenance/vehicle/" + id_vehicle);
		}
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			switch (choice) {
			case 1: 
				// GET
				return UtilitiesHTTP.GET(urls[0]);
			case 2: 
				// GET
				return UtilitiesHTTP.GET(urls[0]);
			case 3:
				// POST
				return UtilitiesHTTP.POST(urls[0]);
			case 4:
				// PUT
				return UtilitiesHTTP.PUT(urls[0]);
			default:
				return null;
			}
		}
		@Override
		protected void onPostExecute(String result) {
			try {
				switch (choice) {
				case 1: 
					getStockAvailable(result);
					break;
				case 2: 
					getMaintenanceDetail(result);
					break;
				case 3:
					updateStockValue();
					break;
				case 4:
					showSuccess();
					break;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
