package net.islandersmc.countrySelector

import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.node.types.PrefixNode
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.command.TabExecutor

//TODO: make <r> actually reset
//TODO: make None actually clear and not set space
//TODO: make sure it actually resets your prefix

fun setPrefix(player: Player, country: String?) {
    val luckPerms = LuckPermsProvider.get()
    val user = luckPerms.userManager.loadUser(player.uniqueId).join()
    val prefix = PrefixNode.builder("$country ", 50)
        .build()
    user.data().add(prefix)
    luckPerms.userManager.saveUser(user)
}

class Select(private val plugin : JavaPlugin) : TabExecutor {
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

        val country = args[0]
        if (country !in countries) {
            sender.sendMessage("Invalid country: $country")
            return true
        }

        val countryLowercase = country.lowercase()
        val countryData: String? = plugin.config.getString("countries.$countryLowercase")
        val player : Player = sender
        setPrefix(player, countryData)
        sender.sendMessage("You set your country to $country")
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String,args: Array<out String>)
        : List<String> {
        if (args.size == 1) {
            val typed = args[0]

            return countries
                .filter { it.startsWith(typed) }
                .sorted()
        }

        return emptyList()
    }
}