package me.yawlick.beeswarm.listener

import me.yawlick.beeswarm.BeeSwarm
import me.yawlick.beeswarm.player.statistic.Stats
import me.yawlick.beeswarm.player.tool.Tool
import me.yawlick.beeswarm.player.BSSPlayer
import me.yawlick.beeswarm.player.PlayerData
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.entity.Boss
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin : Listener {
    var BSS: BeeSwarm? = BeeSwarm().INSTANCE

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = BSSPlayer(event.player)
        val bukkitPlayer = player.player
        val data = PlayerData() // Тут должна быть загрузка даты с монго дб  /  создание даты для нового игрока, но монго дб не подклчюается
        BSS!!.playerData.put(bukkitPlayer.uniqueId, data)

        player.data.setStats(Stats.RED_POLLEN, 2500)
        player.data.setStats(Stats.BLUE_POLLEN, 2500)
        player.data.setStats(Stats.WHITE_POLLEN, 2500)
        player.data.setStats(Stats.POLLEN, 2500)
        player.data.setStats(Stats.WALK_SPEED, 300)
        player.data.setStats(Stats.JUMP_POWER, 100)

        player.data.tool = Tool.DarkScythe
        bukkitPlayer!!.inventory.setItemInMainHand(Tool.DarkScythe.itemStack)

        try {
            val speed: Int = player.getStat(Stats.WALK_SPEED) as Int
            bukkitPlayer.walkSpeed = 0.2f * (speed / 100)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        val honeyBossbar = Bukkit.createBossBar("§lМёд: §r" + ChatColor.GOLD + player.data.honey, BarColor.YELLOW, BarStyle.SOLID)
        honeyBossbar.progress = 1.0
        honeyBossbar.addPlayer(bukkitPlayer)

        val pollenBossbar = Bukkit.createBossBar("§lПыльца в контейнере: §r§6${player.data.pollen}§8§l/§r§7${player.data.capacity}", BarColor.WHITE, BarStyle.SOLID)
        pollenBossbar.addPlayer(bukkitPlayer)

        player.data.bossBars = arrayOf<org.bukkit.boss.BossBar?>(pollenBossbar, honeyBossbar)

        bukkitPlayer.sendMessage("Инструмент: " + player.data.tool.displayName)

        Bukkit.getScheduler().runTaskLater(BSS!!, Runnable {
            for (stat in Stats.entries) {
                bukkitPlayer.sendMessage(stat.displayName + "§r: " + player.getStat(stat))
            }
        }, 20L)
    }
}
