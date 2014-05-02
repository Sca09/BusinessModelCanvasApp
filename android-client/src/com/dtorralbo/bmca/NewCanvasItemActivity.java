package com.dtorralbo.bmca;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.appspot.businessmodelcanvasapp.bmca.model.CanvasItem;

public class NewCanvasItemActivity extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_canvas_item);
		
		Intent intent = getIntent();
		int currentCategory = intent.getIntExtra("currentCategory", -1);
		setCategorySpinner(currentCategory);
		
		findViewById(R.id.addCanvasItemButton).setOnClickListener(this);
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
		case R.id.addCanvasItemButton:
			addCanvasItem(view);
			finish();
			break;
		case R.id.cancelButton:
			finish();
			break;
		}
	}

	private void addCanvasItem(View view) {
		
		Spinner categorySpinner = (Spinner) findViewById(R.id.newItemCategory);
		EditText titleEditText = (EditText) findViewById(R.id.newItemTitle);
		EditText descriptionEditText = (EditText) findViewById(R.id.newItemDescription);
		EditText authorEditText = (EditText) findViewById(R.id.newItemAuthor);
		
		Category category = Category.getCategory(categorySpinner.getSelectedItemPosition());
		String title = titleEditText.getText().toString();
		String description = descriptionEditText.getText().toString();
		String author = authorEditText.getText().toString();
		
		CanvasItem item = new CanvasItem();
		item.setCategory(category.getName());
		item.setTitle(title);
		item.setDescription(description);
		item.setAuthor(author);
		
		CanvasItemsManager.addCanvasItem(item);
		
		Intent returnIntent = new Intent();
		returnIntent.putExtra("pickedCategory", category.getIndex());
		
		setResult(RESULT_OK, returnIntent);
	}	
}
