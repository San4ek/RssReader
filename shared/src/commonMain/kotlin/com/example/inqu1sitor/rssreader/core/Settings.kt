package com.example.inqu1sitor.rssreader.core

class Settings(val defaultFeedUrls: Set<String>) {
    fun isDefault(feedUrl: String) = defaultFeedUrls.contains(feedUrl)
}