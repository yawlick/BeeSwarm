package me.yawlick.beeswarm.listener

import me.yawlick.beeswarm.BeeSwarm
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.FoodLevelChangeEvent

class DisabledEvents : Listener {
    var BSS: BeeSwarm? = BeeSwarm.INSTANCE

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onFoodChange(event: FoodLevelChangeEvent) {
        event.isCancelled = true
    }
}
