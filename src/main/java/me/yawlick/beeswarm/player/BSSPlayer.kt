package me.yawlick.beeswarm.player

import me.yawlick.beeswarm.BeeSwarm
import me.yawlick.beeswarm.player.statistic.Stats
import me.yawlick.beeswarm.utils.StumpsHelper
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.entity.Player

class BSSPlayer(val player: Player) {
    val data: PlayerData = PlayerData()
    var BSS: BeeSwarm? = BeeSwarm.INSTANCE

    fun setData(): PlayerData {
        return data
    }

    fun updateClient() {
        var bar = pollenBossbar
        bar!!.name(Component.text("§lПыльца в контейнере: §r" + ChatColor.GOLD + data.pollen + ChatColor.DARK_GRAY + "§l/§r"
                + ChatColor.GRAY + data.capacity))
        bar.progress(data.pollen.toFloat() / data.capacity)

        bar = honeyBossbar
        bar!!.name(Component.text("§lМёд: §r" + ChatColor.GOLD + data.honey))
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

    fun getStat(stats: Stats?): Any? {
        return data.stats!![stats]
    }

    fun hasTool(): Boolean {
        if (BSS!!.toolFromItem.containsKey(player.inventory.itemInMainHand)) {
            return true
        }
        return false
    }
}
