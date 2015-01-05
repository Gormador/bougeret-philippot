package com.esiea.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ThirdActivity extends FragmentActivity {
	private FragmentManager fm = getSupportFragmentManager();
	
	private ImageView imgView;

	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_third);
	       
	    //Get the ID of the beer, and then create an object Biere using a JsonObject; then print it in the view
	    String beerId = (String) getIntent().getSerializableExtra("beerId");
		Biere biere = creatBiereFromJSONObject(getJSONObjectFromURL(Integer.parseInt(beerId)));	
		imgView = (ImageView) findViewById(R.id.imageBeer);
		new ImageLoadTask(biere.getURLBigImage(), imgView).execute();
		
		((TextView)findViewById(R.id.name)).setText("Nom de la bière: " + biere.getName());
		((TextView)findViewById(R.id.category)).setText("Catégorie: " + biere.getCategory());
		((TextView)findViewById(R.id.description_beer)).setText("Description: " + biere.getDescription());
		((TextView)findViewById(R.id.country)).setText("Pays d'origine: " + biere.getCountryName());
		((TextView)findViewById(R.id.buveur)).setText("Buveur: " + biere.getBuveur());
		((TextView)findViewById(R.id.note_moyenne)).setText("Note moyenne: " + biere.getNote_moyenne());
		((TextView)findViewById(R.id.number_note)).setText("Nombre de notes: " + biere.getNumber_of_notes());
	 }
	 
	 //*****************************************************************************************
	 //Creat a JSONObject from URL thanks to the ID of the beer
	 private JSONObject getJSONObjectFromURL(int biereId){
	    	DLTask dlTask;
	    	String content;
	    	try {
		    	dlTask = new DLTask();
		    	dlTask.execute("http://binouze.fabrigli.fr/bieres/" + biereId + ".json");
		    	content = new String();
				content = dlTask.get();
				return new JSONObject(content);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return null;
			} catch (ExecutionException e) {
				e.printStackTrace();
				return null;
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
	    }
	 
	 
	//*****************************************************************************************
	 //Creat a Biere from a JSONObject
	 private Biere creatBiereFromJSONObject(JSONObject jsonObject){
	    	Biere biere = new Biere();
	    	try{
			    	biere.setCategory(jsonObject.getString("category"));
			    	biere.setCategory_id(jsonObject.getString("category_id"));
			    	biere.setCreated_at(jsonObject.getString("created_at"));
			    	biere.setDescription(jsonObject.getString("description"));
		    		biere.setId(jsonObject.getString("id"));
			    	biere.setImage(jsonObject.getJSONObject("image"));
			    	biere.setName(jsonObject.getString("name"));
		    		biere.setNote(jsonObject.getString("note"));
			    	biere.setUpdated_at(jsonObject.getString("updated_at"));
			    	biere.setCountry(jsonObject.getJSONObject("country"));
			    	biere.setBuveur(jsonObject.getString("buveur"));
		    		biere.setNote_moyenne(jsonObject.getString("note_moyenne"));
		    		biere.setNumber_of_notes(jsonObject.getString("number_of_notes"));
		   } catch (JSONException e) {
					e.printStackTrace();
				}
	    	return biere;
	    }
	 
	 
	 //*****************************************************************************************
	 //Get a Bitmap from an URL
	 public static Bitmap getBitmapFromURL(String src) {
		    try {
		        Log.e("src",src);
		        URL url = new URL(src);
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setDoInput(true);
		        connection.connect();
		        InputStream input = connection.getInputStream();
		        Bitmap myBitmap = BitmapFactory.decodeStream(input);
		        return myBitmap;
		    } catch (IOException e) {
		        e.printStackTrace();
		        return null;
		    }
		}
	 
	//*****************************************************************************************
	//Création of the action bar and its reactions
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.second, menu);
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
    	case android.R.id.home:
    		onBackPressed();
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
