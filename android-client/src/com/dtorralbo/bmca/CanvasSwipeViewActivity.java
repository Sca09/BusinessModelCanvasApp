package com.dtorralbo.bmca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.dtorralbo.bmca.adapters.SectionsPagerAdapter;

public class CanvasSwipeViewActivity extends FragmentActivity {

	private static final int REQUEST_CODE_RESOLVE_ERR_NEW_ACTIVITY = 5000;
	
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	public SectionsPagerAdapter mSectionsPagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.canvas_swipe_view_activity);
		
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), getApplicationContext());
		
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.canvas_swipe_view_activity, menu);
		return true;
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
			startActivityForResult(intent, REQUEST_CODE_RESOLVE_ERR_NEW_ACTIVITY);
			break;
		}

		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		switch (requestCode) {
//		
//		}
	}
	
	public void openTab(String timeZoneSelected) {
//		int tzSelectedPosition = mSectionsPagerAdapter.getListTimeZones().lastIndexOf(timeZoneSelected);
//		mViewPager.setCurrentItem(tzSelectedPosition + 1);
	}
}
