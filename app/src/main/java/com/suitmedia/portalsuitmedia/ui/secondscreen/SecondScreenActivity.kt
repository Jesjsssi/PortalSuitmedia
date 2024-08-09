package com.suitmedia.portalsuitmedia.ui.secondscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.suitmedia.portalsuitmedia.R
import com.suitmedia.portalsuitmedia.databinding.ActivitySecondScreenBinding
import com.suitmedia.portalsuitmedia.ui.thirdscreen.ThirdScreenActivity

class SecondScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name = intent.getStringExtra(TAG)
        binding.tvName.text = name

        binding.ivPressBack.setOnClickListener {
            finish()
        }

        binding.btnUser.setOnClickListener {
            val intent = Intent(this, ThirdScreenActivity::class.java)
            @Suppress("DEPRECATION")
            startActivityForResult(intent, REQUEST_CODE)
        }
    }



    companion object {
        const val TAG = "TAG"
        const val REQUEST_CODE = 100
    }
}