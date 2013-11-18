package pl.kodujdlapolski.cichy_bohater.gui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public interface HeroFormInterface {
	public void setViewReference(String fieldName, View view);

	public Activity getActivity();

	public void startActivityForResult(Intent intent, int requestCode);

	public void setFormInput(View input);
}
