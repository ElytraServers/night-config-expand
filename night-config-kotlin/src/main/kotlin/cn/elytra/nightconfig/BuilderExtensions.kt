package cn.elytra.nightconfig

import net.neoforged.neoforge.common.ModConfigSpec

inline fun ModConfigSpec.Builder.push(
    path: String,
    block: () -> Unit,
) {
    push(path)
    block()
    pop()
}
