package fr.thisismac.mineweb.bridge.version;


import fr.thisismac.mineweb.bridge.PermissionsBridge;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionEx implements PermissionsBridge {

	@Override
	public String getPrefix(String player) {
		return PermissionsEx.getUser(player).getPrefix();
	}

	@Override
	public String getSuffix(String player) {
		return PermissionsEx.getUser(player).getSuffix();
	}

	@Override
	public String getGroup(String player) {
		return PermissionsEx.getUser(player).getParentIdentifiers().get(0);
	}

	@Override
	public String getGroupList() {
		StringBuilder sb = new StringBuilder();
		for(PermissionGroup group : PermissionsEx.getPermissionManager().getGroupList())
			sb.append(group.getName() + ", ");
		return sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 2) : "none";
	}
}
