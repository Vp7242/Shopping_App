package com.shoppingapp.store.ui

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.shoppingapp.store.R

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        findViewById<TextView>(R.id.introTitle).text =
            highlight(getString(R.string.intro_title), "Shopping App", "Gateway")

        val signIn = findViewById<TextView>(R.id.signIn)
        signIn.text = highlight(getString(R.string.have_account), "Sign in")

        findViewById<TextView>(R.id.getStarted).setOnClickListener { openHome() }
        signIn.setOnClickListener { openHome() }
    }

    private fun openHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun highlight(text: String, vararg words: String): SpannableString {
        val spannable = SpannableString(text)
        val blue = ContextCompat.getColor(this, R.color.blue)
        for (word in words) {
            val start = text.indexOf(word)
            if (start >= 0) {
                spannable.setSpan(
                    ForegroundColorSpan(blue),
                    start,
                    start + word.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return spannable
    }
}
