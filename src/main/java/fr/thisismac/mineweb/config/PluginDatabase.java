package fr.thisismac.mineweb.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.thisismac.mineweb.Core;
import lombok.Getter;
import lombok.Setter;
import net.cubespace.Yamler.Config.Config;

public class PluginDatabase extends Config {
	
	public PluginDatabase() {
		CONFIG_HEADER = new String[]{"DO NOT TOUCH AT THIS FILE, ITS USE LIKE DATABASE FOR THE PLUGIN", "NE TOUCHER PAS A CE FICHIER, IL EST UTILISE COMME BASE DE DONNEES POUR LE PLUGIN"};
	    CONFIG_FILE = new File(Core.get().getDataFolder(), "db.yml");
	}
	
	
	@Getter private HashMap<String, String> timedCmds = new HashMap<String, String>();
	
	@Getter private List<String> playersCmds = new ArrayList<String>();
	
	@Getter @Setter private String ipAllowed = "127.0.0.1";
	
	@Getter @Setter private boolean installed = false;
	
	@Getter @Setter private String key = "42";
	
	@Getter @Setter private int port = 8080;
	
	@Getter @Setter private boolean mineguard = false;
	
	@Getter @Setter private String domain = "http://google.com";
	
	@Getter @Setter private boolean bungeeSupport = false;
	
	
	
}
