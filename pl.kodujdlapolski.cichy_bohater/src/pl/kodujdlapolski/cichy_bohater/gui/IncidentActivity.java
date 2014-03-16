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
import pl.kodujdlapolski.cichy_bohater.data.Field;
import pl.kodujdlapolski.cichy_bohater.data.Schema;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class IncidentActivity extends BaseAcitivity implements
		HeroFormInterface {

	private Schema interventionSchema;
	private static ContentValues formData;
	// map for references to edit views
	private Map<String, View> formViews = new HashMap<String, View>();
	// map for bitmaps from form
	private static Map<String, Bitmap> formBitmaps = new HashMap<String, Bitmap>();

	private View selectedInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_incident);

		formBitmaps = new HashMap<String, Bitmap>();

		LinearLayout interventionLayout = (LinearLayout) findViewById(R.id.incident_form);
		interventionSchema = getCategoryFromIntent();
		addFormAttributes(interventionLayout, interventionSchema.getFields());

		setAppTitle(interventionSchema.getLabel());
	}

	private void addFormAttributes(LinearLayout layout, List<Field> attributes) {
		for (Field attribute : attributes) {
			View attributeView = FormWidgetsGenerator.createViewFromAttribute(
					attribute, this);
			if (attributeView != null) {
				layout.addView(attributeView, new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			}
		}
	}

	public void onSendButtonClick(View view) {
		Intent intent = new Intent(this, SummaryActivity.class);
		formData = getValuesFromAllInputs();
		intent.putExtra(Constants.SCHEMA_EXTRA, interventionSchema);
		startActivity(intent);
	}

	private ContentValues getValuesFromAllInputs() {
		ContentValues values = new ContentValues();
		for (Entry<String, View> entry : formViews.entrySet()) {
			View view = entry.getValue();
			if (view instanceof EditText) {
				EditText editText = (EditText) view;
				values.put(entry.getKey(), editText.getText().toString());
			} else if (view instanceof CheckBox) {
				CheckBox checkBox = (CheckBox) view;
				values.put(entry.getKey(), checkBox.isChecked() ? "1" : "0");
			} else if (view instanceof Spinner) {
				Spinner spinner = (Spinner) view;
				values.put(entry.getKey(), (String) spinner.getSelectedItem());
			} else if (view instanceof ImageView) {
				ImageView imgView = (ImageView) view;
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

	public static ContentValues getFormData() {
		return formData;
	}

	public static Bitmap getBitmap(String bitmapKey) {
		return formBitmaps.get(bitmapKey);
	}

	@Override
	public void setViewReference(String fieldName, View view) {
		formViews.put(fieldName, view);

	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void setFormInput(View input) {
		this.selectedInput = input;

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (selectedInput != null) {
			ImageView imgView = (ImageView) selectedInput;
			String fieldName = (String) imgView.getTag();
			Bitmap mImageBitmap = null;

			try {
				if (requestCode == Constants.TAKE_PHOTO_ACTION
						&& resultCode == Activity.RESULT_OK) {
					mImageBitmap = (Bitmap) data.getExtras().get("data");
				} else if (requestCode == Constants.SELECT_PHOTO_ACTION
						&& resultCode == Activity.RESULT_OK) {
					Uri selectedImage = data.getData();

					InputStream imageStream = getActivity()
							.getContentResolver()
							.openInputStream(selectedImage);
					mImageBitmap = BitmapFactory.decodeStream(imageStream);
				} else {
					super.onActivityResult(requestCode, resultCode, data);
					return;
				}
				if (mImageBitmap != null) {
					formBitmaps.put(fieldName, mImageBitmap);
					imgView.setImageBitmap(scaleDownBitmap(mImageBitmap, 200,
							this));
				}
			} catch (FileNotFoundException e) {
				Log.e(Constants.LOG, e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight,
			Context context) {

		final float densityMultiplier = context.getResources()
				.getDisplayMetrics().density;

		int h = (int) (newHeight * densityMultiplier);
		int w = (int) (h * photo.getWidth() / ((double) photo.getHeight()));

		photo = Bitmap.createScaledBitmap(photo, w, h, true);

		return photo;
	}

}
