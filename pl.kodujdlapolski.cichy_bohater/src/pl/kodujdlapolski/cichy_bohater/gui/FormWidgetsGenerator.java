package pl.kodujdlapolski.cichy_bohater.gui;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.CategoryAttribute;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FormWidgetsGenerator {

	public static View createViewFromAttribute(
			final CategoryAttribute attribute, final HeroFormInterface heroForm) {
		String attributeType = attribute.getAttributeType();
		if (attributeType.equals("Text")) {
			return createEditTextField(attribute, heroForm);
		} else if (attributeType.equals("Photo")) {
			return createPhotoField(attribute, heroForm);
		} else {
			return null;
		}

	}

	private static View createEditTextField(final CategoryAttribute attribute,
			final HeroFormInterface heroForm) {
		Context context = heroForm.getActivity();
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.form_fragment_edit_text, null,
				false);
		TextView label = (TextView) layout.findViewById(R.id.form_label);
		label.setText(attribute.getName());

		EditText input = (EditText) layout.findViewById(R.id.form_input);
		input.setId(ViewIdsGenerator.generateViewId());

		heroForm.setViewReference(attribute.getPermalink(), input);
		return layout;
	}

	private static View createPhotoField(final CategoryAttribute attribute,
			final HeroFormInterface heroForm) {
		Context context = heroForm.getActivity();
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.form_fragment_photo,
				null, false);
		final ImageView photoImage = (ImageView) layout
				.findViewById(R.id.form_image_preview);
		photoImage.setId(ViewIdsGenerator.generateViewId());
		photoImage.setTag(attribute.getPermalink());
		heroForm.setViewReference(attribute.getPermalink(), photoImage);

		TextView label = (TextView) layout.findViewById(R.id.form_label);
		label.setText(attribute.getName());

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
