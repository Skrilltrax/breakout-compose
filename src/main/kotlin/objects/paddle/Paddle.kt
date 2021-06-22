import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import objects.common.PaddleObject
import org.openrndr.math.Vector2
import utils.xOffset
import utils.yOffset

@Composable
fun Paddle(paddleObject: PaddleObject) {
  val bottomBarModifier = Modifier
    .offset(paddleObject.xOffset, paddleObject.yOffset)
    .size(paddleObject.width, paddleObject.height)
    .background(paddleObject.color)

  Box(bottomBarModifier)
}

fun handleKeyEvent(keyCode: Int, paddleObject: PaddleObject): Boolean {
  if (keyCode == 37) {
    paddleObject.position =
      paddleObject.position.copy(
        x = (paddleObject.position.x - 10).coerceIn(
          minXValueForPaddle(),
          maxXValueForPaddle()
        )
      )
    return true
  }
  if (keyCode == 39) {
    paddleObject.position =
      paddleObject.position.copy(
        x = (paddleObject.position.x + 10).coerceIn(
          minXValueForPaddle(),
          maxXValueForPaddle()
        )
      )
    return true
  }

  return false
}

fun minXValueForPaddle() = (Constants.Breakout.PADDLE_WIDTH / 2).toDouble()
fun maxXValueForPaddle() = (Constants.Window.WINDOW_WIDTH - Constants.Breakout.PADDLE_WIDTH / 2).toDouble()
