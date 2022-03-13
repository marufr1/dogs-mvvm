package net.dogs

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import net.dogs.databinding.ActivitySplashBinding
import net.dogs.ui.PicturesActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val splashTime: Long = 3000

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, PicturesActivity::class.java)
            startActivity(intent)
            finish()
        }, splashTime)
    }

    override fun onPause() {
        super.onPause()
        _binding = null
    }

}