
import 'package:flame/components.dart';
import 'package:flame/events.dart';
import 'package:flame/game.dart';
import 'package:flame/src/components/core/component.dart';
import 'package:flame/src/text/renderers/text_renderer.dart';
import 'package:flappy_bird/components/background.dart';
import 'package:flappy_bird/components/pipe_group.dart';

import '../components/bird.dart';
import '../components/config.dart';
import '../components/ground.dart';

class FlappyBirdGame extends FlameGame with TapDetector, HasCollisionDetection{
  late Bird bird;
  late TextComponent score;
  bool isHit = false;
  Timer interval = Timer(Config.pipeInterval, repeat: true);

    @override
    Future<void> onLoad() async {
      print("onload");
      addAll(<Component>[
        Background(),
        Ground(),
        bird = Bird(),
        PipeGroup(),
        score = buildScore(),
      ]);

      interval.onTick = () => add(PipeGroup());
    }

    @override
      void update(double dt) {
        super.update(dt);
        interval.update(dt);
        score.text = 'Score ${bird.score}';
    }

    @override
    void onTap() {
      super.onTap();
      bird.fly();
    }

  TextComponent<TextRenderer> buildScore() {
      return TextComponent(
        text: 'Score: 0',
        position: Vector2(size.x/2, size.y/2*0.2),
        anchor: Anchor.center,
      );
  }


}
