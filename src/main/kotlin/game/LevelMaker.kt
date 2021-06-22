package game

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import objects.common.BrickObject
import org.openrndr.math.Vector2

private val colorList = listOf<Color>(Color.Magenta, Color.Blue, Color.Green, Color.Yellow)

fun createLevel(rows: Int, columns: Int): List<BrickObject> {
  var yOffset: Dp = 0.dp
  var xOffset: Dp = 0.dp

  // columns - 1 gaps + start gap + end gap
  val gapWidth = Constants.Breakout.BRICK_COLUMN_GAP * (columns + 1)
  val effectiveWidth = Constants.Window.WINDOW_WIDTH - gapWidth
  val brickWidth = (effectiveWidth / columns).toDouble()
  val brickHeight = Constants.Breakout.BRICK_HEIGHT.toDouble()

  val brickList = mutableListOf<BrickObject>()

  for (i in 0 until rows) {
    yOffset += brickHeight.dp
    xOffset = Constants.Breakout.BRICK_COLUMN_GAP.dp
    for (j in 0 until columns) {
      val brickPosition = getBrickPosition(xOffset, yOffset, brickWidth, brickHeight)

      brickList.add(
        BrickObject(
          width = brickWidth.dp,
          color = colorList[i % colorList.size]
        ).apply {
          position = Vector2(brickPosition.first, brickPosition.second)
        })

      xOffset += (brickWidth.dp + Constants.Breakout.BRICK_COLUMN_GAP.dp)
    }
    yOffset += Constants.Breakout.BRICK_ROW_GAP.dp
  }

  return brickList
}

private fun getBrickPosition(xOffset: Dp, yOffset: Dp, width: Double, height: Double): Pair<Double, Double> {
  return Pair(xOffset.value + width / 2, yOffset.value + height / 2)
}