package me.yawlick.beeswarm.listener

import me.yawlick.beeswarm.player.PlayerExtension
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class PlayerConvert : Listener, PlayerExtension() {
    @EventHandler
    fun onPlayerConvert(event: PlayerInteractEvent) {
        if (event.action == Action.PHYSICAL) {
            if (event.clickedBlock!!.type == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
                val plr = event.player
                if (plr.getPollen() > 0) {
                    plr.convertPollen(plr.getPollen())
                }
                event.isCancelled = true
            }
        }
    }
}
