package ru.lazard.preferencesdelegate

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by Egor on 23.06.2017.
 */
class TestPreferences(context: Context) : PreferencesInterface {
    override val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    override val isCommitImmediate = true

    var preferenceString: String by PreferenceDelegate()
    var preferenceStringNull: String? by PreferenceDelegate()
    var preferenceStringDefault: String by PreferenceDelegate("a")
    var preferenceStringDefaultNull: String? by PreferenceDelegate("b")

    var preferenceInt: Int by PreferenceDelegate()
    var preferenceIntNull: Int? by PreferenceDelegate()
    var preferenceIntDefault: Int by PreferenceDelegate(1)
    var preferenceIntDefaultNull: Int? by PreferenceDelegate(2)

    var preferenceLong: Long by PreferenceDelegate()
    var preferenceLongNull: Long? by PreferenceDelegate()
    var preferenceLongDefault: Long by PreferenceDelegate(10L)
    var preferenceLongDefaultNull: Long? by PreferenceDelegate(20L)

    var preferenceFloat: Float by PreferenceDelegate()
    var preferenceFloatNull: Float? by PreferenceDelegate()
    var preferenceFloatDefault: Float by PreferenceDelegate(0.1f)
    var preferenceFloatDefaultNull: Float? by PreferenceDelegate(0.2f)

    var preferenceBoolean: Boolean by PreferenceDelegate()
    var preferenceBooleanNull: Boolean? by PreferenceDelegate()
    var preferenceBooleanDefault: Boolean by PreferenceDelegate(true)
    var preferenceBooleanDefaultNull: Boolean? by PreferenceDelegate(true)

    var preferenceStringSet: Set<String>by PreferenceDelegate()
    var preferenceStringSetNull: Set<String>? by PreferenceDelegate()
    var preferenceStringSetDefault: Set<String>by PreferenceDelegate(setOf("a"))
    var preferenceStringSetDefaultNull: Set<String>? by PreferenceDelegate(setOf("b"))
}