package me.yawlick.beeswarm.player

import me.yawlick.beeswarm.BeeSwarm
import me.yawlick.beeswarm.player.statistic.Stats
import me.yawlick.beeswarm.player.tool.Tool
import org.bukkit.boss.BossBar

class PlayerData {
    var BSS: BeeSwarm = BeeSwarm().INSTANCE
    var playerStats: HashMap<Stats?, Any?>? = HashMap<Stats?, Any?>();
    var tool: Tool = Tool.DarkScythe
    var pollen: Int = 0
    var capacity: Int = 0
    var honey: Int = 0
    lateinit var bossBars: Array<BossBar?>

    fun setStats(stat: Stats, i: Any) {
        playerStats?.replace(stat, i)
    }

    fun getStats(stat: Stats): Any? {
        return playerStats?.get(stat)
    }
}
