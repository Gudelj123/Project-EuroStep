package hr.ferit.ivangudelj.eurostep;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerJSONAdapter extends AppCompatActivity implements JSONLoader {

    public final String[] resourceStringArray = new String[]{"Assists","Blocks", "Defensive Rebounds","Field Goals Made","Field Goals Percentage","Free Throws","Free Throws Percentage",
                                            "Games Played", "Minutes Played", "Offensive Rebounds", "Points","Player","Steals", "Three Pointers Made", "Three Pointers Percentage","Team"};
    private JSONObject playersJsonFile;
    private JSONObject playerStatsJsonFile;


    public PlayerJSONAdapter(Context context) {
        try {
            this.playersJsonFile = new JSONObject(loadJSONFromAsset(context,"players.json"));
            this.playerStatsJsonFile = new JSONObject(loadJSONFromAsset(context,"player_stats.json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public ListAdapter getPlayersAdapter(Context context) throws JSONException {
        SimpleAdapter adapter = null;
        try {
            JSONArray arrayPlayer = this.playersJsonFile.getJSONArray("players");
            HashMap<String, String> listPlayer;
            ArrayList<HashMap<String, String>> arrayListPlayers = new ArrayList<>();
            for (int i = 0; i < arrayPlayer.length(); i++){
                JSONObject originalJSONObject = arrayPlayer.getJSONObject(i);
                listPlayer = new HashMap<>();
                String playerName = originalJSONObject.getString("Name");
                String playerAge = originalJSONObject.getString("Age");
                String playerPosition = originalJSONObject.getString("Pos");
                listPlayer.put("Name", playerName);
                listPlayer.put("Age&Pos", playerAge + "," + playerPosition);
                arrayListPlayers.add(listPlayer);
            }
            adapter = new SimpleAdapter(context, arrayListPlayers, R.layout.list_item, new String[]{"Name", "Age&Pos"}, new int[]{R.id.tvMainData, R.id.tvSecondaryData});
            return adapter;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return adapter;
    }


    public String loadJSONFromAsset (Context context, String name){
        String json = null;
        try {
            // Opening data.json file
            InputStream inputStream = context.getAssets().open(name);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            inputStream.read(buffer);
            inputStream.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return json;
        }
        return json;
    }

    public String getCertainPlayerPictureURLAddon(String givenPlayerName){
        try {
            JSONObject objectForPicture = this.playersJsonFile;
            JSONArray array = objectForPicture.getJSONArray("players");
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                String playerName = o.getString("Name");
                String playerURL = playerName.toLowerCase();
                if(givenPlayerName.equals(playerName)) {
                    playerURL = "/" + playerURL.substring(playerURL.indexOf(" ")+1)  + "/" + playerURL.substring(0,playerURL.indexOf(" "));
                    return playerURL;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ListAdapter getCertainPlayerData(Context context,String givenPlayerName) {
        ListAdapter adapter = null;
        try {
            JSONArray arrayToRead = playerStatsJsonFile.getJSONArray("player_stats");
            HashMap<String, String> playerData;
            ArrayList<HashMap<String, String>> arrayListPlayerStats=new ArrayList<>();
            for (int i = 0; i < arrayToRead.length(); i++) {
                JSONObject originalJSONObject = arrayToRead.getJSONObject(i);
                if(givenPlayerName.equals(originalJSONObject.getString("Player"))){
                    for (int j = 0;j<resourceStringArray.length;j++) {
                        playerData = new HashMap<>();
                        String typeOfStats = resourceStringArray[j];
                        String valueOfStats= originalJSONObject.getString(resourceStringArray[j]);
                        playerData.put("MainPart", typeOfStats);
                        playerData.put("SecPart", valueOfStats);
                        arrayListPlayerStats.add(playerData);
                    }
                    adapter = new SimpleAdapter(context, arrayListPlayerStats, R.layout.list_item, new String[]{"MainPart", "SecPart"}, new int[]{R.id.tvMainData, R.id.tvSecondaryData});
                    return adapter;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return adapter;
    }

    public String[] getDisplayDataForCertainPlayer(TeamJSONAdapter teamAdapterForLogo,String givenPlayerName){
        String[] playerDataToReturn = new String[2];
        try {
            JSONArray arrayToRead = playerStatsJsonFile.getJSONArray("player_stats");
            for (int i = 0; i < arrayToRead.length(); i++) {
                JSONObject originalJSONObject = arrayToRead.getJSONObject(i);
                if(givenPlayerName.equals(originalJSONObject.getString("Player"))){
                    playerDataToReturn[0] = teamAdapterForLogo.getTeamPictureFromAbbreviation(originalJSONObject.getString("Team"));
                    playerDataToReturn[1] = originalJSONObject.getString("Team");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return playerDataToReturn;
    }

}

