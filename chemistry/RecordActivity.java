package scm.lingma9.chemistry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        TextView txt_intro = findViewById(R.id.txt_intro);
        txt_intro.setText("Propylene, also known as propene, is an unsaturated organic compound with the chemical formula CH3CH=CH2. \n" +
                "It has one double bond, and is the second simplest member of the alkene class of hydrocarbons. It is a colorless gas with a faint petroleum-like odor.");
        ImageButton img_bg= findViewById(R.id.img_bg);
        img_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(RecordActivity.this,LibActivity.class);
                startActivity(intent2);
            }
        });
    }
}