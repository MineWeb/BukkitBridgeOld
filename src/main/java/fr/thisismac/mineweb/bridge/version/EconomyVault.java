package fr.thisismac.mineweb.bridge.version;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import fr.thisismac.mineweb.bridge.EconomyBridge;
import net.milkbowl.vault.economy.Economy;

public class EconomyVault implements EconomyBridge {

	private Economy vaultEco;
	
	public EconomyVault() {
		// Register chat provirder
        RegisteredServiceProvider<Economy> eco = Bukkit.getServicesManager().getRegistration(Economy.class);
        vaultEco = eco == null ? null : eco.getProvider();
	}
	
	@Override
	public String getBalance(String player) {
		return Bukkit.getPlayer(player) == null ? "PLAYER_NOT_CONNECTED" : vaultEco == null ? "VAULT_PROBLEM" : String.valueOf(vaultEco.getBalance(Bukkit.getPlayer(player)));
	}

}
