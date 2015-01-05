//An alert message Object with a message and a button to close the window.

package com.esiea.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class AboutDFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new AlertDialog.Builder(getActivity())
			.setIcon(R.drawable.ic_about)
			.setTitle("Information")
			.setMessage("Cette application a pour vocation d'être une bibliotheque virtuelle de livre technique nommée \"Biblioctet\". Elle référence pour le moment differents types de bière. Entrez un nom ou une catégorie de bière sur la page principale pour effectuer votre recherche.")
			.setPositiveButton("Fermer", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			}).create();
	}

}