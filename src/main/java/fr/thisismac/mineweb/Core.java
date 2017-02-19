package fr.thisismac.mineweb;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import net.cubespace.Yamler.Config.InvalidConfigurationException;

import com.google.gson.Gson;

import fr.thisismac.mineweb.bridge.BungeeBridge;
import fr.thisismac.mineweb.bridge.EconomyBridge;
import fr.thisismac.mineweb.bridge.FactionsBridge;
import fr.thisismac.mineweb.bridge.PermissionsBridge;
import fr.thisismac.mineweb.bridge.version.EconomyNull;
import fr.thisismac.mineweb.bridge.version.EconomyVault;
import fr.thisismac.mineweb.bridge.version.Factions1_6;
import fr.thisismac.mineweb.bridge.version.Factions2_7;
import fr.thisismac.mineweb.bridge.version.FactionsNull;
import fr.thisismac.mineweb.bridge.version.PermissionEx;
import fr.thisismac.mineweb.bridge.version.PermissionGM;
import fr.thisismac.mineweb.bridge.version.PermissionNull;
import fr.thisismac.mineweb.bridge.version.PermissionVault;
import fr.thisismac.mineweb.cmd.MinewebExecutor;
import fr.thisismac.mineweb.config.PluginDatabase;
import fr.thisismac.mineweb.listeners.PlayerListeners;
import fr.thisismac.mineweb.task.TimedTask;
import fr.thisismac.mineweb.utils.MineGuardHandler;
import fr.thisismac.mineweb.utils.PluginLogger;
import fr.thisismac.mineweb.utils.Updater;
import fr.thisismac.mineweb.web.MethodHandler;
import fr.thisismac.mineweb.web.WebThread;
import lombok.Getter;

public class Core extends JavaPlugin{
	
	public static Core get() {
		return instance;
	}
	
	// Instance
	private static Core						instance;
	
	// Object
	 @Getter private TimedTask						timedTask;
	 @Getter private MineGuardHandler				guardHandler;
	 @Getter private PluginDatabase 				DB;
	 @Getter private PluginLogger 					log;
	 @Getter private MethodHandler					methodHandler;
	 @Getter private WebThread 						webThread;
	 @Getter private Gson							GSON = new Gson();
	 @Getter private Updater						updater;
	 
	 // Bridge
	 @Getter private BungeeBridge					bungeeBridge;
	 @Getter private FactionsBridge					factionsBridge;
	 @Getter private PermissionsBridge				permissionsBridge;
	 @Getter private EconomyBridge					economyBridge;

	 @Override
	 public void onEnable() {
		// BASE
		instance = this;
		getCommand("mineweb").setExecutor(new MinewebExecutor());
		getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
		
		// DB
		this.DB = new PluginDatabase();
		try {
			this.DB.init();
			this.DB.load();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		// WEB SERVER 
		webThread = new WebThread();
		methodHandler = new MethodHandler();
		webThread.run();
		
		
		// Factions hook
		if(getServer().getPluginManager().getPlugin("Factions") == null) {
			factionsBridge = (FactionsBridge) new FactionsNull();
			getLogger().log(Level.INFO, "Factions not found, not hooked into it.");
		}
		else if(getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion().startsWith("1.")) {
			factionsBridge = new Factions1_6();
			getLogger().log(Level.INFO, "Factions found, hooked for version 1.X");
		}
		else if(getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion().startsWith("2.")) {
			factionsBridge = new Factions2_7();
			getLogger().log(Level.INFO, "Factions found, hooked for version 2.X");
		}
		
		// Permissions Hook
		
		 if(getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
			permissionsBridge = new PermissionEx();
			getLogger().log(Level.INFO, "PermissionsEx found, hooked into it for permissions.");
		}
		else if(getServer().getPluginManager().getPlugin("GroupManager") != null) {
			permissionsBridge = new PermissionGM();
			getLogger().log(Level.INFO, "GroupManager found, hooked into it for permissions.");
		}
		else if(getServer().getPluginManager().getPlugin("Vault") != null) {
			permissionsBridge = new PermissionVault();
			getLogger().log(Level.INFO, "Vault found, hooked into it for permissions.");
		}
		else {
			permissionsBridge = new PermissionNull();
			getLogger().log(Level.INFO, "No plugin found to hook for permissions.");
		}
		
		// Economy Hook
		if(getServer().getPluginManager().getPlugin("Vault") != null) {
			economyBridge = new EconomyVault();
			getLogger().log(Level.INFO, "Vault found, hooked into it for economy.");
		}
		else {
			economyBridge = new EconomyNull();
			getLogger().log(Level.INFO, "No plugin found to hook for economy.");
		}
		
		timedTask = new TimedTask();
		timedTask.runTaskTimerAsynchronously(this, 0, 200);
		guardHandler = new MineGuardHandler();
		bungeeBridge = new BungeeBridge();
		
		
		// LOGGER
		log = new PluginLogger();
	
		updater = new Updater();
		getLogger().info("Actual version : " + updater.getCurrentVersion() + " && lastest version : " + updater.getLatestVersion());
		
		if(updater.getLatestVersion() == null) 
			getLogger().info("No update found.");
		else if(updater.isNeedUpdate()) {
			getLogger().info("**************************************************************");
			getLogger().info("Update detected, you must download it for new fix & features !");
			getLogger().info("**************************************************************");
		}
		else {
			getLogger().info("No update found.");
		}
	}
	
	// FUNCTIONS ///
	
	@Override
	public void onDisable() {
		try {
			this.webThread.stopThread();
			this.log.close();
			this.timedTask.cancel();
			this.DB.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void resetLink() {
		DB.setInstalled(false);
		DB.setKey("42");
		DB.setDomain("");
	}
	
	public void saveDB() {
		try {
			this.DB.save();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	public void restartWebServer() {
		try {
			webThread.stopThread();
			webThread = new WebThread();
			webThread.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public enum EnumMethod {
		isConnected(false),
		getPlayerList(false),
		getPlayerCount(false),
		getPlayerMax(false),
		getWhitelist(false),
		getMOTD(false),
		getOnlineMode(false),
		getVersion(false),
		getPlayersWhitelisted(false),
		getPlayersBanned(false),
		getPluginList(false),
		getRAMUsage(false),
		getBungeeServerList(false),
		getServerTimestamp(false),
		
		getPlayerSuffix(false),
		getPlayerPrefix(false),
		getPlayerGroup(false),
		getGroupList(false),
		
		getPlayerMoney(false),

		getPlayerFaction(false),
		getAllFactions(false),
		getFactionPlayers(false),
		getFactionPowers(false),
		getFactionMaxPowers(false),
		getFactionClaims(false),
		getFactionDescription(false),
		getFactionLeader(false),
		getFactionOfficers(false),

		sendPlayerTo(false),
		
		performCommand(true),
		performTimedCommand(true),
		setMineguard(true),
		removePlayerFromBanlist(true),
		removePlayerFromWhitelist(true),
		addPlayerToBanlist(true),
		addPlayerToWhitelist(true),
		readFile(false),
		;
		
		
		private boolean needKey;
		EnumMethod(boolean keyNeeded) {
			this.needKey = keyNeeded;
		}
		
		public boolean isKeyNeeded() {
			return needKey;
		}
	}
}
