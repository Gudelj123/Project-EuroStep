package hr.ferit.ivangudelj.eurostep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

        Button btnCoaches,btnPlayers,btnTeams,btnAppInfo,btnTodaysGames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiate();
    }

    private void initiate() {
        this.btnCoaches = (Button)findViewById(R.id.btnCoaches);
        this.btnPlayers = (Button)findViewById(R.id.btnPlayers);
        this.btnAppInfo = (Button)findViewById(R.id.btnAppInfo);
        this.btnTeams = (Button)findViewById(R.id.btnTeams);
        this.btnTodaysGames = (Button)findViewById(R.id.btnTodayInfo);
        btnCoaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity(1);
            }
        });
        btnPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity(2);
            }
        });
        btnTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity(3);
            }
        });
        btnAppInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity(4);
            }
        });
        btnTodaysGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity(5);
            }
        });
    }

    private void openNewActivity(int i) {
        switch (i){
        case 1:
            Intent intentCoaches = new Intent(this,ListPageView.class);
            intentCoaches.putExtra("buttonNumber", "1");
            startActivity(intentCoaches);
            break;
        case 2:
            Intent intentPlayers = new Intent(this,ListPageView.class);
            intentPlayers.putExtra("buttonNumber", "2");
            startActivity(intentPlayers);
            break;
        case 3:
            Intent intentTeams = new Intent(this,ListPageView.class);
            intentTeams.putExtra("buttonNumber", "3");
            startActivity(intentTeams);
            break;
        case 4:
            Intent intentAppInfo = new Intent(this,AppInfoActivity.class);
            startActivity(intentAppInfo);
            break;
        case 5:
            Intent intentOnl = new Intent();
            intentOnl.setAction(Intent.ACTION_VIEW);
            intentOnl.addCategory(Intent.CATEGORY_BROWSABLE);
            intentOnl.setData(Uri.parse("https://www.espn.com/nba/schedule"));
            startActivity(intentOnl);
            break;
        }
    }
}