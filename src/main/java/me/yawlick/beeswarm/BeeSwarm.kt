package me.yawlick.beeswarm

import me.yawlick.beeswarm.command.AdminCommand
import me.yawlick.beeswarm.command.RegenerateFields
import me.yawlick.beeswarm.player.tool.Tool
import me.yawlick.beeswarm.field.FieldEnum
import me.yawlick.beeswarm.listener.DisabledEvents
import me.yawlick.beeswarm.listener.PlayerConvert
import me.yawlick.beeswarm.listener.PlayerDig
import me.yawlick.beeswarm.listener.PlayerJoin
import me.yawlick.beeswarm.player.PlayerData
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.EntityType
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.ArrayList
import java.util.UUID

class BeeSwarm : JavaPlugin() {
    var INSTANCE: BeeSwarm = this;
    var itemFromTool: HashMap<Tool, ItemStack> = HashMap()
    var toolFromItem: HashMap<ItemStack, Tool> = HashMap()
    var flowers: ArrayList<Block> = ArrayList()
    var flowersLocation: java.util.HashMap<Location, Block> = java.util.HashMap()
    var playerData: HashMap<UUID, PlayerData> = HashMap()

    override fun onEnable() {
        INSTANCE = this;
        getCommand("regeneratefields")!!.setExecutor(RegenerateFields())
        getCommand("admin")!!.setExecutor(AdminCommand())

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, Runnable {
            fun run() {
                generateFields()
            }
        }, 40)

        registerListeners(
                DisabledEvents(),
                PlayerJoin(),
                PlayerConvert(),
                PlayerDig()
        )

        for(tool: Tool in Tool.values()) {
            itemFromTool.put(tool, tool.itemStack)
            toolFromItem.put(tool.itemStack, tool)
        }
    }

    fun registerListeners(vararg listeners: Listener?) {
        val pluginManager = Bukkit.getServer().pluginManager

        for (listener in listeners) {
            pluginManager.registerEvents(listener!!, this)
        }
    }

    override fun onDisable() {
        for (world in server.worlds) {
            for (ent in world.entities) {
                if (ent.type == EntityType.ARMOR_STAND) {
                    ent.remove()
                }
            }
        }
    }

    fun generateFields() {
        flowers.clear()
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED.toString() + "Начало генерации полей..")
        for (v in FieldEnum.values()) {
            v.field.generateField()
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED.toString() + "Поля сгенерированы")
    }
}