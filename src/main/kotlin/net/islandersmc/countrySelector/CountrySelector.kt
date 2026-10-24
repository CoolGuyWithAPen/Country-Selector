package net.islandersmc.countrySelector

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class CountrySelector : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        this.saveResource("config.yml", true)

        logger.info("Loading Country Selector...")

        getCommand("CountrySelect")?.setExecutor(Select(plugin = this))
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
