package ru.lazard.preferencesdelegate

import android.content.Context
import android.content.SharedPreferences
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

/**
 * Simple usage of PreferenceDelegate:<pre><code>
 * class MyPreferences(context:Context): KotlinPreferences(context) {
 *    var stringParam: String? by PreferenceDelegate()  //Preference with name "stringParam", type [String], default value <code>null</code>
 *    var intParam: Int? by PreferenceDelegate(-1) //Preference with name "intParam", type [Int], default value <code>-1</code>
 * }
 * </pre></code>
 *
 *
 * Better usage: <pre><code>
 * class MyPreferences(override val sharedPreferences: SharedPreferences) : PreferencesInterface {
 *    constructor(context: Context, fileName: String = "preferences", accessMode: Int = Context.MODE_PRIVATE) : this(context.getSharedPreferences(fileName, accessMode))
 *    var stringParam: String? by PreferenceDelegate()  //Preference with name "stringParam", type [String], default value <code>null</code>
 *    var intParam: Int? by PreferenceDelegate(-1) //Preference with name "intParam", type [Int], default value <code>-1</code>
 * }
 * </pre></code>
 */
open class KotlinPreferences(override val sharedPreferences: SharedPreferences) : PreferencesInterface {
    constructor(context: Context, fileName: String = "preferences", accessMode: Int = Context.MODE_PRIVATE) : this(context.getSharedPreferences(fileName, accessMode))
}

interface PreferencesInterface {
    val sharedPreferences: SharedPreferences
}


class PreferenceDelegate<T>(val defValue: T? = null) {

    val stringDef: String = ""
    val stringDefNull: String? = null
    val stringSetDef: Set<String> = setOf()
    val stringSetDefNull: Set<String>? = null
    val booleanDef: Boolean = false
    val booleanDefNull: Boolean? = false
    val floatDef: Float = 0f
    val floatDefNull: Float? = 0f
    val intDef: Int = 0
    val intDefNull: Int? = 0
    val longDef: Long = 0L
    val longDefNull: Long? = 0L

    operator fun getValue(thisRef: PreferencesInterface, property: KProperty<*>): T =
            thisRef.sharedPreferences.let {
                val name = property.name
                val returnType = property.returnType
                val erasure = returnType.jvmErasure

                when {
                    erasure.isSubclassOf(String::class) && !returnType.isMarkedNullable  -> return it.getString(name, defValue as? String ?: stringDef) as T
                    erasure.isSubclassOf(String::class) && returnType.isMarkedNullable   -> return it.getString(name, defValue as? String ?: stringDefNull) as T
                    erasure.isSubclassOf(Set::class) && !returnType.isMarkedNullable     -> return it.getStringSet(name, defValue as? Set<String> ?: stringSetDef) as T
                    erasure.isSubclassOf(Set::class) && returnType.isMarkedNullable      -> return it.getStringSet(name, defValue as? Set<String> ?: stringSetDefNull) as T
                    erasure.isSubclassOf(Boolean::class) && !returnType.isMarkedNullable -> return it.getBoolean(name, defValue as? Boolean ?: booleanDef) as T
                    erasure.isSubclassOf(Boolean::class) && returnType.isMarkedNullable  -> return it.getBoolean(name, defValue as? Boolean ?: booleanDefNull ?: booleanDef) as T
                    erasure.isSubclassOf(Float::class) && !returnType.isMarkedNullable   -> return it.getFloat(name, defValue as? Float ?: floatDef) as T
                    erasure.isSubclassOf(Float::class) && returnType.isMarkedNullable    -> return it.getFloat(name, defValue as? Float ?: floatDefNull ?: floatDef) as T
                    erasure.isSubclassOf(Int::class) && !returnType.isMarkedNullable     -> return it.getInt(name, defValue as? Int ?: intDef) as T
                    erasure.isSubclassOf(Int::class) && returnType.isMarkedNullable      -> return it.getInt(name, defValue as? Int ?: intDefNull ?: intDef) as T
                    erasure.isSubclassOf(Long::class) && !returnType.isMarkedNullable    -> return it.getLong(name, defValue as? Long ?: longDef) as T
                    erasure.isSubclassOf(Long::class) && returnType.isMarkedNullable     -> return it.getLong(name, defValue as? Long ?: longDefNull ?: longDef) as T
                    else                                                                 -> throw IllegalArgumentException("Unknown parameter type")
                }
            }


    operator fun setValue(thisRef: PreferencesInterface, property: KProperty<*>, value: T?) =
            thisRef.sharedPreferences.edit().apply {
                val name = property.name
                when (value) {
                    is String   -> putString(name, value)
                    is String?  -> putString(name, value)
                    is Set<*>   -> putStringSet(name, value as Set<String>)
                    is Set<*>?  -> putStringSet(name, value as Set<String>?)
                    is Boolean  -> putBoolean(name, value)
                    is Boolean? -> putBoolean(name, value ?: defValue as? Boolean ?: booleanDefNull ?: booleanDef)
                    is Float    -> putFloat(name, value)
                    is Float?   -> putFloat(name, value ?: defValue as? Float ?: floatDefNull ?: floatDef)
                    is Int      -> putInt(name, value)
                    is Int?     -> putInt(name, value ?: defValue as? Int ?: intDefNull ?: intDef)
                    is Long     -> putLong(name, value)
                    is Long?    -> putLong(name, value ?: defValue as? Long ?: longDefNull ?: longDef)
                    else        -> throw IllegalArgumentException("Unknown parameter type")
                }
                commit()
            }
}