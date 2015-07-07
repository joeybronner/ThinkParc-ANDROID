package fr.thinkparc.app;

/* ======================================================================== *
 *																			*
 * @filename:		HomeActivity.java										*
 * @description:	This activity shows a dashboard of all modules 			*
 * 					available in this app (stock, vehicles, ...)			*
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import fr.thinkparc.subparts.documents.DocumentsActivity;
import fr.thinkparc.subparts.maintenance.MaintenanceActivity;
import fr.thinkparc.subparts.stock.StockActivity;
import fr.thinkparc.subparts.vehicles.VehiclesActivity;
import fr.thinkparc.util.LaunchApp;
import fr.thinkparc.util.Utilities;


public class HomeActivity extends Activity implements OnItemClickListener {

	GridView gridview;
	ImageButton ibLogoff;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		gridview = (GridView) HomeActivity.this.findViewById(R.id.dashboard_grid);
		gridview.setAdapter(new ImageAdapter(this));
		gridview.setOnItemClickListener(this);
		
		// Hide Action Bar
		getActionBar().hide();
		
		// Hack to disable GridView scrolling
		gridview.setOnTouchListener(new OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility") @Override
			public boolean onTouch(View v, MotionEvent event) {
				return event.getAction() == MotionEvent.ACTION_MOVE;
			}
		});
		
		// Listener on Logoff button
		ibLogoff = (ImageButton) findViewById(R.id.imLogoff);
		ibLogoff.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Utilities.openView(HomeActivity.this, AuthenticationActivity.class);
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		Toast.makeText(this, getResources().getString(R.string.backpress_logoff), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		// Here, redirect to the good view
		String icon_selected = ICONS[position].map;
		if (icon_selected.equals("stock")) {
			Utilities.openView(this, StockActivity.class);
		}
		else if (icon_selected.equals("vehicles")) {
			Utilities.openView(this, VehiclesActivity.class);
		}
		else if (icon_selected.equals("maintenance")) {
			Utilities.openView(this, MaintenanceActivity.class);
		}
		else if (icon_selected.equals("documents")) {
			Utilities.openView(this, DocumentsActivity.class);
		}
		else if (icon_selected.equals("webbrowser")) {
			// Redirect to browser
			Intent httpIntent = new Intent(Intent.ACTION_VIEW);
			httpIntent.setData(Uri.parse("http://www.think-parc.com"));
			startActivity(httpIntent);   
		}
		else {
			Toast toast = Toast.makeText(this,"Error msg", Toast.LENGTH_LONG);
			toast.show();
		}
	}
	
	// Custom Grid View ---
	
	final static LauncherIcon[] ICONS = {
		new LauncherIcon(R.drawable.facubes, LaunchApp.stock, "stock"),
		new LauncherIcon(R.drawable.facar, LaunchApp.vehicles, "vehicles"),
		new LauncherIcon(R.drawable.fawrench, LaunchApp.maintenance, "maintenance"),
		new LauncherIcon(R.drawable.fafile, LaunchApp.documents, "documents"),
		new LauncherIcon(R.drawable.falaptop, LaunchApp.webbrowser, "webbrowser"),
	};

	static class LauncherIcon {
		final String text;
		final int imgId;
		final String map;

		public LauncherIcon(int imgId, String text, String map) {
			super();
			this.imgId = imgId;
			this.text = text;
			this.map = map;
		}
	}

	static class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		@Override
		public int getCount() {
			return ICONS.length;
		}

		@Override
		public LauncherIcon getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		static class ViewHolder {
			public ImageView icon;
			public TextView text;
		}

		// Create a new ImageView for each item referenced by the Adapter
		@SuppressLint("InflateParams") @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			ViewHolder holder;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) mContext.getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);

				v = vi.inflate(R.layout.dashboard_icon, null);
				holder = new ViewHolder();
				holder.text = (TextView) v.findViewById(R.id.dashboard_icon_text);
				holder.icon = (ImageView) v.findViewById(R.id.dashboard_icon_img);
				v.setTag(holder);
			}
			else {
				holder = (ViewHolder) v.getTag();
			}

			holder.icon.setImageResource(ICONS[position].imgId);
			holder.text.setText(ICONS[position].text);
			return v;
		}
	}
}
