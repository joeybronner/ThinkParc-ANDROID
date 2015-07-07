package fr.thinkparc.util;

/* ======================================================================== *
 *																			*
 * @filename:		CustomAdapterDocument.java								*
 * @description:	Adapter for a custom list view for listing documents	*
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.thinkparc.app.R;
import fr.thinkparc.obj.Document;
import fr.thinkparc.subparts.documents.DocumentsActivity;

public class CustomAdapterDocument extends BaseAdapter implements OnClickListener {

	private Activity activity;
	private ArrayList<?> data;
	private static LayoutInflater inflater = null;
	public Resources res;
	Document document = null;
	int i=0;

	/* Constructor */
	public CustomAdapterDocument(Activity a, ArrayList<?> d,Resources resLocal) {
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
		public ImageView ivTypeFile;
		public TextView tvFile;
	}

	@SuppressLint({ "InflateParams", "DefaultLocale" }) public View getView(int position, View convertView, ViewGroup parent) {
		View documentrow = convertView;
		ViewHolder holder;

		if(convertView == null) {
			documentrow = inflater.inflate(R.layout.documentrow, null);

			holder = new ViewHolder();
			holder.ivTypeFile = (ImageView) documentrow.findViewById(R.id.ivTypeFile);
			holder.tvFile = (TextView) documentrow.findViewById(R.id.tvFile);

			/* Apply hodel to document row */
			documentrow.setTag(holder);
		}
		else 
			holder = (ViewHolder) documentrow.getTag();

		if(data.size()<=0) {
			holder.tvFile.setText("No files available");
		} else {
			document = null;
			document = (Document) data.get(position);
			String extension = Utilities.getExtensionFile(document.getPath()).toLowerCase();

			int iconDrawable = R.drawable.file_simple;
			if (extension.equals("pdf")) {
				iconDrawable = R.drawable.file_pdf;
			} else if (extension.equals("jpg") || extension.equals("png") || extension.equals("bmp")) {
				iconDrawable = R.drawable.file_picture;
			} else if (extension.equals("ppt") || extension.equals("pptx")) {
				iconDrawable = R.drawable.file_ppt;
			} else if (extension.equals("xls") || extension.equals("xlsx") || extension.equals("xlsm")) {
				iconDrawable = R.drawable.file_excel;
			} else if (extension.equals("doc") || extension.equals("docx") || extension.equals("txt")) {
				iconDrawable = R.drawable.file_word;
			} 

			holder.ivTypeFile.setImageResource(iconDrawable);
			holder.tvFile.setText(document.getPath());

			/* Add onClick listener on each row */
			documentrow.setOnClickListener(new OnItemClickListener(position));
		}
		return documentrow;
	}


	/* Called when item is clicked in ListView */
	private class OnItemClickListener implements OnClickListener{           
		private int mPosition;

		OnItemClickListener(int position){
			mPosition = position;
		}

		@Override
		public void onClick(View arg0) {
			DocumentsActivity sct = (DocumentsActivity) activity;
			sct.onItemClick(mPosition);
		}               
	}

	@Override
	public void onClick(View v) { /* Nothing */ }
}