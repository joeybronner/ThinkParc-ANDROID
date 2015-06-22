package fr.thinkparc.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import fr.thinkparc.subparts.VehiclesActivity;
import fr.thinkparc.util.LaunchApp;
import fr.thinkparc.util.Utilities;


public class HomeActivity extends Activity implements OnItemClickListener {

	private String _errormsg;
	GridView gridview;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		gridview = (GridView) HomeActivity.this.findViewById(R.id.dashboard_grid);
		gridview.setAdapter(new ImageAdapter(this));
		gridview.setOnItemClickListener(this);
		
		// TypeFace Font Awesome (icons)
		Typeface fa = Typeface.createFromAsset(getAssets(), "FontAwesome/fontawesome-webfont.ttf");
		
		// Hide Action Bar
		getActionBar().hide();
		
		// Hack to disable GridView scrolling
		gridview.setOnTouchListener(new OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility") @Override
			public boolean onTouch(View v, MotionEvent event) {
				return event.getAction() == MotionEvent.ACTION_MOVE;
			}
		});
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		// Here, redirect to the good view
		String icon_selected = ICONS[position].map;
		if (icon_selected.equals("stock")) {
			// Here, open activity
		}
		else if (icon_selected.equals("vehicles")) {
			Utilities.openView(this, VehiclesActivity.class);
		}
		else if (icon_selected.equals("maintenance")) {
			// Here, open activity
		}
		else if (icon_selected.equals("reporting")) {
			// Here, open activity
		}
		else if (icon_selected.equals("settings")) {
			// Here, open activity
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
		new LauncherIcon(R.drawable.fasignal, LaunchApp.reporting, "reporting"),
		new LauncherIcon(R.drawable.fauser, LaunchApp.settings, "settings"),
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
