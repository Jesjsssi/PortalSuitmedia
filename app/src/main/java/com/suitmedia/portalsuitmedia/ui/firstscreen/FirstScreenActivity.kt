package com.suitmedia.portalsuitmedia.ui.firstscreen

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.suitmedia.portalsuitmedia.R
import com.suitmedia.portalsuitmedia.databinding.ActivityFirstScreenBinding
import com.suitmedia.portalsuitmedia.ui.secondscreen.SecondScreenActivity

class FirstScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnCheck.setOnClickListener {
            val text = binding.etPalindrome.text.toString()
            val message = if (isPalindrome(text)) {
                "$text is a palindrome"
            } else {
                "$text is not a palindrome"
            }

            AlertDialog.Builder(this)
                .setTitle("Palindrome Check")
                .setMessage(message)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        binding.btnNext.setOnClickListener {
            if (binding.etName.text.toString().isEmpty()) {
                Toast.makeText(this, "Please input your name", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, SecondScreenActivity::class.java)
                intent.putExtra(SecondScreenActivity.TAG, binding.etName.text.toString())
                startActivity(intent)
            }
        }
    }

    private fun isPalindrome(str: String): Boolean {
        val cleanedStr = str.replace(" ", "")
        return cleanedStr == cleanedStr.reversed()
    }
}