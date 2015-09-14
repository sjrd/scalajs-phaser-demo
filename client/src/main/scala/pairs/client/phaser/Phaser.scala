package pairs.client.phaser

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom.html

@js.native
@JSGlobal
object Phaser extends js.Object {
  val AUTO: Int = js.native
}

@js.native
@JSGlobal("Phaser.Game")
class Game(
    width: Double | String = 800,
    height: Double | String = 600,
    renderer: Int = Phaser.AUTO,
    parent: String | html.Element = "") extends js.Object {

}
