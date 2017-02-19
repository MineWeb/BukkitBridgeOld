package fr.thisismac.mineweb.bridge;

public interface FactionsBridge {
	
	public String getPlayerFaction(String player);
	
	public String getFactionPlayers(String faction);
	
	public String getAllFactions();
	
	public String getFactionClaims(String faction);
	
	public String getFactionPowers(String faction);
	
	public String getFactionMaxPowers(String faction);

	public String getFactionDescription(String faction);
	
	public String getFactionLeader(String faction);
	
	public String getFactionOfficers(String faction);
	
	
}
