package basics

import kotlin.random.Random

fun main() {
    // Create a collection of 100 random integers between 1 and 100
    val integers  = (1..100).map { (0..100).random() }

    // Print all the integers until an element is less than or equal to 10
    for (integer in integers) {
        if(integer <= 10) {
            break
        }

        println(integer)
    }
}