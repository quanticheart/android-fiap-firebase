package com.quanticheart.core.extensions

fun Double.format(digits: Int) = String.format("%.${digits}f", this)