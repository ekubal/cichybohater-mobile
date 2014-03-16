package pl.kodujdlapolski.cichy_bohater.gui;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.Field;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class FormWidgetsGenerator {

	public static View createViewFromAttribute(final Field attribute,
			final HeroFormInterface heroForm) {
		String attributeType = attribute.getType();
		if (attributeType.equals(Constants.COMBO_FIELD_TYPE)) {
			return createSelectField(attribute, heroForm);
		} else if (attributeType.equals(Constants.CHECKBOX_FIELD_TYPE)) {
			return createCheckboxField(attribute, heroForm);
		} else if (attributeType.equals(Constants.TEXT_FIELD_TYPE)) {
			return createEditTextField(attribute, heroForm,
					InputType.TYPE_CLASS_TEXT);
		} else if (attributeType.equals(Constants.TEXT_AREA_FIELD_TYPE)) {
			return createEditTextField(attribute, heroForm,
					InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		} else if (attributeType.equals(Constants.NUMBER_FIELD_TYPE)) {
			return createEditTextField(attribute, heroForm,
					InputType.TYPE_CLASS_NUMBER
							| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		} else if (attributeType.equals(Constants.PHOTO_FIELD_TYPE)) {
			return createPhotoField(attribute, heroForm);
		} else {
			return null;
		}

	}

	private static View createEditTextField(final Field attribute,
			final HeroFormInterface heroForm, int inputType) {
		Context context = heroForm.getActivity();
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.form_fragment_edit_text, null,
				false);
		TextView label = (TextView) layout.findViewById(R.id.form_label);
		label.setText(attribute.getName() + ":");

		EditText input = (EditText) layout.findViewById(R.id.form_input);
		input.setInputType(inputType);

		heroForm.setViewReference(attribute.getPermalink(), input);
		return layout;
	}

	private static View createCheckboxField(final Field attribute,
			final HeroFormInterface heroForm) {
		Context context = heroForm.getActivity();
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.form_fragment_checkbox, null,
				false);

		CheckBox input = (CheckBox) layout.findViewById(R.id.form_input);
		input.setText(attribute.getName());

		heroForm.setViewReference(attribute.getPermalink(), input);
		return layout;
	}

	private static View createSelectField(final Field attribute,
			final HeroFormInterface heroForm) {
		Context context = heroForm.getActivity();
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.form_fragment_select, null,
				false);

		TextView label = (TextView) layout.findViewById(R.id.form_label);
		label.setText(attribute.getName() + ":");

		Spinner input = (Spinner) layout.findViewById(R.id.form_input);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_dropdown_item,
				attribute.getPossibleValues());

		input.setAdapter(adapter);

		heroForm.setViewReference(attribute.getPermalink(), input);
		return layout;
	}

	private static View createPhotoField(final Field attribute,
			final HeroFormInterface heroForm) {
		Context context = heroForm.getActivity();
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.form_fragment_photo,
				null, false);
		final ImageView photoImage = (ImageView) layout
				.findViewById(R.id.form_image_preview);
		photoImage.setTag(attribute.getPermalink());
		heroForm.setViewReference(attribute.getPermalink(), photoImage);

		TextView label = (TextView) layout.findViewById(R.id.form_label);
		label.setText(attribute.getName() + ":");

		Button takePhotoButton = (Button) layout
				.findViewById(R.id.take_photo_button);
		takePhotoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent takePictureIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);

				heroForm.setFormInput(photoImage);
				heroForm.startActivityForResult(takePictureIntent,
						Constants.TAKE_PHOTO_ACTION);
			}
		});
		Button selectPhotoButton = (Button) layout
				.findViewById(R.id.gallery_photo_button);
		selectPhotoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				heroForm.setFormInput(photoImage);
				heroForm.startActivityForResult(photoPickerIntent,
						Constants.SELECT_PHOTO_ACTION);
			}
		});
		return layout;
	}
}
