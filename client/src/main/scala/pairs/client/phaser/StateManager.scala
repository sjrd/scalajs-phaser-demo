package pairs.client.phaser

import scala.scalajs.js
import js.annotation._
import js.|

@js.native
@JSName("Phaser.StateManager")
class StateManager(val game: Game) extends js.Object {
  def add(key: String, state: State | js.Dynamic,
      autoStart: Boolean = false): Unit = js.native

  def start(key: String): Unit = js.native
}
