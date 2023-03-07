package com.robbyari.storyapp.view.detail

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.robbyari.storyapp.R
import com.robbyari.storyapp.databinding.ActivityDetailBinding
import com.robbyari.storyapp.helper.ViewModelFactory
import com.robbyari.storyapp.repository.local.datastore.UserPreference

class DetailActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.detail_story)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#B1464A")))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewModel()
    }

    private fun setupViewModel() {
        val story = intent.getStringExtra(EXTRA_STORY)

        detailViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[DetailViewModel::class.java]

        detailViewModel.getStoryDetail(story!!)

        detailViewModel.detail.observe(this) {
            if (it != null) {
                binding.apply {
                    tvDetailName.text = it.story.name
                    tvCreated.text = it.story.create
                    tvId.text = it.story.id
                    tvDetailDescription.text = it.story.desc
                    Glide.with(this@DetailActivity)
                        .load(it.story.photo)
                        .centerCrop()
                        .into(ivDetailPhoto)
                }
            }
        }
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }

}