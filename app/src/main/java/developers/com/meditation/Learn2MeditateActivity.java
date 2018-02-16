package developers.com.meditation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Learn2MeditateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn2_meditate);
    }
    public void callTheBasicsActivity(View view) {
        Intent lrn2meditateIntent = new Intent(Learn2MeditateActivity.this, TheBasicsActivity.class);
        startActivity(lrn2meditateIntent);
    }
}
