package me.yawlick.beeswarm.listener

import me.yawlick.beeswarm.BeeSwarm
import me.yawlick.beeswarm.player.statistic.Stats
import me.yawlick.beeswarm.player.tool.Tool
import me.yawlick.beeswarm.player.PlayerData
import me.yawlick.beeswarm.player.PlayerExtension
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin : Listener, PlayerExtension() {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val data = PlayerData() // Тут должна быть загрузка даты с монго дб  /  создание даты для нового игрока, но монго дб не подклчюается
        BeeSwarm.getInstance().playerData.put(player.uniqueId, data)

        player.setStat(Stats.RED_POLLEN, 2500)
        player.setStat(Stats.BLUE_POLLEN, 2500)
        player.setStat(Stats.WHITE_POLLEN, 2500)
        player.setStat(Stats.POLLEN, 2500)
        player.setStat(Stats.WALK_SPEED, 300)
        player.setStat(Stats.JUMP_POWER, 100)

        player.setTool(Tool.DarkScythe)

        try {
            val speed: Int = player.getStat(Stats.WALK_SPEED) as Int
            player.walkSpeed = 0.2f * (speed / 100)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        val honeyBossbar = Bukkit.createBossBar("§lМёд: §r" + ChatColor.GOLD + player.getHoney(), BarColor.YELLOW, BarStyle.SOLID)
        honeyBossbar.progress = 1.0
        honeyBossbar.addPlayer(player)

        val pollenBossbar = Bukkit.createBossBar("§lПыльца в контейнере: §r§6${player.getPollen()}§8§l/§r§7${player.getCapacity()}", BarColor.WHITE, BarStyle.SOLID)
        pollenBossbar.addPlayer(player)

        player.setStatsBossbars(arrayOf(pollenBossbar, honeyBossbar))

        player.sendMessage("Инструмент: " + player.getTool().displayName)

        Bukkit.getScheduler().runTaskLater(BeeSwarm.getInstance(), Runnable {
            for (stat in Stats.entries) {
                player.sendMessage(stat.displayName + "§r: " + player.getStat(stat))
            }
        }, 20L)
    }
}
