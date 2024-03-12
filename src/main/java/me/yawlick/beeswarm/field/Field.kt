package me.yawlick.beeswarm.field

import me.yawlick.beeswarm.BeeSwarm
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import java.util.*

class Field internal constructor(
    var first: Location, // Первый угол поля
    var second: Location, // Противоположный угол поля
    var flowersPercent: Array<Int>, var name: String // Кол-во цветов на поле в процентном соотношении
) {

    val redFlowers: Int
        get() = flowersPercent[0] // 0

    val blueFlowers: Int
        get() = flowersPercent[1] // 1

    val whiteFlowers: Int
        get() = flowersPercent[2] // 2

    fun generateField() {
        var material = Material.MAGENTA_CONCRETE
        val random = Random()
        val totalFlowers = redFlowers + blueFlowers + whiteFlowers
        val minX = first.blockX
        val minY = first.blockY
        val minZ = first.blockZ
        val maxX = second.blockX
        val maxY = second.blockY
        val maxZ = second.blockZ

        for (X in minX until maxX + 1) {
            for (Y in minY until maxY + 1) {
                for (Z in minZ until maxZ + 1) {
                    val rand = random.nextInt(totalFlowers)
                    material = if (rand < redFlowers) {
                        Material.POPPY // КРАСНЫЙ
                    } else if (rand < redFlowers + blueFlowers) {
                        Material.CORNFLOWER // СИНИЙ
                    } else {
                        Material.OXEYE_DAISY // БЕЛЫЙ
                    }
                    //Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Поле: " + field.getName() + " | X: " + X + " | Y: " + Y + " | Z: " + Z);
                    val location = Location(Bukkit.getServer().getWorld("world"), X.toDouble(), Y.toDouble(), Z.toDouble())
                    val block = Bukkit.getServer().getWorld("world")!!.getBlockAt(location)
                    block.type = material
                    BeeSwarm.getInstance().flowers.add(block)
                    BeeSwarm.getInstance().flowersLocation.put(block.location, block)
                }
            }
        }
    }
}
