import 'package:flame/components.dart';
import 'package:flame/flame.dart';

import '../game/assets.dart';
import '../game/flappy_bird_game.dart';

class Background extends SpriteComponent with HasGameReference<FlappyBirdGame>{
  Background();
  @override
  Future<void> onLoad() async {
   final background = await Flame.images.load(Assets.background);
   size = game.size;  //gameRef -> game
   sprite = Sprite(background);
  }
}