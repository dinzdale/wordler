package model.ui.game_pieces

import androidx.compose.ui.graphics.Color

enum class TileStatus {
    EMPTY, NO_MATCH, MATCH_IN_POSITION, MATCH_OUT_POSITION
}

enum class PieceColor(val backGround: Color, val foreGround: Color) {
    INITIAL(Color.Black, Color.Black),
    NO_MATCH(Color.Black, Color.White),
    MATCH_IN_POSITION(Color(red = 70, 235, 52), Color.White),
    MATCH_OUT_POSITION(Color(red = 235, 204, 52), Color.White);

    companion object {
        fun getColor(tileData: TileData): PieceColor {
            return when (tileData.status) {
                TileStatus.EMPTY -> INITIAL
                TileStatus.NO_MATCH -> NO_MATCH
                TileStatus.MATCH_IN_POSITION -> MATCH_IN_POSITION
                TileStatus.MATCH_OUT_POSITION -> MATCH_OUT_POSITION
            }
        }
    }
}


data class TileData(val char: Char, val status: TileStatus, val columnPosition: Int = 0)
data class RowData(val tileData:List<TileData>, val rowPosition: Int = 0)
