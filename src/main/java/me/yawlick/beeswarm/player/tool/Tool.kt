package me.yawlick.beeswarm.player.tool

import me.yawlick.beeswarm.utils.StumpType
import org.bukkit.ChatColor

enum class Tool(val realName: String, val displayName: String, val description: String, val price: Int, val stumpSize: Int, val stumpType: StumpType, val cooldown: Int, val power: Int) {
    TIDE_POPPER("Tide Popper", ChatColor.BLUE.toString() + "Tide Popper", ChatColor.AQUA.toString() + "Водяное наслаждение",
            500, 6, StumpType.SQUARE, 3, 7),
    DARK_SCYTHE("Dark Scythe", ChatColor.DARK_RED.toString() + "Dark Scythe", ChatColor.RED.toString() + "Выжигает поля огнём",
            1000, 3, StumpType.CIRCLE, 3, 5)
}
