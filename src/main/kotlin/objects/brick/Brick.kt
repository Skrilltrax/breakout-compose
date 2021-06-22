import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import objects.common.BrickObject
import utils.xOffset
import utils.yOffset

@Composable
fun Brick(brickObject: BrickObject) {
  val bottomBarModifier = Modifier
    .offset(brickObject.xOffset, brickObject.yOffset)
    .size(brickObject.width, brickObject.height)
    .background(brickObject.color)

  Box(bottomBarModifier)
}
