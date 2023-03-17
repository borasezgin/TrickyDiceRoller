package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int delayTime = 20;  // zar resimleri degisirken bir gecikme olmasi gerekli ki zar donuyo hissi versin
    int rollAnimations = 30;
    int[] diceImages = new int[]{R.drawable.d1,R.drawable.d2,R.drawable.d3,R.drawable.d4,R.drawable.d5,R.drawable.d6};   // zar rwesimlerini bir listeye atiyorum buradan cekicem sonra
    Random random = new Random();  // random kullnamam gerekli
    TextView tvTitle;   // title yazim icin
    ImageView die1;  // image lara verdigim id ler
    ImageView die2;
    LinearLayout diceContainer;   // tum layout dice container deddim
    MediaPlayer mp;  // arkada calacak zar sesi icin
    Button secretButton;
    boolean hackActive = false;
    int zarToplam;
    int hileliZarToplam;

    int[] num = new int[]{0,0,0,0,0,0};
    int zar1;
    int zar2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvTitle = findViewById(R.id.tvTitle);
        diceContainer = findViewById(R.id.diceContainer);
        die1 = findViewById(R.id.die1);
        die2 = findViewById(R.id.die2);
        mp = MediaPlayer.create(this,R.raw.roll);    // roll un icinde ses dosyayi var onu al ordan dedim
        secretButton = findViewById(R.id.hackButton);



        secretButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // bu butona tikladigimi belirtmem gerekiyor
                // ve bu butona tikladigim icin her sey farkli ilelemeli
                try {
                    Log.d("console yazisi","hile yaptin ");
                    hackActive = true;
                    rollHackDice();     // EKRANA TIKLADIM O ZAMAN GIT ROLLDICE FONKSIYONUNU CALISTIR DEDIM
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });


        if(hackActive == false) {
            diceContainer.setOnClickListener(new View.OnClickListener() {   // DICE CONTAINER A tiklandiginda yani TUM LAYOUT A TIKLANDIGINDA GIRICEK
                @Override
                public void onClick(View view) {
                    try {
                        Log.d("console yazisi","hile YAPMADIN ");
                        rollDice();   // EKRANA TIKLADIM O ZAMAN GIT ROLLDICE FONKSIYONUNU CALISTIR DEDIM
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }

    private void rollHackDice() {
        Runnable runnable = new Runnable() {   // RUNNABLE BIR FONKSIYON
            @Override
            public void run() {
                for (int i=0; i < rollAnimations; i++){    // 40 kere devam et diyorum 40 kere zarlar degisicek
                    int zar1 = random.nextInt(6)+1;    // zar1 diye int deger tutuyorum ve bu 40 kere 1 ile 7 arasinda sayi tutucak
                    int zar2 = random.nextInt(6)+1;     // zar2 diye int deger tutuyorum ve bu 40 kere 1 ile 7 arasinda sayi tutucak
                    die1.setImageResource(diceImages[zar1 -1]);   // die1 id sine sahip olan image view a IMAGE SET EDICEZ VE BUNU   DICE IMAGES LISTESINDEN ALICAZ INDEX I DE YUKARDAKI RANDOM SAYILAR BELIRLICEK
                    die2.setImageResource(diceImages[zar2 -1]);
                    hileliZarToplam = zar1 + zar2;
                    Log.d("console hileli toplam", String.valueOf(hileliZarToplam));

                    if (hileliZarToplam<zarToplam){
                        rollAnimations++;
                    }

                    try {
                        Thread.sleep(delayTime);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        if (mp != null){
            mp.start();
        }
    }

    private void rollDice() {

        Runnable runnable = new Runnable() {   // RUNNABLE BIR FONKSIYON
            @Override
            public void run() {
                for (int i=0; i < rollAnimations; i++){    // 40 kere devam et diyorum 40 kere zarlar degisicek
                     zar1 = random.nextInt(6)+1;    // zar1 diye int deger tutuyorum ve bu 40 kere 1 ile 7 arasinda sayi tutucak
                     zar2 = random.nextInt(6)+1;     // zar2 diye int deger tutuyorum ve bu 40 kere 1 ile 7 arasinda sayi tutucak
                    die1.setImageResource(diceImages[zar1 -1]);   // die1 id sine sahip olan image view a IMAGE SET EDICEZ VE BUNU   DICE IMAGES LISTESINDEN ALICAZ INDEX I DE YUKARDAKI RANDOM SAYILAR BELIRLICEK
                    die2.setImageResource(diceImages[zar2 -1]);

                    zarToplam = zar1 + zar2;

                    try {
                        Thread.sleep(delayTime);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

                for (int i = 1; i < 6; i++) {
                    if (zar1 == i) {
                        num[i]++;
                        Log.d("ILK ZAR", String.valueOf(num[i]));

                    }
                }
                for (int i = 1; i < 6; i++) {
                    if (zar2 == i) {
                        num[i]++;
                        Log.d("IKINCI ZAR", String.valueOf(num[i]));

                    }
                }


                Log.d("HILESIZ toplam", String.valueOf(zarToplam));

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        if (mp != null){
            mp.start();
        }



    }
}