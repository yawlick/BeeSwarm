package me.yawlick.beeswarm.command

import me.yawlick.beeswarm.BeeSwarm
import me.yawlick.beeswarm.player.BSSPlayer
import me.yawlick.beeswarm.player.tool.Tool
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AdminCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, s: String, args: Array<String>): Boolean {
        if(sender is Player) {
            val player = BSSPlayer(sender)
            if(args.size > 1) {
                when(args[0]) {
                    "settool" ->
                        for(tool in Tool.values()) {
                            if(tool.realName.equals(args[1], true)) {
                                player.setTool(tool)
                            }
                            break;
                        }
                    else -> {}
                }
            }
        }

        return true
    }
}