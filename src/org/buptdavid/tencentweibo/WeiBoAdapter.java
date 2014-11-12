/**
 * 
 */
package org.buptdavid.tencentweibo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.buptdavid.tencentweibo.R;
import org.buptdavid.tencentweibo.model.WeiBoData;
import org.buptdavid.tencentweibo.model.WeiBoList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author weijielu
 *
 */
public class WeiBoAdapter extends BaseAdapter {
	private WeiBoList weiBoList;
	private List<WeiBoData> info = null;
	
	private Map<Integer,View> rowViews = new HashMap<Integer,View>();
	private Context context = null;
	
	public WeiBoAdapter(WeiBoList weiBoList,Context context){
		this.weiBoList = weiBoList;
		info = weiBoList.getData().getInfo();
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return info.size();
	}

	@Override
	public Object getItem(int position) {
		return info.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = rowViews.get(position);
		if(rowView == null){
			LayoutInflater layoutInflater = LayoutInflater.from(context);

			rowView = layoutInflater.inflate(R.layout.item, null);

			TextView nameView = (TextView)rowView.findViewById(R.id.nameId);
			TextView textView = (TextView)rowView.findViewById(R.id.textId);

			WeiBoData weiBoData = (WeiBoData)getItem(position);
			nameView.setText(weiBoData.getName());
			textView.setText(weiBoData.getText());
			rowViews.put(position, rowView);
		}
		return rowView;
	}

}
