package ru.lazard.preferencesdelegate

import android.content.Context
import android.util.Log


class SpeedTest(context: Context) {
    fun runTests(){
        readStartValues()
        setValues()
        setNullValues()
    }
    private var preferences = TestPreferences(context)


    fun speedTest(message: String? = null, func: () -> Unit) {
        val time = System.currentTimeMillis()
        func()
        Log.e("SpeedTest", "${message ?: ""} time = ${System.currentTimeMillis() - time}")
    }

    fun readStartValues() {

        preferences.sharedPreferences.edit().apply { clear();commit() }
        speedTest("readStartValues") {
            assertEquals("", preferences.preferenceString)
            assertEquals(null, preferences.preferenceStringNull)
            assertEquals("a", preferences.preferenceStringDefault)
            assertEquals("b", preferences.preferenceStringDefaultNull)

            assertEquals(0, preferences.preferenceInt)
            assertEquals(null, preferences.preferenceIntNull)
            assertEquals(1, preferences.preferenceIntDefault)
            assertEquals(2, preferences.preferenceIntDefaultNull)

            assertEquals(0L, preferences.preferenceLong)
            assertEquals(null, preferences.preferenceLongNull)
            assertEquals(10L, preferences.preferenceLongDefault)
            assertEquals(20L, preferences.preferenceLongDefaultNull)

            assertEquals(0f, preferences.preferenceFloat)
            assertEquals(null, preferences.preferenceFloatNull)
            assertEquals(0.1f, preferences.preferenceFloatDefault)
            assertEquals(0.2f, preferences.preferenceFloatDefaultNull)

            assertEquals(false, preferences.preferenceBoolean)
            assertEquals(null, preferences.preferenceBooleanNull)
            assertEquals(true, preferences.preferenceBooleanDefault)
            assertEquals(true, preferences.preferenceBooleanDefaultNull)

            assertEquals(setOf<String>(), preferences.preferenceStringSet)
            assertEquals(null, preferences.preferenceStringSetNull)
            assertEquals(setOf("a"), preferences.preferenceStringSetDefault)
            assertEquals(setOf("b"), preferences.preferenceStringSetDefaultNull)
        }

    }


    fun setNullValues() {
        preferences.sharedPreferences.edit().apply { clear();commit() }

        speedTest("setNullValues") {
            preferences.preferenceStringNull = null
            preferences.preferenceStringDefaultNull = null
            preferences.preferenceIntNull = null
            preferences.preferenceIntDefaultNull = null
            preferences.preferenceLongNull = null
            preferences.preferenceLongDefaultNull = null
            preferences.preferenceFloatNull = null
            preferences.preferenceFloatDefaultNull = null
            preferences.preferenceBooleanNull = null
            preferences.preferenceBooleanDefaultNull = null
            preferences.preferenceStringSetNull = null
            preferences.preferenceStringSetDefaultNull = null
        }

        assertEquals(null, preferences.preferenceStringNull)
        assertEquals("b", preferences.preferenceStringDefaultNull)
        assertEquals(null, preferences.preferenceIntNull)
        assertEquals(2, preferences.preferenceIntDefaultNull)
        assertEquals(null, preferences.preferenceLongNull)
        assertEquals(20L, preferences.preferenceLongDefaultNull)
        assertEquals(null, preferences.preferenceFloatNull)
        assertEquals(0.2f, preferences.preferenceFloatDefaultNull)
        assertEquals(null, preferences.preferenceBooleanNull)
        assertEquals(true, preferences.preferenceBooleanDefaultNull)
        assertEquals(null, preferences.preferenceStringSetNull)
        assertEquals(setOf("b"), preferences.preferenceStringSetDefaultNull)

    }


    fun setValues() {
        preferences.sharedPreferences.edit().apply { clear();commit() }

        speedTest("setValues") {
            preferences.preferenceString = "5"
            preferences.preferenceStringNull = "5a"
            preferences.preferenceStringDefault = "5b"
            preferences.preferenceStringDefaultNull = "5c"

            preferences.preferenceInt = 11
            preferences.preferenceIntNull = 12
            preferences.preferenceIntDefault = 13
            preferences.preferenceIntDefaultNull = 14

            preferences.preferenceLong = 21L
            preferences.preferenceLongNull = 2200L
            preferences.preferenceLongDefault = 23L
            preferences.preferenceLongDefaultNull = 24L

            preferences.preferenceFloat = 0.31f
            preferences.preferenceFloatNull = 0.32f
            preferences.preferenceFloatDefault = 0.33f
            preferences.preferenceFloatDefaultNull = 0.34f

            preferences.preferenceBoolean = true
            preferences.preferenceBooleanNull = true
            preferences.preferenceBooleanDefault = true
            preferences.preferenceBooleanDefaultNull = true

            preferences.preferenceStringSet = setOf("a1")
            preferences.preferenceStringSetNull = setOf("a2")
            preferences.preferenceStringSetDefault = setOf("a3")
            preferences.preferenceStringSetDefaultNull = setOf("a4")
        }

        speedTest("readValues") {
            assertEquals("5", preferences.preferenceString)
            assertEquals("5a", preferences.preferenceStringNull)
            assertEquals("5b", preferences.preferenceStringDefault)
            assertEquals("5c", preferences.preferenceStringDefaultNull)

            assertEquals(11, preferences.preferenceInt)
            assertEquals(12, preferences.preferenceIntNull)
            assertEquals(13, preferences.preferenceIntDefault)
            assertEquals(14, preferences.preferenceIntDefaultNull)

            assertEquals(21L, preferences.preferenceLong)
            assertEquals(2200L, preferences.preferenceLongNull)
            assertEquals(23L, preferences.preferenceLongDefault)
            assertEquals(24L, preferences.preferenceLongDefaultNull)

            assertEquals(0.31f, preferences.preferenceFloat)
            assertEquals(0.32f, preferences.preferenceFloatNull)
            assertEquals(0.33f, preferences.preferenceFloatDefault)
            assertEquals(0.34f, preferences.preferenceFloatDefaultNull)

            assertEquals(true, preferences.preferenceBoolean)
            assertEquals(true, preferences.preferenceBooleanNull)
            assertEquals(true, preferences.preferenceBooleanDefault)
            assertEquals(true, preferences.preferenceBooleanDefaultNull)

            assertEquals(setOf("a1"), preferences.preferenceStringSet)
            assertEquals(setOf("a2"), preferences.preferenceStringSetNull)
            assertEquals(setOf("a3"), preferences.preferenceStringSetDefault)
            assertEquals(setOf("a4"), preferences.preferenceStringSetDefaultNull)
        }

    }

    fun assertEquals(first: Any?, second: Any?) {
        if (first != second) throw Throwable("error")
    }
}
