package com.practicum.playlistmaker

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val coverImage = findViewById<ImageView>(R.id.cover)
        val backButton = findViewById<View>(R.id.back_button)
        val trackName = findViewById<TextView>(R.id.track_name)
        val trackGroup = findViewById<TextView>(R.id.track_group)
        val duration = findViewById<TextView>(R.id.duration)
        val albumName = findViewById<TextView>(R.id.album)
        val year = findViewById<TextView>(R.id.year)
        val genre = findViewById<TextView>(R.id.genre)
        val country = findViewById<TextView>(R.id.country)
        val track = intent.getParcelableExtra<Track>("track")

        backButton.setOnClickListener {
            finish()
        }

        fun getTrackInfo(track: Track) {
            trackName.text = track.trackName
            trackGroup.text = track.artistName
            duration.text =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
            albumName.text =
                if (track.collectionName!!.isNotEmpty()) track.collectionName else ""
            year.text = track.releaseDate?.substring(0, 4)
            genre.text = track.primaryGenreName
            country.text = track.country

            getTrackInfo(track!!)

            val artworkUrl512 = track.artworkUrl100?.replaceAfterLast('/', "512x512bb.jpg")

            Glide.with(this)
                .load(artworkUrl512)
                .placeholder(R.drawable.album_3x)
                .centerCrop()
                .transform(RoundedCorners(this.resources.getDimensionPixelSize(R.dimen.dm2)))
                .into(coverImage)
        }
    }
}