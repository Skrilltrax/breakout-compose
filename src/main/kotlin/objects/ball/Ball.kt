import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import objects.common.BallObject
import utils.xOffset
import utils.yOffset

@Composable
fun Ball(ballObject: BallObject) {
  val ballModifier = Modifier
    .offset(ballObject.xOffset, ballObject.yOffset)
    .size(ballObject.width, ballObject.height)
    .background(ballObject.color, shape = CircleShape)

  Box(modifier = ballModifier)
}
