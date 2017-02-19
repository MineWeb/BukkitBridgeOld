package fr.thisismac.mineweb.bridge.version;

import fr.thisismac.mineweb.bridge.FactionsBridge;

public class FactionsNull implements FactionsBridge {

	@Override
	public String getPlayerFaction(String player) {
		return "PLUGIN_NOT_FOUND";
	}

	@Override
	public String getFactionPlayers(String faction) {
		return "PLUGIN_NOT_FOUND";
	}

	@Override
	public String getAllFactions() {
		return "PLUGIN_NOT_FOUND";
	}

	@Override
	public String getFactionClaims(String faction) {
		return "PLUGIN_NOT_FOUND";
	}

	@Override
	public String getFactionPowers(String faction) {
		return "PLUGIN_NOT_FOUND";
	}

	@Override
	public String getFactionMaxPowers(String faction) {
		return "PLUGIN_NOT_FOUND";
	}

	@Override
	public String getFactionDescription(String faction) {
		return "PLUGIN_NOT_FOUND";
	}

	@Override
	public String getFactionLeader(String faction) {
		return "PLUGIN_NOT_FOUND";
	}

	@Override
	public String getFactionOfficers(String faction) {
		return "PLUGIN_NOT_FOUND";
	}

}
