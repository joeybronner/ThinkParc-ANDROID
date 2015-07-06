package fr.thinkparc.util;

import fr.thinkparc.app.R;
import android.app.Application;

public class LaunchApp extends Application {
	
	// Global variables
	public static String stock;
	public static String vehicles;
	public static String maintenance;
	public static String reporting;
	public static String webbrowser;

	@Override
	public void onCreate() {
		super.onCreate();
		
		// Retrieve data from resources
		stock = getString(R.string.stock);
		vehicles = getString(R.string.vehicles);
		maintenance = getString(R.string.maintenance);
		reporting = getString(R.string.reporting);
		webbrowser = getString(R.string.webbrowser);
	}

}
