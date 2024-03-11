package me.yawlick.beeswarm.player

import me.yawlick.beeswarm.BeeSwarm
import me.yawlick.beeswarm.player.statistic.Stats
import me.yawlick.beeswarm.player.tool.Tool
import net.kyori.adventure.bossbar.BossBar

class PlayerData {
    var BSS: BeeSwarm? = BeeSwarm.INSTANCE
    var stats: HashMap<Stats?, Any?>? = null
    var tool: Tool = Tool.DARK_SCYTHE
    var pollen: Int = 0
    var capacity: Int = 0
    var honey: Int = 0
    lateinit var bossBars: Array<BossBar?>

    fun setStats(stat: Stats?, i: Any) {
        stats?.replace(stat, i)
    }

    fun getStats(stat: Stats?): Any? {
        return stats?.get(stat)
    }
}
