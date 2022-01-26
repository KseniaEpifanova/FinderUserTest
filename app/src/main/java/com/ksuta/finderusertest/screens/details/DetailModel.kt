package com.ksuta.finderusertest.screens.details

data class DetailModel(
    val name: String,
    val url: String,
    val reputation: Int?,
    val topTags : String?,
    val location: String?,
    val creationDate: String?
)
