import androidx.compose.ui.graphics.Color

object Constants {

  object Window {
    const val WINDOW_WIDTH = 800
    const val WINDOW_HEIGHT = 600
  }

  object Breakout {
    const val BALL_SIZE = 20
    const val BALL_INITIAL_X = Window.WINDOW_WIDTH / 2
    const val BALL_INITIAL_Y = 400

    const val PADDLE_WIDTH = 150
    const val PADDLE_HEIGHT = 10
    const val PADDLE_INITIAL_X = Window.WINDOW_WIDTH / 2
    const val PADDLE_INITIAL_Y = 550

    const val BRICK_WIDTH = 40
    const val BRICK_HEIGHT = 20
    const val BRICK_COLUMN_GAP = 4
    const val BRICK_ROW_GAP = 4

    val BALL_COLOR = Color.White
    val BOTTOM_BAR_COLOR = Color.Yellow
    val BACKGROUND_COLOR = Color.Black
  }
}