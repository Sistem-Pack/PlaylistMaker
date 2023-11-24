package com.practicum.playlistmaker.data.contentprovider.impl

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.Dimension
import com.practicum.playlistmaker.data.contentprovider.ContentProvider

class ContentProviderImpl(private val context: Context) : ContentProvider {
    @SuppressLint("DiscouragedApi")
    override fun getStringFromResources(resourceString: String): String {
        val resourceId = context.resources
            .getIdentifier(resourceString, "string", context.packageName)
        return context.getString(resourceId)
    }

    @SuppressLint("DiscouragedApi", "UseCompatLoadingForDrawables")
    override fun getDrawableFromResources(resourceString: String): Drawable? {
        val resourceId = context.resources
            .getIdentifier(resourceString, "drawable", context.packageName)
        return context.getDrawable(resourceId)
    }

}