package pl.kodujdlapolski.cichy_bohater.gui;

import java.util.List;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import pl.kodujdlapolski.cichy_bohater.data.CategoryAttribute;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SummaryFragment extends Fragment {
	private Category incidentCategory;
	private ContentValues inputValues;

	@Override
	public void setArguments(Bundle args) {
		incidentCategory = (Category) args.get(Constants.CATEGORY_EXTRA);
		inputValues = (ContentValues) args.get(Constants.INCIDENT_DATA_EXTRA);
		super.setArguments(args);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (incidentCategory == null) {
			incidentCategory = (Category) getActivity().getIntent().getExtras()
					.get(Constants.CATEGORY_EXTRA);
		}
		if (inputValues == null) {
			inputValues = IncidentActivity.getFormData();
		}
		setRetainInstance(true);

		LinearLayout fragmentLayout = (LinearLayout) inflater.inflate(
				R.layout.summary_fragment, container, false);

		List<CategoryAttribute> attributes = incidentCategory
				.getCategoryAttributes();
		for (CategoryAttribute attribute : attributes) {
			String attributeType = attribute.getAttributeType();
			String attributeName = attribute.getPermalink();
			String attributeValue = inputValues.getAsString(attributeName);
			if (attributeType != null && attributeType.equals("Text")
					&& attributeValue != null) {
				View layout = inflater.inflate(R.layout.summary_fragment_text,
						null, false);
				TextView attributeLabel = (TextView) layout
						.findViewById(R.id.summary_label);
				attributeLabel.setText(attribute.getPermalink() + ":");
				TextView inputText = (TextView) layout
						.findViewById(R.id.summary_text);
				inputText.setText(attributeValue);
				fragmentLayout.addView(layout);
			} else if (attributeType != null && attributeType.equals("Photo")
					&& attributeValue != null) {
				View layout = inflater.inflate(R.layout.summary_fragment_photo,
						null, false);
				TextView attributeLabel = (TextView) layout
						.findViewById(R.id.summary_label);
				attributeLabel.setText(attribute.getPermalink() + ":");
				ImageView imageView = (ImageView) layout
						.findViewById(R.id.summary_photo);
				// byte[] encodeByte = Base64.decode(attributeValue,
				// Base64.DEFAULT);
				// Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
				// encodeByte.length);
				// imageView.setImageBitmap(bitmap);
				fragmentLayout.addView(layout);
			}
		}
		return fragmentLayout;
	}
}
