package com.dtorralbo.bmca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.appspot.businessmodelcanvasapp.bmca.model.CanvasItem;
import com.dtorralbo.bmca.CanvasItemsManager.OnCanvasItemDeletedListener;
import com.dtorralbo.bmca.CanvasItemsManager.OnCanvasItemUpdatedListener;

public class UpdateCanvasItemActivity extends Activity implements View.OnClickListener {

	private static final int DIALOG_KEY_UPDATED = 0;
	private static final int DIALOG_KEY_DELETE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_canvas_item);
		
		Intent intent = getIntent();
		Long itemToUpdate = intent.getLongExtra("itemToUpdate", -1L);
		String categoryToUpdate = intent.getStringExtra("categoryToUpdate");
		
		setItemData(itemToUpdate, categoryToUpdate);
		
		findViewById(R.id.setCanvasItemButton).setOnClickListener(this);
		findViewById(R.id.deleteItemButton).setOnClickListener(this);
	}

	private void setItemData(Long itemToUpdate, String categoryToUpdate) {
		CanvasItemsManager manager = CanvasItemsManager.getInstance(this);
		HashMap<String, List<CanvasItem>> canvasItemsList = manager.getCanvasItemsInstance();
		
		List<CanvasItem> canvasItems = canvasItemsList.get(categoryToUpdate);
		
		CanvasItem canvasItemtoupdate = null;
		
		for(CanvasItem item : canvasItems) {
			if(item.getId().equals(itemToUpdate)) {
				canvasItemtoupdate = item;
				break;
			}
		}
		
		TextView idTextView = (TextView) findViewById(R.id.updateItemId);
		idTextView.setText(String.valueOf(canvasItemtoupdate.getId()));
		
		Spinner categorySpinner = (Spinner) findViewById(R.id.updateItemCategory);
		Category[] categories = Category.values();
		List<String> list = new ArrayList<String>();
		for(Category category : categories){
			list.add(category.getName());
		}
				
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categorySpinner.setAdapter(dataAdapter);
		
		categorySpinner.setSelection(Category.getIndexByName(canvasItemtoupdate.getCategory()));		
		
		EditText titleEditText = (EditText) findViewById(R.id.updateItemTitle);
		titleEditText.setText(canvasItemtoupdate.getTitle());
		
		EditText descriptionEditText = (EditText) findViewById(R.id.updateItemDescription);
		descriptionEditText.setText(canvasItemtoupdate.getDescription());
		
		EditText authorEditText = (EditText) findViewById(R.id.updateItemAuthor);
		authorEditText.setText(canvasItemtoupdate.getAuthor());
		
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.setCanvasItemButton:
			setCanvasItem(view);
			break;
		case R.id.deleteItemButton:
			deleteCanvasItem(view);
			break;
		}		
	}

	private void setCanvasItem(View view) {
		TextView idTextView = (TextView) findViewById(R.id.updateItemId); 
		Spinner categorySpinner = (Spinner) findViewById(R.id.updateItemCategory);
		EditText titleEditText = (EditText) findViewById(R.id.updateItemTitle);
		EditText descriptionEditText = (EditText) findViewById(R.id.updateItemDescription);
		EditText authorEditText = (EditText) findViewById(R.id.updateItemAuthor);
		
		Long id = Long.valueOf(idTextView.getText().toString());
		final Category category = Category.getCategory(categorySpinner.getSelectedItemPosition());
		String title = titleEditText.getText().toString();
		String description = descriptionEditText.getText().toString();
		String author = authorEditText.getText().toString();
		
		CanvasItem item = new CanvasItem();
		item.setId(id);
		item.setCategory(category.getName());
		item.setTitle(title);
		item.setDescription(description);
		item.setAuthor(author);
		
		showDialog(DIALOG_KEY_UPDATED);
		
		CanvasItemsManager manager = CanvasItemsManager.getInstance(this);
		
		manager.updateCanvasItem(item, new OnCanvasItemUpdatedListener() {
			
			@Override
			public void onCanvasItemUpdated(HashMap<String, List<CanvasItem>> canvasItems) {
				dismissDialog(DIALOG_KEY_UPDATED);
				
				Intent returnIntent = new Intent();
				returnIntent.putExtra("pickedCategory", category.getIndex());
				
				setResult(RESULT_OK, returnIntent);
				finish();
			}
		});
	}
	
	private void deleteCanvasItem(View view) {
		TextView idTextView = (TextView) findViewById(R.id.updateItemId); 
		
		Long id = Long.valueOf(idTextView.getText().toString());
		
		showDialog(DIALOG_KEY_DELETE);
		
		CanvasItemsManager manager = CanvasItemsManager.getInstance(this);
		
		manager.deleteCanvasItem(id, new OnCanvasItemDeletedListener() {
			
			@Override
			public void onCanvasItemDeleted(HashMap<String, List<CanvasItem>> canvasItems) {
				dismissDialog(DIALOG_KEY_DELETE);
				
				setResult(RESULT_OK);
				finish();
			}
		});
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_KEY_UPDATED: {
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setTitle("Updating Activity");
			dialog.setMessage("Please wait while loading...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		}
		case DIALOG_KEY_DELETE: {
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setTitle("Deleting Activity");
			dialog.setMessage("Please wait while loading...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		}
		}

		return null;
	}
}
