/*
 * Authors: Marie Bougeret & Gaëtan Philippot (& various other sources on the interwebs)
 *
 * First Created: Nov. 27th 2014
 * Last updated: Jan. 5th 2015
 *
 * This app is the result of a mobile programming course (tought by P.E. F.)
 * It should have been a companion app for biblioctet.hyacinthe.fr, but alas, this is not.
 * 
 * Feel free to use this code in whatever way you see fit. Give us a shout if, against all odds, you found it useful!
 * 
 */

package com.esiea.test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends FragmentActivity {
	private EditText editText = null;
	private Button validate;
	private String result;
	private FragmentManager fm = getSupportFragmentManager();
	private ArrayList<Biere> listBiere;
	private String URL = "http://binouze.fabrigli.fr/bieres.json";
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    
	    //Loading text, appeared while loading
	    final TextView load = (TextView)findViewById(R.id.load);
		load.setVisibility(View.INVISIBLE);
	    		
		// This gets the bieres.json file and stores it into a ArrayList<Biere>
	    listBiere = new ArrayList<Biere>();
	    JSONArray jsonArray = getJSONArrayFromURL();
	    listBiere = creatArrayListFromJSONArray(jsonArray);	    
	    	    
	    //Reaction when button "rechercher" is pressed:
	    editText = (EditText) findViewById(R.id.editText_recherche);
	    validate = (Button) findViewById(R.id.button_chercher);
	    validate.setOnClickListener(
	    		new View.OnClickListener() {
	    			@Override
	    			public void onClick(View v) {
	    				Intent intentSecAct = new Intent(MainActivity.this, SecondActivity.class);
	    				result = editText.getText().toString();
	    				ArrayList<Integer> listId = getIdFromTextEdit(result);
	    				intentSecAct.putIntegerArrayListExtra("ListId", listId);
	    				
	    				//Make the loading message visible and start the second activity
	    				load.setVisibility(View.VISIBLE);
	    				startActivity(intentSecAct);
	    			}
	
	    		}
	   );
	    
    }
    
    //***********************************************************************************
    //Creat a JSONArray with the given URL
    private JSONArray getJSONArrayFromURL(){
    	DLTask dlTask = new DLTask();
	    dlTask.execute(this.URL);
	    String content = new String();
		try {
			content = dlTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return null;
		}
		
	    try {
			return new JSONArray(content);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
    }
 
    //***********************************************************************************
    //Creat an ArrayList<Biere> from a JSONArray with only necessary informations 
    private ArrayList<Biere> creatArrayListFromJSONArray(JSONArray jsonArray){
    	listBiere = new ArrayList<Biere>();
    	Biere biere;
    	try{
	    	for(int i = 0; i < jsonArray.length(); i++){
		    	biere = new Biere();
		    	JSONObject jsonObject = jsonArray.getJSONObject(i);
		    	
		    	biere.setCategory(jsonObject.getString("category"));
	    		biere.setId(jsonObject.getString("id"));
		    	biere.setName(jsonObject.getString("name"));
		    	biere.setCountry(jsonObject.getJSONObject("country"));
		    	biere.setBuveur(jsonObject.getString("buveur"));
		    	listBiere.add(biere);
	    	}
	   } catch (JSONException e) {
				e.printStackTrace();
			}
    	return listBiere;
    }
    
    //***********************************************************************************
    //Compare a String (which have to be the result of TextEdit with informations recorded in the ArrayList<Biere> and record their ID in a ArrayList<Integer>
    private ArrayList<Integer> getIdFromTextEdit(String result){
    	ArrayList<Integer> listId = new ArrayList<Integer>();
    	int tmp;
    	for(int i = 0; i < this.listBiere.size(); i++){			
			if(this.listBiere.get(i).getName().contains(result) || this.listBiere.get(i).getCategory().contains(result) || this.listBiere.get(i).getBuveur().contains(result) || this.listBiere.get(i).getCountryName().contains(result)){
				tmp = Integer.parseInt(this.listBiere.get(i).getId());
				listId.add(tmp);
			}
    	}
    	
    	return listId;
    }
    
    //**************************************************************************************
    //Création of the action bar and its reactions

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
    		ActionBar actionBar = getActionBar();
    		actionBar.setTitle("Beerbliotèque");
    		actionBar.setDisplayHomeAsUpEnabled(true);
    	}
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        case R.id.menu_about:
        	AboutDFragment fragmentAbout = new AboutDFragment();
        	fragmentAbout.show(fm, "ResearchDFragment");
                return true;
        case R.id.menu_search:
        	ResearchDFragment fragmentResearch = new ResearchDFragment();
        	fragmentResearch.show(fm, "ResearchDFragment");
                return true;
        default:
                return super.onOptionsItemSelected(item);
        }
    }
}