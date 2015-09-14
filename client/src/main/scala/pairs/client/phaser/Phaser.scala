package pairs.client.phaser

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom.html

@js.native
object Phaser extends js.Object {
  val AUTO: Int = js.native
}

@js.native
@JSName("Phaser.Game")
class Game(
    width: Double | String,
    height: Double | String,
    renderer: Int,
    parent: String | html.Element) extends js.Object {

}
