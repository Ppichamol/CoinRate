package com.example.cinemo.ui

class Bonus {

    fun generateFibonacci(n: Int): List<Int> {
        val result = mutableListOf<Int>()
        if (n <= 0) {
            return result
        }

        var a = 0
        var b = 1

        repeat(n) {
            result.add(a)
            val nextNum = a + b
            a = b
            b = nextNum
        }

        return result
    }

    fun filterTwoArray(array1: List<Int>, array2: List<Int>): List<Int> {
        val filterArray = mutableListOf<Int>()

        for (numArray1 in array1) {
            for (numArray2 in array2) {
                if (numArray1 == numArray2) {
                    filterArray.add(numArray2)
                }
            }
        }

        return filterArray
    }

    fun findPrimeNumber(n: Int): List<Int> {
        fun isPrime(number: Int): Boolean {
            if (number <= 1) {
                return false
            }

            if (number <= 3) {
                return true
            }

            if (number % 2 == 0 || number % 3 == 0) {
                return false
            }

            var i = 5

            while (i * i <= number) {
                if (number % i == 0 || number % (i + 2) == 0) {
                    return false
                }
                i += 6
            }

            return true
        }

        val primeNumberList = mutableListOf<Int>()
        for (i in 0..n) {
            if (isPrime(i)) {
                primeNumberList.add(i)
            }
        }

        return primeNumberList
    }
}