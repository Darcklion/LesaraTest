package com.lesaratest.models

data class Product (
        var id: String,
        var name: String,
        var price: Double, //	"14.9900"
        var msrp: String, //	"19.9900"
        var sku: String,
        var enabled_from: String, //	"2017-02-07"
        var show_msrp: String, //	"1"
        var show_msrp_index: Int,
        var discount: Int,
        var thumbnail_path: String
)