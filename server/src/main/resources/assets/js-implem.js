(function() {
"use strict";

class Point {
  constructor(x, y) {
    this.x = x;
    this.y = y;
  }
}

class GameState extends Phaser.State {
  constructor(starCount) {
    super();
    this._starCount = starCount;
  }

  create() {
    const starsGraphics = this.game.add.graphics(50, 50);
    for (let i = 0; i < this._starCount; i++) {
      const points = this._makeStarPolygon(i * 24).map(
        pt => [pt.x, pt.y]
      );
      starsGraphics.beginFill(0xFFD700);
      starsGraphics.drawPolygon(points);
      starsGraphics.endFill();
    }
  }

  _makeStarPolygon(offset) {
    const result = [];
    for (let i = 0; i < 10; i++) {
      const angle = 2*Math.PI/10 * i + Math.PI/2;
      const len = (i % 2 === 0) ? 10 : 4;
      result.push(new Point(
        offset + 10 + len*Math.cos(angle),
        10 - len*Math.sin(angle)));
    }
    return result;
  }
}

const game = new Phaser.Game(300, 124, undefined,
  "pairs-container-js");
game.state.add("game", new GameState(5));
game.state.start("game");

})();
