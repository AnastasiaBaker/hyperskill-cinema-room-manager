package cinema

fun main() {
    println("Enter the number of rows:")

    val rows = readLine()!!.toInt()

    println("Enter the number of seats in each row:")

    val columns = readLine()!!.toInt()
    val movieHall = MovieHall(rows, columns)

    while (true) {
        println("\n" +
                "1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit")

        when (readLine()!!) {
            "0" -> break
            "1" -> movieHall.printScheme()
            "2" -> movieHall.buyTicket()
            "3" -> movieHall.printStatistics()
        }
    }
}

class MovieHall(private val rows: Int, private val columns: Int) {
    private val seats = Array(rows) { Array(columns) { 'S' } }
    private val totalSeats = rows * columns
    private val middleRow = rows / 2
    private val totalIncome = if (totalSeats <= 60) {
        totalSeats * 10
    } else middleRow * columns * 10 + (rows - middleRow) * columns * 8

    private fun ticketPrice(row: Int): Int = if (totalSeats <= 60) 10 else if (row <= middleRow) 10 else 8

    fun buyTicket() {
        while (true) {
            println("\nEnter a row number:")

            val row = readLine()!!.toInt()

            println("Enter a seat number in that row:")

            val column = readLine()!!.toInt()

            when {
                row > rows || column > columns -> println("\nWrong input!")
                seats[row - 1][column - 1] == 'B' -> println("\nThat ticket has already been purchased!")
                else -> {
                    seats[row - 1][column - 1] = 'B'
                    println("\nTicket price: \$${ticketPrice(row)}")
                    break
                }
            }
        }
    }

    fun printScheme() {
        println("\nCinema:\n${seats[0].indices.joinToString(" ", "  ") { (it + 1).toString() }}")

        for (i in seats.indices) println("${i + 1} ${seats[i].joinToString(" ")}")
    }

    fun printStatistics() {
        var sold = 0
        var income = 0

        seats.forEachIndexed { i, row ->
            row.forEach { if (it == 'B') {
                sold++
                income += ticketPrice(i + 1)
            } }
        }

        val percentage = "%.2f".format(sold / (totalSeats / 100.0))

        println("\nNumber of purchased tickets: $sold\n" +
                "Percentage: $percentage%\n" +
                "Current income: \$$income\n" +
                "Total income: \$$totalIncome")
    }
}
