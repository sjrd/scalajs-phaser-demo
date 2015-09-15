package pairs.client.phaser

import scala.scalajs.js
import js.annotation._

@js.native
@JSName("Phaser.State")
abstract class State extends js.Object {
  protected final def game: Game = js.native

  protected final def load: Loader = js.native

  def preload(): Unit = js.native

  def create(): Unit = js.native
}
