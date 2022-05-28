package ps.school.numbersgamefinalproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ps.school.numbersgamefinalproject.R;

public class Splash_screen extends AppCompatActivity {

    View image;
    TextView app_name_Splash_screen;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread =new Thread(){
            @Override
            public void run() {

                try {
                    sleep(1250);

                    Intent intent = new Intent(getApplicationContext(),Login_Activity.class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();

        image = findViewById(R.id.imageView_splash_screen);
        app_name_Splash_screen = findViewById(R.id.app_name_Splash_screen);
      Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
         R.anim.amime);

        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animeion2);
      image.startAnimation(animation1);
      app_name_Splash_screen.startAnimation(animation2);
    }
}