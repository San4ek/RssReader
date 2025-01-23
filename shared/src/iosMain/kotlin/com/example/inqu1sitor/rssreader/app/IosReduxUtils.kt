package com.example.inqu1sitor.rssreader.app

import com.example.inqu1sitor.rssreader.core.wrap

fun FeedStore.watchState() = observeState().wrap()
fun FeedStore.watchSideEffect() = observeSideEffect().wrap()