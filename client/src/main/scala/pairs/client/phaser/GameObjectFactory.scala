package pairs.client.phaser

import scala.scalajs.js
import js.annotation._

@js.native
@JSName("Phaser.GameObjectFactory")
class GameObjectFactory(game: Game) extends js.Object {
  def sprite(x: Double = 0, y: Double = 0,
      key: String = js.native): Sprite = js.native

  def graphics(x: Double = 0, y: Double = 0): Graphics = js.native
}
