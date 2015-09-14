package pairs.client.phaser

import scala.scalajs.js
import js.annotation._

@js.native
@JSName("Phaser.StateManager")
class StateManager(val game: Game) extends js.Object {
  def add(key: String, state: State,
      autoStart: Boolean = false): Unit = js.native

  def start(key: String): Unit = js.native
}
