import 'dart:math';

import 'package:flame/components.dart';
import 'package:flappy_bird/game/pipe_position.dart';

import '../game/flappy_bird_game.dart';
import 'config.dart';
import 'pipe.dart';

class PipeGroup extends PositionComponent with HasGameReference<FlappyBirdGame>{
  PipeGroup();
  final _random = Random();

  @override
  Future<void> onLoad() async {
    position.x = game.size.x;

    final heightMinusGround = game.size.y - Config.groundHeight;
    final spacing = 100 + _random.nextDouble()* (heightMinusGround / 4 );
    final centerY = spacing + _random.nextDouble() * (heightMinusGround - spacing);
    addAll([
      Pipe(pipePosition: PipePosition.top, height: centerY - spacing/2),
      Pipe(pipePosition: PipePosition.bottom, height: heightMinusGround - (centerY + spacing / 1)),
    ]);
  }
  @override
  void update(double dt){
    super.update(dt);
    position.x -=Config.gameSpeed * dt;
    if (position.x < 0){
      removeFromParent();
      updateScore();
    }
  if (game.isHit){
    removeFromParent();
    game.isHit = false;
  }
  }

  void updateScore() { //todo supprimer méthode utiliser qu'une seul fois
    game.bird.score +=1;
  }
}