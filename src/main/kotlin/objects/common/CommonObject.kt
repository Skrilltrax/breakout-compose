package objects.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.openrndr.math.Vector2
import utils.angle
import kotlin.math.max
import kotlin.math.min

// Taken from https://github.com/SebastianAigner/asteroids-compose-for-desktop/blob/master/src/main/kotlin/GameObject.kt
sealed class CommonObject(
  val width: Dp,
  val height: Dp,
  val color: Color,
  speed: Double = 0.0,
  angle: Double = 0.0,
  position: Vector2 = Vector2.ZERO
) {
  var speed by mutableStateOf(speed)
  var angle by mutableStateOf(angle)
  var position by mutableStateOf(position)
  var movementVector
    get() = (Vector2.UNIT_X * speed).rotate(angle)
    set(value) {
      speed = value.length
      angle = value.angle()
    }

  fun update(timeDiff: Float) {
    position += movementVector
  }
}

class BallObject(diameter: Dp = Constants.Breakout.BALL_SIZE.dp, color: Color = Color.White) :
  CommonObject(diameter, diameter, color) {
  private val radius = diameter / 2

  @Suppress("LocalVariableName")
  fun overlapsWith(commonObject: CommonObject): Boolean {
    val X1: Double = commonObject.position.x - (commonObject.width / 2).value
    val Y1: Double = commonObject.position.y - (commonObject.height / 2).value

    val X2: Double = commonObject.position.x + (commonObject.width / 2).value
    val Y2: Double = commonObject.position.y + (commonObject.height / 2).value

    val Xc: Double = this.position.x
    val Yc: Double = this.position.y
    // Find the nearest point on the
    // rectangle to the center of
    // the circle
    val Xn: Int = max(X1, min(Xc, X2)).toInt()
    val Yn: Int = max(Y1, min(Yc, Y2)).toInt()

    // Find the distance between the
    // nearest point and the center
    // of the circle
    // Distance between 2 points,
    // (x1, y1) & (x2, y2) in
    // 2D Euclidean space is
    // ((x1-x2)**2 + (y1-y2)**2)**0.5
    val Dx: Int = Xn - Xc.toInt()
    val Dy: Int = Yn - Yc.toInt()

    return Dx * Dx + Dy * Dy <= (radius.value * radius.value)
  }
}

class PaddleObject(
  width: Dp = Constants.Breakout.PADDLE_WIDTH.dp,
  height: Dp = Constants.Breakout.PADDLE_HEIGHT.dp,
  color: Color = Color.Red
) : CommonObject(width, height, color)

class BrickObject(
  width: Dp = Constants.Breakout.BRICK_WIDTH.dp,
  height: Dp = Constants.Breakout.BRICK_HEIGHT.dp,
  color: Color = Color.Green,
) : CommonObject(width, height, color)
