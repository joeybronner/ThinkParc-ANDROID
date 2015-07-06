package fr.thinkparc.subparts;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import fr.thinkparc.app.R;
import fr.thinkparc.obj.StockElements;
import fr.thinkparc.util.Constants;
import fr.thinkparc.util.CustomAdapter;
import fr.thinkparc.util.UtilitiesHTTP;

public class MaintenanceSiteActivity extends Activity {

	ListView list;
	CustomAdapter adapter;
	public ArrayList<StockElements> stockElements = new ArrayList<StockElements>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintenance_site);
		
		// Load values
		Intent myIntent = getIntent();
		String reference = myIntent.getStringExtra("reference");
		String quantity = myIntent.getStringExtra("quantity");
		// Here, load all vehicles in maintenance
		if(UtilitiesHTTP.isConnected((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
			new HttpAsyncTask().execute("http://think-parc.com/webservice/v1/companies/" + Constants.ID_COMPANY + "/maintenance/stock/" + reference + "/quantity/" + quantity);
		}
	}
	
	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			return UtilitiesHTTP.GET(urls[0]);
		}
		@Override
		protected void onPostExecute(String result) {
			try {
				getStockAvailable(result);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	/* Get Stock available parts */
	private void getStockAvailable(String r) throws JSONException {
		
		JSONArray jsonarray = new JSONArray(r);
		for (int i = 0; i < jsonarray.length(); i++) {
		    JSONObject jsonobject = jsonarray.getJSONObject(i);
			final StockElements stock = new StockElements();
			stock.setSiteName(jsonobject.getString("name"));
			stock.setQuantity(jsonobject.getString("quanty"));
			stock.setRack(jsonobject.getString("rack"));
			stock.setPosition(jsonobject.getString("position"));
			stock.setBay(jsonobject.getString("bay"));
			stockElements.add(stock);
		}
		
		list = (ListView) findViewById(R.id.list);

		/* Create custom adapter for stocks values */
		adapter=new CustomAdapter(this, stockElements,getResources());
		list.setAdapter(adapter);
	}

	public void onItemClick(int mPosition) {
		StockElements tempValues = (StockElements) stockElements.get(mPosition);
		Toast.makeText(this, ""+tempValues.getSiteName(),Toast.LENGTH_LONG).show();
	}
}
