package fr.thisismac.mineweb.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.thisismac.mineweb.Core;

public class MineGuardHandler {

	public JsonObject sendMineGuardRequest(String name, String ip)  {
		try {
			URL obj = new URL(Core.get().getDB().getDomain() + "api/mineguard?user=" + name +"&ip=" + ip);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
		
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return new JsonParser().parse(response.toString()).getAsJsonObject();
			
		} catch (Exception e) {
			e.printStackTrace();
			JsonObject temp = new JsonObject();
			temp.addProperty("result", "PROBLEM");
			return temp;
		}
	}
	
}
