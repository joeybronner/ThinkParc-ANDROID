package fr.thinkparc.app;

/* ======================================================================== *
 *																			*
 * @filename:		AuthenticationActivity.java								*
 * @description:	This activity asserts a connection between this app		*
 * 					and the database to retrieve all data.					*
 * 					User informations are stored as constants				*
 *																			*
 * @author(s): 		Joey BRONNER											*
 * @contact(s):		joeybronner@gmail.com									*
 * @creation: 		01/07/2015												*
 * @remarks:		-														*
 * 																			*
 * @rights:			Think-Parc Software �, 2015.							*
 *																			*
 *																			*
 * Date       | Developer      | Changes description						* 
 * ------------------------------------------------------------------------ *
 * 01/07/2015 | J.BRONNER      | Creation									*
 * ------------------------------------------------------------------------ *
 * JJ/MM/AAAA | ...			   | ...			 							*
 * =========================================================================*/

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import fr.thinkparc.util.Constants;
import fr.thinkparc.util.UtilitiesHTTP;

public class AuthenticationActivity extends Activity {

	EditText username;
	EditText password;
	Button connect;
	String URL_CONNECT_ANDROID = "http://www.think-parc.com/db/connect_android.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authentication);

		// Hide ActionBar
		ActionBar actionBar = getActionBar();
		actionBar.hide();

		// Retrieve UI elements
		username = (EditText) findViewById(R.id.etLogin);
		password = (EditText) findViewById(R.id.etPassword);

		connect = (Button) findViewById(R.id.btConnect);
		connect.setBackgroundColor(getResources().getColor(R.color.light_blue));
	}

	public void login(View view) {
		if(UtilitiesHTTP.isConnected((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
			new HttpAsyncTask().execute(URL_CONNECT_ANDROID, username.getText().toString(), password.getText().toString());
		} else {
			Toast.makeText(getBaseContext(), getResources().getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
		}
	}

	class HttpAsyncTask extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(AuthenticationActivity.this, null, getResources().getString(R.string.loading), true, false);
		}
		
		@Override
		protected String doInBackground(String... urls) {
			return UtilitiesHTTP.POSTLogin(urls[0], urls[1], urls[2]);
		}
		@Override
		protected void onPostExecute(String result) {
			try {
				JSONObject r = new JSONObject(result);
				String access = r.getString("access");
				String token = r.getString("token");
				if (access.equals("success")) {
					/* Store user's values as Constants */
					Constants.ID_COMPANY = r.getString("id_company");
					Constants.ID_ROLE = r.getString("id_role");
					Constants.ID_USER = r.getString("id_user");
					Constants.FIRSTNAME = r.getString("firstname");
					Constants.LASTNAME = r.getString("lastname");
					Constants.LOGIN = r.getString("login");
					Constants.EMAIL = r.getString("email");
					Constants.IMAGE = r.getString("image");
					Constants.TOKEN = r.getString("token");
					
					Intent intent = new Intent(AuthenticationActivity.this, HomeActivity.class);
					startActivity(intent);
					progressDialog.dismiss();
				} else {
					progressDialog.dismiss();
					Toast.makeText(getBaseContext(), token + getResources().getString(R.string.error_incorrect), Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				Toast.makeText(getBaseContext(), getResources().getString(R.string.error_retrieving), Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}
	}
}
