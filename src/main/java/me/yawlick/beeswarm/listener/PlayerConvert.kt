package me.yawlick.beeswarm.listener

import me.yawlick.beeswarm.BeeSwarm
import me.yawlick.beeswarm.player.BSSPlayer
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class PlayerConvert : Listener {
    var BSS: BeeSwarm? = BeeSwarm().INSTANCE

    @EventHandler
    fun onPlayerConvert(event: PlayerInteractEvent) {
        if (event.action == Action.PHYSICAL) {
            if (event.clickedBlock!!.type == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
                val plr = BSSPlayer(event.player)
                if (plr.data.pollen > 0) {
                    plr.convertPollen(plr.data.pollen)
                }
                event.isCancelled = true
            }
        }
    }
}
