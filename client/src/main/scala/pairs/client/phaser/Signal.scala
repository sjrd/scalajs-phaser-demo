package pairs.client.phaser

import scala.scalajs.js
import js.annotation._

@js.native
@JSName("Phaser.Signal")
class Signal[JSListenerType <: js.Function, ScalaListenerType] extends js.Object {
  @JSName("add")
  def addInternal(listener: JSListenerType, context: Any = js.undefined,
      priority: Double = 0): Unit = js.native
}

object Signal {
  implicit class SignalOps[JSListenerType <: js.Function, ScalaListenerType](
      val self: Signal[JSListenerType, ScalaListenerType]) extends AnyVal {
    def add(listener: ScalaListenerType)(
        implicit ev: ScalaListenerType => JSListenerType): Unit =
      self.addInternal(listener)

    def add(priority: Double)(listener: ScalaListenerType)(
        implicit ev: ScalaListenerType => JSListenerType): Unit =
      self.addInternal(listener, priority = priority)
  }
}
