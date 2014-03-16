package pl.kodujdlapolski.cichy_bohater.data;

import java.util.List;

import pl.kodujdlapolski.cichy_bohater.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SchemaAdapter extends ArrayAdapter<Schema> {
	private List<Schema> values;
	private Context context;

	public SchemaAdapter(Context context, List<Schema> categories) {
		super(context, R.layout.category_list_item, categories);
		this.context = context;
		this.values = categories;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater
				.inflate(R.layout.category_list_item, parent, false);
		TextView categoryName = (TextView) view
				.findViewById(R.id.category_name);
		Schema cat = values.get(position);
		categoryName.setText(cat.getLabel());

		ImageView categoryImage = (ImageView) view
				.findViewById(R.id.category_image);
		// if (cat.getImageUrl() != null) {
		// Picasso.with(context).load(cat.getImageUrl()).into(categoryImage);
		// }
		return view;
	}

	public List<Schema> getValues() {
		return values;
	}
}
