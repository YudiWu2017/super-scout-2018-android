package com.example.sam.blutoothsocketreceiver;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FinalDataPoints extends ActionBarActivity {
    String numberOfMatch;
    String teamNumberOne;
    String teamNumberTwo;
    String teamNumberThree;
    String firstDefense;
    String secondDefense;
    String thirdDefense;
    String fourthDefense;
    String alliance;
    TextView finalScore;
    JSONObject superExternalData;
    ArrayList<String> teamOneDataName;
    ArrayList<String> teamOneDataScore;
    ArrayList<String> teamTwoDataName;
    ArrayList<String> teamTwoDataScore;
    ArrayList<String> teamThreeDataName;
    ArrayList<String> teamThreeDataScore;
    EditText allianceScore;
    ToggleButton captureCheck;
    File dir;
    PrintWriter file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finaldatapoints);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent = getIntent();
        numberOfMatch = intent.getExtras().getString("matchNumber");
        teamNumberOne = intent.getExtras().getString("teamNumberOne");
        teamNumberTwo = intent.getExtras().getString("teamNumberTwo");
        teamNumberThree = intent.getExtras().getString("teamNumberThree");
        firstDefense = intent.getExtras().getString("firstDefensePicked");
        secondDefense = intent.getExtras().getString("secondDefensePicked");
        thirdDefense = intent.getExtras().getString("thirdDefensePicked");
        fourthDefense = intent.getExtras().getString("fourthDefensePicked");
        alliance = intent.getExtras().getString("alliance");
        finalScore = (TextView)findViewById(R.id.finalScoreTextView);
        superExternalData = new JSONObject();
        if(alliance.equals("Blue Alliance")){
            finalScore.setTextColor(Color.BLUE);
        }else if(alliance.equals("Red Alliance")){
            finalScore.setTextColor(Color.RED);
        }
        teamOneDataName = intent.getStringArrayListExtra("dataNameOne");
        teamOneDataScore = intent.getStringArrayListExtra("ranksOfOne");
        teamTwoDataName = intent.getStringArrayListExtra("dataNameTwo");
        teamTwoDataScore = intent.getStringArrayListExtra("ranksOfTwo");
        teamThreeDataName = intent.getStringArrayListExtra("dataNameThree");
        teamThreeDataScore = intent.getStringArrayListExtra("ranksOfThree");

        allianceScore = (EditText) findViewById(R.id.finalScoreEditText);
        captureCheck = (ToggleButton) findViewById(R.id.captureToggleButton);
        dir = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/Super_scout_data");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.submit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Submit) {
            final Firebase firebaseRef = new Firebase("https://1678-dev-2016.firebaseio.com");
            Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {}

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {}
            };
            try {

                file = null;
                //make the directory of the file
                dir.mkdir();
                //can delete when doing the actual thing
                file = new PrintWriter(new FileOutputStream(new File(dir, ("Q" + numberOfMatch + " "  + new SimpleDateFormat("MM-dd-yyyy-H:mm:ss").format(new Date())))));
            } catch (IOException IOE) {
                Log.e("File error", "Failed to open File");
                return false;
            }
            firebaseRef.authWithPassword("1678programming@gmail.com", "Squeezecrush1", authResultHandler);
            firebaseRef.child("/TeamInMatchDatas").child(teamNumberOne + "Q" + numberOfMatch).child("teamNumber").setValue(Integer.parseInt(teamNumberOne));
            firebaseRef.child("/TeamInMatchDatas").child(teamNumberOne + "Q" + numberOfMatch).child("matchNumber").setValue(Integer.parseInt(numberOfMatch));
            firebaseRef.child("/TeamInMatchDatas").child(teamNumberTwo + "Q" + numberOfMatch).child("teamNumber").setValue(Integer.parseInt(teamNumberTwo));
            firebaseRef.child("/TeamInMatchDatas").child(teamNumberTwo + "Q" + numberOfMatch).child("matchNumber").setValue(Integer.parseInt(numberOfMatch));
            firebaseRef.child("/TeamInMatchDatas").child(teamNumberThree + "Q" + numberOfMatch).child("teamNumber").setValue(Integer.parseInt(teamNumberThree));
            firebaseRef.child("/TeamInMatchDatas").child(teamNumberThree + "Q" + numberOfMatch).child("matchNumber").setValue(Integer.parseInt(numberOfMatch));
            if(alliance.equals("Blue Alliance")) {
                firebaseRef.child("/Matches").child(numberOfMatch).child("blueDefensePositions").child("0").setValue(firstDefense);
                firebaseRef.child("/Matches").child(numberOfMatch).child("blueDefensePositions").child("1").setValue(secondDefense);
                firebaseRef.child("/Matches").child(numberOfMatch).child("blueDefensePositions").child("2").setValue(thirdDefense);
                firebaseRef.child("/Matches").child(numberOfMatch).child("blueDefensePositions").child("3").setValue(fourthDefense);
                firebaseRef.child("/Matches").child(numberOfMatch).child("blueDefensePositions").child("4").setValue("lb");

            }else if(alliance.equals("Red Alliance")){
                firebaseRef.child("/Matches").child(numberOfMatch).child("redDefensePositions").child("0").setValue(firstDefense);
                firebaseRef.child("/Matches").child(numberOfMatch).child("redDefensePositions").child("1").setValue(secondDefense);
                firebaseRef.child("/Matches").child(numberOfMatch).child("redDefensePositions").child("2").setValue(thirdDefense);
                firebaseRef.child("/Matches").child(numberOfMatch).child("redDefensePositions").child("3").setValue(fourthDefense);
                firebaseRef.child("/Matches").child(numberOfMatch).child("redDefensePositions").child("4").setValue("lb");
            }
            try {
                superExternalData.put("matchNumber", numberOfMatch);
                superExternalData.put("teamOne", teamNumberOne);
                superExternalData.put("teamTwo", teamNumberTwo);
                superExternalData.put("teamThree", teamNumberThree);
                superExternalData.put("defenseOne", firstDefense);
                superExternalData.put("defenseTwo", secondDefense);
                superExternalData.put("defenseThree", thirdDefense);
                superExternalData.put("defenseFour", fourthDefense);

            }catch(JSONException JE){
                Log.e("JSON Error", "couldn't put keys and values in json object");
            }

            for (int i = 0; i < teamOneDataName.size(); i++) {
                firebaseRef.child("/TeamInMatchDatas").child(teamNumberOne + "Q" + numberOfMatch).child(teamOneDataName.get(i)).setValue(Integer.parseInt(teamOneDataScore.get(i)));
                file.println(teamOneDataName.get(i) + ":" + teamOneDataScore.get(i));
            }
            for (int i = 0; i < teamTwoDataName.size(); i++) {
                firebaseRef.child("/TeamInMatchDatas").child(teamNumberTwo + "Q" + numberOfMatch).child(teamTwoDataName.get(i)).setValue(Integer.parseInt(teamTwoDataScore.get(i)));
                file.println(teamTwoDataName.get(i) + ":" + teamTwoDataScore.get(i));
            }
            for (int i = 0; i < teamThreeDataName.size(); i++) {
                firebaseRef.child("/TeamInMatchDatas").child(teamNumberThree + "Q" + numberOfMatch).child(teamThreeDataName.get(i)).setValue(Integer.parseInt(teamThreeDataScore.get(i)));
                file.println(teamThreeDataName.get(i) + ":" + teamThreeDataScore.get(i));
            }
            Log.wtf("test", "upload");
                if (alliance.equals("Blue Alliance")) {
                    firebaseRef.child("/Matches").child(numberOfMatch).child("blueAllianceDidCapture").setValue(captureCheck.isChecked() ? "true" : "false");
                    firebaseRef.child("/Matches").child(numberOfMatch).child("blueScore").setValue(Integer.parseInt(allianceScore.getText().toString()));
                    file.println("blueAllianceCapture :" + "true");
                } else if (alliance.equals("Red Alliance")) {
                    firebaseRef.child("/Matches").child(numberOfMatch).child("redAllianceDidCapture").setValue(captureCheck.isChecked() ? "true" : "false");
                    firebaseRef.child("/Matches").child(numberOfMatch).child("redScore").setValue(Integer.parseInt(allianceScore.getText().toString()));
                    file.println("blueAllianceCapture :" + "false");
                }



            Toast.makeText(this, "Sent Match Data", Toast.LENGTH_SHORT).show();
            Intent backToHome = new Intent(this, MainActivity.class);
            backToHome.putExtra("alliance", alliance);
            backToHome.putExtra("number", numberOfMatch);
            Log.e("alliance", alliance);
            startActivity(backToHome);
        }
        return super.onOptionsItemSelected(item);
    }
}