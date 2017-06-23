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

    private lateinit var delegate: Delegate<*>
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var name: String


    private fun initDelegate(thisRef: PreferencesInterface, property: KProperty<*>) {
        preferences = thisRef.sharedPreferences
        editor = preferences.edit()
        name = property.name
        val erasure = property.returnType.jvmErasure
        when {
            erasure.isSubclassOf(String::class)  -> delegate = DelegateString()
            erasure.isSubclassOf(Set::class)     -> delegate = DelegateStringSet()
            erasure.isSubclassOf(Boolean::class) -> delegate = DelegateBoolean()
            erasure.isSubclassOf(Float::class)   -> delegate = DelegateFloat()
            erasure.isSubclassOf(Int::class)     -> delegate = DelegateInt()
            erasure.isSubclassOf(Long::class)    -> delegate = DelegateLong()
            else                                 -> throw IllegalArgumentException("Unknown parameter type ${property.returnType} - ${erasure}")
        }
    }

    operator fun getValue(thisRef: PreferencesInterface, property: KProperty<*>): T {
        initDelegate(thisRef, property);
        if (!preferences.contains(name)) return defValue as T
        return delegate.get() as T
    }


    operator fun setValue(thisRef: PreferencesInterface, property: KProperty<*>, value: T?) {
        initDelegate(thisRef, property);
        if (value == null) editor.remove(name) else delegate.set(value)
        if (thisRef.iCommitImmediate) editor.commit() else editor.apply()
    }

    private interface Delegate<T> {
        fun get(): T
        fun set(value: Any): SharedPreferences.Editor
    }

    private inner class DelegateString : Delegate<String> {
        override fun get() = preferences.getString(name, null)
        override fun set(value: Any) = editor.putString(name, value as String)
    }

    private inner class DelegateInt : Delegate<Int> {
        override fun get() = preferences.getInt(name, 0)
        override fun set(value: Any) = editor.putInt(name, value as Int)
    }

    private inner class DelegateFloat : Delegate<Float> {
        override fun get() = preferences.getFloat(name, 0f)
        override fun set(value: Any) = editor.putFloat(name, value as Float)
    }

    private inner class DelegateBoolean : Delegate<Boolean> {
        override fun get() = preferences.getBoolean(name, false)
        override fun set(value: Any) = editor.putBoolean(name, value as Boolean)
    }

    private inner class DelegateLong : Delegate<Long> {
        override fun get() = preferences.getLong(name, 0L)
        override fun set(value: Any) = editor.putLong(name, value as Long)
    }

    private inner class DelegateStringSet : Delegate<Set<String>> {
        override fun get() = preferences.getStringSet(name, setOf())
        override fun set(value: Any) = editor.putStringSet(name, value as Set<String>)
    }


}