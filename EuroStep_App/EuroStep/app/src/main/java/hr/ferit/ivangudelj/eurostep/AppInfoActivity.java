package hr.ferit.ivangudelj.eurostep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AppInfoActivity extends AppCompatActivity  {

    Button btnBack;
    ImageView ivEuroLogo,ivSevenLogo,ivBankLogo,ivTurkishLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_info);
        initiate();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppInfoActivity.this ,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initiate() {
        this.btnBack = (Button) findViewById(R.id.btnBackAppInfo);
        this.ivBankLogo = (ImageView) findViewById(R.id.ivEuroBankLogo);
        this.ivSevenLogo = (ImageView) findViewById(R.id.ivLogosevendays);
        this.ivEuroLogo = (ImageView) findViewById(R.id.ivAppInfoImage);
        this.ivTurkishLogo = (ImageView) findViewById(R.id.ivTAirLinesLogo);
    }
}
