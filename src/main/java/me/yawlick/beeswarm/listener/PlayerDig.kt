package me.yawlick.beeswarm.listener

import me.yawlick.beeswarm.player.PlayerExtension
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class PlayerDig : Listener, PlayerExtension() {
    @EventHandler
    fun onPlayerDig(event: PlayerInteractEvent) {
        val act = event.action
        if (act == Action.LEFT_CLICK_AIR || act == Action.RIGHT_CLICK_AIR || act == Action.LEFT_CLICK_BLOCK || act == Action.RIGHT_CLICK_BLOCK) {
            dig(event.player)
        }
    }

    @EventHandler
    fun onPlayerDig2(event: PlayerInteractAtEntityEvent) {
        dig(event.player)
    }

    fun dig(player: Player) {
        val bukkitPlayer = player.player
        if (!bukkitPlayer!!.hasCooldown(bukkitPlayer.inventory.itemInMainHand.type)) {
            if (player.hasTool()) {
                bukkitPlayer.setCooldown(bukkitPlayer.inventory.itemInMainHand.type, ((20*player.getTool().cooldown).toInt()))

                player.digFlowers()
            }
        }
    }
}
