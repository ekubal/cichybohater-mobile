package pl.kodujdlapolski.cichy_bohater.gui;

import java.util.List;

import pl.kodujdlapolski.cichy_bohater.AppStatus;
import pl.kodujdlapolski.cichy_bohater.Constants;
import pl.kodujdlapolski.cichy_bohater.data.Category;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public abstract class BaseAcitivity extends ActionBarActivity {

	protected List<Category> getCategoriesFromIntent() {
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			return ((List<Category>) extras
					.get(Constants.CATEGORIES_LIST_EXTRA));
		} else {
			return null;
		}
	}

	protected Category getCategoryFromIntent() {
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			return (Category) extras.get(Constants.CATEGORY_EXTRA);
		} else {
			return null;
		}
	}

	protected void setAppTitle(String appTitle) {
		getSupportActionBar().setTitle(appTitle);
	}

	protected void setAppTitle(int appTitleResource) {
		getSupportActionBar().setTitle(appTitleResource);
	}

	@Override
	public void onPause() {
		AppStatus.getInstance().disableLocationUpdates(this);
		super.onPause();
	}

	@Override
	public void onResume() {
		AppStatus.getInstance().enableLocationUpdates(this);
		super.onResume();
	}
}
