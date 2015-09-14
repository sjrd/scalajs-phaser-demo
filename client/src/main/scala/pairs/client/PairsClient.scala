package pairs.client

import scala.scalajs.js
import org.scalajs.dom

import pairs.client.phaser._

object PairsClient extends js.JSApp {
  def main(): Unit = {
    val game = new Game(width = 800, height = 520, parent = "pairs-container")
  }
}
