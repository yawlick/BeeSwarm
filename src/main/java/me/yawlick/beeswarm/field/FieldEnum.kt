package me.yawlick.beeswarm.field

import org.bukkit.Bukkit
import org.bukkit.Location

enum class FieldEnum(val field: Field) {
    //SUNFLOWER(0,1,-31, 32,1,-18, "Sunflower", 25, 25, 50),
    SUNFLOWER(Field(
            Location(Bukkit.getWorld("world"), 0.0, 1.0, -31.0),
            Location(Bukkit.getWorld("world"), 32.0, 1.0, -18.0),
            arrayOf(25, 25, 50),  // Красные, синие и белые цветки в процентах
            "Sunflower"
    )),

    //DANDELION(0,1,8, 32,1,21, "Dandelion", 10, 10, 80),
    DANDELION(Field(
            Location(Bukkit.getWorld("world"), 0.0, 1.0, 8.0),
            Location(Bukkit.getWorld("world"), 32.0, 1.0, 21.0),
            arrayOf(10, 10, 80),  // Красные, синие и белые цветки в процентах
            "Dandelion"
    )),

    //BALANCEFIELD(0,1,-12,32,1,2,"BalanceField", 33, 33, 34),
    BALANCEFIELD(Field(
            Location(Bukkit.getWorld("world"), 0.0, 1.0, -12.0),
            Location(Bukkit.getWorld("world"), 32.0, 1.0, 2.0),
            arrayOf(33, 33, 34),  // Красные, синие и белые цветки в процентах
            "Balance Field"
    )),

    //REDFIELD(38,1,-31, 70,1,-18, "RedField", 100, 0, 0),
    REDFIELD(Field(
            Location(Bukkit.getWorld("world"), 38.0, 1.0, -31.0),
            Location(Bukkit.getWorld("world"), 70.0, 1.0, -18.0),
            arrayOf(100, 0, 0),  // Красные, синие и белые цветки в процентах
            "Red Field"
    )),

    //BLUEFIELD(38,1,-12, 70,1,2, "BlueField", 0, 100, 0),
    BLUEFIELD(Field(
            Location(Bukkit.getWorld("world"), 38.0, 1.0, -12.0),
            Location(Bukkit.getWorld("world"), 70.0, 1.0, 2.0),
            arrayOf(0, 100, 0),  // Красные, синие и белые цветки в процентах
            "Blue Field"
    )),

    //WHITEFIELD(38,1,8, 70,1,21, "WhiteField", 0, 0, 100),
    WHITEFIELD(Field(
            Location(Bukkit.getWorld("world"), 38.0, 1.0, 8.0),
            Location(Bukkit.getWorld("world"), 70.0, 1.0, 21.0),
            arrayOf(0, 0, 100),  // Красные, синие и белые цветки в процентах
            "White Field"
    )) ///////////
}
