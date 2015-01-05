//A window that allows the user to make a research everywhere in the app.
//Currently working alone but the link between the Activity and this Fragment is not made

package com.esiea.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

public class ResearchDFragment extends DialogFragment {
	private EditText editText;
	private String contentEditText;
	private Boolean quit = false;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		editText = new EditText(getActivity());
		return new AlertDialog.Builder(getActivity())
			.setIcon(R.drawable.ic_research)
			.setTitle("Entrez votre recherche:")
			.setMessage("Cette option n'est pas encore disponible, veuillez utiliser la recherche de la page principale.")
			.setView(editText)
			.setPositiveButton("Chercher", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					contentEditText = editText.getText().toString();
					quit = true;
				}
			})
			.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,	int which) {
				}
		}).create();
	}


	/*********************************************************************************************** 
	*GETTERS & SETTERS
	*/
	public String getContentEditText() {
		return contentEditText;
	}
	
	public Boolean getQuit() {
		return quit;
	}

}