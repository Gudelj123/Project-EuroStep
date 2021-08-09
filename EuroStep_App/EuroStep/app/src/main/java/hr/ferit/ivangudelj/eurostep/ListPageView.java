package hr.ferit.ivangudelj.eurostep;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import java.util.List;

public class ListPageView extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener  {
    private Button btnBack;
    private EditText etSearchItem;
    private ListView lvTrazenaLista;
    public String pressedButtonNumber;
    public ListAdapter tempadapter = null;
    public ListAdapter realListAdapter = null;
    Intent intentPerson;


    @Override
        //Funkcija koja se pokreće prilikom stvaranja activity.a (inicijalizacija objekata activity-a)
//**************************************************************************************************************
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        initiate();
        lvTrazenaLista.setTextFilterEnabled(true);
        intentPerson = new Intent(getApplicationContext(),CertainSelect.class);
//**************************************************************************************************************
        // Funkcija pregleda textBoxa koji koristimo za search
//**************************************************************************************************************
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lvTrazenaLista.setFilterText(etSearchItem.getText().toString());
                if(etSearchItem.getText().toString().isEmpty()){
                    lvTrazenaLista.clearTextFilter();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        etSearchItem.addTextChangedListener(watcher);
//**************************************************************************************************************

         //Povratak na prethodni activity
//**************************************************************************************************************
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPageView.this, MainActivity.class);
                startActivity(intent);
            }
        });
//**************************************************************************************************************
        //Pozivanje nekog adaptera ovisno o predanom broju s proslog activity-a
       pressedButtonNumber = getIntent().getExtras().getString("buttonNumber");
       callAdapter(pressedButtonNumber);
    }
    private void callAdapter(String pressedButtonNumber) {
        switch (pressedButtonNumber) {
            case "1":
                realListAdapter = coaches();
                break;
            case "2":
                realListAdapter = players();
                break;
            case "3":
                realListAdapter = teams();
                break;
        }
        lvTrazenaLista.setAdapter(realListAdapter);
        lvTrazenaLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String toNextActivity = realListAdapter.getItem(position).toString();
                String sendToCertainSelect;
                switch (pressedButtonNumber){
                    case "1": //Coaches
                        break;
                    case "2":  //Players
                        sendToCertainSelect = toNextActivity.substring(toNextActivity.indexOf("Name=")+5,toNextActivity.length()-1);
                        intentPerson.putExtra("Value",sendToCertainSelect);
                        intentPerson.putExtra("IDNumb",pressedButtonNumber);
                        startActivity(intentPerson);
                        break;
                    case "3":  //Teams
                        sendToCertainSelect = toNextActivity.substring(toNextActivity.indexOf("Name=")+5,toNextActivity.indexOf("Location")-2);
                        intentPerson.putExtra("Value",sendToCertainSelect);
                        intentPerson.putExtra("IDNumb",pressedButtonNumber);
                        startActivity(intentPerson);
                        break;
                }
            }
        });

    }
//**************************************************************************************************************
private void initiate() {
    btnBack = (Button) findViewById(R.id.btnBackListPageView);
    lvTrazenaLista = (ListView) findViewById(R.id.lvItemList);
    etSearchItem = (EditText) findViewById(R.id.etSearchBox);
}
//************************************PREPARING TEAMS ADAPTER***********************************************************
    private ListAdapter teams() {
        TeamJSONAdapter teamAdapter = new TeamJSONAdapter(getApplicationContext());
        try {
            tempadapter = teamAdapter.getTeamsAdapter(getApplicationContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tempadapter;
    }

//**************************************************************************************************************
    //*************************************PREPARING PLAYERS ADAPTER*********************************************************
        private ListAdapter players() {
            PlayerJSONAdapter playerAdapter = new PlayerJSONAdapter(getApplicationContext());
            try {
                tempadapter = playerAdapter.getPlayersAdapter(getApplicationContext());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return tempadapter;
        }

//**************************************************************************************************************
    //*****************************PREPARING COACHES ADAPTER******************************************************************
        private ListAdapter coaches() {
            CoachJSONAdapter coachAdapter = new CoachJSONAdapter(getApplicationContext());
            tempadapter = coachAdapter.getCoachesAdapter(getApplicationContext());
            return tempadapter;
        }
//**************************************************************************************************************
    @Override
    public void onClick(View v) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

}