package net.dogs.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.dogs.data.adapter.PicturesAdapter
import net.dogs.data.local.DogsDatabase
import net.dogs.databinding.ActivityPicturesBinding
import net.dogs.dialog.CustomLoadingDialog

@AndroidEntryPoint
class PicturesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPicturesBinding
    private val viewModel: PicturesViewModel by viewModels()
    private var loadingUI: CustomLoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPicturesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUI = CustomLoadingDialog(this)
        setupObserver()

        binding.btnRefresh.setOnClickListener {
            lifecycleScope.launch {
                viewModel.fetchAndLoadDog()
            }
        }
    }

    private fun setupObserver() {
        viewModel.loading.observe(this) {
            if (it) loadingUI?.show()
            else loadingUI?.dismiss()
        }
        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
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
}