package fr.thisismac.mineweb.bridge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import fr.thisismac.mineweb.Core;

public class BungeeBridge implements PluginMessageListener {

	private String currentServer;
	private ArrayList<String> servers = new ArrayList<String>();
	private HashMap<String, Integer> serversCount = new HashMap<String, Integer>();
	private HashMap<String, String> serversList = new HashMap<String,String>();
	private int taskID = 0;
	
	public BungeeBridge() {
		Bukkit.getMessenger().registerOutgoingPluginChannel(Core.get(), "BungeeCord");
		Bukkit.getMessenger().registerIncomingPluginChannel(Core.get(), "BungeeCord", this);
		
		if(!Core.get().getDB().isBungeeSupport()) return;

		startQueryingData();
	}
	

	private void getCurrentServerName() {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("GetServer");
		
		Iterables.getFirst(Core.get().getServer().getOnlinePlayers(), null).sendPluginMessage(Core.get(), "BungeeCord", out.toByteArray());
	}


	public void sendPlayerTo(String player, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(server);
		Core.get().getServer().getPlayer(player).sendPluginMessage(Core.get(), "BungeeCord", out.toByteArray());
	}
	
	// Send Request Function
	public void getAllServers() {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("GetServers");
		Iterables.getFirst(Core.get().getServer().getOnlinePlayers(), null).sendPluginMessage(Core.get(), "BungeeCord", out.toByteArray());
	}
	
	private void getAllServerCount() {
		for(String server : servers) {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("PlayerCount");
			out.writeUTF(server);
			Iterables.getFirst(Core.get().getServer().getOnlinePlayers(), null).sendPluginMessage(Core.get(), "BungeeCord", out.toByteArray());
		}
	}
	
	private void getAllServerList() {
		for(String server : servers) {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("PlayerList");
			out.writeUTF(server);
			Iterables.getFirst(Core.get().getServer().getOnlinePlayers(), null).sendPluginMessage(Core.get(), "BungeeCord", out.toByteArray());
		}
	}
	
	public void startQueryingData() {
		taskID = Core.get().getServer().getScheduler().scheduleSyncRepeatingTask(Core.get(), new Runnable() {

			@Override
			public void run() {
				if(Core.get().getServer().getOnlinePlayers().size() > 0) {
					getAllServers();
					getCurrentServerName();
					getAllServerCount();
					getAllServerList();
				}
				
			}
			
		}, 0, 20 * 30);
	}
	
	public void stopQueryingData() {
		Core.get().getServer().getScheduler().cancelTask(taskID);
		taskID = 0;
	}


	@Override
	public void onPluginMessageReceived(String channel, Player p, byte[] data) {
		 if (!channel.equals("BungeeCord")) return;
		 
		 ByteArrayDataInput in = ByteStreams.newDataInput(data);
		 String channels = in.readUTF();
		 
		 if(channels.equals("GetServers")) {
			 String[] tempservers = in.readUTF().split(", ");
			 for(String s : tempservers) {
				 servers.add(s);
			 }
		 }
		 else if(channels.equals("PlayerCount")) {
			 String name = in.readUTF();
			 int count = in.readInt();
			 serversCount.put(name, count);
		 }
		 else if(channels.equals("PlayerList")) {
			 String name = in.readUTF();
			 String list = in.readUTF();
			 serversList.put(name, list);
		 }
		 else if(channels.equals("GetServer")) {
			 String servername = in.readUTF();
			 currentServer = servername;
			 
		 }
		  
	}
	
	
	public HashMap<String, Integer> getBungeeServerCount() {
		return serversCount;
	}
	
	public ArrayList<String> getServerList() {
		return servers;
	}
	
	public HashMap<String, String> getBungeeServerList() {
		return serversList;
	}
	
	public String getCurrentServer() {
		return currentServer;
	}

}
