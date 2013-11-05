package pl.kodujdlapolski.cichy_bohater.data;

import java.util.List;

import pl.kodujdlapolski.cichy_bohater.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CategoryAdapter extends ArrayAdapter<Category> {
	private List<Category> values;
	private Context context;

	public CategoryAdapter(Context context, List<Category> objects) {
		super(context, R.layout.category_list_item, objects);
		this.context = context;
		this.values = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater
				.inflate(R.layout.category_list_item, parent, false);
		TextView categoryName = (TextView) view
				.findViewById(R.id.category_name);
		Category cat = values.get(position);
		categoryName.setText(cat.getName());
		return view;
	}

	public List<Category> getValues() {
		return values;
	}
}
