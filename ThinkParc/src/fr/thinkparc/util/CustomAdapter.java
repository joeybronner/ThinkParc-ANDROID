package fr.thinkparc.util;

/* ======================================================================== *
 *																			*
 * @filename:		CustomAdapter.java										*
 * @description:	Adapter for a custom list view for listing parts by		*
 * 					site 													*
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

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import fr.thinkparc.app.R;
import fr.thinkparc.obj.StockElements;
import fr.thinkparc.subparts.maintenance.MaintenanceSiteActivity;

public class CustomAdapter extends BaseAdapter implements OnClickListener {

	private Activity activity;
	private ArrayList<?> data;
	private static LayoutInflater inflater = null;
	public Resources res;
	StockElements stockElement = null;
	int i=0;

	/* Constructor */
	public CustomAdapter(Activity a, ArrayList<?> d,Resources resLocal) {
		activity = a;
		data = d;
		res = resLocal;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/* Get Data size (number of elements) */
	public int getCount() {
		if(data.size()<=0)
			return 1;
		return data.size();
	}

	/* Get an item */
	public Object getItem(int position) {
		return position;
	}

	/* Get an item ID */
	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public TextView tvSiteName;
		public TextView tvLocalisation;
		public TextView tvQuantity;
	}

	@SuppressLint("InflateParams") public View getView(int position, View convertView, ViewGroup parent) {
		View stockrow = convertView;
		ViewHolder holder;

		if(convertView == null) {
			stockrow = inflater.inflate(R.layout.stockrow, null);

			holder = new ViewHolder();
			holder.tvSiteName = (TextView) stockrow.findViewById(R.id.tvSiteName);
			holder.tvLocalisation = (TextView) stockrow.findViewById(R.id.tvLocalisation);
			holder.tvQuantity = (TextView) stockrow.findViewById(R.id.tvQuantity);

			/* Apply hodel to stock row */
			stockrow.setTag(holder);
		}
		else 
			holder = (ViewHolder)stockrow.getTag();

		if(data.size()<=0) {
			holder.tvQuantity.setText("0");
			holder.tvQuantity.setTextColor(Color.parseColor("#A82A2A"));
			holder.tvSiteName.setText("No parts available");
			holder.tvLocalisation.setText("");
		} else {
			stockElement = null;
			stockElement = (StockElements) data.get(position);

			holder.tvSiteName.setText(stockElement.getSiteName());
			holder.tvLocalisation.setText(	"R:" + stockElement.getRack() + " | " +
											"B:" + stockElement.getBay() + " | " +
											"P:" + stockElement.getPosition());
			holder.tvQuantity.setText(stockElement.getQuantity());

			/* Add onClick listener on each row */
			stockrow.setOnClickListener(new OnItemClickListener(position));
		}
		return stockrow;
	}

	/* Called when item is clicked in ListView */
	private class OnItemClickListener  implements OnClickListener{           
		private int mPosition;

		OnItemClickListener(int position){
			mPosition = position;
		}

		@Override
		public void onClick(View arg0) {
			MaintenanceSiteActivity sct = (MaintenanceSiteActivity)activity;
			sct.onItemClick(mPosition);
		}               
	}

	@Override
	public void onClick(View v) { /* Nothing */ }
}