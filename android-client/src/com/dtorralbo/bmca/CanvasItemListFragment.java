package com.dtorralbo.bmca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.appspot.businessmodelcanvasapp.bmca.model.CanvasItem;
import com.dtorralbo.bmca.CanvasItemsManager.OnCanvasItemsListedListener;
import com.dtorralbo.bmca.adapters.CanvasItemAdapter;

public class CanvasItemListFragment extends Fragment {

	public static final String ARG_SECTION_NAME = "section_name";
	
	LinearLayout rootView;
	private Activity activity;
	private ListView itemsListView;
	private String category;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = (LinearLayout) inflater.inflate(R.layout.item_list, container, false);

		if(getArguments() != null) {
			String categorySent = getArguments().getString(ARG_SECTION_NAME);
		
			if(categorySent != null) {
				category = categorySent;
			}
		}
		
		getListCanvasItem();
		
		return rootView;
	}

	private void getListCanvasItem() {
		CanvasItemsManager manager = CanvasItemsManager.getInstance(activity);
		
		manager.listCanvasItems(new OnCanvasItemsListedListener() {
			
			@Override
			public void onCanvasItemsListed(HashMap<String, List<CanvasItem>> canvasItems) {
				List<CanvasItem> items = canvasItems.get(category);
				
				if(items == null) {
					items = new ArrayList<CanvasItem>();
				}	
				
				CanvasItemAdapter adapter = new CanvasItemAdapter(activity, R.layout.canvas_item, items);
				
				itemsListView = (ListView) rootView.findViewById(R.id.item_list_view);
				itemsListView.setAdapter(adapter);
			}
		});
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}
}