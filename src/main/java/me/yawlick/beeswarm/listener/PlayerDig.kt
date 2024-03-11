package me.yawlick.beeswarm.listener

import me.yawlick.beeswarm.BeeSwarm
import me.yawlick.beeswarm.player.BSSPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class PlayerDig : Listener {
    var BSS: BeeSwarm? = BeeSwarm().INSTANCE

    @EventHandler
    fun onPlayerDig(event: PlayerInteractEvent) {
        val act = event.action
        if (act == Action.LEFT_CLICK_AIR || act == Action.RIGHT_CLICK_AIR || act == Action.LEFT_CLICK_BLOCK || act == Action.RIGHT_CLICK_BLOCK) {
            dig(BSSPlayer(event.player))
        }
    }

    @EventHandler
    fun onPlayerDig2(event: PlayerInteractAtEntityEvent) {
        dig(BSSPlayer(event.player))
    }

    fun dig(player: BSSPlayer) {
        val bukkitPlayer = player.player
        if (!bukkitPlayer!!.hasCooldown(bukkitPlayer.inventory.itemInMainHand.type)) {
            if (player.hasTool()) {
                bukkitPlayer.setCooldown(bukkitPlayer.inventory.itemInMainHand.type, ((20*player.data.tool.cooldown).toInt()))

                player.digFlowers()
            }
        }
    }
}
