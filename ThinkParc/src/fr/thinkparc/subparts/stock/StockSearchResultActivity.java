package fr.thinkparc.subparts.stock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import fr.thinkparc.app.R;
import fr.thinkparc.util.UtilitiesHTTP;

public class StockSearchResultActivity extends Activity {

	String id_part;
	String id_site;
	int quantity;
	Button btAvailable;
	TextView tvQuantity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_search_result);

		// Load values
		Intent myIntent = getIntent();
		id_part = myIntent.getStringExtra("id_part");
		id_site = myIntent.getStringExtra("id_site");
		
		// Load UI elements
		btAvailable = (Button) findViewById(R.id.btAvailable);
		tvQuantity = (TextView) findViewById(R.id.tvQuantity);

		if(UtilitiesHTTP.isConnected((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
			new HttpAsyncTask().execute("http://think-parc.com/webservice/v1/companies/stocks/available/site/" + id_site + "/reference/" + id_part);
		}
	}

	private void displayQuantity(String r) throws JSONException {
		// Retrieve quantity in stock
		JSONArray jsonarray = new JSONArray(r);
		JSONObject jsonobject = jsonarray.getJSONObject(0);
		quantity = Integer.parseInt(jsonobject.getString("quantitytotal"));
		
		if (quantity > 0) {
			btAvailable.setBackgroundColor(getResources().getColor(R.color.green_light));
			btAvailable.setText(getResources().getString(R.string.stock_available));
			btAvailable.setCompoundDrawables(getResources().getDrawable(R.drawable.fa_check), null, null, null);
			tvQuantity.setTextColor(getResources().getColor(R.color.green_light));
		} else {
			btAvailable.setBackgroundColor(getResources().getColor(R.color.red));
			btAvailable.setText(getResources().getString(R.string.stock_notavailable));
			btAvailable.setCompoundDrawables(getResources().getDrawable(R.drawable.fa_error), null, null, null);
			tvQuantity.setTextColor(getResources().getColor(R.color.red));
		}
		
		// Display the number of values
		tvQuantity.setText(Integer.toString(quantity));

	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(StockSearchResultActivity.this, null, getResources().getString(R.string.loading), true, false);
		}

		@Override
		protected String doInBackground(String... urls) {
			return UtilitiesHTTP.GET(urls[0]);
		}
		@Override
		protected void onPostExecute(String result) {
			try {
				displayQuantity(result);
				progressDialog.dismiss();
			} catch (JSONException e) {
				progressDialog.dismiss();
				e.printStackTrace();
			}
		}
	}
}
