package ru.lazard.kotlinpreferences

import android.content.Context
import android.support.annotation.Nullable
import ru.lazard.preferencesdelegate.KotlinPreferences
import ru.lazard.preferencesdelegate.PreferenceDelegate


/**
 * Simple usage of PreferenceDelegate
 */
class MyPreferences(context:Context): KotlinPreferences(context) {
    /**
     *  Preference with name "stringParam", type [String], default value <code>null</code>
     */
    var stringParam: String? by PreferenceDelegate()

    /**
     *  Preference with name "intParam", type [Int], default value <code>-1</code>
     */
    var intParam: Int? by PreferenceDelegate(-1)
}