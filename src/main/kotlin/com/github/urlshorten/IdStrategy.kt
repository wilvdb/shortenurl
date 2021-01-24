package com.github.urlshorten

import java.math.BigInteger
import java.util.function.Function

enum class IdStrategy : Function<Long, String> {
    BASE_62 {
        private val corpus = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray()
        override fun apply(seed: Long): String {
            val number = seed.toString()
            val buf = CharArray(number.length)
            var charPos = number.length - 1
            var bigIntegerNumber = BigInteger.valueOf(seed)
            val radix = BigInteger.valueOf(corpus.size.toLong())
            while (bigIntegerNumber >= radix) {
                buf[charPos--] = corpus[bigIntegerNumber.mod(radix).toInt()]
                bigIntegerNumber = bigIntegerNumber.divide(radix)
            }
            buf[charPos] = corpus[bigIntegerNumber.toInt()]
            return String(buf, charPos, number.length - charPos)
        }
    },
    BASE_10 {
        override fun apply(seed: Long): String {
            var value = BigInteger.ZERO
            for (c in java.lang.Long.toString(seed).toCharArray()) {
                value = value.multiply(BigInteger.valueOf(62))
                if (c in '0'..'9') {
                    value = value.add(BigInteger.valueOf((c - '0').toLong()))
                }
                if (c in 'a'..'z') {
                    value = value.add(BigInteger.valueOf((c - 'a' + 10).toLong()))
                }
                if (c in 'A'..'Z') {
                    value = value.add(BigInteger.valueOf((c - 'A' + 36).toLong()))
                }
            }
            return value.toString()
        }
    }
}