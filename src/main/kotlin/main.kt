import androidx.compose.desktop.LocalAppWindow
import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import game.Game
import game.GameState
import game.GameStatus

fun main() = Window(size = IntSize(800, 600), title = "Breakout Compose") {
  val game = remember { Game() }
  val density = LocalDensity.current
  LaunchedEffect(Unit) {
    while (true) {
      withFrameNanos {
        game.update(it)
      }
    }
  }

  Breakout(game)
}

@Composable
fun Breakout(game: Game, color: Color = Constants.Breakout.BACKGROUND_COLOR) {
  LocalAppWindow.current.keyboard.onKeyEvent = { keyEvent ->
    if (game.gameState == GameState.STOPPED) {
      game.startGame()
      true
    } else {
      handleKeyEvent(keyEvent.nativeKeyEvent.keyCode, game.paddle)
    }
  }

  if (game.gameState == GameState.STOPPED) {
    Box(modifier = Modifier.background(color).fillMaxHeight().fillMaxWidth(), contentAlignment = Alignment.Center) {
      val text = when (game.gameStatus) {
        GameStatus.IDLE -> "BREAKOUT"
        GameStatus.LOST -> "YOU LOST"
        GameStatus.WON -> "YOU WONT"
      }
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text, modifier = Modifier.padding(10.dp), fontSize = 32.sp, color = Color.White)
        Text("Press any key to start the game", modifier = Modifier.padding(6.dp), fontSize = 24.sp, color = Color.White)
      }
    }
  } else {
    Box(modifier = Modifier.background(color).fillMaxHeight().fillMaxWidth()) {
      Ball(game.ball)
      Paddle(game.paddle)
      game.bricks.forEach {
        Brick(it)
      }
    }
  }
}