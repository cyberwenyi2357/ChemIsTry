package scm.lingma9.chemistry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ArFragment fragment;
    private Scene scene;

    private ModelRenderable modelRenderable;
    private final int ch_index=0,ch2_index=1;
    private int[] models = {R.raw.ch,R.raw.ch2,R.raw.ch3,R.raw.co2};
    private String[] modelNames = {"ch","ch2","ch3","co2"};
    private Random rand = new Random();
    private ModelRenderable[] renderables = new ModelRenderable[models.length];
    int i_c = 0,i_h = 0,i_o = 0;
    int round=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_log=findViewById(R.id.btn_log);
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(MainActivity.this,MenuActivity.class);
                startActivity(intent1);
            }
        });

    }
    }