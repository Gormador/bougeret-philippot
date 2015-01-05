package com.esiea.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class SecondActivity extends FragmentActivity {
	private ListView listView;
	private ArrayList<Biere> listBiere;
	private FragmentManager fm = getSupportFragmentManager();
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        
        //Retrieves the ID list and check if it is not empty
        @SuppressWarnings("unchecked")
		ArrayList<Integer> listId = (ArrayList<Integer>) getIntent().getSerializableExtra("ListId");
        if(listId == null)
        	((TextView)findViewById(R.id.text_result)).setText("Aucun éléments ne corresond à cotre recherche, veuillez reessayer.");

        //Create the ArrayList<Biere> of beers that interests the users, using their ID and a JSONObject
        else{
			ArrayList<JSONObject> listJSON = getJSONObjectFromURLs(listId);		
			listBiere = creatBiereListFromJSONObject(listJSON);
			
	        
		    //Creation of the ListView that contains the "interesting" beers:
			listView = (ListView) findViewById(R.id.newListView);
			ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
			    
			HashMap<String, String> map;
		    
		    for(int i = 0; i < listBiere.size(); i++){
		    	map = new HashMap<String, String>();
		    	map.put("titre" , listBiere.get(i).getName());
		    	map.put("description", listBiere.get(i).getCategory());
		    	map.put("img", String.valueOf(R.drawable.ic_launcher));
		    	listItem.add(map);
		    }
			   
			SimpleAdapter adapt = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.listview_display, new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description_});
			listView.setAdapter(adapt);
			
			//When user click on a beer, goes to another activity corresponding to this beer
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
		     	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		    		@SuppressWarnings("unchecked")
					HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
		    		Intent intentThiAct = new Intent(SecondActivity.this, ThirdActivity.class);
					intentThiAct.putExtra("beerId", getIdFromName((String) map.get("titre")));
					startActivity(intentThiAct);
				}
		    });
		
        }
    }
    
    
    //***************************************************************************************
    //Creat an ArrayList<JSONObject> based on a URL
    private ArrayList<JSONObject> getJSONObjectFromURLs(ArrayList<Integer> listId){
    	DLTask dlTask;
    	JSONObject jsonObject;
    	String content;
    	ArrayList<JSONObject> listJsonObject = new ArrayList<JSONObject>();
    	try {
	    	for(int i = 0; i < listId.size(); i++){
	    		dlTask = new DLTask();
	    		dlTask.execute("http://binouze.fabrigli.fr/bieres/" + listId.get(i) + ".json");
	    		content = new String();
				content = dlTask.get();
				jsonObject = new JSONObject(content);
				listJsonObject.add(jsonObject);
	    	}
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
		return listJsonObject;
    }
    
    //***************************************************************************************
    //Creat an ArrayList<Biere> based on a JSONObject, recording only intersting informations
    private ArrayList<Biere> creatBiereListFromJSONObject(ArrayList<JSONObject> listJSON){
    	ArrayList<Biere> listBiere= new ArrayList<Biere>();
    	Biere biere;
    	try{
	    	for(int i = 0; i < listJSON.size(); i++){
		    	biere = new Biere();
		    	
		    	biere.setCategory(listJSON.get(i).getString("category"));
	    		biere.setId(listJSON.get(i).getString("id"));
		    	biere.setName(listJSON.get(i).getString("name"));
		    	listBiere.add(biere);
	    	}
	   } catch (JSONException e) {
				e.printStackTrace();
			}
    	return listBiere;
    }
    
    //***********************************************************************************************
    //Get the ID of a beer, knowing its name
    private String getIdFromName(String name){
    	for(int i = 0; i < this.listBiere.size(); i++){
    		if(listBiere.get(i).getName() == name){
    			return listBiere.get(i).getId();
    		}
    	}
    	return null;
    }
    
    
    //***********************************************************************************************
    //Création of the action bar and its reactions
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
