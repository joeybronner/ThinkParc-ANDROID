package fr.thinkparc.util;

/* ======================================================================== *
 *																			*
 * @filename:		LaunchApp.java											*
 * @description:	Values for Home dashboard.								*
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

import fr.thinkparc.app.R;
import android.app.Application;

public class LaunchApp extends Application {
	
	// Global variables
	public static String stock;
	public static String vehicles;
	public static String maintenance;
	public static String documents;
	public static String webbrowser;

	@Override
	public void onCreate() {
		super.onCreate();
		
		// Retrieve data from resources
		stock = getString(R.string.stock);
		vehicles = getString(R.string.vehicles);
		maintenance = getString(R.string.maintenance);
		documents = getString(R.string.documents);
		webbrowser = getString(R.string.webbrowser);
	}

}
