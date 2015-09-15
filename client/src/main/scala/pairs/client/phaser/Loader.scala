package pairs.client.phaser

import scala.scalajs.js
import js.annotation._

@js.native
@JSName("Phaser.Loader")
class Loader extends js.Object {
  def image(key: String, url: String = js.native,
      overwrite: Boolean = false): this.type = js.native
}
