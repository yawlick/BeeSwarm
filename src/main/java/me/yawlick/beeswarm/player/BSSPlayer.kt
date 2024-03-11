package me.yawlick.beeswarm.player

import me.yawlick.beeswarm.BeeSwarm
import me.yawlick.beeswarm.player.statistic.Stats
import me.yawlick.beeswarm.player.tool.Tool
import me.yawlick.beeswarm.utils.StumpsHelper
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player

class BSSPlayer(val player: Player) {
    var BSS: BeeSwarm? = BeeSwarm().INSTANCE
    var data: PlayerData = BSS!!.playerData.get(player.uniqueId)!!

    fun updateClient() {
        var bar = pollenBossbar
        bar!!.setTitle("§lПыльца в контейнере: §r§6${data.pollen}§8§l/§r§7${data.capacity}")
        bar!!.progress = (data.pollen.toFloat() / data.capacity).toDouble()
        bar!!.isVisible = true

        bar = honeyBossbar
        bar!!.setTitle("§lМёд: §r" + ChatColor.GOLD + data.honey)
        bar!!.isVisible = true
    }

    fun digFlowers() {
        if (hasTool()) {
            if (data.pollen >= data.capacity) {
                player.sendMessage(ChatColor.RED.toString() + "§l У вас полный контейнер!§f §7(Конвертируйте пыльцу в мёд у вашего улья)")
                player.playSound(player.location, Sound.ENTITY_VILLAGER_NO, 1f, 1f)
                return
            }

            val pollen = data.pollen
            val newpollen = StumpsHelper().checkFlowers(data.tool, player)
            var summa = pollen + newpollen
            if (summa > data.capacity) {
                summa = data.capacity
            }
            data.pollen = summa
            updateClient()
        }
    }

    fun convertPollen(pollen: Int) {
        var pollen = pollen
        if (data.pollen < pollen) {
            pollen = data.pollen
        }
        data.pollen = data.pollen - pollen
        data.honey = data.honey  + pollen
        player.playSound(player.location, Sound.ENTITY_VILLAGER_YES, 1f, 1f)
        updateClient()
    }

    val pollenBossbar: BossBar?
        get() = data.bossBars[0]

    val honeyBossbar: BossBar?
        get() = data.bossBars[1]

    fun getStat(statName: Stats?): Any? {
        val statValue: Any? = data.playerStats?.get(statName)
        return statValue
    }

    fun hasTool(): Boolean {
        if (BSS!!.toolFromItem.containsKey(player.inventory.itemInMainHand)) {
            return true
        }
        return false
    }

    fun setTool(tool: Tool) {
        data.tool = tool
        player.setItemInHand(tool.itemStack)
        player.sendMessage("§l§aВы одели палку : ${tool.displayName}")
    }
}
