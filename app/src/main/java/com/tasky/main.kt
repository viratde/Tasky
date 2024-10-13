package com.tasky

class Solution {

    fun isValidSudoku(board: Array<CharArray>): Boolean {

        val vAxisData: MutableMap<Int, CharArray> = mutableMapOf()
        val hAxisData: MutableMap<Int, CharArray> = mutableMapOf()
        val gridData: MutableMap<Int, CharArray> = mutableMapOf()

        for (vAxisValue in board.withIndex()) {

            for (hAxisValue in vAxisValue.value.withIndex()) {
                val grid = gridFinder(vAxisValue.index, hAxisValue.index)
                if(vAxisData.containsKey(vAxisValue.index)){
                    if(vAxisData[vAxisValue.index]!!.indexOf(hAxisValue.value) != -1){

                    }
                }else{

                }
            }

        }
        return false
    }

    private fun gridFinder(vAxis: Int, hAxis: Int): Int {
        return (vAxis / 3) * 3 + (hAxis) / 3
    }

    private fun getXAxisData(x: Int, board: Array<CharArray>): CharArray {
        return board[x].filter { it.isDigit() }.toCharArray()
    }

    private fun getYAxisData(y: Int, board: Array<CharArray>): CharArray {
        return board.map { it[y] }.filter { it.isDigit() }.toCharArray()
    }

    private fun gridValuesFinder(grid: Int, board: Array<CharArray>): CharArray {
        val columnStart = (grid % 3) * 3
        val columnIndices = columnStart..columnStart + 2
        val rowStart = (grid / 3) * 3
        return (rowStart..rowStart + 2).map {
            board[it].slice(columnIndices)
        }.flatten().filter { it.isDigit() }.toCharArray()
    }

}

fun main() {

    val s = Solution()

    val board = arrayOf(
        charArrayOf('5', '3', '.', '.', '7', '.', '.', '.', '.'),
        charArrayOf('6', '.', '.', '1', '9', '5', '.', '.', '.'),
        charArrayOf('.', '9', '8', '.', '.', '.', '.', '6', '.'),
        charArrayOf('8', '.', '.', '.', '6', '.', '.', '.', '3'),
        charArrayOf('4', '.', '.', '8', '.', '3', '.', '.', '1'),
        charArrayOf('7', '.', '.', '.', '2', '.', '.', '.', '6'),
        charArrayOf('.', '6', '.', '.', '.', '.', '2', '8', '.'),
        charArrayOf('.', '.', '.', '4', '1', '9', '.', '.', '5'),
        charArrayOf('.', '.', '.', '.', '8', '.', '.', '7', '9')
    )
    s.isValidSudoku(board)


}