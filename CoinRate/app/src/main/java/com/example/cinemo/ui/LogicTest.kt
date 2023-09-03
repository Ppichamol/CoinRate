package com.example.cinemo.ui

class LogicTest {

    private fun validate(input: Int): Boolean {
        val inputString = input.toString()
        val inputArray = inputString.toCharArray()

        return validateNoMoreThanSix(inputArray) &&
                validateNoDoubleDuplicate(inputArray) &&
                validateNoNextCloseNumber(inputArray) &&
                validateNoMoreThanTwoOfDouble(inputArray)
    }

    private fun validateNoMoreThanSix(n: CharArray): Boolean {
        if (n.size < 6) {
            return false
        }
        return true
    }

    private fun validateNoDoubleDuplicate(n: CharArray): Boolean {
        var doubleTextCount = 0

        for (i in 0 until n.size - 1) {
            if (n[i] == n[i + 1]) {
                doubleTextCount++
                if (doubleTextCount == 2) {
                    return false
                }
            } else {
                doubleTextCount = 0
            }
        }

        return true
    }

    private fun validateNoNextCloseNumber(n: CharArray): Boolean {
        var nextCloseNumberCount = 0

        for (i in 0 until n.size - 1) {
            if (n[i] + 1 == n[i + 1]) {
                nextCloseNumberCount++
                if (nextCloseNumberCount == 2) {
                    return false
                }
            } else {
                nextCloseNumberCount = 0
            }
        }

        nextCloseNumberCount = 0

        for (i in 0 until n.size - 1) {
            if (n[i] - 1 == n[i + 1]) {
                nextCloseNumberCount++
                if (nextCloseNumberCount == 2) {
                    return false
                }
            } else {
                nextCloseNumberCount = 0
            }
        }

        return true
    }

    private fun validateNoMoreThanTwoOfDouble(n: CharArray): Boolean {
        var consecutiveCount = 0
        for (i in 0 until n.size step 2) {
            if (i + 1 < n.size && n[i] == n[i + 1]) {
                consecutiveCount++
                if (consecutiveCount > 2) {
                    return false
                }
            }
        }
        consecutiveCount = 0
        for (i in 1 until n.size step 2) {
            if (i + 1 < n.size && n[i] == n[i + 1]) {
                consecutiveCount++
                if (consecutiveCount > 2) {
                    return false
                }
            }
        }
        return true
    }
}