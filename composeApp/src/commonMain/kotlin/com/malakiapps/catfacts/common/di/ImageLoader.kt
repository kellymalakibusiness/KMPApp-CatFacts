package com.malakiapps.catfacts.common.di

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import okio.FileSystem

@OptIn(ExperimentalCoilApi::class)
@Composable
fun setImageLoaderBuilder(){
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, 0.1)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.05)
                    .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "cats_image_cache")
                    .build()
            }
            .logger(DebugLogger())
            .crossfade(true)
            .build()
    }
}