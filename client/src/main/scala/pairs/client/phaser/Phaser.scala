package pairs.client.phaser

import scala.scalajs.js
import js.annotation._
import js.|

@js.native
@JSGlobal
object Phaser extends js.Object {
  val AUTO: Int = js.native

  @js.native
  class Game(
      width: Double = 800, height: Double = 600,
      renderer: Int = AUTO, parent: String = ""
  ) extends js.Object {
    val state: StateManager = js.native
    val add: GameObjectFactory = js.native
  }

  @js.native
  class StateManager extends js.Object {
    def add(name: String, ctor: js.Any): Unit = js.native
    def start(name: String): Unit = js.native
  }

  @js.native
  abstract class State extends js.Object {
    protected val game: Game = js.native
    protected val load: Loader = js.native
  }

  @js.native
  class Loader extends js.Object {
    def image(key: String, url: String,
        overwrite: Boolean = false): Unit = js.native
  }

  @js.native
  class GameObjectFactory extends js.Object {
    def sprite(x: Double, y: Double, key: String): Sprite = js.native
  }

  @js.native
  class Sprite extends js.Object {
    val key: String = js.native
    var x: Double = js.native
    var y: Double = js.native
    var visible: Boolean = js.native
    var inputEnabled: Boolean = js.native
    var alpha: Double = js.native
    val events: Events = js.native
  }

  @js.native
  class Events extends js.Object {
    var onInputDown: Signal = js.native
    var onInputOver: Signal = js.native
    var onInputOut: Signal = js.native
  }

  @js.native
  class Signal extends js.Object {
    def add(listener: js.Function): Unit = js.native
  }
}
