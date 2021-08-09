package hr.ferit.ivangudelj.eurostep;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class TeamJSONAdapter extends AppCompatActivity implements JSONLoader {


    private JSONObject teamsJsonFile;
    private JSONObject jsonStatsFileForPlayersList;
    public String json = null;


    public TeamJSONAdapter(Context context) {
        try {
            this.teamsJsonFile = new JSONObject(loadJSONFromAsset(context, "teams.json"));
            this.jsonStatsFileForPlayersList = new JSONObject(loadJSONFromAsset(context,"player_stats.json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public ListAdapter getTeamsAdapter(Context context) throws JSONException {
        ListAdapter adapter = null;
        try {
            JSONArray array = this.teamsJsonFile.getJSONArray("teams");
            HashMap<String, String> listTeams;
            ArrayList<HashMap<String, String>> arrayListTeams = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject originalJSONObject = array.getJSONObject(i);
                listTeams = new HashMap<>();
                String teamName = originalJSONObject.getString("TeamName");
                String teamAbbreviation = originalJSONObject.getString("TeamAbbr");
                String teamLocation = originalJSONObject.getString("Location");

                listTeams.put("TeamName", teamName);
                listTeams.put("TeamAbbr", teamAbbreviation);
                listTeams.put("Location", teamLocation);


                arrayListTeams.add(listTeams);
            }
            adapter = new SimpleAdapter(context, arrayListTeams, R.layout.list_item, new String[]{"TeamName", "Location"}, new int[]{R.id.tvMainData, R.id.tvSecondaryData});
            return adapter;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return adapter;
    }

    public String loadJSONFromAsset(Context context,String name) {
        try {
            InputStream is = context.getAssets().open(name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String getCertainTeamPicture(String nameOfTheTeam){
        try {
            JSONArray array = teamsJsonFile.getJSONArray("teams");
            for (int i = 0; i < array.length(); i++) {
                JSONObject originalJSONObject = array.getJSONObject(i);
                String tName = originalJSONObject.getString("TeamName");
                String teamURL = originalJSONObject.getString("TeamLogo");
                if(tName.equals(nameOfTheTeam)) {
                    return teamURL;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getCertainTeamData(String nameOfTheTeam) {
        String[] teamDataToReturn = new String[3];
        try {
            JSONArray array = teamsJsonFile.getJSONArray("teams");
            for (int i = 0; i < array.length(); i++) {
                JSONObject originalJSONObject = array.getJSONObject(i);
                if(originalJSONObject.getString("TeamName").trim().equals(nameOfTheTeam.trim())){
                    String teamName = originalJSONObject.getString("TeamName");
                    String teamLocation = originalJSONObject.getString("Location");
                    String teamAbbreviation = originalJSONObject.getString("TeamAbbr");
                    teamDataToReturn[0] = teamName;
                    teamDataToReturn[1] = teamAbbreviation;
                    teamDataToReturn[2] = teamLocation;
                    return teamDataToReturn;
                }
            }
            return  teamDataToReturn;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return teamDataToReturn;
    }
    public ListAdapter getCertainTeamPlayerList(Context context,String givenTeamAbbreviation) {
        ListAdapter adapter = null;
        try {
            JSONArray arrayToRead = jsonStatsFileForPlayersList.getJSONArray("player_stats");
            HashMap<String, String> playersList;
            ArrayList<HashMap<String, String>> arrayListPlayers=new ArrayList<>();
            for (int i = 0; i < arrayToRead.length(); i++) {
                JSONObject originalJSONObject = arrayToRead.getJSONObject(i);
                if(givenTeamAbbreviation.equals(originalJSONObject.getString("Team"))){
                    playersList = new HashMap<>();
                    String playerFromSelectedTeam = originalJSONObject.getString("Player");
                    String gamesPlayerPlayedForTheTeam = originalJSONObject.getString("Games Played");
                    playersList.put("MainPart",playerFromSelectedTeam);
                    playersList.put("SecPart","Games played: " + gamesPlayerPlayedForTheTeam);
                    arrayListPlayers.add(playersList);
                }
            }
            adapter = new SimpleAdapter(context, arrayListPlayers, R.layout.list_item, new String[]{"MainPart", "SecPart"}, new int[]{R.id.tvMainData, R.id.tvSecondaryData});
            return adapter;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return adapter;
    }
    public String getTeamPictureFromAbbreviation(String givenAbbreviation){
        String teamNametoReturn = null;
        try {
            JSONArray arrayToRead = teamsJsonFile.getJSONArray("teams");
            for(int i =0;i<arrayToRead.length();i++) {
                JSONObject originalJSONObject = arrayToRead.getJSONObject(i);
                if (givenAbbreviation.equals(originalJSONObject.getString("TeamAbbr"))){
                    teamNametoReturn = originalJSONObject.getString("TeamLogo");
                }
            }
            return teamNametoReturn;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return teamNametoReturn;
    }
}
