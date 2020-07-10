package com.github.urlshorten;

import java.math.BigInteger;
import java.util.function.Function;

public enum IdStrategy implements Function<Long, String> {
    BASE_62 {

        private char[] corpus   = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

        @Override
        public String apply(Long seed) {
            var number = Long.toString(seed);
            var buf = new char[number.length()];
            var charPos = number.length() - 1;
            var bigIntegerNumber = BigInteger.valueOf(seed);
            var radix = BigInteger.valueOf(corpus.length);

            while (bigIntegerNumber.compareTo(radix) >= 0) {
                buf[charPos--] = corpus[bigIntegerNumber.mod(radix).intValue()];
                bigIntegerNumber = bigIntegerNumber.divide(radix);
            }
            buf[charPos] = corpus[bigIntegerNumber.intValue()];
            return new String(buf, charPos, (number.length() - charPos));
        }
    },
    BASE_10 {
        @Override
        public String apply(Long seed) {
            var value = BigInteger.ZERO;
            for (char c : Long.toString(seed).toCharArray())
            {
                value = value.multiply(BigInteger.valueOf(62));
                if ('0' <= c && c <= '9')
                {
                    value = value.add(BigInteger.valueOf(c - '0'));
                }
                if ('a' <= c && c <= 'z')
                {
                    value = value.add(BigInteger.valueOf(c - 'a' + 10));
                }
                if ('A' <= c && c <= 'Z')
                {
                    value = value.add(BigInteger.valueOf(c - 'A' + 36));
                }
            }
            return value.toString();
        }
    }
}
