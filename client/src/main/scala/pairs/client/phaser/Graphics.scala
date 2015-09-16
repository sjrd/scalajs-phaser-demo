package pairs.client.phaser

import scala.scalajs.js
import js.annotation._

@js.native
@JSName("Phaser.Graphics")
class Graphics protected () extends js.Object {
  def clear(): Unit = js.native
  def beginFill(color: Int): Unit = js.native
  def endFill(): Unit = js.native
  def drawPolygon(path: js.Array[PointLike]): Unit = js.native
}
