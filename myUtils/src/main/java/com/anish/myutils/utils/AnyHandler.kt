package com.anish.myutils.utils

object AnyHandler {

    fun getDouble(any: Any?): Double {
        var finalVal = 0.00
        if (any == null) {
            return finalVal
        } else if (any is String) {
            finalVal = any.toDouble()
            return finalVal
        } else {
            finalVal = any as Double
            return finalVal
        }

    }
}