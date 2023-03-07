package scm.lingma9.chemistry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.ar.core.Anchor;
import com.google.ar.core.Camera;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Trackable;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.collision.Ray;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.List;
//model loading refers to the inclass exercise of lecture 9
public class LibActivity extends AppCompatActivity {
    private ArFragment fragment2;
    private ModelRenderable modelRenderable;
    private int[] model = {R.raw.ch,R.raw.ch2,R.raw.ch3,R.raw.co2,R.raw.mole,R.raw.nacl};
    private String[] modelName = {"ch","ch2","ch3","co2","mole","nacl"};
    private ModelRenderable[] renderablee = new ModelRenderable[model.length];

    int isClick=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib);
        Button btn_1 = findViewById(R.id.btnch);
        Button btn_2 = findViewById(R.id.btnch2);
        Button btn_3 = findViewById(R.id.btnch3);
        Button btn_4 = findViewById(R.id.btnco2);
        Button btn_5 = findViewById(R.id.btnc3h6);
        Button btn_6 = findViewById(R.id.btnnacl);

        fragment2 = (ArFragment)
                getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        loadModel();
//here is our own contribution
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClick=0;
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClick=1;
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClick=2;
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClick=3;
            }
        });
        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClick=4;
            }
        });
        btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClick=5;
            }
        });


        fragment2.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (renderablee[isClick] == null) {
                        return;
                    }
                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(fragment2.getArSceneView().getScene());
                    TransformableNode modelNode = new TransformableNode(fragment2.getTransformationSystem());
                    modelNode.setParent(anchorNode);
                    modelNode.setName(modelName[isClick]);
                    modelNode.setRenderable(renderablee[isClick]);
                    modelNode.getScaleController().setMinScale(0.3f);
                    modelNode.getScaleController().setMaxScale(0.6f);

                });

    }

    private void loadModel() {
        ModelRenderable.builder()
                .setSource(this, R.raw.ch)
                .setIsFilamentGltf(true)
                .build()
                .thenAccept(renderable -> modelRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Log.e("HelloARCore", "Unable to load Renderable.", throwable);
                            return null;
                        });

        for (int index = 0; index < renderablee.length; index++) {
            int finalIndex = index;
            ModelRenderable.builder()
                    .setSource(this, model[finalIndex]) // exercise 1
                    .setIsFilamentGltf(true)
                    .build()
                    .thenAccept(renderable -> renderablee[finalIndex] = renderable)
                    .exceptionally(
                            throwable -> {
                                Log.e("HelloARCore", "Unable to load Renderable.", throwable);
                                return null;
                            });
        }
    }

}