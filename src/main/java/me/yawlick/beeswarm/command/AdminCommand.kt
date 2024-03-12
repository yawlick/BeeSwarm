package me.yawlick.beeswarm.command

import me.yawlick.beeswarm.player.PlayerExtension
import me.yawlick.beeswarm.player.tool.Tool
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AdminCommand : CommandExecutor, PlayerExtension() {
    override fun onCommand(sender: CommandSender, command: Command, s: String, args: Array<String>): Boolean {
        if(sender is Player) {
            val player = sender
            if(args.size > 1) {
                when(args[0].lowercase()) {
                    "settool" ->
                        for(tool in Tool.values()) {
                            if(tool.realName.equals(args[1], true)) {
                                player.setTool(tool)
                                break;
                            }
                        }
                    "set" ->
                        when(args[1].lowercase()) {
                            "honey" -> player.setHoney(args[2].toInt())
                            "capacity" -> player.setCapacity(args[2].toInt())
                            "pollen" -> player.setPollen(args[2].toInt())
                        }
                    else -> {}
                }
            }
        }

        return true
    }
}