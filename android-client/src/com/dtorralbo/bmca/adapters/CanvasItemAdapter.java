package com.dtorralbo.bmca.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appspot.businessmodelcanvasapp.bmca.model.CanvasItem;
import com.dtorralbo.bmca.R;
import com.dtorralbo.bmca.UpdateCanvasItemActivity;

public class CanvasItemAdapter extends ArrayAdapter<CanvasItem> {

	Context context; 
    int layoutResourceId;
    List<CanvasItem> data = null;

    private static final int REQUEST_CODE_RESOLVE_ERR_UPDATE_ITEM = 6000;
    
	public CanvasItemAdapter(Context context, int resource, List<CanvasItem> data) {
		super(context, resource, data);
		this.context = context;
		this.layoutResourceId = resource;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		CanvasItemHolder holder = null;
		
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);
		
		holder = new CanvasItemHolder();
		holder.itemId = (TextView) row.findViewById(R.id.itemId);
		holder.itemTitle = (TextView) row.findViewById(R.id.itemTitle);
		holder.itemDescription = (TextView) row.findViewById(R.id.itemDescription);
		holder.itemCategory = (TextView) row.findViewById(R.id.itemCategory);
		holder.itemAuthor = (TextView) row.findViewById(R.id.itemAuthor);
		
		final CanvasItem item = data.get(position);
		if(item.getId() != null) {
			holder.itemId.setText(item.getId().toString());
		}
		holder.itemTitle.setText(item.getTitle());
		holder.itemDescription.setText(item.getDescription());
		holder.itemCategory.setText(item.getCategory());
		holder.itemAuthor.setText(item.getAuthor());
		
		row.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(context, UpdateCanvasItemActivity.class);
				intent.putExtra("itemToUpdate", item.getId());
				intent.putExtra("categoryToUpdate", item.getCategory());
				
				((Activity)context).startActivityForResult(intent, REQUEST_CODE_RESOLVE_ERR_UPDATE_ITEM);
			}
		});
		
		return row;
	}
	
	
	static class CanvasItemHolder {
		TextView itemId;
		TextView itemTitle;
		TextView itemDescription;
		TextView itemCategory;
		TextView itemAuthor;
	}
}
