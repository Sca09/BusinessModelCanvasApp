package com.dtorralbo.bmca;

import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.appspot.businessmodelcanvasapp.bmca.model.CanvasItem;
import com.dtorralbo.bmca.CanvasItemsManager.OnCanvasItemsListedListener;
import com.dtorralbo.bmca.adapters.SectionsPagerAdapter;

public class CanvasSwipeViewActivity extends FragmentActivity {

	private static final int REQUEST_CODE_RESOLVE_ERR_NEW_ACTIVITY = 5000;
	private static final int REQUEST_CODE_RESOLVE_ERR_UPDATE_ITEM = 6000;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	public SectionsPagerAdapter mSectionsPagerAdapter;

	private static final int DIALOG1_KEY = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.canvas_swipe_view_activity);

		showDialog(DIALOG1_KEY);
		initData();
		
		// mSectionsPagerAdapter = new
		// SectionsPagerAdapter(getSupportFragmentManager(),
		// getApplicationContext());
		//
		// mViewPager = (ViewPager) findViewById(R.id.pager);
		// mViewPager.setAdapter(mSectionsPagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.canvas_swipe_view_activity, menu);
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG1_KEY: {
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setTitle("BMCA");
			dialog.setMessage("Please wait while loading...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		}
		}

		return null;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_home:
			mViewPager.setCurrentItem(0);
			break;

		case R.id.action_add:
			Intent intent = new Intent(this, NewCanvasItemActivity.class);
			intent.putExtra("currentCategory", mViewPager.getCurrentItem());
			startActivityForResult(intent,
					REQUEST_CODE_RESOLVE_ERR_NEW_ACTIVITY);
			break;
		}

		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE_RESOLVE_ERR_NEW_ACTIVITY:
			if (resultCode == RESULT_OK) {
				mSectionsPagerAdapter.notifyDataSetChanged();

				int pickedCategory = data.getIntExtra("pickedCategory", -1);
				if (pickedCategory > -1) {
					openTab(pickedCategory);
				}
			}
			break;
		case REQUEST_CODE_RESOLVE_ERR_UPDATE_ITEM:
			if (resultCode == RESULT_OK) {
				mSectionsPagerAdapter.notifyDataSetChanged();

				if (data != null) {
					int pickedCategory = data.getIntExtra("pickedCategory", -1);
					if (pickedCategory > -1) {
						openTab(pickedCategory);
					}
				}
			}
			break;
		}
	}

	public void openTab(int pickedCategory) {
		mViewPager.setCurrentItem(pickedCategory);
	}

	private void initData() {

		CanvasItemsManager manager = CanvasItemsManager.getInstance(this);

		manager.listCanvasItems(new OnCanvasItemsListedListener() {

			@Override
			public void onCanvasItemsListed(HashMap<String, List<CanvasItem>> canvasItems) {
				
				mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), getApplicationContext());
				mViewPager = (ViewPager) findViewById(R.id.pager);
				mViewPager.setAdapter(mSectionsPagerAdapter);
				
				dismissDialog(DIALOG1_KEY);
			}
		});
	}
}
