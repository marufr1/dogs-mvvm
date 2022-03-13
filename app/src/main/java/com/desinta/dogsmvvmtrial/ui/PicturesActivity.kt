package com.desinta.dogsmvvmtrial.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.desinta.dogsmvvmtrial.ViewModelFactory
import com.desinta.dogsmvvmtrial.data.DogRepository
import com.desinta.dogsmvvmtrial.data.adapter.PicturesAdapter
import com.desinta.dogsmvvmtrial.databinding.ActivityPicturesBinding
import com.desinta.dogsmvvmtrial.dialog.CustomLoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PicturesActivity : AppCompatActivity(), PicturesView {

    private lateinit var binding: ActivityPicturesBinding
    private lateinit var viewModel: PicturesViewModel
    private var loadingUI: CustomLoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPicturesBinding.inflate(layoutInflater)

        val factory = ViewModelFactory(DogRepository(this))
        viewModel = ViewModelProvider(this, factory).get(PicturesViewModel::class.java).apply { view = this@PicturesActivity }
        setContentView(binding.root)

        loadingUI = CustomLoadingDialog(this)
        binding.btnRefresh.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.fetchAndLoadDog()
            }
        }
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.dogs.observe(this) {
            binding.recyclerDogs.apply {
                this.layoutManager = GridLayoutManager(context, 3)
                this.adapter = PicturesAdapter(it)
            }
        }
    }

    override fun showLoading() {
        runOnUiThread {
            loadingUI?.show()
        }
    }

    override fun hideLoading() {
        runOnUiThread {
            loadingUI?.dismiss()
        }
    }

    override fun showMessage(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}