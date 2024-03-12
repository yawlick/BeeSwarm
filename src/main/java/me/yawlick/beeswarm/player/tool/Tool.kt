package me.yawlick.beeswarm.player.tool

import me.yawlick.beeswarm.utils.ItemBuilder
import me.yawlick.beeswarm.utils.StumpType
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Item
import org.bukkit.inventory.ItemStack

enum class Tool(val realName: String, val displayName: String, val description: String, val price: Int, val stumpSize: Int, val stumpType: StumpType, val cooldown: Double, val power: Int, val itemStack: ItemStack) {
    Scooper(
        "Scooper",
        "§l§7Лопата",
        "§7§o« Лопата с дедушкиного сарая »",
        0,
        1,
        StumpType.SQUARE,
        0.8,
        2,
        ItemBuilder(Material.WOODEN_SHOVEL)
            .name("§l§7Лопата")
            .lore("§7§o« Лопата с дедушкиного сарая »")
            .enchantment(Enchantment.DIG_SPEED, 3)
            .make(),
    ),

    Rake(
        "Rake",
        "§l§7Грабли",
        "§7§o« Грабли с дедушкиного... подвала? »",
        800,
        1,
        StumpType.SQUARE,
        0.7,
        2,
        ItemBuilder(Material.WOODEN_SHOVEL)
            .name("§l§7Грабли")
            .lore("§7§o« Грабли с дедушкиного... подвала? »")
            .enchantment(Enchantment.DIG_SPEED, 3)
            .make(),
    ),

    Clippers(
        "Clippers",
        "§l§7Ножницы",
        "§7§o« Их точно придумали для этого?? »",
        2200,
        1,
        StumpType.SQUARE,
        0.6,
        9,
        ItemBuilder(Material.WOODEN_SHOVEL)
            .name("§l§7Ножницы")
            .lore("§7§o« Их точно придумали для этого?? »")
            .enchantment(Enchantment.DIG_SPEED, 3)
            .make(),
    ),

    Magnet(
        "Magnet",
        "§l§7Цветочный Магнит",
        "§7§o« я чё ебу почему тут магнит? в оригинале магнит же »",
        5500,
        1,
        StumpType.SQUARE,
        0.8,
        2,
        ItemBuilder(Material.WOODEN_SHOVEL)
            .name("§l§7Цветочный Магнит")
            .lore("§7§o« я чё ебу почему тут магнит? в оригинале магнит же »")
            .enchantment(Enchantment.DIG_SPEED, 3)
            .make(),
    ),

    Vacuum(
        "Vacuum",
        "§l§fПылесос",
        "§7§o« Весьма удобное изобретение! »",
        14000,
        2,
        StumpType.CIRCLE,
        0.8,
        2,
        ItemBuilder(Material.WOODEN_SHOVEL)
            .name("§l§fПылесос")
            .lore("§7§o« Весьма удобное изобретение! »")
            .enchantment(Enchantment.DIG_SPEED, 3)
            .make(),
    ),

    TidePopper(
        "Tide Popper",
        ChatColor.BLUE.toString() + "Tide Popper",
        ChatColor.AQUA.toString() + "Водяное наслаждение",
        500,
        6,
        StumpType.SQUARE,
        1.0,
        7,
        ItemBuilder(Material.WOODEN_SHOVEL)
            .name(ChatColor.BLUE.toString() + "Tide Popper")
            .lore(ChatColor.AQUA.toString() + "Водяное наслаждение")
            .enchantment(Enchantment.DIG_SPEED, 3)
            .make(),
    ),

    DarkScythe(
        "Dark Scythe",
        ChatColor.DARK_RED.toString() + "Dark Scythe",
        ChatColor.RED.toString() + "Выжигает поля огнём",
        1000,
        3,
        StumpType.CIRCLE,
        1.0,
        5,
        ItemBuilder(Material.DIAMOND_SWORD)
            .name(ChatColor.DARK_RED.toString() + "Dark Scythe")
            .lore(ChatColor.RED.toString() + "Выжигает поля огнём")
            .enchantment(Enchantment.DIG_SPEED, 3)
            .make(),
    )
}
