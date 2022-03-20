package net.dogs.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch
import net.dogs.data.adapter.PicturesAdapter
import net.dogs.databinding.ActivityPicturesBinding
import net.dogs.dialog.CustomLoadingDialog

class PicturesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPicturesBinding
    private lateinit var viewModel: PicturesViewModel
    private var loadingUI: CustomLoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPicturesBinding.inflate(layoutInflater)

        binding.btnRefresh.setOnClickListener {
            lifecycleScope.launch {
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
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) View.VISIBLE
            else View.GONE
        }
        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

}