package me.yawlick.beeswarm.player.statistic

enum class Stats(val realName: String, val displayName: String, val description: String, private val statType: StatType, private val method: StatType) {
    WALK_SPEED("WalkSpeed", "§lСкорость ходьбы", "Увеличивает скорость движения персонажа",
            StatType.MOVEMENT, StatType.PERCENT),
    JUMP_POWER("JumpPower", "§lСила прыжка", "Увеличивает высоту прыжка персонажа",
            StatType.MOVEMENT, StatType.PERCENT),

    POLLEN("Pollen", "§lПыльца", "Увеличивает сбор всех видов пыльцы",
            StatType.DIG, StatType.PERCENT),
    WHITE_POLLEN("WhitePollen", "§l§fБелая пыльца§r", "Увеличивает сбор белой пыльцы",
            StatType.DIG, StatType.PERCENT),
    BLUE_POLLEN("BluePollen", "§l§9Синяя пыльца§r", "Увеличивает сбор синей пыльцы",
            StatType.DIG, StatType.PERCENT),
    RED_POLLEN("RedPollen", "§l§cКрасная пыльца§r", "Увеличивает сбор красной пыльцы",
            StatType.DIG, StatType.PERCENT)
}
