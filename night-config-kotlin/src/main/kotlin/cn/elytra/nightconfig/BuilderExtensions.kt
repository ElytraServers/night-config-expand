@file:OptIn(ExperimentalContracts::class)

package cn.elytra.nightconfig

import net.neoforged.neoforge.common.ModConfigSpec
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

inline fun ModConfigSpec.Builder.push(
    path: String,
    block: () -> Unit,
) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    push(path)
    block()
    pop()
}

inline fun buildConfigSpec(block: ModConfigSpec.Builder.() -> Unit): ModConfigSpec {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return ModConfigSpec.Builder().apply(block).build()
}
