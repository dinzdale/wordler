package model.ui.game_pieces

import androidx.compose.ui.graphics.Color

enum class TileKeyStatus {
    INITIAL_KEY, EMPTY, NO_MATCH, MATCH_IN_POSITION, MATCH_OUT_POSITION
}

enum class PieceColor(val backGround: Color, val foreGround: Color) {
    INITIAL_KEY(Color(red=102, 107, 100),Color.White),
    INITIAL(Color.Black, Color.Black),
    NO_MATCH(Color.Black, Color.White),
    MATCH_IN_POSITION(Color(red = 70, 235, 52), Color.White),
    MATCH_OUT_POSITION(Color(red = 235, 204, 52), Color.White);

    companion object {
        fun getColor(tileKey: TileKey): PieceColor {
            when (val key = tileKey) {
                val status = when(key) {

                }
                is TileData, is KeyData -> {
                    when (key.) {
                        TileKeyStatus.INITIAL_KEY->INITIAL_KEY
                        TileKeyStatus.EMPTY -> INITIAL
                        TileKeyStatus.NO_MATCH -> NO_MATCH
                        TileKeyStatus.MATCH_IN_POSITION -> MATCH_IN_POSITION
                        TileKeyStatus.MATCH_OUT_POSITION -> MATCH_OUT_POSITION
                    }
                }
                    is KeyData ->{}
            }
            return when (tileData.status) {
                TileKeyStatus.INITIAL_KEY->INITIAL_KEY
                TileKeyStatus.EMPTY -> INITIAL
                TileKeyStatus.NO_MATCH -> NO_MATCH
                TileKeyStatus.MATCH_IN_POSITION -> MATCH_IN_POSITION
                TileKeyStatus.MATCH_OUT_POSITION -> MATCH_OUT_POSITION
            }
        }
    }
}

enum class KeyType {
    ALPHA, ENTER, RETURN
}

sealed class TileKey()
data class TileData(val char: Char, val status: TileKeyStatus, val columnPosition: Int = 0) : TileKey()

data class KeyData(val char: Char?=null, val keyType: KeyType, val status: TileKeyStatus) : TileKey()


data class RowData(val tileData:List<TileData>, val rowPosition: Int = 0)





