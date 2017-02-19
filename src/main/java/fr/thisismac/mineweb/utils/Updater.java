package fr.thisismac.mineweb.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonParser;

import fr.thisismac.mineweb.Core;
import lombok.Getter;

public class Updater {
	
		@Getter private String latestVersion;
		@Getter private boolean needUpdate = false;
		@Getter private String currentVersion;
		
		public Updater() {
			checkUpdate();
		}
	     
	    public void checkUpdate() {
	    	currentVersion = Core.get().getDescription().getVersion();
	    	
	    	latestVersion = getOnlineLastestVersion();
	        if (!currentVersion.equals(latestVersion)) {
	        	needUpdate = true;
	        }
	    }

	    public String getOnlineLastestVersion() {
	        String version = null;
	        try {
	        	HttpURLConnection con = (HttpURLConnection) new URL("http://mineweb.org/api/plugin_version/bukkit").openConnection();
	            con.setDoOutput(true);
	            con.setRequestMethod("POST");

	            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String tmp;
				StringBuffer response = new StringBuffer();
			
				while ((tmp = in.readLine()) != null) 
					response.append(tmp);
				in.close();
				return new JsonParser().parse(response.toString()).getAsJsonObject().get("last_release").getAsString();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	        return version;
	    }
	   
}
