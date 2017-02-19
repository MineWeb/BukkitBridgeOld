package fr.thisismac.mineweb.bridge.version;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.data.Group;
import org.bukkit.Bukkit;

import fr.thisismac.mineweb.bridge.PermissionsBridge;

public class PermissionGM implements PermissionsBridge{


    private GroupManager groupManager;
	
    public PermissionGM() {
    	groupManager = (GroupManager) Bukkit.getPluginManager().getPlugin("GroupManager");
    }
    
	@Override
	public String getPrefix(String player) {
		return Bukkit.getPlayer(player) == null ? "PLAYER_NOT_CONNECTED" : groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(player).getUserPrefix(player);
	}

	@Override
	public String getSuffix(String player) {
        return Bukkit.getPlayer(player) == null ? "PLAYER_NOT_CONNECTED" : groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(player).getUserSuffix(player);
	}

	@Override
	public String getGroup(String player) {
		return Bukkit.getPlayer(player) == null ? "PLAYER_NOT_CONNECTED" : groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(player).getGroup(player);
	}

	@Override
	public String getGroupList() {
		StringBuilder sb = new StringBuilder();
		for(Group group : GroupManager.getGlobalGroups().getGroupList())
			sb.append(group.getName() + ", ");
		return sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 2) : "none";
	}


}
