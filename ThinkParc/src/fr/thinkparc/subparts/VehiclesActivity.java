package fr.thinkparc.subparts;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import fr.thinkparc.app.R;
import fr.thinkparc.util.UtilitiesHTTP;

public class VehiclesActivity extends Activity {

	EditText etResponse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vehicles);

		// References for UI elements
		etResponse = (EditText) findViewById(R.id.etResponse);

		if(UtilitiesHTTP.isConnected((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
			// call AsynTask to perform network operation on separate thread
			new HttpAsyncTask().execute("http://think-parc.com/webservice/v1/about/developers");
		}

	}
	
	private void hello(String r) {
		etResponse.setText(r);
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			return UtilitiesHTTP.GET(urls[0]);
		}
		@Override
		protected void onPostExecute(String result) {
			hello(result);
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
