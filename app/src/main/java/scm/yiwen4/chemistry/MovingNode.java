package scm.lingma9.chemistry;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

import java.util.Random;


public class MovingNode extends TransformableNode {

    private float speedX = 0; // meter
    private float speedZ = 0; // meter
    private Random rand = new Random();

    public MovingNode(TransformationSystem transformationSystem) {
        super(transformationSystem);

        resetMovingDirection();
    }

    public void resetMovingDirection() {

        speedX = (rand.nextFloat() - 0.5f) * 0.01f;
        speedZ = (rand.nextFloat() - 0.5f) * 0.01f;
    }

    @Override
    public void onUpdate(FrameTime frameTime) {
        super.onUpdate(frameTime);
        oneUpdatestep();

    }

    private void oneUpdatestep() {
        Vector3 posi = getLocalPosition();
        posi.x += speedX;
        posi.z += speedZ;
        setLocalPosition(posi);
    }
}