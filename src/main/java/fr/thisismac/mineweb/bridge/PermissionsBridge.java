package fr.thisismac.mineweb.bridge;

public interface PermissionsBridge {
	
	public String getPrefix(String player);
	
	public String getSuffix(String player);
	
	public String getGroup(String player);

	public String getGroupList();
	
}
