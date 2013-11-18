package pl.kodujdlapolski.cichy_bohater.gui;

import pl.kodujdlapolski.cichy_bohater.R;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class LoadingDialog {

	public static AlertDialog createLoadingDialog(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View dialogView = inflater.inflate(R.layout.loading_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(dialogView);
		return builder.create();
	}

}
