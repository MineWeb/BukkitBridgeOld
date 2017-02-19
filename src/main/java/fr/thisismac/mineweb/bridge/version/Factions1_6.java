package fr.thisismac.mineweb.bridge.version;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.struct.Role;

import fr.thisismac.mineweb.bridge.FactionsBridge;

public class Factions1_6 implements FactionsBridge{

	@Override
	public String getPlayerFaction(String player) {
		Player p = Bukkit.getPlayer(player);
		if(p == null) return "PLAYER_NOT_CONNECTED";
		String tag = FPlayers.i.get(p).getTag();
		return tag.equals("") ? "Wilderness" : tag;
	}

	@Override
	public String getFactionPlayers(String faction) {
		Faction f = Factions.i.getByTag(faction);
		if(f == null) return "FACTION_DOESNT_EXIST";
		StringBuilder temp = new StringBuilder();
		for(FPlayer p : f.getFPlayers()) {
			temp.append(p.getName() + ", ");
		}
		return temp.toString().length() > 0 ? temp.toString().substring(0, temp.toString().length() - 2) : "none";
	}

	@Override
	public String getAllFactions() {
		StringBuilder temp = new StringBuilder();
		for(Entry<String, Faction> f : Factions.i.getMap().entrySet()) {
			if(f.getValue().getTag().equals("§2Wilderness") || f.getValue().getTag().equals("Warzone") || f.getValue().getTag().equals("SafeZone")) {}
			temp.append(f.getValue().getTag() + ", ");
		}
		return temp.toString().length() > 0 ? temp.toString().substring(0, temp.toString().length() - 2) : "none";
	}

	@Override
	public String getFactionClaims(String faction) {
		return Factions.i.getByTag(faction) == null ? "FACTION_DOESNT_EXIST" : String.valueOf(Factions.i.getByTag(faction).getLandRounded());
	}

	@Override
	public String getFactionPowers(String faction) {
		return Factions.i.getByTag(faction) == null ? "FACTION_DOESNT_EXIST" : String.valueOf(Factions.i.getByTag(faction).getPowerRounded());
	}

	@Override
	public String getFactionMaxPowers(String faction) {
		return Factions.i.getByTag(faction) == null ? "FACTION_DOESNT_EXIST" : String.valueOf(Factions.i.getByTag(faction).getPowerMaxRounded());
	}

	@Override
	public String getFactionDescription(String faction) {
		return Factions.i.getByTag(faction) == null ? "FACTION_DOESNT_EXIST" : Factions.i.getByTag(faction).getDescription();
	}

	@Override
	public String getFactionLeader(String faction) {
		return Factions.i.getByTag(faction) == null ? "FACTION_DOESNT_EXIST" : Factions.i.getByTag(faction).getFPlayerAdmin().getName();
	}

	@Override
	public String getFactionOfficers(String faction) {
		if(Factions.i.getByTag(faction) == null) return "FACTION_DOESNT_EXIST";
		StringBuilder temp = new StringBuilder();
		for(FPlayer p : Factions.i.getByTag(faction).getFPlayersWhereRole(Role.MODERATOR)) {
			temp.append(p.getName() + ", ");
		}
		return temp.toString().length() > 0 ? temp.toString().substring(0, temp.toString().length() - 2) : "none";
	}

}
