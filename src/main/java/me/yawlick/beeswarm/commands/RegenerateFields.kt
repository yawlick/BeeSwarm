package me.yawlick.beeswarm.commands

import me.yawlick.beeswarm.BeeSwarm
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class RegenerateFields : CommandExecutor {
    override fun onCommand(commandSender: CommandSender, command: Command, s: String, strings: Array<String>): Boolean {
        BeeSwarm.INSTANCE!!.generateFields()
        return true
    }
}
