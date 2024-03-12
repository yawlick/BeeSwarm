package me.yawlick.beeswarm.utils

import me.yawlick.beeswarm.BeeSwarm
import me.yawlick.beeswarm.player.statistic.Stats
import me.yawlick.beeswarm.player.PlayerExtension
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

class StumpsHelper : PlayerExtension() {
    var BSS: BeeSwarm = BeeSwarm.getInstance()
    var flowers: ArrayList<Block> = BSS.flowers;

    fun checkFlowers(plr: Player): Int {
        val tool = plr.getTool()
        val size = tool.stumpSize
        val loc = plr.location
        val minX = loc.blockX - size
        val minY = loc.blockY - size
        val minZ = loc.blockZ - size
        val maxX = loc.blockX + size
        val maxY = loc.blockY + size
        val maxZ = loc.blockZ + size
        var pollen = 0
        var redPollen = 0
        var bluePollen = 0
        var whitePollen = 0
        val a = plr.location.clone().add(0.0, 0.5, 0.0)
        val b = plr.location.clone().add(0.0, 1.0, 0.0)
        val c = plr.location.clone().add(0.0, 1.5, 0.0)
        val stands = ArrayList<ArmorStand>()
        if (tool.stumpType == StumpType.SQUARE) {
            for (X in minX until maxX + 1) {
                for (Y in minY until maxY + 1) {
                    for (Z in minZ until maxZ + 1) {
                        val location = Location(plr.world, X.toDouble(), Y.toDouble(), Z.toDouble())
                        val block = plr.world.getBlockAt(location)
                        val flower = block.blockData.clone()
                        val color = block.type
                        if (block.type == Material.AIR) {
                            continue
                        }

                        if (flowers.contains(block)) {
                            flowers.remove(block)

                            if (color == Material.POPPY) {
                                redPollen += tool.power * ((plr.getStat(Stats.RED_POLLEN) as Int + plr.getStat(Stats.POLLEN) as Int) / 100)
                            }
                            if (color == Material.CORNFLOWER) {
                                bluePollen += tool.power * ((plr.getStat(Stats.BLUE_POLLEN) as Int + plr.getStat(Stats.POLLEN) as Int) / 100)
                            }
                            if (color == Material.OXEYE_DAISY) {
                                whitePollen += tool.power * ((plr.getStat(Stats.WHITE_POLLEN) as Int + plr.getStat(Stats.POLLEN) as Int) / 100)
                            }

                            pollen += (redPollen + bluePollen + whitePollen)
                            block.type = Material.AIR
                            plr.playSound(block.location, Sound.BLOCK_WOOD_PLACE, 1f, 1f)
                            plr.world.spawnParticle(Particle.VILLAGER_HAPPY, block.location, 5)

                            Bukkit.getScheduler().runTaskLater(BSS, Runnable {
                                val loc2 = block.location
                                plr.playSound(loc2, Sound.BLOCK_GILDED_BLACKSTONE_BREAK, 1f, 1f)
                                plr.world.getBlockAt(loc2).type = flower.material
                                plr.world.getBlockAt(loc2).blockData = flower
                                flowers.add(block)
                            }, 40L)
                        }
                    }
                }
            }
        } else if (tool.stumpType == StumpType.CIRCLE) {
            val playerLocation = plr.location
            val world = playerLocation.world

            val playerX = playerLocation.x
            val playerY = playerLocation.y
            val playerZ = playerLocation.z

            for (xOffset in -size..size) {
                for (yOffset in -size..size) {
                    for (zOffset in -size..size) {
                        val location = Location(world, playerX + xOffset, playerY + yOffset, playerZ + zOffset)
                        val block = location.block
                        if (block.type == Material.AIR) {
                            continue
                        }

                        val distanceSquared = playerLocation.distanceSquared(location)
                        if (distanceSquared <= (size * size) + 0.25) {
                            val flower = block.blockData.clone()
                            val color = block.type
                            if (flowers.contains(block)) {
                                flowers.remove(block)

                                when (color) {
                                    Material.POPPY -> redPollen += tool.power * ((plr.getStat(Stats.RED_POLLEN) as Int + plr.getStat(Stats.POLLEN) as Int) / 100)
                                    Material.CORNFLOWER -> bluePollen += tool.power * ((plr.getStat(Stats.BLUE_POLLEN) as Int + plr.getStat(Stats.POLLEN) as Int) / 100)
                                    Material.OXEYE_DAISY -> whitePollen += tool.power * ((plr.getStat(Stats.WHITE_POLLEN) as Int + plr.getStat(Stats.POLLEN) as Int) / 100)
                                    else -> {}
                                }
                                pollen += (redPollen + bluePollen + whitePollen)
                                block.type = Material.AIR
                                plr.playSound(block.location, Sound.BLOCK_WOOD_PLACE, 1f, 1f)
                                plr.world.spawnParticle(Particle.VILLAGER_HAPPY, block.location, 5)

                                Bukkit.getScheduler().runTaskLater(BSS, Runnable {
                                    val loc2 = block.location
                                    plr.playSound(loc2, Sound.BLOCK_GILDED_BLACKSTONE_BREAK, 1f, 1f)
                                    plr.world.getBlockAt(loc2).type = flower.material
                                    plr.world.getBlockAt(loc2).blockData = flower
                                    flowers.add(block)
                                }, 40L)
                            }
                        }
                    }
                }
            }
        }
        var Z1 = 1

        for (pollenType in Pollen.entries) {
            when (pollenType) {
                Pollen.RED -> if (redPollen > 0) {
                    val stand = plr.world.spawnEntity(Location(plr.world, 256000.0, 256.0, 256000.0), EntityType.ARMOR_STAND) as ArmorStand
                    stands.add(stand)
                    stand.isInvisible = true
                    stand.setGravity(false)
                    stand.isCollidable = false
                    stand.isCustomNameVisible = true
                    stand.customName = ChatColor.YELLOW.toString() + "+§l" + pollenType.color + redPollen
                    if (Z1 == 1) {
                        stand.teleport(a)
                        Z1 = 2
                    } else if (Z1 == 2) {
                        stand.teleport(b)
                        Z1 = 3
                    } else if (Z1 == 3) {
                        stand.teleport(c)
                    }
                }

                Pollen.BLUE -> if (bluePollen > 0) {
                    val stand = plr.world.spawnEntity(Location(plr.world, 256000.0, 256.0, 256000.0), EntityType.ARMOR_STAND) as ArmorStand
                    stands.add(stand)
                    stand.isInvisible = true
                    stand.setGravity(false)
                    stand.isCollidable = false
                    stand.isCustomNameVisible = true
                    stand.customName = ChatColor.YELLOW.toString() + "+§l" + pollenType.color + bluePollen
                    if (Z1 == 1) {
                        stand.teleport(a)
                        Z1 = 2
                    } else if (Z1 == 2) {
                        stand.teleport(b)
                        Z1 = 3
                    } else if (Z1 == 3) {
                        stand.teleport(c)
                    }
                }

                Pollen.WHITE -> if (whitePollen > 0) {
                    val stand = plr.world.spawnEntity(Location(plr.world, 256000.0, 256.0, 256000.0), EntityType.ARMOR_STAND) as ArmorStand
                    stands.add(stand)
                    stand.isInvisible = true
                    stand.setGravity(false)
                    stand.isCollidable = false
                    stand.isCustomNameVisible = true
                    stand.customName = ChatColor.YELLOW.toString() + "+§l" + pollenType.color + whitePollen
                    if (Z1 == 1) {
                        stand.teleport(a)
                        Z1 = 2
                    } else if (Z1 == 2) {
                        stand.teleport(b)
                        Z1 = 3
                    } else if (Z1 == 3) {
                        stand.teleport(c)
                    }
                }

                else -> {}
            }
        }

        Bukkit.getScheduler().runTaskLater(BSS, Runnable {
            for (stand in stands) {
                stand.remove()
            }
        }, 40L)

        if (pollen > plr.getCapacity()) {
            pollen = plr.getCapacity()
        }
        return pollen
    }
}
