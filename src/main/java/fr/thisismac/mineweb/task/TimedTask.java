package fr.thisismac.mineweb.task;

import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import fr.thisismac.mineweb.Core;

public class TimedTask extends BukkitRunnable {

	@Override
	public void run() {
		Iterator<Entry<String, String>> it = Core.get().getDB().getTimedCmds().entrySet().iterator();
		
		while(it.hasNext()) {
			Entry<String, String> entry = it.next();
			
			if(System.currentTimeMillis() >= Long.valueOf(entry.getKey())) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), entry.getValue());
				it.remove();
			}
		}
	}

}
