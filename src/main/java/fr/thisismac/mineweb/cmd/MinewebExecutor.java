package fr.thisismac.mineweb.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.thisismac.mineweb.Core;

public class MinewebExecutor implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if(!cmd.getName().equals("mineweb")) return true;
		if(!sender.isOp()) return true;
		if(args.length == 0 || args == null) return true;
		
		if(args[0].equalsIgnoreCase("setup")) {
			Core.get().resetLink();
			sender.sendMessage(ChatColor.GREEN + "Mineweb bridge is ready to be linked with a website.");
			
		}
		else if(args[0].equalsIgnoreCase("port")) {
			if(args.length == 1) {
				sender.sendMessage(ChatColor.RED + "You must indicate the port that you wanna use (ex : /mineweb port 8080)");
				return true;
			}
			Core.get().getDB().setPort(Integer.valueOf(args[1]));
			Core.get().restartWebServer();
			if (Core.get().getWebThread().getWebServer().isFailed()) {
				sender.sendMessage(ChatColor.RED + "Sorry but the port " + args[1] + " is already in use/you cant use it, try another one.");
			}
			else {
				sender.sendMessage(ChatColor.GREEN + "The plugin will now listen for request on port " + args[1]);
				Core.get().saveDB();
			}
		}
		else if(args[0].equalsIgnoreCase("bungee")) {
			if(args.length == 1) {
				sender.sendMessage(ChatColor.RED + "You must precise if you want to enable or disable the bungee bridge");
				sender.sendMessage(ChatColor.RED + " > Example : /mineweb bungee enable OR /mineweb bungee disable");
				return true;
			}
			else if(args[1].equals("enable")) {
				Core.get().getDB().setBungeeSupport(true);
				Core.get().saveDB();
				Core.get().getBungeeBridge().getAllServers();
				Core.get().getBungeeBridge().startQueryingData();
				sender.sendMessage(ChatColor.GREEN + "You have enabled the bungeecord support.");
			}
			else if(args[1].equals("disable")) {
				Core.get().getDB().setBungeeSupport(false);
				Core.get().saveDB();
				Core.get().getBungeeBridge().stopQueryingData();
				Core.get().getBungeeBridge().getServerList().clear();
				Core.get().getBungeeBridge().getBungeeServerCount().clear();
				Core.get().getBungeeBridge().getBungeeServerList().clear();
				sender.sendMessage(ChatColor.GREEN + "You have disabled the bungeecord support.");
			}
		}
		
		return false;
	}
}
