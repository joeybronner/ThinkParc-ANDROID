package fr.thinkparc.subparts.documents;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import fr.thinkparc.app.R;
import fr.thinkparc.obj.Document;
import fr.thinkparc.util.Constants;
import fr.thinkparc.util.CustomAdapterDocument;
import fr.thinkparc.util.UtilitiesHTTP;

public class DocumentsActivity extends Activity {

	ListView list;
	CustomAdapterDocument adapter;
	public ArrayList<Document> documentElements = new ArrayList<Document>();
	int choice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_documents);

		// Load all documents (global for company)
		if(UtilitiesHTTP.isConnected((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
			choice = 1;
			new HttpAsyncTask().execute("http://think-parc.com/webservice/v1/companies/" + Constants.ID_COMPANY + "/files/global");
		}
	}

	private void showDocuments(String r) throws JSONException {
		JSONArray jsonarray = new JSONArray(r);
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobject = jsonarray.getJSONObject(i);
			final Document doc = new Document();
			doc.setId_file(jsonobject.getString("id_file"));
			doc.setPath(jsonobject.getString("path"));
			doc.setId_element(jsonobject.getString("id_element"));
			doc.setFiletype(jsonobject.getString("id_filestype"));
			doc.setDate_upload(jsonobject.getString("date_upload"));
			documentElements.add(doc);
		}

		list = (ListView) findViewById(R.id.list);

		/* Create custom adapter for stocks values */
		adapter = new CustomAdapterDocument(this, documentElements, getResources());
		list.setAdapter(adapter);
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(DocumentsActivity.this, null, getResources().getString(R.string.loading), true, false);
		}

		@Override
		protected String doInBackground(String... urls) {
			switch (choice) {
			case 1 :
				return UtilitiesHTTP.GET(urls[0]);
			case 2 :
				downloadFile(urls[0], urls[1]);
				break;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				switch (choice) {
				case 1 : 
					showDocuments(result);
					break;
				}
				progressDialog.dismiss();
			} catch (JSONException e) {
				progressDialog.dismiss();
				e.printStackTrace();
			}
		}
	}

	public void onItemClick(int position) {
		String u = "http://www.think-parc.com/files/global_company/" + documentElements.get(position).getPath();
		if(UtilitiesHTTP.isConnected((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
			choice = 2;
			new HttpAsyncTask().execute(documentElements.get(position).getPath(), u);
		}
	}

	public boolean downloadFile(final String path, final String u) {
		Intent httpIntent = new Intent(Intent.ACTION_VIEW);
		httpIntent.setData(Uri.parse(u));
		startActivity(httpIntent); 
		return true;
	}

}
