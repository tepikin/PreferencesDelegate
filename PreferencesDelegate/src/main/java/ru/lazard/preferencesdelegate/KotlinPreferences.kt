package ru.lazard.preferencesdelegate

import android.content.SharedPreferences
import kotlin.reflect.KProperty
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

/**
 * Simple usage of PreferenceDelegate:<pre><code>
 * class MyPreferences(context:Context) : PreferencesInterface {
 *    override val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
 *    var stringParam: String? by PreferenceDelegate()  //Preference with name "stringParam", type [String], default value <code>null</code>
 *    var intParam: Int? by PreferenceDelegate(-1) //Preference with name "intParam", type [Int], default value <code>-1</code>
 * }
 * </pre></code>
 */
interface PreferencesInterface {
    val sharedPreferences: SharedPreferences
    val iCommitImmediate get() = true
}

class PreferenceDelegate<T>(val defValue: T? = null) {


    operator fun getValue(thisRef: PreferencesInterface, property: KProperty<*>): T =
            thisRef.sharedPreferences.let {
                val name = property.name
                val erasure = property.returnType.jvmErasure

                if (!it.contains(name)) return defValue as T

                when {
                    erasure.isSubclassOf(String::class)  -> return it.getString(name, null) as T
                    erasure.isSubclassOf(Set::class)     -> return it.getStringSet(name, null) as T
                    erasure.isSubclassOf(Boolean::class) -> return it.getBoolean(name, false) as T
                    erasure.isSubclassOf(Float::class)   -> return it.getFloat(name, 0f) as T
                    erasure.isSubclassOf(Int::class)     -> return it.getInt(name, 0) as T
                    erasure.isSubclassOf(Long::class)    -> return it.getLong(name, 0L) as T
                    else                                 -> throw IllegalArgumentException("Unknown parameter type")
                }
            }


    operator fun setValue(thisRef: PreferencesInterface, property: KProperty<*>, value: T?) =
            thisRef.sharedPreferences.edit().apply {
                val name = property.name
                val erasure = property.returnType.jvmErasure
                if (value == null) remove(name) else
                    when (value) {
                        erasure.isSubclassOf(String::class)  -> putString(name, value as String)
                        erasure.isSubclassOf(Set::class)     -> putStringSet(name, value as Set<String>?)
                        erasure.isSubclassOf(Boolean::class) -> putBoolean(name, value as Boolean)
                        erasure.isSubclassOf(Float::class)   -> putFloat(name, value as Float)
                        erasure.isSubclassOf(Int::class)     -> putInt(name, value as Int)
                        erasure.isSubclassOf(Long::class)    -> putLong(name, value as Long)
                        else                                 -> throw IllegalArgumentException("Unknown parameter type")
                    }

                if (thisRef.iCommitImmediate) {
                    commit()
                } else {
                    apply()
                }
            }
}