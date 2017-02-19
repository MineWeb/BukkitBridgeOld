package fr.thisismac.mineweb.bridge.version;

import fr.thisismac.mineweb.bridge.PermissionsBridge;

public class PermissionNull implements PermissionsBridge {

	@Override
	public String getPrefix(String player) {
		return "PLUGIN_NOT_FOUND";
	}

	@Override
	public String getSuffix(String player) {
		return "PLUGIN_NOT_FOUND";
	}

	@Override
	public String getGroup(String player) {
		return "PLUGIN_NOT_FOUND";
	}

	@Override
	public String getGroupList() {
		return "PLUGIN_NOT_FOUND";
	}

}
