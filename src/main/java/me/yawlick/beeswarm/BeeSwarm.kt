package me.yawlick.beeswarm

import me.yawlick.beeswarm.commands.RegenerateFields
import me.yawlick.beeswarm.player.tool.Tool
import me.yawlick.beeswarm.field.Fields
import me.yawlick.beeswarm.listener.DisabledEvents
import me.yawlick.beeswarm.listener.PlayerConvert
import me.yawlick.beeswarm.listener.PlayerDig
import me.yawlick.beeswarm.listener.PlayerJoin
import me.yawlick.beeswarm.mongodb.MongoConnection
import me.yawlick.beeswarm.utils.ItemBuilder
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class BeeSwarm : JavaPlugin() {
    var itemFromTool: HashMap<Tool, ItemStack> = HashMap()
    var toolFromItem: HashMap<ItemStack, Tool> = HashMap()
    var flowers: ArrayList<Block> = ArrayList()
    var flowersLocation: HashMap<Location, Block> = HashMap()

    override fun onEnable() {
        INSTANCE = this
        MongoConnection().connect()

        getCommand("regeneratefields")!!.setExecutor(RegenerateFields())
        generateFields()
        createTools()

        registerListeners(
                DisabledEvents(),
                PlayerJoin(),
                PlayerConvert(),
                PlayerDig()
        )
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

    fun createTools() {
        var itemStack = ItemBuilder(Material.DIAMOND_AXE)
                .name(Tool.TIDE_POPPER.displayName)
                .lore(Tool.TIDE_POPPER.description)
                .enchantment(Enchantment.WATER_WORKER, 5)
                .make()
        itemFromTool[Tool.TIDE_POPPER] = itemStack
        toolFromItem[itemStack] = Tool.TIDE_POPPER


        itemStack = ItemBuilder(Material.DIAMOND_SWORD)
                .name(Tool.DARK_SCYTHE.displayName)
                .lore(Tool.DARK_SCYTHE.description)
                .enchantment(Enchantment.FIRE_ASPECT, 5)
                .make()
        itemFromTool[Tool.DARK_SCYTHE] = itemStack
        toolFromItem[itemStack] = Tool.DARK_SCYTHE
    }

    fun generateFields() {
        flowers.clear()
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED.toString() + "Начало генерации полей..")
        for (obj in Fields.entries) {
            obj.field.generateField()
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED.toString() + "Поля сгенерированы")
    }

    companion object {
        @JvmField
        var INSTANCE: BeeSwarm? = null
    }
}