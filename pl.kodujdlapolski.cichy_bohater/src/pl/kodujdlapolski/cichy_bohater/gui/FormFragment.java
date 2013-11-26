package pl.kodujdlapolski.cichy_bohater.gui;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import pl.kodujdlapolski.cichy_bohater.data.CategoryAttribute;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FormFragment extends Fragment implements HeroFormInterface {

	private Map<String, View> formViews = new HashMap<String, View>();
	private Map<String, Bitmap> formBitmaps = new HashMap<String, Bitmap>();
	private Category incidentCategory;
	private View chosenInput;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.form_fragment, container, false);
		setRetainInstance(true);

		incidentCategory = getCategoryFromIntent();
		if (incidentCategory != null) {
			getActivity().setTitle(incidentCategory.getName());

			List<CategoryAttribute> attributes = incidentCategory
					.getCategoryAttributes();
			for (CategoryAttribute attribute : attributes) {
				View attributeView = FormWidgetsGenerator
						.createViewFromAttribute(attribute, this);
				if (attributeView != null) {
					layout.addView(attributeView,
							new LinearLayout.LayoutParams(
									LayoutParams.MATCH_PARENT,
									LayoutParams.WRAP_CONTENT));
				}
			}
		}
		return layout;
	}

	public void setViewReference(String fieldName, View view) {
		formViews.put(fieldName, view);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (chosenInput != null) {
			if (requestCode == Constants.TAKE_PHOTO_ACTION
					&& resultCode == Activity.RESULT_OK) {
				ImageView imgView = (ImageView) chosenInput;
				Bundle extras = data.getExtras();
				Bitmap mImageBitmap = (Bitmap) extras.get("data");
				String fieldName = (String) imgView.getTag();
				formBitmaps.put(fieldName, mImageBitmap);
				imgView.setImageBitmap(mImageBitmap);

			} else if (requestCode == Constants.SELECT_PHOTO_ACTION
					&& resultCode == Activity.RESULT_OK) {
				Uri selectedImage = data.getData();
				InputStream imageStream;
				try {
					imageStream = getActivity().getContentResolver()
							.openInputStream(selectedImage);
					Bitmap mImageBitmap = BitmapFactory
							.decodeStream(imageStream);
					ImageView imgView = (ImageView) chosenInput;
					String fieldName = (String) imgView.getTag();
					formBitmaps.put(fieldName, mImageBitmap);
					imgView.setImageBitmap(mImageBitmap);
				} catch (FileNotFoundException e) {
					Log.e(Constants.LOG, e.getMessage());
					e.printStackTrace();
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void setFormInput(View input) {
		this.chosenInput = input;

	}

	public ContentValues getAllInputs() {
		ContentValues values = new ContentValues();
		for (Entry<String, View> entry : formViews.entrySet()) {
			Log.d(Constants.LOG, entry.getKey());
			View v = entry.getValue();
			if (v instanceof EditText) {
				EditText editText = (EditText) v;
				values.put(entry.getKey(), editText.getText().toString());
			} else if (v instanceof ImageView) {
				ImageView imgView = (ImageView) v;
				Bitmap bitmap = formBitmaps.get(imgView.getTag());
				if (bitmap != null) {
					ByteArrayOutputStream bao = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
					values.put(entry.getKey(), bao.toByteArray());
				}
			}
		}
		return values;

	}

	private Category getCategoryFromIntent() {
		Bundle extras = getActivity().getIntent().getExtras();
		if (extras != null) {
			return (Category) extras.get(Constants.CATEGORY_EXTRA);
		} else {
			return null;
		}
	}
}
