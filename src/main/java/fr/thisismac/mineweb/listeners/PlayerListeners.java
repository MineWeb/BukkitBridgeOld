package fr.thisismac.mineweb.listeners;

import java.util.Iterator;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import fr.thisismac.mineweb.Core;

public class PlayerListeners implements Listener{

	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent e) {
		if(e.getPlayer().isOp() && Core.get().getUpdater().isNeedUpdate()) {
			e.getPlayer().sendMessage(ChatColor.DARK_RED + "------------------------------");
			e.getPlayer().sendMessage(ChatColor.GOLD + "A update for mineweb bukkit is avaible");
			e.getPlayer().sendMessage(ChatColor.GOLD + "Une update du plugin mineweb  bukkit est disponible");
			e.getPlayer().sendMessage(ChatColor.DARK_RED + "-----------------------------");
			return;
		}
		
		Iterator<String> it = Core.get().getDB().getPlayersCmds().iterator();
		while(it.hasNext()) {
			String[] entry = it.next().split(":!:");
			if (entry[0].equalsIgnoreCase(e.getPlayer().getName())) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), entry[1]);
				it.remove();
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onLogin(PlayerLoginEvent e) {
		if(!Core.get().getDB().isMineguard()) return;
	
		switch(MineguardResponse.valueOf(Core.get().getGuardHandler().sendMineGuardRequest(e.getPlayer().getName(), e.getAddress().getHostAddress()).get("result").getAsString())) {
			default : {
				Core.get().getLogger().log(Level.SEVERE, "There is some problem with Mineguard, he might be disable");
				Core.get().getDB().setMineguard(false);
				break ;
			}
			case UNKNOWN_USER : {
				e.setResult(Result.ALLOWED);
				break ;
			}
			case NOT_ALLOWED : {
				e.setResult(Result.KICK_OTHER);
				e.setKickMessage(ChatColor.RED + "You are not allowed to connect on this account (MineGuard) !");
				break ;
			}
			case SUCCESS : {
				e.setResult(Result.ALLOWED);
				break ;
			}
		}
	}
	
	public enum MineguardResponse {
		UNKNOWN_USER,
		NOT_ALLOWED,
		SUCCESS;
	}
}
