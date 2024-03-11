package me.yawlick.beeswarm.listener

import me.yawlick.beeswarm.BeeSwarm
import me.yawlick.beeswarm.player.statistic.Stats
import me.yawlick.beeswarm.player.tool.Tool
import me.yawlick.beeswarm.player.BSSPlayer
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin : Listener {
    var BSS: BeeSwarm? = BeeSwarm.INSTANCE

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = BSSPlayer(event.player)
        val bukkitPlayer = player.player

        player.data.tool = Tool.DARK_SCYTHE
        bukkitPlayer!!.inventory.setItemInMainHand(BSS!!.itemFromTool[Tool.DARK_SCYTHE])

        // Пока что без привязки к монго дб
        player.data.setStats(Stats.RED_POLLEN, 2500)
        player.data.setStats(Stats.BLUE_POLLEN, 2500)
        player.data.setStats(Stats.WHITE_POLLEN, 2500)
        player.data.setStats(Stats.POLLEN, 2500)
        player.data.setStats(Stats.WALK_SPEED, 300)
        player.data.setStats(Stats.JUMP_POWER, 100)

        bukkitPlayer.walkSpeed = 0.2f * (player.getStat(Stats.WALK_SPEED) as Int / 100)

        val honeyBossbar = BossBar.bossBar(Component.text("§lМёд: §r" + ChatColor.GOLD + player.data.honey), 1.0f, BossBar.Color.YELLOW, BossBar.Overlay.PROGRESS)
        val honey = Bukkit.createBossBar("§lМёд: §r" + ChatColor.GOLD + player.data.honey, BarColor.YELLOW, BarStyle.SOLID)
        honey.progress = 1.0
        honey.addPlayer(bukkitPlayer)

        val pollenBossbar = BossBar.bossBar(Component.text("§lПыльца в контейнере: §r" + ChatColor.GOLD + player.data.pollen + ChatColor.DARK_GRAY + "§l/§r"
                + ChatColor.GRAY + player.data.capacity), 0.0f, BossBar.Color.WHITE, BossBar.Overlay.PROGRESS)
        pollenBossbar.addViewer(Audience.audience())

        player.data.bossBars = arrayOf<BossBar?>(pollenBossbar, honeyBossbar)

        bukkitPlayer.sendMessage("Инструмент: " + player.data.tool.displayName)

        Bukkit.getScheduler().runTaskLater(BSS!!, Runnable {
            for (stat in Stats.entries) {
                bukkitPlayer.sendMessage(stat.displayName + "§r: " + player.getStat(stat))
            }
        }, 20L)
    }
}
