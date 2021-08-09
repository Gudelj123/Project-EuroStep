package hr.ferit.ivangudelj.eurostep;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class CoachJSONAdapter extends AppCompatActivity implements JSONLoader {
    private JSONObject coachesJsonFile;

    public CoachJSONAdapter(Context context) {
        try {
            this.coachesJsonFile = new JSONObject(loadJSONFromAsset(context,"coaches.json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public ListAdapter getCoachesAdapter(Context context) {
        ListAdapter adapter = null;
        try {
            JSONArray arrayCoach = this.coachesJsonFile.getJSONArray("coaches");
            HashMap<String, String> listCoach;
            ArrayList<HashMap<String, String>> arrayListCoaches = new ArrayList<>();
            for (int i = 0; i < arrayCoach.length(); i++) {
                JSONObject originalJSONObject = arrayCoach.getJSONObject(i);
                listCoach = new HashMap<>();
                String coachName_v = originalJSONObject.getString("Name");
                String coachTeamID_v = originalJSONObject.getString("TeamName");
                listCoach.put("Name", coachName_v);
                listCoach.put("Team", coachTeamID_v);
                arrayListCoaches.add(listCoach);
            }
            adapter = new SimpleAdapter(context, arrayListCoaches, R.layout.list_item, new String[]{"Name", "Team"}, new int[]{R.id.tvMainData, R.id.tvSecondaryData});
            return adapter;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return adapter;
    }


    public String loadJSONFromAsset (Context context, String name){
        String json;
        try {
            InputStream in = context.getAssets().open(name);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
