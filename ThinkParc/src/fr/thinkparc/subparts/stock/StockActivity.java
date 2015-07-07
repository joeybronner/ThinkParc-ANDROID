package fr.thinkparc.subparts.stock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import fr.thinkparc.app.R;
import fr.thinkparc.util.Utilities;

public class StockActivity extends Activity {

	Button btSearchReference;
	Button btAddStock;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock);
		
		// Button Add in stock
		btAddStock = (Button) findViewById(R.id.btAddStock);
		btAddStock.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Utilities.openView(StockActivity.this, StockAddActivity.class);
			}
		});
		
		// Button Search a reference
		btSearchReference = (Button) findViewById(R.id.btSearchReference);
		btSearchReference.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Utilities.openView(StockActivity.this, StockSearchActivity.class);
			}
		});
	}
}
