package cn.elytra.nightconfig

import net.neoforged.neoforge.common.ModConfigSpec
import kotlin.reflect.KProperty

operator fun <T> ModConfigSpec.ConfigValue<T>.getValue(
    thisRef: Any?,
    property: KProperty<*>,
): T? = this.get()

operator fun <T> ModConfigSpec.ConfigValue<T>.setValue(
    thisRef: Any?,
    property: KProperty<*>,
    value: T?,
) {
    this.set(value)
}

operator fun ModConfigSpec.LongValue.getValue(
    thisRef: Any?,
    property: KProperty<*>,
): Long = this.asLong

operator fun ModConfigSpec.IntValue.getValue(
    thisRef: Any?,
    property: KProperty<*>,
): Int = this.asInt

operator fun ModConfigSpec.DoubleValue.getValue(
    thisRef: Any?,
    property: KProperty<*>,
): Double = this.asDouble

operator fun ModConfigSpec.BooleanValue.getValue(
    thisRef: Any?,
    property: KProperty<*>,
): Boolean = this.asBoolean
