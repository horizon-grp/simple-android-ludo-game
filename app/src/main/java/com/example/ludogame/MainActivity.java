package com.example.ludogame;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // Views
    ImageView diceImage;
    Button rollDiceButton;
    TextView infoText;
//    LinearLayout playerAPath;
//    LinearLayout playerBPath;
    TextView playerALabel;
    TextView playerBLabel;

    ArrayList<ImageView> player1Path;
    ArrayList<ImageView> player2Path;

    int player1Position;
    int player2Position;

    private enum Player{
        PLAYER_1,
        PLAYER_2
    }


    // Variables
    Player currentPlayer; // 1 for Player A, 2 for Player B

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        diceImage = findViewById(R.id.dice_image);
        rollDiceButton = findViewById(R.id.roll_dice_button);
        infoText = findViewById(R.id.info_text);
//        playerAPath = findViewById(R.id.playerA_path);
//        playerBPath = findViewById(R.id.playerB_path);
        playerALabel = findViewById(R.id.playerA_label);
        playerBLabel = findViewById(R.id.playerB_label);

        player1Position = 0;
        player2Position = 0;
        currentPlayer = Player.PLAYER_1;

        player1Path = new ArrayList<ImageView>();

        player1Path.add((ImageView) findViewById(R.id.stepA_1));
        player1Path.add((ImageView) findViewById(R.id.stepA_2));
        player1Path.add((ImageView) findViewById(R.id.stepA_3));
        player1Path.add((ImageView) findViewById(R.id.stepA_4));
        player1Path.add((ImageView) findViewById(R.id.stepA_5));
        player1Path.add((ImageView) findViewById(R.id.stepA_6));
        player1Path.add((ImageView) findViewById(R.id.stepA_7));
        player1Path.add((ImageView) findViewById(R.id.stepA_8));
        player1Path.add((ImageView) findViewById(R.id.stepA_9));
        player1Path.add((ImageView) findViewById(R.id.stepA_10));

        player2Path = new ArrayList<ImageView>();
        player2Path.add((ImageView) findViewById(R.id.stepB_1));
        player2Path.add((ImageView) findViewById(R.id.stepB_2));
        player2Path.add((ImageView) findViewById(R.id.stepB_3));
        player2Path.add((ImageView) findViewById(R.id.stepB_4));
        player2Path.add((ImageView) findViewById(R.id.stepB_5));
        player2Path.add((ImageView) findViewById(R.id.stepB_6));
        player2Path.add((ImageView) findViewById(R.id.stepB_7));
        player2Path.add((ImageView) findViewById(R.id.stepB_8));
        player2Path.add((ImageView) findViewById(R.id.stepB_9));
        player2Path.add((ImageView) findViewById(R.id.stepB_10));


        // Set initial turn information
        updateTurnInfo();

        // Set onClickListener for roll dice button
        rollDiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });
    }
    private void rollDice() {
        // Generate a random number between 1 and 6
        Random random = new Random();
        int diceValue = random.nextInt(6) + 1;
        Log.d("my-los", Integer.toString(diceValue));

        // Update the dice image
        String diceImageName = "dice_" + diceValue;
        int resourceId = getResources().getIdentifier(diceImageName, "drawable", getPackageName());
        diceImage.setImageResource(resourceId);

        // Update player's position based on the dice value
        if (currentPlayer == Player.PLAYER_1){
            updatePlayerPosition(diceValue, Player.PLAYER_1);
            currentPlayer = Player.PLAYER_2;
        }
        else if (currentPlayer == Player.PLAYER_2){
            updatePlayerPosition(diceValue, Player.PLAYER_2);
            currentPlayer = Player.PLAYER_1;
        }

        // Update Info
        updateTurnInfo();
    }

    private void updatePlayerPosition(int moves, Player player){
        if(playerCanMove(moves, player)){
            if (player == Player.PLAYER_1){
                (player1Path.get(player1Position)).setImageResource(R.drawable.player1);

                player1Position += moves;

                (player1Path.get(player1Position)).setImageResource(R.drawable.player1_seed);

            }
            else if (player == Player.PLAYER_2){
                (player2Path.get(player2Position)).setImageResource(R.drawable.player2);

                player2Position += moves;

                (player2Path.get(player2Position)).setImageResource(R.drawable.player2_seed);

            }
        }
    }

    private boolean playerCanMove(int value, Player player){
        int position;
        int remaining;
        if (player == Player.PLAYER_1){
            position = player1Position;
            remaining = 9 - position;
            return value <= remaining;
        }
        else if (player == Player.PLAYER_2){
            position = player2Position;
            remaining = 9 - position;
            return value <= remaining;
        }
        return false;
    }
    private void updateTurnInfo() {
        String turnText = "Player " + ((currentPlayer == Player.PLAYER_1) ? "A" : "B") + "'s Turn";
        infoText.setText(turnText);
    }
}