package functional

fun main() {
    // Some faulty data with ages of our users
    val data = mapOf(
        "users1.csv" to listOf(32, 45, 17, -1, 34),
        "users2.csv" to listOf(19, -1, 67, 22),
        "users3.csv" to listOf(),
        "users4.csv" to listOf(56, 32, 18, 44)
    )

    // Find the average age of users (use only valid ages for the calculation!)
    val averagesMap = data.mapValues {
        it.value.filter { age -> age >= 0 }.average()
    }

    println(averagesMap)

    // Extract the names of input files that contain faulty data
    val faultyDataKeys = data.filter {
        it.value.any { age -> age < 0 }
    }.keys

    println(faultyDataKeys)

    // Count the total number of faulty entries across all input files
    val faultyDataMap = data.mapValues {
        it.value.filter { age -> age < 0 }.count()
    }

    println(faultyDataMap)
}