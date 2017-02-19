package fr.thisismac.mineweb.web;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import fr.thisismac.mineweb.Core;
import fr.thisismac.mineweb.Core.EnumMethod;

public class MethodHandler {
	
	public String getData(EnumMethod method, String target) {
		switch(method) {
			case getPlayerList : {
				if(!Core.get().getDB().isBungeeSupport()) {
					StringBuilder temp = new StringBuilder();
					for(Player p : Bukkit.getOnlinePlayers()) {
						temp.append(p.getName() + ", ");
					}
					return temp.toString().length() > 0 ? temp.toString().substring(0, temp.toString().length() - 2) : "none";
				}
				else {
					if(target.length() == 0 || target == null) {
						StringBuilder temp = new StringBuilder();
						for(Player p : Bukkit.getOnlinePlayers()) {
							temp.append(p.getName() + ", ");
						}
						return temp.toString().length() > 0 ? temp.toString().substring(0, temp.toString().length() - 2) : "none";
					}
					else if(!Core.get().getBungeeBridge().getServerList().contains(target)) {
						return "SERVER_DOESNT_EXIST";
					}
					else if(Core.get().getBungeeBridge().getBungeeServerList().get(target) == null || Core.get().getBungeeBridge().getBungeeServerList().get(target).isEmpty()){
						return "none";
					}
					else {
						return Core.get().getBungeeBridge().getBungeeServerList().get(target);
					}
				}
			}
			
			case getPlayerCount : {
				if(!Core.get().getDB().isBungeeSupport()) {
					return String.valueOf(Bukkit.getOnlinePlayers().size());
				}
				else {
					if(target.length() == 0) {
						return String.valueOf(Bukkit.getOnlinePlayers().size());
					}
					else if(!Core.get().getBungeeBridge().getServerList().contains(target)) {
						return "SERVER_DOESNT_EXIST";
					}
					else {
						return String.valueOf(Core.get().getBungeeBridge().getBungeeServerCount().get(target));
					}
				}
				
			}
			
			case getPlayerMax : {
				return String.valueOf(Bukkit.getMaxPlayers());
			}
			
			case isConnected : {
				return Bukkit.getPlayer(target) != null ? String.valueOf(true) : String.valueOf(false); 
			}
			
			case getWhitelist : {
				return String.valueOf(Bukkit.hasWhitelist());
			}
			
			case getMOTD : {
				return String.valueOf(Bukkit.getMotd());
			}
			
			case getOnlineMode : {
				return String.valueOf(Bukkit.getOnlineMode());
			}
			
			case getVersion : {
				String version = String.valueOf(Bukkit.getVersion()).split(": ")[1];
				return version.substring(0, version.length() - 1);
			}
			
			case getBungeeServerList : {
				if(Core.get().getDB().isBungeeSupport()) {
					StringBuilder temp = new StringBuilder();
					for(String server : Core.get().getBungeeBridge().getServerList()) {
						temp.append(server + ", ");
					}
					return temp.toString().length() > 0 ? temp.toString().substring(0, temp.toString().length() - 2) : "none";
				}
				else {
					return "BUNGEE_SUPPORT_DISABLED";
				}
			}
			
			case getPlayersWhitelisted : {
				StringBuilder temp = new StringBuilder();
				for(OfflinePlayer p : Bukkit.getWhitelistedPlayers()) {
					temp.append(p.getName() + ", ");
				}
				return temp.toString().length() > 0 ? temp.toString().substring(0, temp.toString().length() - 2) : "none";
			}
			
			case getPlayersBanned : {
				StringBuilder temp = new StringBuilder();
				for(OfflinePlayer p : Bukkit.getBannedPlayers()) {
					temp.append(p.getName() + ", ");
				}
				return temp.toString().length() > 0 ? temp.toString().substring(0, temp.toString().length() - 2) : "none";
			}
			
			case getRAMUsage : {
				return String.valueOf((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000);
			}
			
			case getPluginList : {
				StringBuilder temp = new StringBuilder();
				for(Plugin p : Bukkit.getPluginManager().getPlugins()) {
					temp.append(p.getName() + ", ");
				}
				return temp.toString().length() > 0 ? temp.toString().substring(0, temp.toString().length() - 2) : "none";
			}
			
			case getPlayerGroup : {
				return Core.get().getPermissionsBridge().getGroup(target);
			}
			
			case getGroupList : {
				return Core.get().getPermissionsBridge().getGroupList();
			}
			
			case getPlayerPrefix : {
				return Core.get().getPermissionsBridge().getPrefix(target);
			}
			
			case getPlayerSuffix : {
				return Core.get().getPermissionsBridge().getSuffix(target);
			}
			
			case getPlayerMoney : {
				return Core.get().getEconomyBridge().getBalance(target);
			}
			
			case getPlayerFaction : {
				return Core.get().getFactionsBridge().getPlayerFaction(target);
			}
			
			case getFactionPlayers : {
				return Core.get().getFactionsBridge().getFactionPlayers(target);
			}
			
			case getAllFactions : {
				return Core.get().getFactionsBridge().getAllFactions();
			}
			
			case getFactionClaims : {
				return Core.get().getFactionsBridge().getFactionClaims(target);
			}
			
			case getFactionPowers : {
				return Core.get().getFactionsBridge().getFactionPowers(target);
			}
			
			case getFactionMaxPowers : {
				return Core.get().getFactionsBridge().getFactionMaxPowers(target);
			}
			
			case getFactionDescription : {
				return Core.get().getFactionsBridge().getFactionDescription(target);
			}
			
			case getFactionLeader : {
				return Core.get().getFactionsBridge().getFactionLeader(target);
			}
			
			case getFactionOfficers : {
				return Core.get().getFactionsBridge().getFactionOfficers(target);
			}
			
			case getServerTimestamp : {
				return String.valueOf(System.currentTimeMillis());
			}
			case readFile : {
				StringBuilder buf = new StringBuilder();
				try {
					BufferedReader br = new BufferedReader(new FileReader(Core.get().getDataFolder() + File.separator + target));
					String tmp;
					while ((tmp = br.readLine()) != null) 
						buf.append(tmp);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				return buf.toString();
			}
		default:
			return null;
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public boolean performAction(EnumMethod method, String action) {
		switch(method) {
			case performCommand : {
				if(action.contains(":!:")) {
					String[] temp = action.split(":!:");
					if(Bukkit.getPlayer(temp[0]) != null) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), temp[1]);
					}
					else {
						Core.get().getDB().getPlayersCmds().add(action);
						Core.get().getLogger().info(temp[0] + " isnt connected, command will be executed when is will be connected");
					}
					return true;
				}
				else {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), action);
					return true;
				}
			}
			
			case performTimedCommand : {
				if(action.contains(":!:")) {
					String[] temp = action.split(":!:");
					Core.get().getDB().getTimedCmds().put(temp[0], temp[1]);
					return true;
				}
				else {
					return false;
				}
			}
			
			case setMineguard : {
				Core.get().getDB().setMineguard(Boolean.valueOf(action));
				Core.get().getLogger().info("Mineguard has been passed to " + Boolean.valueOf(action) +" by a web request");
				Core.get().saveDB();
				return true;
			}
			
			case sendPlayerTo : {
				if(Core.get().getDB().isBungeeSupport()) {
					String[] temp = action.split(":!:");
					Core.get().getBungeeBridge().sendPlayerTo(temp[0], temp[1]);
					return true;
				}
				else
					return false;
			}
			
			case removePlayerFromBanlist : {
				Core.get().getServer().getBannedPlayers().remove(Bukkit.getOfflinePlayer(action));
				return !Core.get().getServer().getBannedPlayers().contains(Bukkit.getOfflinePlayer(action));
			}
			
			case addPlayerToWhitelist : {
				Core.get().getServer().getWhitelistedPlayers().add(Bukkit.getOfflinePlayer(action));
				return Core.get().getServer().getWhitelistedPlayers().contains(Bukkit.getOfflinePlayer(action));
			}
			
			case addPlayerToBanlist : {
				Core.get().getServer().getBannedPlayers().add(Bukkit.getOfflinePlayer(action));
				return Core.get().getServer().getBannedPlayers().contains(Bukkit.getOfflinePlayer(action));
			}
			
			case removePlayerFromWhitelist : {
				Core.get().getServer().getWhitelistedPlayers().remove(Bukkit.getOfflinePlayer(action));
				return !Core.get().getServer().getWhitelistedPlayers().contains(Bukkit.getOfflinePlayer(action));
			}
		default:
			return false;
		}
	}
}
