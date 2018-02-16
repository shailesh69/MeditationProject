package developers.com.meditation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences pre ;
    private SharedPreferences.Editor editor ;
    private TextView txtName ;
    private Button btnSignOut ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        int meditatingpeople = 15867;
        int leftmargin = 120;
        int rightmargin = 10;

        LinearLayout linearlayout = (LinearLayout) findViewById(R.id.mylinearlayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        // yourbutton.setLayoutParams(params);
        TextView msg = new TextView(this);
        msg.setText("People Meditating today");
        msg.setTextColor(Color.parseColor("#ffffff"));
        linearlayout.addView(msg);


        while(meditatingpeople > 0 )
        {

            params.setMargins(leftmargin, 0,0, 0);
            TextView txt = new TextView(this);
            txt.setText(""+meditatingpeople%10);
            txt.setPadding(10,10,10,10);
            //  txt.layout(leftmargin,0,ri,0);
            txt.setBackgroundColor(Color.parseColor("#436060"));
            txt.setTextColor(Color.parseColor("#ffffff"));
            meditatingpeople = meditatingpeople / 10;
            txt.setLayoutParams(params);
            linearlayout.addView(txt);
            leftmargin = leftmargin - 25;
            //  rightmargin = rightmargin + 10;
        }

        LinearLayout lrn2Mdtate = (LinearLayout)findViewById(R.id.lrn2meditate);
        lrn2Mdtate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lrn2meditateIntent = new Intent(MainActivity.this, Learn2MeditateActivity.class);
                startActivity(lrn2meditateIntent);
            }
        });

        LinearLayout connect = (LinearLayout) findViewById(R.id.lytConnect);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent connectUpIntent = new Intent(MainActivity.this, ConnectUpActivity.class);
                startActivity(connectUpIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
