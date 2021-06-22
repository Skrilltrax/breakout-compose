package utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import objects.common.CommonObject
import org.openrndr.math.Vector2
import kotlin.math.atan2

// Taken from https://github.com/SebastianAigner/asteroids-compose-for-desktop/blob/master/src/main/kotlin/Game.kt#L15
fun Vector2.angle(): Double {
  val rawAngle = atan2(y = this.y, x = this.x)
  return (rawAngle / Math.PI) * 180
}

val CommonObject.xOffset: Dp get() = position.x.dp - (width / 2)
val CommonObject.yOffset: Dp get() = position.y.dp - (height / 2)

val CommonObject.maxX get() = this.position.x + this.width.value / 2
val CommonObject.minX get() = this.position.x - this.width.value / 2

val CommonObject.maxY get() = this.position.y + this.height.value / 2
val CommonObject.minY get() = this.position.y - this.height.value / 2