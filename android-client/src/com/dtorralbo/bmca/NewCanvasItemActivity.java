package com.dtorralbo.bmca;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NewCanvasItemActivity extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_canvas_item);
		
		Intent intent = getIntent();
		int currentCategory = intent.getIntExtra("currentCategory", -1);
		setCategorySpinner(currentCategory);
		
		findViewById(R.id.cancelButton).setOnClickListener(this);
	}

	private void setCategorySpinner(int currentCategory) {
		Spinner categorySpinner = (Spinner) findViewById(R.id.newItemCategory);
		
		Category[] categories = Category.values();
		List<String> list = new ArrayList<String>();
		for(Category category : categories){
			list.add(category.getName());
		}
				
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categorySpinner.setAdapter(dataAdapter);
		
		if(currentCategory > -1 && currentCategory < Category.values().length) {
			categorySpinner.setSelection(currentCategory);
		}
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.cancelButton:
			finish();
			break;
		}
	}	
}
