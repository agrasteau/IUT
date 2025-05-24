import 'package:flame/collisions.dart';
import 'package:flame/components.dart';
import 'package:flame/effects.dart';
import 'package:flappy_bird/game/bird_movements.dart';
import 'package:flappy_bird/game/flappy_bird_game.dart';
import 'package:flutter/animation.dart';
import 'package:flutter/foundation.dart';

import '../game/assets.dart';
import 'config.dart';

class Bird extends SpriteGroupComponent<BirdMovements> with HasGameReference<FlappyBirdGame>, CollisionCallbacks{
  int score = 0;

  @override
  Future<void> onLoad() async {
    final birdMidFlap = await game.loadSprite(Assets.birdMidFlap);
    final birdUpFlap = await game.loadSprite(Assets.birdUpFlap);
    final birdDownFlap = await game.loadSprite(Assets.birdDownFlap);

    size = Vector2(50, 40);

    sprites = {
      BirdMovements.middle: birdMidFlap,
      BirdMovements.up: birdUpFlap,
      BirdMovements.down: birdDownFlap,
    };
    add(CircleHitbox());
    position = Vector2(50, game.size.y/2 - size.y/2);
    current = BirdMovements.middle;
  }

  @override
  void update(double dt) {
    super.update(dt);
    position.y += Config.birdVelocity * dt;
  }

  @override
  void onCollisionStart(
      Set<Vector2> intersectionPoints,
      PositionComponent other,
      ) {
    super.onCollisionStart(intersectionPoints, other);
    gameOver();
  }

  void fly() {
    add(
      MoveByEffect(
        Vector2(0, Config.gravity.toDouble()),
        EffectController( duration: 0.2, curve:Curves.decelerate),
        onComplete: () => current = BirdMovements.down,
      ),
    );
    current = BirdMovements.up;
  }

  void gameOver() {
    game.overlays.add(
      'gameOver'
    );
    game.pauseEngine();
    game.isHit = true;
  }

  void reset() {
    position = Vector2(50, game.size.y/2 - size.y/2);
    score = 0;
  }
}