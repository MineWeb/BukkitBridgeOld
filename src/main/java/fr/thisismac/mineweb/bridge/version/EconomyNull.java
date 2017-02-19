package fr.thisismac.mineweb.bridge.version;

import fr.thisismac.mineweb.bridge.EconomyBridge;

public class EconomyNull implements EconomyBridge{

	@Override
	public String getBalance(String player) {
		return "PLUGIN_NOT_FOUND";
	}

}
