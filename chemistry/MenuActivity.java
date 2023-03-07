package scm.lingma9.chemistry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        MediaPlayer playerBG = MediaPlayer.create(this,R.raw.bgm);
//button to lib page
        Button btn_lib = findViewById(R.id.btn_lib);
        btn_lib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MenuActivity.this, LibActivity.class);
                startActivity(intent2);
            }
        });
        //button to AR page
        Button btn_log=findViewById(R.id.btn_new);
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(MenuActivity.this,ARActivity.class);
                startActivity(intent1);
                playerBG.start();
            }
        });
        //button to record page
        Button btn_record = findViewById(R.id.btn_record);
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MenuActivity.this, RecordActivity.class);
                startActivity(intent3);
            }
        });
    }


}