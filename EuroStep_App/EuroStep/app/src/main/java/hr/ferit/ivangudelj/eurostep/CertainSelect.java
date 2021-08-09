package hr.ferit.ivangudelj.eurostep;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;


public class CertainSelect extends AppCompatActivity {
    ImageView ivLogo;
    ImageView ivPlayerPicture;
    String pictureToShow;
    String selectedOption;
    String sentItem;
    TextView tvTeamAbbr;
    TextView tvName;
    TextView tvPositionOrLocation;
    ListView lvDescription;
    ListAdapter tempAdapter = null;
    TeamJSONAdapter teamAdapter = null;
    PlayerJSONAdapter playerAdapter = null;
    Button btnBackCPerson;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certainitem_activity);
        initiate();
        getDataFromPreviousActivity();
        switch (selectedOption) {
            case "2":   //PLAYERS
                players();
                break;
            case "3":   //TEAMS
                teams();
                break;
        }
        btnBackCPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListPageView.class);
                intent.putExtra("buttonNumber",selectedOption);
                startActivity(intent);
            }
        });

    }

    private void initiate() {
        ivLogo = (ImageView) findViewById(R.id.ivEmptyLogo);
        ivPlayerPicture = (ImageView) findViewById(R.id.ivPlayerPicture);
        tvTeamAbbr=(TextView) findViewById(R.id.tvTeamAbbr);
        tvName=(TextView) findViewById(R.id.tvPlayerName);
        tvPositionOrLocation = (TextView) findViewById(R.id.tvPlayerYear);
        btnBackCPerson = (Button) findViewById(R.id.btnBacCertainPersoneView);
        lvDescription = (ListView) findViewById(R.id.lvPlayerDescription);
        teamAdapter = new TeamJSONAdapter(getApplicationContext());
        playerAdapter = new PlayerJSONAdapter(getApplicationContext());
    }

    private void players() {
        String[] playerDataToShow;
        playerDataToShow = playerAdapter.getDisplayDataForCertainPlayer(teamAdapter,sentItem);
        String pictureURL = playerAdapter.getCertainPlayerPictureURLAddon(sentItem);
        initiatePlayers(playerDataToShow);
        tempAdapter = playerAdapter.getCertainPlayerData(getApplicationContext(),sentItem);
        Picasso.get().load("https://nba-players.herokuapp.com/players"+ pictureURL).into(ivPlayerPicture);
        lvDescription.setAdapter(tempAdapter);
    }


    private void teams() {
        String[] teamDataToShow;
        teamDataToShow = teamAdapter.getCertainTeamData(sentItem);
        initiateTeams(teamDataToShow);
        pictureToShow = teamAdapter.getCertainTeamPicture(sentItem);
        tempAdapter = teamAdapter.getCertainTeamPlayerList(getApplicationContext(),teamDataToShow[1]);
        lvDescription.setAdapter(tempAdapter);
        Picasso.get().load(pictureToShow).into(ivPlayerPicture);
    }

    private void getDataFromPreviousActivity() {
        sentItem = getIntent().getExtras().getString("Value");
        selectedOption = getIntent().getExtras().getString("IDNumb");
        selectedOption= selectedOption.trim();
    }

    private void initiateTeams(String[] dataToShow) {

        tvName.setText(dataToShow[0]);
        tvTeamAbbr.setText(dataToShow[1]);
        tvPositionOrLocation.setText(dataToShow[2]);
        ivLogo.setVisibility(View.INVISIBLE);
    }

    private void initiatePlayers(String[] dataToShow) {
        tvName.setText(sentItem);
        tvTeamAbbr.setVisibility(View.INVISIBLE);
        Picasso.get().load(dataToShow[0]).into(ivLogo);
        tvPositionOrLocation.setText(dataToShow[1]);
    }
}
