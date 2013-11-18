package pl.kodujdlapolski.cichy_bohater.gui.form_fragments;

import pl.kodujdlapolski.cichy_bohater.R;
import pl.kodujdlapolski.cichy_bohater.data.CategoryAttribute;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class EditTextFragment extends Fragment {

	public EditTextFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.form_fragment_edit_text,
				container, false);
		return view;
	}

	public void updateContent(CategoryAttribute attribute) {
		View v = getView();
		TextView label = (TextView) v.findViewById(R.id.form_label);
		EditText input = (EditText) v.findViewById(R.id.form_input);
		label.setText(attribute.getTitle());
		input.setText("");

	}
}
