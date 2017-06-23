package ru.lazard.kotlinpreferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import ru.lazard.preferencesdelegate.PreferenceDelegate
import ru.lazard.preferencesdelegate.PreferencesInterface


/**
 * Simple usage of PreferenceDelegate
 */
class MyPreferences(context: Context) : PreferencesInterface {

    override val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    /**
     *  Preference with name "stringParam", type [String], default value <code>null</code>
     */
    var stringParam: String? by PreferenceDelegate()

    /**
     *  Preference with name "intParam", type [Int], default value <code>-1</code>
     */
    var intParam: Int? by PreferenceDelegate(-1)
}