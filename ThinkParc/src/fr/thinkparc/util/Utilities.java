package fr.thinkparc.util;

import android.content.Context;
import android.content.Intent;

public class Utilities {

	public static boolean isFinishedTimer(int _m, int _s, int _ms) {
		if(_m == 0 && _s == 0 && _ms == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static void openView(Context c, Class<?> cla) {
		Intent intent = new Intent(c, cla);
		c.startActivity(intent);
	}
	
	public static void openView(Context c, Class<?> cla, String id_vehicle) {
		Intent intent = new Intent(c, cla);
		intent.putExtra("id_vehicle",id_vehicle);
		c.startActivity(intent);
	}
	
	public static void openView(Context c, Class<?> cla, String id_vehicle, String reference, int qt) {
		Intent intent = new Intent(c, cla);
		intent.putExtra("id_vehicle", id_vehicle);
		intent.putExtra("reference", reference);
		intent.putExtra("quantity", String.valueOf(qt));
		c.startActivity(intent);
	}
	
	public static String getExtensionFile(String path) {
		int i = path.lastIndexOf('.');
		if (i > 0) {
		    return path.substring(i+1);
		} else {
			return "";
		}
	}

}

