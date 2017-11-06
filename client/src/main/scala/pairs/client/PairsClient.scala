package pairs.client

import scala.scalajs.js
import org.scalajs.dom

object PairsClient {
  def main(args: Array[String]): Unit = {
    val p = dom.document.createElement("p")
    p.textContent = "Hello"
    dom.document.getElementById("pairs-container").appendChild(p)
  }
}
