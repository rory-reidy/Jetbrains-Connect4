package connectfour

fun checkVertical(board: MutableList<MutableList<Char>>) : Boolean {
    //println("Vertical")
    for (column in board) {
        for (i in 0..column.size - 4) {
            if (column[i] == column[i+1] && column[i+1] == column[i+2] && column[i+2] == column[i+3] && column[i] != ' ') {
                return true
            }
        }
    }
    return false
}
fun checkHorizontal(board: MutableList<MutableList<Char>>) : Boolean {
    //println("horizontal")
    for (i in board[0].indices) {
        for (j in 0..board.size-4) {
            if (board[j][i] == board[j+1][i] && board[j][i] == board[j+2][i] && board[j][i] == board[j+3][i] && board[j][i] != ' ') {
                return true
            }
        }
    }
    return false
}
fun checkDiag1(board: MutableList<MutableList<Char>>) : Boolean {
    for (i in 0..board[0].size - 4) {
        for (j in 0..board.size-4) {
            if (board[j][i] == board[j+1][i+1] && board[j][i] == board[j+2][i+2] && board[j][i] == board[j+3][i+3] && board[j][i] != ' ') {
                return true
            }
        }
    }
    return false
}
fun checkDiag2(board: MutableList<MutableList<Char>>) : Boolean {
    for (i in 0..board[0].size-4) {
        for (j in 3 until board.size) {
            if (board[j][i] == board[j-1][i+1] && board[j][i] == board[j-2][i+2] && board[j][i] == board[j-3][i+3] && board[j][i] != ' ') {
                return true
            }
        }
    }
    return false
}

fun checkWin(board: MutableList<MutableList<Char>>) : Boolean {
    return (checkHorizontal(board) || checkVertical(board) || checkDiag1(board) || checkDiag2(board))
}

fun checkDraw(board: MutableList<MutableList<Char>>) : Boolean {
    for (column in board) {
        if (column.contains(' ')) {
            return false
        }
    }
    return true
}

fun main() {
    println("Connect Four")
    println("First player's name:")
    val playerOne = readln()
    println("Second player's name:")
    val playerTwo = readln()

    var rows = 6
    var columns = 7

    var cont = true
    while (cont) {
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        val input = readln().trim().lowercase()
        if (input.isNotEmpty()) {
            if (input.matches(Regex("[0-9]+\\s*x\\s*[0-9]+"))) {
                val coords = input.split('x')
                if (coords[0].trim().toInt() in 5..9) {
                    cont = false
                    rows = coords[0].trim().toInt()
                } else {
                    cont = true
                    println("Board rows should be from 5 to 9")
                } // 18004633339
                // 605347277262
                if (coords[1].trim().toInt() in 5..9) {
                    columns = coords[1].trim().toInt()
                } else {
                    cont = true
                    println("Board columns should be from 5 to 9")
                }
            } else {
                println("Invalid input")
            }
        } else if (input.isEmpty()) {
            cont = false
        }
    }
    var numGames = 1
    var c = true
    while (c) {
        println("Do you want to play single or multiple games?")
        println("For a single game, input 1 or press Enter")
        print("Input a number of games:")
        val entry = readln()
        if (entry.isEmpty()) {
            c = false
        } else if (entry.toIntOrNull() == null || entry.toInt() < 1) {
            println("Invalid input")
        } else {
            numGames = entry.toInt()
            c = false
        }
    }
    println("$playerOne VS $playerTwo")
    println("$rows X $columns board")
    if (numGames == 1) {
        println("Single Game")
    } else {
        println("Total $numGames games")
    }
    //making column lists
    val columnsList = mutableListOf<MutableList<Char>>()
    for (i in 1..columns) {
        val columnList = mutableListOf<Char>()
        for (j in 1..rows) {
            columnList += ' '
        }
        columnsList += columnList
    }
    var scoreOne = 0
    var scoreTwo = 0
    var gameCount = 1
    var first = true
    var repeat = false
    if (numGames > 1) {
        println("Game #1")
    }
    while (true) {
        if (!repeat) {
            for (x in 1..columns) {
                print(" $x")
            }
            println()
            for (y in 1..rows) {
                var line = "║"
                for (x in 1..columns) {
                    line += columnsList[x - 1][rows - y]
                    line += "║"
                }
                println(line)
            }
            print("╚")
            for (x in 2..columns) {
                print("═╩")
            }
            print("═╝\n")
        }
        val player = if (first) {
            playerOne
        } else {
            playerTwo
        }
        println("$player's turn:")
        val entry = readln()
        var number: Int
        if (entry.matches(Regex("[0-9]+"))) {
            number = entry.toInt()
            if (number in 1..columns) {
                val rowNum = columnsList[number-1].indexOf(' ')
                if (rowNum == -1) {
                    println("Column $number is full")
                    repeat = true
                    first = !first
                } else {
                    repeat = false
                    var newChar = 'o'
                    if (!first) {
                        newChar = '*'
                    }
                    columnsList[number-1][rowNum] = newChar
                    if (checkWin(columnsList)) {
                        for (x in 1..columns) {
                            print(" $x")
                        }
                        println()
                        for (y in 1..rows) {
                            var line = "║"
                            for (x in 1..columns) {
                                line += columnsList[x - 1][rows - y]
                                line += "║"
                            }
                            println(line)
                        }
                        print("╚")
                        for (x in 2..columns) {
                            print("═╩")
                        }
                        print("═╝\n")
                        println("Player $player won")
                        columnsList.clear()
                        for (i in 1..columns) {
                            val columnList = mutableListOf<Char>()
                            for (j in 1..rows) {
                                columnList += ' '
                            }
                            columnsList += columnList
                        }
                        if (first) {
                            scoreOne+=2
                        } else {
                            scoreTwo+=2
                        }
                        if (numGames > 1) {
                            println("Score")
                            println("$playerOne: $scoreOne $playerTwo: $scoreTwo")
                        }
                        if (numGames == gameCount) {
                            println("Game over!")
                            return
                        } else {
                            gameCount++
                            println("Game #$gameCount")
                            first = (gameCount % 2 == 0)
                        }
                    } else if (checkDraw(columnsList)) {
                        for (x in 1..columns) {
                            print(" $x")
                        }
                        println()
                        for (y in 1..rows) {
                            var line = "║"
                            for (x in 1..columns) {
                                line += columnsList[x - 1][rows - y]
                                line += "║"
                            }
                            println(line)
                        }
                        print("╚")
                        for (x in 2..columns) {
                            print("═╩")
                        }
                        print("═╝\n")
                        println("It is a draw")
                        scoreOne++
                        scoreTwo++
                        columnsList.clear()
                        for (i in 1..columns) {
                            val columnList = mutableListOf<Char>()
                            for (j in 1..rows) {
                                columnList += ' '
                            }
                            columnsList += columnList
                        }
                        if (numGames > 1) {
                            println("Score")
                            println("$playerOne: $scoreOne $playerTwo: $scoreTwo")
                        }
                        if (numGames == gameCount) {
                            println("Game over!")
                            return
                        } else {
                            gameCount++
                            println("Game #$gameCount")
                            first = (gameCount % 2 == 0)
                        }
                    }
                }
            } else {
                println("The column number is out of range (1 - $columns)")
                repeat = true
                first = !first
            }
        } else if (entry == "end") {
            println("Game over!")
            return
        } else {
            println("Incorrect column number")
            repeat = true
            first = !first
        }
        first = !first
    }
}