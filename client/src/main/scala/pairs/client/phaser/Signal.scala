package pairs.client.phaser

import scala.scalajs.js
import js.annotation._

@js.native
@JSName("Phaser.Signal")
class Signal[ListenerType <: js.Function] extends js.Object {
  def add(listener: ListenerType): Unit = js.native
}
