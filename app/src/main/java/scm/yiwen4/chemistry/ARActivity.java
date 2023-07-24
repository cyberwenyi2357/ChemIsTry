package scm.lingma9.chemistry;

import static java.lang.StrictMath.random;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.ar.core.Anchor;
import com.google.ar.core.Camera;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Trackable;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.collision.Ray;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.List;
import java.util.Random;
// the AR scene loading and model loading refers to the inclass exercise of lecture 9,and shooting part refers to the inclass exercise of lecture 10
public class ARActivity extends AppCompatActivity {
    private ArFragment fragment;
    private Scene scene;
    private ModelRenderable modelRenderable;
    private final int ch_index=0,ch2_index=1;
//    private int[] models = {R.raw.ch,R.raw.ch2,R.raw.ch3,R.raw.cl,R.raw.co2,R.raw.na,R.raw.carbon,R.raw.oxygen};
//    private String[] modelNames = {"ch","ch2","ch3","cl","co2","na","carbon","oxygen"};
    private int[] models = {R.raw.ch,R.raw.ch2,R.raw.ch3,R.raw.co2,R.raw.mole,R.raw.nacl};
    private String[] modelNames = {"ch","ch2","ch3","co2","mole","nacl"};
    private Random rand = new Random();
    private ModelRenderable[] renderables = new ModelRenderable[models.length];
    int i_c = 0,i_h = 0,i_o = 0;
    int a,b,c;
    int component_1=0;
    private int[] screenPt = new int[2];
    private TextView shootingSight;

    private int click_num=1;
    private boolean answer=false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        shootingSight = findViewById(R.id.txt_shoot_center);
        shootingSight.getLocationOnScreen(screenPt);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MediaPlayer playerBF = MediaPlayer.create(this,R.raw.bubble);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        Button btnShoot = findViewById(R.id.btn_shoot);
        TextView result=findViewById(R.id.text_result);

        fragment = (ArFragment)
                getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        scene = fragment.getArSceneView().getScene();
        loadModel();
        fragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    int renderable_index = rand.nextInt(renderables.length-2); // exercise 1
                    if (renderables[renderable_index] == null) {
                        return;
                    }
                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(fragment.getArSceneView().getScene());
                    TransformableNode modelNode = new MovingNode(fragment.getTransformationSystem());
                    modelNode.setParent(anchorNode);
                    modelNode.setName(modelNames[renderable_index]);
                    modelNode.setRenderable(renderables[renderable_index]);
                    modelNode.getScaleController().setMinScale(0.3f);
                    modelNode.getScaleController().setMaxScale(0.6f);
                    playerBF.start();
                }
                );

        scene.addOnUpdateListener(new Scene.OnUpdateListener() {
            @Override
            public void onUpdate(FrameTime frameTime) {
                perFrameAction();
            }
        });
        btnShoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoot();
            }
        });

        Button submit=findViewById(R.id.btn_done);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i_c==3 && i_h==6 && i_o==0){
                    result.setText("Congratulations! You are correct!" +
                            "The final structure of C3H6 is shown above.");
                    result.setTextColor(Color.GREEN);

                    addObstacle();
                }else{
                    result.setText("You are wrong! The correct structure of C3H6 is shown above.");
                    result.setTextColor(Color.RED);
                    addObstacle();
                }
            }
        });
        Button btnnext=findViewById(R.id.btn_next);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText("");
                clear();
                a= (int) (random()*13);
                b= (int) (random()*13);
                c= (int) (random()*13);
                TextView question_new = findViewById(R.id.txt_question);
                String q_str = "C"+a+"H"+b+"O"+c;
                question_new.setText(q_str);
                clearAllObstacles();
            }
        });

    }

    private void perFrameAction() {
        if (shootingSight == null) return;

        Frame frame = fragment.getArSceneView().getArFrame();

        if (frame.getCamera().getTrackingState() == TrackingState.TRACKING)
            shootingSight.setTextColor(Color.GREEN);
        else
            shootingSight.setTextColor(Color.RED);
    }

    private void shoot() {
        Frame frame = fragment.getArSceneView().getArFrame();
        Camera camera = frame.getCamera();
        if (camera.getTrackingState() != TrackingState.TRACKING)
            return;

        HitTestResult sceneHit = sceneHitTest(screenPt[0], screenPt[1]);
        if (sceneHit != null && sceneHit.getNode() != null) {
            Node node = sceneHit.getNode();

            TextView number_c = findViewById(R.id.num_C);
            TextView number_h = findViewById(R.id.num_H);
            TextView number_o = findViewById(R.id.num_O);
// The calculation of different atoms are our own contribution
            if(node.getName().equals(modelNames[0])){
                i_c+=1;i_h+=1;i_o+=0;
                component_1+=1;
            }else if(node.getName().equals(modelNames[1])){
                i_c+=1;i_h+=2;i_o+=0;
            }else if(node.getName().equals(modelNames[2])){
                i_c+=1;i_h+=3;i_o+=0;
            }else if(node.getName().equals(modelNames[3])){
//                    i_c+=1;i_h+=3;i_o+=0;
            }else if(node.getName().equals(modelNames[4])){
                i_c+=1;i_h+=0;i_o+=2;
            }else if(node.getName().equals(modelNames[5])){
//                    i_c+=1;i_h+=2;i_o+=0;
            }

            String c_str = Integer.toString(i_c);
            number_c.setText(c_str);
            String h_str = Integer.toString(i_h);
            number_h.setText(h_str);
            String o_str = Integer.toString(i_o);
            number_o.setText(o_str);

            removeNode(node);
        }
    }

private void clear() {
    i_c = 0;
    i_h = 0;
    i_o = 0;

    TextView number_c = findViewById(R.id.num_C);
    String c_str = Integer.toString(i_c);
    number_c.setText(c_str);

    TextView number_h = findViewById(R.id.num_H);
    String h_str = Integer.toString(i_h);
    number_h.setText(h_str);

    TextView number_o = findViewById(R.id.num_O);
    String o_str = Integer.toString(i_o);
    number_o.setText(o_str);

}
    private void loadModel() {
        ModelRenderable.builder()
                .setSource(this, R.raw.mole)
                .setIsFilamentGltf(true)
                .build()
                .thenAccept(renderable -> modelRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Log.e("HelloARCore", "Unable to load Renderable.", throwable);
                            return null;
                        });

        for (int index = 0; index < renderables.length; index++) {
            int finalIndex = index;
            ModelRenderable.builder()
                    .setSource(this, models[finalIndex]) // exercise 1
                    .setIsFilamentGltf(true)
                    .build()
                    .thenAccept(renderable -> renderables[finalIndex] = renderable)
                    .exceptionally(
                            throwable -> {
                                Log.e("HelloARCore", "Unable to load Renderable.", throwable);
                                return null;
                            });
        }
    }

    private void removeNode(Node node) {
        AnchorNode parent = (AnchorNode)node.getParent();
        parent.getAnchor().detach();
        parent.removeChild(node);
        scene.removeChild(parent);
    }

    private HitTestResult sceneHitTest(float x, float y) {
        Ray ray = scene.getCamera().screenPointToRay(x, y);
        return scene.hitTest(ray, true);
    }
    private void addObstacle() {
        HitTestResult sceneHit = sceneHitTest(screenPt[0], screenPt[1]);
        if (sceneHit != null && sceneHit.getNode() != null) return;

        Frame frame = fragment.getArSceneView().getArFrame();
        Camera camera = frame.getCamera();
        if (camera.getTrackingState() != TrackingState.TRACKING) return;

        // exercise 2
        // add a new object if the hit happens on a detected plane
        List<HitResult> hits = frame.hitTest(screenPt[0], screenPt[1]);
        for (HitResult hit : hits) {
            Trackable trackable = hit.getTrackable();
            if (trackable instanceof Plane &&
                    ((Plane)trackable).isPoseInPolygon(hit.getHitPose()))
            {
                Anchor anchor = hit.createAnchor();
                AnchorNode anchorNode = new AnchorNode(anchor);
                anchorNode.setParent(scene);

                TransformableNode house = new TransformableNode(fragment.getTransformationSystem());
                house.setParent(anchorNode);
                house.setName(modelNames[4]);
                house.setRenderable(renderables[4]);

                // set default model scale
                house.getScaleController().setMinScale(0.8f);
                house.getScaleController().setMaxScale(1.0f);

                // we consider the closest plane only in case there are multiple planes
                break;
            }
        }
    }
    private void clearAllObstacles(){
        scene.callOnHierarchy((node -> {
            if(node.getName().equals(modelNames[4])){
                removeNode(node);

            } else if(node.getName().equals(modelNames[5])){
                removeNode(node);
            }

        }));
    }
}