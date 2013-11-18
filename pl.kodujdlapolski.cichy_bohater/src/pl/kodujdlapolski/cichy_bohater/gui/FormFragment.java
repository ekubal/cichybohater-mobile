package pl.kodujdlapolski.cichy_bohater.gui;

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
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FormFragment extends Fragment implements HeroFormInterface {

	private Map<String, View> formViews = new HashMap<String, View>();

	private AlertDialog loadingDialog;
	private Category incidentCategory;

	private View chosenInput;

	public FormFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.form_fragment, container, false);
		setRetainInstance(true);

		// LinearLayout layout = new LinearLayout(getActivity());
		// layout.setGravity(LinearLayout.VERTICAL);

		createLoadingDialog();

		Bundle inputExtras = getActivity().getIntent().getExtras();
		if (inputExtras != null) {
			incidentCategory = (Category) inputExtras
					.get(Constants.CATEGORY_EXTRA);
			if (incidentCategory != null) {
				getActivity().setTitle(incidentCategory.getName());

				List<CategoryAttribute> attributes = incidentCategory
						.getCategoryAttributes();
				for (CategoryAttribute attr : attributes) {
					View attributeView = FormWidgetsGenerator
							.createViewFromAttribute(attr, this);
					if (attributeView != null) {
						layout.addView(attributeView,
								new LinearLayout.LayoutParams(
										LinearLayout.LayoutParams.MATCH_PARENT,
										LinearLayout.LayoutParams.WRAP_CONTENT));
					}
				}

				if (loadingDialog != null) {
					loadingDialog.dismiss();
					loadingDialog = null;
				}
			}
		}
		return layout;
	}

	private void createLoadingDialog() {
		if (loadingDialog != null && loadingDialog.isShowing()) {
			loadingDialog.dismiss();
		}
		loadingDialog = LoadingDialog.createLoadingDialog(getActivity());
		loadingDialog.show();
	}

	public void setViewReference(String fieldName, View view) {
		formViews.put(fieldName, view);
	}

	public Map<String, String> getDataFromFragment() {
		return null;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("onAR", "Test");
		Toast.makeText(getActivity(), "xxx", Toast.LENGTH_LONG).show();
		if (chosenInput != null) {

			if (requestCode == Constants.TAKE_PHOTO_ACTION
					&& resultCode == Activity.RESULT_OK) {
				ImageView img = (ImageView) chosenInput;
				Bundle extras = data.getExtras();
				Bitmap mImageBitmap = (Bitmap) extras.get("data");

				img.setImageBitmap(mImageBitmap);
			} else if (requestCode == Constants.SELECT_PHOTO_ACTION
					&& resultCode == Activity.RESULT_OK) {

				Uri selectedImage = data.getData();
				InputStream imageStream;
				try {
					imageStream = getActivity().getContentResolver()
							.openInputStream(selectedImage);
					Bitmap mImageBitmap = BitmapFactory
							.decodeStream(imageStream);
					ImageView img = (ImageView) chosenInput;
					img.setImageBitmap(mImageBitmap);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
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

	public Map<String, String> getAllInputs() {
		Map<String, String> dataMap = new HashMap<String, String>();
		for (Entry<String, View> entry : formViews.entrySet()) {
			View v = entry.getValue();
			if (v instanceof EditText) {
				EditText v2 = (EditText) v;
				dataMap.put(entry.getKey(), v2.getText().toString());
				// } else if (v instanceof ImageView) {
				// ImageView v2 = (ImageView) v;
				// v2.buildDrawingCache();
				// Bitmap bmp = v2.getDrawingCache();
				// ByteArrayOutputStream bao = new ByteArrayOutputStream();
				// bmp.compress(Bitmap.CompressFormat.JPEG, 100, bao);
				// String ba1 = Base64.encodeToString(bao.toByteArray(),
				// Base64.DEFAULT);
				// dataMap.put(entry.getKey(), ba1);
			}
		}
		return dataMap;
	}
}
