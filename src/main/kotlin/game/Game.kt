package game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import objects.common.BallObject
import objects.common.PaddleObject
import objects.common.BrickObject
import org.openrndr.math.Vector2
import utils.*

class Game {
  var prevTime = 0L
  val ball by mutableStateOf(BallObject())
  val paddle by mutableStateOf(PaddleObject())
  val bricks = mutableStateListOf<BrickObject>()
  var gameState by mutableStateOf(GameState.STOPPED)
  var gameStatus by mutableStateOf(GameStatus.IDLE)

  fun startGame() {
    ball.position = Vector2(Constants.Breakout.BALL_INITIAL_X.toDouble(), Constants.Breakout.BALL_INITIAL_Y.toDouble())
    paddle.position = Vector2(Constants.Breakout.PADDLE_INITIAL_X.toDouble(), Constants.Breakout.PADDLE_INITIAL_Y.toDouble())
    ball.movementVector = Vector2(0.0, 2.0)
    bricks.addAll(createLevel(4, 8))
    gameState = GameState.RUNNING
  }

  fun update(time: Long) {
    val delta = time - prevTime
    val floatDelta = (delta / 1E8).toFloat()
    prevTime = time

    if (gameState == GameState.STOPPED) return
    ball.update(floatDelta)

    if (collideWithPaddle()) {
      // If ball's center is outside the paddle and ball is coming from left
      if (ball.position.x <= paddle.minX && ball.movementVector.x > 0) {
        ball.movementVector = ball.movementVector * -1.0
        return
      }

      // If ball's center is outside the paddle and ball is coming from right
      if (ball.position.x >= paddle.maxX && ball.movementVector.x < 0) {
        ball.movementVector = ball.movementVector * -1.0
        return
      }

      // https://www.informit.com/articles/article.aspx?p=2180417&seqNum=2
      // If the ball collides with the left part reflect it from a vector
      if (ball.position.x <= paddle.minX + 30) {
        ball.movementVector = ball.movementVector.reflect(Vector2(-0.196, -0.981))
        return
      }
      if (ball.position.x >= paddle.maxX - 30) {
        ball.movementVector = ball.movementVector.reflect(Vector2(0.196, -0.981))
        return
      }

      ball.movementVector = ball.movementVector.copy(y = ball.movementVector.y * -1.0)
      return
    }

    if (collideWitBottomWall()) {
      lostGame()
      return
    }

    if (bricks.isEmpty()) {
      wonGame()
      return
    }

    if (collideWithEndWall()) {
      ball.movementVector = ball.movementVector.copy(x = -1 * ball.movementVector.x)
    }
    if (collideWithTopWall()) {
      ball.movementVector = ball.movementVector.copy(y = -1 * ball.movementVector.y)
    }
    if (collideWithStartWall()) {
      ball.movementVector = ball.movementVector.copy(x = -1 * ball.movementVector.x)
    }

    val removedBrick = collideWithBricks()
    if (removedBrick != null) {
      // If ball's center is outside the paddle and ball is coming from left
      if (ball.position.x <= removedBrick.minX && ball.movementVector.x > 0) {
        ball.movementVector = ball.movementVector * -1.0
        return
      }

      // If ball's center is outside the paddle and ball is coming from right
      if (ball.position.x >= removedBrick.maxX && ball.movementVector.x < 0) {
        ball.movementVector = ball.movementVector * -1.0
        return
      }

      // https://www.informit.com/articles/article.aspx?p=2180417&seqNum=2
      // If the ball collides with the left part reflect it from a vector
      // if (ball.position.x <= paddle.minX + 30) {
      //   ball.movementVector = ball.movementVector.reflect(Vector2(-0.196, -0.981))
      //   return
      // }
      // if (ball.position.x >= paddle.maxX - 30) {
      //   ball.movementVector = ball.movementVector.reflect(Vector2(0.196, -0.981))
      //  return
      // }

      ball.movementVector = ball.movementVector.copy(y = ball.movementVector.y * -1.0)
      return
    }
  }

  private fun collideWithBricks(): BrickObject? {
    for (i in bricks.size - 1 downTo 0) {
      if (collideWithBrick(bricks[i])) {
        val removedBrick = bricks.removeAt(i)
        return removedBrick
      }
    }

    return null
  }

  private fun lostGame() {
    bricks.clear()
    gameState = GameState.STOPPED
    gameStatus = GameStatus.LOST
  }

  private fun wonGame() {
    gameState = GameState.STOPPED
    gameStatus = GameStatus.WON
  }

  private fun collideWithBrick(brickObject: BrickObject): Boolean = ball.overlapsWith(brickObject)

  private fun collideWithPaddle(): Boolean = ball.overlapsWith(paddle)

  private fun collideWithOtherWalls(): Boolean = collideWithStartWall() or collideWithTopWall() or collideWithEndWall()

  private fun collideWithEndWall(): Boolean = ball.maxX >= Constants.Window.WINDOW_WIDTH

  private fun collideWithStartWall(): Boolean = ball.minX <= 0

  private fun collideWitBottomWall(): Boolean = ball.maxY >= Constants.Window.WINDOW_HEIGHT

  private fun collideWithTopWall(): Boolean = ball.minY <= 0
}