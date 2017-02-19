package fr.thisismac.mineweb.bridge.version;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import fr.thisismac.mineweb.bridge.PermissionsBridge;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

public class PermissionVault implements PermissionsBridge{

	private Permission 	vaultPerms;
	private Chat 		vaultChat;
	
	public PermissionVault() {
		// Register permissions provider
		RegisteredServiceProvider<Permission> perm = Bukkit.getServicesManager().getRegistration(Permission.class);
		vaultPerms = perm.getProvider();
		
		// Register chat provirder
        RegisteredServiceProvider<Chat> chat = Bukkit.getServicesManager().getRegistration(Chat.class);
        vaultChat = chat == null ? null : chat.getProvider();
	}
	@Override
	public String getPrefix(String player) {
		return Bukkit.getPlayer(player) == null ? "PLAYER_NOT_CONNECTED" : vaultChat == null ? "VAULT_PROBLEM" : vaultChat.getPlayerPrefix(Bukkit.getPlayer(player));
	}

	@Override
	public String getSuffix(String player) {
		return Bukkit.getPlayer(player) == null ? "PLAYER_NOT_CONNECTED" : vaultChat == null ? "VAULT_PROBLEM" : vaultChat.getPlayerSuffix(Bukkit.getPlayer(player));
	}

	@Override
	public String getGroup(String player) {
		return Bukkit.getPlayer(player) == null ? "PLAYER_NOT_CONNECTED" : vaultPerms.getPrimaryGroup(Bukkit.getPlayer(player));
	}

	@Override
	public String getGroupList() {
		StringBuilder sb = new StringBuilder();
		for(String group : vaultPerms.getGroups()) 
			sb.append(group + ", ");
		return sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 2) : "none";
	}

}
