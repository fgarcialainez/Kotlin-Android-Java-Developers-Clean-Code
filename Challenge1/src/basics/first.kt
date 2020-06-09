package basics

fun main() {
    // Print initial message
    print("Enter your name: ")

    // Read input from command line
    val username = readLine()

    // Custom greeting message depending on the user input
    if (username != null && username.isNotEmpty()) {
        println("Welcome $username!")
    }
    else {
        println("Welcome anonymous username!")
    }
}