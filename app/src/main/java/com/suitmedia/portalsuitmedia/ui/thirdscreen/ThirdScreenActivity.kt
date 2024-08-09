package com.suitmedia.portalsuitmedia.ui.thirdscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.suitmedia.portalsuitmedia.databinding.ActivityThirdScreenBinding
import com.suitmedia.portalsuitmedia.ui.ViewModelFactory
import com.suitmedia.portalsuitmedia.Result
import com.suitmedia.portalsuitmedia.adapter.UserAdapter

class ThirdScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdScreenBinding
    private lateinit var viewModel: ThirdViewModel
    private var currentPage = 1
    private val perPage = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupViewModel()
        setupUserList()
    }

    private fun setupUI() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ivPressBack.setOnClickListener {
            finish()
        }
    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[ThirdViewModel::class.java]
    }

    private fun setupUserList() {
        val userAdapter = UserAdapter()
        binding.rvListUser.apply {
            layoutManager = LinearLayoutManager(this@ThirdScreenActivity)
            setHasFixedSize(true)
            adapter = userAdapter
        }

        viewModel.getUsers(currentPage, perPage).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    result.data.observe(this) { pagingData ->
                        userAdapter.submitData(lifecycle, pagingData)
                    }
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.getUsers(currentPage, perPage).observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.swipeRefreshLayout.isRefreshing = false
                        result.data.observe(this) { pagingData ->
                            userAdapter.submitData(lifecycle, pagingData)
                        }
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.swipeRefreshLayout.isRefreshing = false
                        Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}