package pl.kodujdlapolski.cichy_bohater.gui;

import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.R;
import android.os.Bundle;
import android.widget.TextView;

public class ErrorActivity extends BaseAcitivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_error);

		String str = getIntent().getExtras().getString(Constants.ERROR_MESSAGE);

		TextView errorMsg = (TextView) findViewById(R.id.error_text);
		errorMsg.setText(str);
	}

}
