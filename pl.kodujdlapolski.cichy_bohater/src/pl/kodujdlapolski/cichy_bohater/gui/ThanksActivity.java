package pl.kodujdlapolski.cichy_bohater.gui;

import pl.kodujdlapolski.cichy_bohater.R;
import android.os.Bundle;

public class ThanksActivity extends BaseAcitivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thanks);
		setAppTitle(R.string.app_name);
	}

}
