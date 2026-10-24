package net.islandersmc.countrySelector

import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.node.types.PrefixNode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin


fun setPrefix(player: Player, country: String?) {
    val luckPerms = LuckPermsProvider.get()
    val user = luckPerms.userManager.loadUser(player.uniqueId).join()
    val prefix = PrefixNode.builder("$country ", 50)
        .build()
    user.data().add(prefix)
    luckPerms.userManager.saveUser(user)
}

class Select(private val plugin : JavaPlugin) : CommandExecutor {
    private val countries = listOf("Canada", "France", "None")
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args : Array<out String>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("Only players can use this command.")
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("Usage: /CountrySelect <country>")
            return true
        }
        val country = args[0].lowercase()
        val countryData: String? = plugin.config.getString("countries.$country")
        val player : Player = sender
        setPrefix(player, countryData)
        sender.sendMessage("You set your country to Canada")
        return true
    }
}