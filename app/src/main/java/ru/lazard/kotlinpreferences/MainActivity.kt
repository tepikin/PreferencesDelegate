package ru.lazard.kotlinpreferences

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity() {


    val text = StringBuilder()
    val textView by lazy { findViewById(R.id.text) as TextView }
    val settings by lazy { MyPreferences(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set default params
        settings.stringParam = null
        settings.intParam = null
        printValues()

        //set new params
        settings.stringParam = "123"
        settings.intParam = 10
        printValues()


    }

    private fun printValues() =
            text.apply {
                settings.apply {
                    appendln("stringParam = $stringParam")
                    appendln("intParam = $intParam\n")
                }
                textView.setText(toString())
            }
}
