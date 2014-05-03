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

import com.appspot.businessmodelcanvasapp.bmca.model.CanvasItem;
import com.dtorralbo.bmca.CanvasItemsManager.OnCanvasItemAddedListener;

public class NewCanvasItemActivity extends Activity implements
		View.OnClickListener {

	private static final int DIALOG1_KEY = 0;

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
		for (Category category : categories) {
			list.add(category.getName());
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categorySpinner.setAdapter(dataAdapter);

		if (currentCategory > -1 && currentCategory < Category.values().length) {
			categorySpinner.setSelection(currentCategory);
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.addCanvasItemButton:
			addCanvasItem(view);
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

		final Category category = Category.getCategory(categorySpinner
				.getSelectedItemPosition());
		String title = titleEditText.getText().toString();
		String description = descriptionEditText.getText().toString();
		String author = authorEditText.getText().toString();

		CanvasItem item = new CanvasItem();
		item.setCategory(category.getName());
		item.setTitle(title);
		item.setDescription(description);
		item.setAuthor(author);

		showDialog(DIALOG1_KEY);

		CanvasItemsManager manager = CanvasItemsManager.getInstance(this);

		manager.addCanvasItem(item, new OnCanvasItemAddedListener() {

			@Override
			public void onCanvasItemAdded(HashMap<String, List<CanvasItem>> canvasItems) {
				dismissDialog(DIALOG1_KEY);
				
				Intent returnIntent = new Intent();
				returnIntent.putExtra("pickedCategory", category.getIndex());

				setResult(RESULT_OK, returnIntent);
				finish();
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG1_KEY: {
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setTitle("Adding new Activity");
			dialog.setMessage("Please wait while loading...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		}
		}

		return null;
	}
}
