package pairs.client

import scala.scalajs.js
import org.scalajs.dom

import pairs.client.phaser._

object PairsClient {
  def main(args: Array[String]): Unit = {
    val game = new Game(800, 520, Phaser.AUTO, "pairs-container")
  }
}
