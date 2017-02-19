package fr.thisismac.mineweb.bridge.version;

import org.bukkit.Bukkit;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.util.IdUtil;

import fr.thisismac.mineweb.bridge.FactionsBridge;

public class Factions2_7 implements FactionsBridge{

	@Override
	public String getPlayerFaction(String player) {
		return Bukkit.getPlayer(player) == null ? "PLAYER_NOT_CONNECTED" : MPlayer.get(IdUtil.getId(player)).getFactionName();
	}

	@Override
	public String getFactionPlayers(String faction) {
		if(FactionColl.get().getByName(faction) == null) return "FACTION_DOESNT_EXIST";
		StringBuilder temp = new StringBuilder();
		for(MPlayer p : FactionColl.get().getByName(faction).getMPlayers()) {
			temp.append(p.getName() + ", ");
		}
		return temp.toString().length() > 0 ? temp.toString().substring(0, temp.toString().length() - 2) : "none";
	}

	@Override
	public String getAllFactions() {
		StringBuilder temp = new StringBuilder();
		for(Faction faction : FactionColl.get().getAll()) {
			if(!faction.isNone() && !faction.getId().equals(FactionColl.get().getWarzone().getId()) && !faction.getId().equals(FactionColl.get().getSafezone().getId()))
				temp.append(faction.getName() + ", ");
		}
		return temp.toString().length() > 0 ? temp.toString().substring(0, temp.toString().length() - 2) : "none";
	}

	@Override
	public String getFactionClaims(String faction) {
		Faction f = FactionColl.get().getByName(faction);
		if (f == null) 
			return "FACTION_DOESNT_EXIST";
		else
			return String.valueOf(f.getLandCount());
	}

	@Override
	public String getFactionPowers(String faction) {
		Faction f = FactionColl.get().getByName(faction);
		if (f == null) 
			return "FACTION_DOESNT_EXIST";
		else
			return String.valueOf(f.getPowerRounded());
	}

	@Override
	public String getFactionMaxPowers(String faction) {
		Faction f = FactionColl.get().getByName(faction);
		if (f == null) 
			return "FACTION_DOESNT_EXIST";
		else
			return String.valueOf(f.getPowerMaxRounded());
	}

	@Override
	public String getFactionDescription(String faction) {
		Faction f = FactionColl.get().getByName(faction);
		if (f == null) 
			return "FACTION_DOESNT_EXIST";
		else
			return String.valueOf(f.getDescription());
	}

	@Override
	public String getFactionLeader(String faction) {
		Faction f = FactionColl.get().getByName(faction);
		if (f == null) 
			return "FACTION_DOESNT_EXIST";
		else
			return String.valueOf(f.getLeader().getName());
	}

	@Override
	public String getFactionOfficers(String faction) {
		Faction f = FactionColl.get().getByName(faction);
		if(f == null) return "FACTION_DOESNT_EXIST";
		
		StringBuilder temp = new StringBuilder();
		for(MPlayer p : f.getMPlayersWhereRole(Rel.OFFICER)) {
			temp.append(p.getName() + ", ");
		}
		return temp.toString().length() > 0 ? temp.toString().substring(0, temp.toString().length() - 2) : "none";
	}

}
