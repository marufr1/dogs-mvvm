package net.dogs.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.dogs.ViewModelFactory
import net.dogs.data.DogRepository
import net.dogs.data.adapter.PicturesAdapter
import net.dogs.data.local.DogsDatabase
import net.dogs.databinding.ActivityPicturesBinding
import net.dogs.dialog.CustomLoadingDialog

class PicturesActivity : AppCompatActivity() {

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

    override fun onDestroy() {
        super.onDestroy()
        DogsDatabase.destroyInstance()
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