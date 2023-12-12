package model.ui.game_pieces

import androidx.compose.ui.graphics.Color

enum class TileStatus {
    INITIAL, MATCH_IN_POSITION, MATCH_OUT_POSITION
}

enum class PieceColor(val backGround: Color, val foreGround: Color) {
    INITIAL(Color.Black, Color.Black),
    MATCH_IN_POSITION(Color(red = 70, 235, 52), Color.White),
    MATCH_OUT_POSITION(Color(red = 235, 204, 52), Color.White);

    companion object {
        fun getColor(tileData: TileData): PieceColor {
            return when (tileData.status) {
                TileStatus.INITIAL -> INITIAL
                TileStatus.MATCH_IN_POSITION -> MATCH_IN_POSITION
                TileStatus.MATCH_OUT_POSITION -> MATCH_OUT_POSITION
            }
        }
    }
}


data class TileData(val char: Char, val status: TileStatus, val rowPosition: Int = 0)
