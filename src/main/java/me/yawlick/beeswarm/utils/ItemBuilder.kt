package me.yawlick.beeswarm.utils

import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.material.MaterialData
import java.util.*

class ItemBuilder {
    private val item: ItemStack
    private val itemM: ItemMeta?

    /**
     *
     * Короче только этот атеймбилдер не написан мной,
     * но насколько знаю, на кристе используется такой же (или использовался)
     *
     */
    constructor(itemType: Material?) {
        item = ItemStack(itemType!!)
        itemM = item.itemMeta
    }

    constructor(itemStack: ItemStack) {
        item = itemStack
        itemM = item.itemMeta
    }

    constructor() {
        item = ItemStack(Material.AIR)
        itemM = item.itemMeta
    }

    fun type(material: Material?): ItemBuilder {
        make().type = material!!
        return this
    }

    fun amount(itemAmt: Int?): ItemBuilder {
        make().amount = itemAmt!!
        return this
    }

    fun name(name: String?): ItemBuilder {
        meta()!!.setDisplayName(name)
        make().setItemMeta(meta())
        return this
    }

    fun lore(lore: String?): ItemBuilder {
        var lores = meta()!!.lore
        if (lores == null) {
            lores = ArrayList()
        }
        lores.add(lore)
        meta()!!.lore = lores
        make().setItemMeta(meta())
        return this
    }

    fun lores(lores: Array<String?>): ItemBuilder {
        var loresList = meta()!!.lore
        if (loresList == null) {
            loresList = ArrayList()
        } else {
            loresList.clear()
        }
        Collections.addAll(loresList, *lores)
        meta()!!.lore = loresList
        return this
    }

    fun durability(durability: Int): ItemBuilder {
        make().durability = durability.toShort()
        return this
    }

    @Suppress("deprecation")
    fun data(data: Int): ItemBuilder {
        make().data = MaterialData(make().type, data.toByte())
        return this
    }

    fun enchantment(enchantment: Enchantment?, level: Int): ItemBuilder {
        make().addUnsafeEnchantment(enchantment!!, level)
        return this
    }

    fun enchantment(enchantment: Enchantment?): ItemBuilder {
        make().addUnsafeEnchantment(enchantment!!, 1)
        return this
    }

    fun enchantments(enchantments: Array<Enchantment?>, level: Int): ItemBuilder {
        make().enchantments.clear()
        for (enchantment in enchantments) {
            make().addUnsafeEnchantment(enchantment!!, level)
        }
        return this
    }

    fun enchantments(enchantments: Array<Enchantment?>): ItemBuilder {
        make().enchantments.clear()
        for (enchantment in enchantments) {
            make().addUnsafeEnchantment(enchantment!!, 1)
        }
        return this
    }

    fun clearEnchantment(enchantment: Enchantment): ItemBuilder {
        val itemEnchantments = make().enchantments
        for (enchantmentC in itemEnchantments.keys) {
            if (enchantment === enchantmentC) {
                itemEnchantments.remove(enchantmentC)
            }
        }
        return this
    }

    fun clearEnchantments(): ItemBuilder {
        make().enchantments.clear()
        return this
    }

    fun clearLore(lore: String?): ItemBuilder {
        if (meta()!!.lore!!.contains(lore)) {
            meta()!!.lore!!.remove(lore)
        }
        make().setItemMeta(meta())
        return this
    }

    fun clearLores(): ItemBuilder {
        meta()!!.lore!!.clear()
        make().setItemMeta(meta())
        return this
    }

    fun color(color: Color?): ItemBuilder {
        if (make().type == Material.LEATHER_HELMET || make().type == Material.LEATHER_CHESTPLATE || make().type == Material.LEATHER_LEGGINGS || make().type == Material.LEATHER_BOOTS) {
            val meta = meta() as LeatherArmorMeta?
            meta!!.setColor(color)
            make().setItemMeta(meta)
        }
        return this
    }

    fun clearColor(): ItemBuilder {
        if (make().type == Material.LEATHER_HELMET || make().type == Material.LEATHER_CHESTPLATE || make().type == Material.LEATHER_LEGGINGS || make().type == Material.LEATHER_BOOTS) {
            val meta = meta() as LeatherArmorMeta?
            meta!!.setColor(null)
            make().setItemMeta(meta)
        }
        return this
    }

    fun skullOwner(name: String?): ItemBuilder {
        if (make().type == Material.PLAYER_HEAD && make().durability == 3.toByte().toShort()) {
            val skullMeta = meta() as SkullMeta?
            skullMeta!!.setOwner(name)
            make().setItemMeta(meta())
        }
        return this
    }

    fun meta(): ItemMeta? {
        return itemM
    }

    fun make(): ItemStack {
        return item
    }
}
