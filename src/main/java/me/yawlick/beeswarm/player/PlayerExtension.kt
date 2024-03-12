package me.yawlick.beeswarm.player

import me.yawlick.beeswarm.BeeSwarm
import me.yawlick.beeswarm.player.statistic.Stats
import me.yawlick.beeswarm.player.tool.Tool
import me.yawlick.beeswarm.utils.StumpsHelper
import org.bukkit.*
import org.bukkit.advancement.Advancement
import org.bukkit.advancement.AdvancementProgress
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeInstance
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.PistonMoveReaction
import org.bukkit.block.data.BlockData
import org.bukkit.boss.BossBar
import org.bukkit.conversations.Conversation
import org.bukkit.conversations.ConversationAbandonedEvent
import org.bukkit.entity.*
import org.bukkit.entity.memory.MemoryKey
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.inventory.*
import org.bukkit.map.MapView
import org.bukkit.metadata.MetadataValue
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachment
import org.bukkit.permissions.PermissionAttachmentInfo
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.util.BoundingBox
import org.bukkit.util.RayTraceResult
import org.bukkit.util.Vector
import java.net.InetSocketAddress
import java.util.*

open class PlayerExtension : Player {
    fun Player.getData(): PlayerData {
        return BeeSwarm.getInstance().playerData.get(this.uniqueId)!!
    }

    fun Player.updateClient() {
        var bar = getPollenBossbar()
        bar!!.setTitle("§lПыльца в контейнере: §r§6${getData().pollen}§8§l/§r§7${getData().capacity}")
        bar!!.progress = (getData().pollen.toFloat() / getData().capacity).toDouble()
        bar!!.isVisible = true

        bar = getHoneyBossbar()
        bar!!.setTitle("§lМёд: §r" + ChatColor.GOLD + getHoney())
        bar!!.isVisible = true
    }

    fun Player.digFlowers() {
        if (hasTool()) {
            if (getPollen() >= getCapacity()) {
                this.sendMessage(ChatColor.RED.toString() + "§l У вас полный контейнер!§f §7(Конвертируйте пыльцу в мёд у вашего улья)")
                this.playSound(this.location, Sound.ENTITY_VILLAGER_NO, 1f, 1f)
                return
            }

            val newpollen = StumpsHelper().checkFlowers(this)
            var summa = getPollen() + newpollen
            if (summa > getCapacity()) {
                summa = getCapacity()
            }
            setPollen(summa)
            updateClient()
        }
    }

    fun Player.convertPollen(value: Int) {
        var pollen = value
        if (getPollen() < pollen) {
            pollen = getPollen()
        }
        setPollen(getPollen() - pollen)
        setHoney(getHoney() + pollen)
        this.playSound(this.location, Sound.ENTITY_VILLAGER_YES, 1f, 1f)
        updateClient()
    }

    fun Player.setPollen(value: Int) {
        getData().pollen = value
    }

    fun Player.setHoney(value: Int) {
        getData().honey = value
    }

    fun Player.setCapacity(value: Int) {
        getData().capacity = value
    }

    fun Player.setTool(tool: Tool) {
        getData().tool = tool
        this.setItemInHand(tool.itemStack)
        this.sendMessage("§l§aВы одели палку : ${tool.displayName}")
    }

    fun Player.setStatsBossbars(bossBars: Array<BossBar?>) {
        getData().bossBars = bossBars
    }

    fun Player.setPollenBossbar(bossBar: BossBar) {
        getData().bossBars[0] = bossBar
    }

    fun Player.setHoneyBossbar(bossBar: BossBar) {
        getData().bossBars[1] = bossBar
    }

    fun Player.setStat(statName: Stats,  statValue: Any) {
        getData().setStats(statName, statValue)
    }

    fun Player.getPollen(): Int {
        return getData().pollen
    }

    fun Player.getHoney(): Int {
        return getData().honey
    }

    fun Player.getCapacity(): Int {
        return getData().capacity
    }

    fun Player.getTool(): Tool {
        return getData().tool
    }

    fun Player.getPollenBossbar(): BossBar? {
        return getData().bossBars[0]
    }

    fun Player.getHoneyBossbar(): BossBar? {
        return getData().bossBars[1]
    }

    fun Player.getStat(statName: Stats?): Any? {
        val statValue: Any? = getData().playerStats?.get(statName)
        return statValue
    }

    fun Player.hasTool(): Boolean {
        if (BeeSwarm.getInstance().toolFromItem.containsKey(this.inventory.itemInMainHand)) {
            return true
        }
        return false
    }

    override fun getAttribute(p0: Attribute): AttributeInstance? {
        TODO("Not yet implemented")
    }

    override fun setMetadata(p0: String, p1: MetadataValue) {
        TODO("Not yet implemented")
    }

    override fun getMetadata(p0: String): MutableList<MetadataValue> {
        TODO("Not yet implemented")
    }

    override fun hasMetadata(p0: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeMetadata(p0: String, p1: Plugin) {
        TODO("Not yet implemented")
    }

    override fun isOp(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setOp(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isPermissionSet(p0: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPermissionSet(p0: Permission): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasPermission(p0: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasPermission(p0: Permission): Boolean {
        TODO("Not yet implemented")
    }

    override fun addAttachment(p0: Plugin, p1: String, p2: Boolean): PermissionAttachment {
        TODO("Not yet implemented")
    }

    override fun addAttachment(p0: Plugin): PermissionAttachment {
        TODO("Not yet implemented")
    }

    override fun addAttachment(p0: Plugin, p1: String, p2: Boolean, p3: Int): PermissionAttachment? {
        TODO("Not yet implemented")
    }

    override fun addAttachment(p0: Plugin, p1: Int): PermissionAttachment? {
        TODO("Not yet implemented")
    }

    override fun removeAttachment(p0: PermissionAttachment) {
        TODO("Not yet implemented")
    }

    override fun recalculatePermissions() {
        TODO("Not yet implemented")
    }

    override fun getEffectivePermissions(): MutableSet<PermissionAttachmentInfo> {
        TODO("Not yet implemented")
    }

    override fun sendMessage(p0: String) {
        TODO("Not yet implemented")
    }

    override fun sendMessage(p0: Array<out String>) {
        TODO("Not yet implemented")
    }

    override fun sendMessage(p0: UUID?, p1: String) {
        TODO("Not yet implemented")
    }

    override fun sendMessage(p0: UUID?, p1: Array<out String>) {
        TODO("Not yet implemented")
    }

    override fun getServer(): Server {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun spigot(): Player.Spigot {
        TODO("Not yet implemented")
    }

    override fun getCustomName(): String? {
        TODO("Not yet implemented")
    }

    override fun setCustomName(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun getPersistentDataContainer(): PersistentDataContainer {
        TODO("Not yet implemented")
    }

    override fun getLocation(): Location {
        TODO("Not yet implemented")
    }

    override fun getLocation(p0: Location?): Location? {
        TODO("Not yet implemented")
    }

    override fun setVelocity(p0: Vector) {
        TODO("Not yet implemented")
    }

    override fun getVelocity(): Vector {
        TODO("Not yet implemented")
    }

    override fun getHeight(): Double {
        TODO("Not yet implemented")
    }

    override fun getWidth(): Double {
        TODO("Not yet implemented")
    }

    override fun getBoundingBox(): BoundingBox {
        TODO("Not yet implemented")
    }

    override fun isOnGround(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isInWater(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getWorld(): World {
        TODO("Not yet implemented")
    }

    override fun setRotation(p0: Float, p1: Float) {
        TODO("Not yet implemented")
    }

    override fun teleport(p0: Location): Boolean {
        TODO("Not yet implemented")
    }

    override fun teleport(p0: Location, p1: PlayerTeleportEvent.TeleportCause): Boolean {
        TODO("Not yet implemented")
    }

    override fun teleport(p0: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun teleport(p0: Entity, p1: PlayerTeleportEvent.TeleportCause): Boolean {
        TODO("Not yet implemented")
    }

    override fun getNearbyEntities(p0: Double, p1: Double, p2: Double): MutableList<Entity> {
        TODO("Not yet implemented")
    }

    override fun getEntityId(): Int {
        TODO("Not yet implemented")
    }

    override fun getFireTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun getMaxFireTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun setFireTicks(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun remove() {
        TODO("Not yet implemented")
    }

    override fun isDead(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isValid(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPersistent(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setPersistent(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getPassenger(): Entity? {
        TODO("Not yet implemented")
    }

    override fun setPassenger(p0: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPassengers(): MutableList<Entity> {
        TODO("Not yet implemented")
    }

    override fun addPassenger(p0: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun removePassenger(p0: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun eject(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getFallDistance(): Float {
        TODO("Not yet implemented")
    }

    override fun setFallDistance(p0: Float) {
        TODO("Not yet implemented")
    }

    override fun setLastDamageCause(p0: EntityDamageEvent?) {
        TODO("Not yet implemented")
    }

    override fun getLastDamageCause(): EntityDamageEvent? {
        TODO("Not yet implemented")
    }

    override fun getUniqueId(): UUID {
        TODO("Not yet implemented")
    }

    override fun getTicksLived(): Int {
        TODO("Not yet implemented")
    }

    override fun setTicksLived(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun playEffect(p0: Location, p1: Effect, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> playEffect(p0: Location, p1: Effect, p2: T?) {
        TODO("Not yet implemented")
    }

    override fun playEffect(p0: EntityEffect) {
        TODO("Not yet implemented")
    }

    override fun getType(): EntityType {
        TODO("Not yet implemented")
    }

    override fun isInsideVehicle(): Boolean {
        TODO("Not yet implemented")
    }

    override fun leaveVehicle(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getVehicle(): Entity? {
        TODO("Not yet implemented")
    }

    override fun setCustomNameVisible(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isCustomNameVisible(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setGlowing(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isGlowing(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setInvulnerable(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isInvulnerable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isSilent(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSilent(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun hasGravity(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setGravity(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getPortalCooldown(): Int {
        TODO("Not yet implemented")
    }

    override fun setPortalCooldown(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getScoreboardTags(): MutableSet<String> {
        TODO("Not yet implemented")
    }

    override fun addScoreboardTag(p0: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeScoreboardTag(p0: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPistonMoveReaction(): PistonMoveReaction {
        TODO("Not yet implemented")
    }

    override fun getFacing(): BlockFace {
        TODO("Not yet implemented")
    }

    override fun getPose(): Pose {
        TODO("Not yet implemented")
    }

    override fun damage(p0: Double) {
        TODO("Not yet implemented")
    }

    override fun damage(p0: Double, p1: Entity?) {
        TODO("Not yet implemented")
    }

    override fun getHealth(): Double {
        TODO("Not yet implemented")
    }

    override fun setHealth(p0: Double) {
        TODO("Not yet implemented")
    }

    override fun getAbsorptionAmount(): Double {
        TODO("Not yet implemented")
    }

    override fun setAbsorptionAmount(p0: Double) {
        TODO("Not yet implemented")
    }

    override fun getMaxHealth(): Double {
        TODO("Not yet implemented")
    }

    override fun setMaxHealth(p0: Double) {
        TODO("Not yet implemented")
    }

    override fun resetMaxHealth() {
        TODO("Not yet implemented")
    }

    override fun <T : Projectile?> launchProjectile(p0: Class<out T>): T & Any {
        TODO("Not yet implemented")
    }

    override fun <T : Projectile?> launchProjectile(p0: Class<out T>, p1: Vector?): T & Any {
        TODO("Not yet implemented")
    }

    override fun getEyeHeight(): Double {
        TODO("Not yet implemented")
    }

    override fun getEyeHeight(p0: Boolean): Double {
        TODO("Not yet implemented")
    }

    override fun getEyeLocation(): Location {
        TODO("Not yet implemented")
    }

    override fun getLineOfSight(p0: MutableSet<Material>?, p1: Int): MutableList<Block> {
        TODO("Not yet implemented")
    }

    override fun getTargetBlock(p0: MutableSet<Material>?, p1: Int): Block {
        TODO("Not yet implemented")
    }

    override fun getLastTwoTargetBlocks(p0: MutableSet<Material>?, p1: Int): MutableList<Block> {
        TODO("Not yet implemented")
    }

    override fun getTargetBlockExact(p0: Int): Block? {
        TODO("Not yet implemented")
    }

    override fun getTargetBlockExact(p0: Int, p1: FluidCollisionMode): Block? {
        TODO("Not yet implemented")
    }

    override fun rayTraceBlocks(p0: Double): RayTraceResult? {
        TODO("Not yet implemented")
    }

    override fun rayTraceBlocks(p0: Double, p1: FluidCollisionMode): RayTraceResult? {
        TODO("Not yet implemented")
    }

    override fun getRemainingAir(): Int {
        TODO("Not yet implemented")
    }

    override fun setRemainingAir(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getMaximumAir(): Int {
        TODO("Not yet implemented")
    }

    override fun setMaximumAir(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getArrowCooldown(): Int {
        TODO("Not yet implemented")
    }

    override fun setArrowCooldown(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getArrowsInBody(): Int {
        TODO("Not yet implemented")
    }

    override fun setArrowsInBody(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getMaximumNoDamageTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun setMaximumNoDamageTicks(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getLastDamage(): Double {
        TODO("Not yet implemented")
    }

    override fun setLastDamage(p0: Double) {
        TODO("Not yet implemented")
    }

    override fun getNoDamageTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun setNoDamageTicks(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getKiller(): Player? {
        TODO("Not yet implemented")
    }

    override fun addPotionEffect(p0: PotionEffect): Boolean {
        TODO("Not yet implemented")
    }

    override fun addPotionEffect(p0: PotionEffect, p1: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun addPotionEffects(p0: MutableCollection<PotionEffect>): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasPotionEffect(p0: PotionEffectType): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPotionEffect(p0: PotionEffectType): PotionEffect? {
        TODO("Not yet implemented")
    }

    override fun removePotionEffect(p0: PotionEffectType) {
        TODO("Not yet implemented")
    }

    override fun getActivePotionEffects(): MutableCollection<PotionEffect> {
        TODO("Not yet implemented")
    }

    override fun hasLineOfSight(p0: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun getRemoveWhenFarAway(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setRemoveWhenFarAway(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getEquipment(): EntityEquipment? {
        TODO("Not yet implemented")
    }

    override fun setCanPickupItems(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getCanPickupItems(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isLeashed(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getLeashHolder(): Entity {
        TODO("Not yet implemented")
    }

    override fun setLeashHolder(p0: Entity?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isGliding(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setGliding(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isSwimming(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSwimming(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isRiptiding(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isSleeping(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setAI(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun hasAI(): Boolean {
        TODO("Not yet implemented")
    }

    override fun attack(p0: Entity) {
        TODO("Not yet implemented")
    }

    override fun swingMainHand() {
        TODO("Not yet implemented")
    }

    override fun swingOffHand() {
        TODO("Not yet implemented")
    }

    override fun setCollidable(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isCollidable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCollidableExemptions(): MutableSet<UUID> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> getMemory(p0: MemoryKey<T>): T? {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> setMemory(p0: MemoryKey<T>, p1: T?) {
        TODO("Not yet implemented")
    }

    override fun getCategory(): EntityCategory {
        TODO("Not yet implemented")
    }

    override fun setInvisible(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isInvisible(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getInventory(): PlayerInventory {
        TODO("Not yet implemented")
    }

    override fun getEnderChest(): Inventory {
        TODO("Not yet implemented")
    }

    override fun getMainHand(): MainHand {
        TODO("Not yet implemented")
    }

    override fun setWindowProperty(p0: InventoryView.Property, p1: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getOpenInventory(): InventoryView {
        TODO("Not yet implemented")
    }

    override fun openInventory(p0: Inventory): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun openInventory(p0: InventoryView) {
        TODO("Not yet implemented")
    }

    override fun openWorkbench(p0: Location?, p1: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun openEnchanting(p0: Location?, p1: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun openMerchant(p0: Villager, p1: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun openMerchant(p0: Merchant, p1: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun closeInventory() {
        TODO("Not yet implemented")
    }

    override fun getItemInHand(): ItemStack {
        TODO("Not yet implemented")
    }

    override fun setItemInHand(p0: ItemStack?) {
        TODO("Not yet implemented")
    }

    override fun getItemOnCursor(): ItemStack {
        TODO("Not yet implemented")
    }

    override fun setItemOnCursor(p0: ItemStack?) {
        TODO("Not yet implemented")
    }

    override fun hasCooldown(p0: Material): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCooldown(p0: Material): Int {
        TODO("Not yet implemented")
    }

    override fun setCooldown(p0: Material, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun getSleepTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun sleep(p0: Location, p1: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun wakeup(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getBedLocation(): Location {
        TODO("Not yet implemented")
    }

    override fun getGameMode(): GameMode {
        TODO("Not yet implemented")
    }

    override fun setGameMode(p0: GameMode) {
        TODO("Not yet implemented")
    }

    override fun isBlocking(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isHandRaised(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getExpToLevel(): Int {
        TODO("Not yet implemented")
    }

    override fun getAttackCooldown(): Float {
        TODO("Not yet implemented")
    }

    override fun discoverRecipe(p0: NamespacedKey): Boolean {
        TODO("Not yet implemented")
    }

    override fun discoverRecipes(p0: MutableCollection<NamespacedKey>): Int {
        TODO("Not yet implemented")
    }

    override fun undiscoverRecipe(p0: NamespacedKey): Boolean {
        TODO("Not yet implemented")
    }

    override fun undiscoverRecipes(p0: MutableCollection<NamespacedKey>): Int {
        TODO("Not yet implemented")
    }

    override fun hasDiscoveredRecipe(p0: NamespacedKey): Boolean {
        TODO("Not yet implemented")
    }

    override fun getDiscoveredRecipes(): MutableSet<NamespacedKey> {
        TODO("Not yet implemented")
    }

    override fun getShoulderEntityLeft(): Entity? {
        TODO("Not yet implemented")
    }

    override fun setShoulderEntityLeft(p0: Entity?) {
        TODO("Not yet implemented")
    }

    override fun getShoulderEntityRight(): Entity? {
        TODO("Not yet implemented")
    }

    override fun setShoulderEntityRight(p0: Entity?) {
        TODO("Not yet implemented")
    }

    override fun dropItem(p0: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun getExhaustion(): Float {
        TODO("Not yet implemented")
    }

    override fun setExhaustion(p0: Float) {
        TODO("Not yet implemented")
    }

    override fun getSaturation(): Float {
        TODO("Not yet implemented")
    }

    override fun setSaturation(p0: Float) {
        TODO("Not yet implemented")
    }

    override fun getFoodLevel(): Int {
        TODO("Not yet implemented")
    }

    override fun setFoodLevel(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getSaturatedRegenRate(): Int {
        TODO("Not yet implemented")
    }

    override fun setSaturatedRegenRate(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getUnsaturatedRegenRate(): Int {
        TODO("Not yet implemented")
    }

    override fun setUnsaturatedRegenRate(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getStarvationRate(): Int {
        TODO("Not yet implemented")
    }

    override fun setStarvationRate(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun isConversing(): Boolean {
        TODO("Not yet implemented")
    }

    override fun acceptConversationInput(p0: String) {
        TODO("Not yet implemented")
    }

    override fun beginConversation(p0: Conversation): Boolean {
        TODO("Not yet implemented")
    }

    override fun abandonConversation(p0: Conversation) {
        TODO("Not yet implemented")
    }

    override fun abandonConversation(p0: Conversation, p1: ConversationAbandonedEvent) {
        TODO("Not yet implemented")
    }

    override fun sendRawMessage(p0: String) {
        TODO("Not yet implemented")
    }

    override fun sendRawMessage(p0: UUID?, p1: String) {
        TODO("Not yet implemented")
    }

    override fun serialize(): MutableMap<String, Any> {
        TODO("Not yet implemented")
    }

    override fun isOnline(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isBanned(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isWhitelisted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setWhitelisted(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getPlayer(): Player? {
        TODO("Not yet implemented")
    }

    override fun getFirstPlayed(): Long {
        TODO("Not yet implemented")
    }

    override fun getLastPlayed(): Long {
        TODO("Not yet implemented")
    }

    override fun hasPlayedBefore(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getBedSpawnLocation(): Location? {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(p0: Statistic) {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(p0: Statistic, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(p0: Statistic, p1: Material) {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(p0: Statistic, p1: Material, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(p0: Statistic, p1: EntityType) {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(p0: Statistic, p1: EntityType, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(p0: Statistic) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(p0: Statistic, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(p0: Statistic, p1: Material) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(p0: Statistic, p1: Material, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(p0: Statistic, p1: EntityType) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(p0: Statistic, p1: EntityType, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun setStatistic(p0: Statistic, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun setStatistic(p0: Statistic, p1: Material, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun setStatistic(p0: Statistic, p1: EntityType, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun getStatistic(p0: Statistic): Int {
        TODO("Not yet implemented")
    }

    override fun getStatistic(p0: Statistic, p1: Material): Int {
        TODO("Not yet implemented")
    }

    override fun getStatistic(p0: Statistic, p1: EntityType): Int {
        TODO("Not yet implemented")
    }

    override fun sendPluginMessage(p0: Plugin, p1: String, p2: ByteArray) {
        TODO("Not yet implemented")
    }

    override fun getListeningPluginChannels(): MutableSet<String> {
        TODO("Not yet implemented")
    }

    override fun getDisplayName(): String {
        TODO("Not yet implemented")
    }

    override fun setDisplayName(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun getPlayerListName(): String {
        TODO("Not yet implemented")
    }

    override fun setPlayerListName(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun getPlayerListHeader(): String? {
        TODO("Not yet implemented")
    }

    override fun getPlayerListFooter(): String? {
        TODO("Not yet implemented")
    }

    override fun setPlayerListHeader(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun setPlayerListFooter(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun setPlayerListHeaderFooter(p0: String?, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun setCompassTarget(p0: Location) {
        TODO("Not yet implemented")
    }

    override fun getCompassTarget(): Location {
        TODO("Not yet implemented")
    }

    override fun getAddress(): InetSocketAddress? {
        TODO("Not yet implemented")
    }

    override fun kickPlayer(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun chat(p0: String) {
        TODO("Not yet implemented")
    }

    override fun performCommand(p0: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isSneaking(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSneaking(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isSprinting(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSprinting(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun saveData() {
        TODO("Not yet implemented")
    }

    override fun loadData() {
        TODO("Not yet implemented")
    }

    override fun setSleepingIgnored(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isSleepingIgnored(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setBedSpawnLocation(p0: Location?) {
        TODO("Not yet implemented")
    }

    override fun setBedSpawnLocation(p0: Location?, p1: Boolean) {
        TODO("Not yet implemented")
    }

    override fun playNote(p0: Location, p1: Byte, p2: Byte) {
        TODO("Not yet implemented")
    }

    override fun playNote(p0: Location, p1: Instrument, p2: Note) {
        TODO("Not yet implemented")
    }

    override fun playSound(p0: Location, p1: Sound, p2: Float, p3: Float) {
        TODO("Not yet implemented")
    }

    override fun playSound(p0: Location, p1: String, p2: Float, p3: Float) {
        TODO("Not yet implemented")
    }

    override fun playSound(p0: Location, p1: Sound, p2: SoundCategory, p3: Float, p4: Float) {
        TODO("Not yet implemented")
    }

    override fun playSound(p0: Location, p1: String, p2: SoundCategory, p3: Float, p4: Float) {
        TODO("Not yet implemented")
    }

    override fun stopSound(p0: Sound) {
        TODO("Not yet implemented")
    }

    override fun stopSound(p0: String) {
        TODO("Not yet implemented")
    }

    override fun stopSound(p0: Sound, p1: SoundCategory?) {
        TODO("Not yet implemented")
    }

    override fun stopSound(p0: String, p1: SoundCategory?) {
        TODO("Not yet implemented")
    }

    override fun sendBlockChange(p0: Location, p1: Material, p2: Byte) {
        TODO("Not yet implemented")
    }

    override fun sendBlockChange(p0: Location, p1: BlockData) {
        TODO("Not yet implemented")
    }

    override fun sendBlockDamage(p0: Location, p1: Float) {
        TODO("Not yet implemented")
    }

    override fun sendChunkChange(p0: Location, p1: Int, p2: Int, p3: Int, p4: ByteArray): Boolean {
        TODO("Not yet implemented")
    }

    override fun sendSignChange(p0: Location, p1: Array<out String>?) {
        TODO("Not yet implemented")
    }

    override fun sendSignChange(p0: Location, p1: Array<out String>?, p2: DyeColor) {
        TODO("Not yet implemented")
    }

    override fun sendMap(p0: MapView) {
        TODO("Not yet implemented")
    }

    override fun updateInventory() {
        TODO("Not yet implemented")
    }

    override fun setPlayerTime(p0: Long, p1: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getPlayerTime(): Long {
        TODO("Not yet implemented")
    }

    override fun getPlayerTimeOffset(): Long {
        TODO("Not yet implemented")
    }

    override fun isPlayerTimeRelative(): Boolean {
        TODO("Not yet implemented")
    }

    override fun resetPlayerTime() {
        TODO("Not yet implemented")
    }

    override fun setPlayerWeather(p0: WeatherType) {
        TODO("Not yet implemented")
    }

    override fun getPlayerWeather(): WeatherType? {
        TODO("Not yet implemented")
    }

    override fun resetPlayerWeather() {
        TODO("Not yet implemented")
    }

    override fun giveExp(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun giveExpLevels(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getExp(): Float {
        TODO("Not yet implemented")
    }

    override fun setExp(p0: Float) {
        TODO("Not yet implemented")
    }

    override fun getLevel(): Int {
        TODO("Not yet implemented")
    }

    override fun setLevel(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getTotalExperience(): Int {
        TODO("Not yet implemented")
    }

    override fun setTotalExperience(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun sendExperienceChange(p0: Float) {
        TODO("Not yet implemented")
    }

    override fun sendExperienceChange(p0: Float, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun getAllowFlight(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setAllowFlight(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun hidePlayer(p0: Player) {
        TODO("Not yet implemented")
    }

    override fun hidePlayer(p0: Plugin, p1: Player) {
        TODO("Not yet implemented")
    }

    override fun showPlayer(p0: Player) {
        TODO("Not yet implemented")
    }

    override fun showPlayer(p0: Plugin, p1: Player) {
        TODO("Not yet implemented")
    }

    override fun canSee(p0: Player): Boolean {
        TODO("Not yet implemented")
    }

    override fun isFlying(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setFlying(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setFlySpeed(p0: Float) {
        TODO("Not yet implemented")
    }

    override fun setWalkSpeed(p0: Float) {
        TODO("Not yet implemented")
    }

    override fun getFlySpeed(): Float {
        TODO("Not yet implemented")
    }

    override fun getWalkSpeed(): Float {
        TODO("Not yet implemented")
    }

    override fun setTexturePack(p0: String) {
        TODO("Not yet implemented")
    }

    override fun setResourcePack(p0: String) {
        TODO("Not yet implemented")
    }

    override fun setResourcePack(p0: String, p1: ByteArray) {
        TODO("Not yet implemented")
    }

    override fun getScoreboard(): Scoreboard {
        TODO("Not yet implemented")
    }

    override fun setScoreboard(p0: Scoreboard) {
        TODO("Not yet implemented")
    }

    override fun isHealthScaled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setHealthScaled(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setHealthScale(p0: Double) {
        TODO("Not yet implemented")
    }

    override fun getHealthScale(): Double {
        TODO("Not yet implemented")
    }

    override fun getSpectatorTarget(): Entity? {
        TODO("Not yet implemented")
    }

    override fun setSpectatorTarget(p0: Entity?) {
        TODO("Not yet implemented")
    }

    override fun sendTitle(p0: String?, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun sendTitle(p0: String?, p1: String?, p2: Int, p3: Int, p4: Int) {
        TODO("Not yet implemented")
    }

    override fun resetTitle() {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(p0: Particle, p1: Location, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(p0: Particle, p1: Double, p2: Double, p3: Double, p4: Int) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> spawnParticle(p0: Particle, p1: Location, p2: Int, p3: T?) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> spawnParticle(p0: Particle, p1: Double, p2: Double, p3: Double, p4: Int, p5: T?) {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(p0: Particle, p1: Location, p2: Int, p3: Double, p4: Double, p5: Double) {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(
        p0: Particle,
        p1: Double,
        p2: Double,
        p3: Double,
        p4: Int,
        p5: Double,
        p6: Double,
        p7: Double
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> spawnParticle(
        p0: Particle,
        p1: Location,
        p2: Int,
        p3: Double,
        p4: Double,
        p5: Double,
        p6: T?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> spawnParticle(
        p0: Particle,
        p1: Double,
        p2: Double,
        p3: Double,
        p4: Int,
        p5: Double,
        p6: Double,
        p7: Double,
        p8: T?
    ) {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(p0: Particle, p1: Location, p2: Int, p3: Double, p4: Double, p5: Double, p6: Double) {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(
        p0: Particle,
        p1: Double,
        p2: Double,
        p3: Double,
        p4: Int,
        p5: Double,
        p6: Double,
        p7: Double,
        p8: Double
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> spawnParticle(
        p0: Particle,
        p1: Location,
        p2: Int,
        p3: Double,
        p4: Double,
        p5: Double,
        p6: Double,
        p7: T?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> spawnParticle(
        p0: Particle,
        p1: Double,
        p2: Double,
        p3: Double,
        p4: Int,
        p5: Double,
        p6: Double,
        p7: Double,
        p8: Double,
        p9: T?
    ) {
        TODO("Not yet implemented")
    }

    override fun getAdvancementProgress(p0: Advancement): AdvancementProgress {
        TODO("Not yet implemented")
    }

    override fun getClientViewDistance(): Int {
        TODO("Not yet implemented")
    }

    override fun getPing(): Int {
        TODO("Not yet implemented")
    }

    override fun getLocale(): String {
        TODO("Not yet implemented")
    }

    override fun updateCommands() {
        TODO("Not yet implemented")
    }

    override fun openBook(p0: ItemStack) {
        TODO("Not yet implemented")
    }
}