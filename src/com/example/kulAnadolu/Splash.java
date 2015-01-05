
package com.example.kulAnadolu;
import com.example.kulAnadolu.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread mSplashThread;//thread classdan obje olustrduk uygulamann 4 saniye uyutulmasi icin
        mSplashThread = new Thread(){
            @Override public void run(){ 
                try { 
                	sleep(3000);  

                        Intent i=new Intent(Splash.this,MainActivity.class);
                        startActivity(i);
                    } 
                catch(InterruptedException ex){ 
                	ex.printStackTrace();
 
                }
                finally{
                     
                    
                    finish();
                }
                 
            } 
        };//thread objesini olustrduk ve istedmz sekilde sekillendrdik
        mSplashThread.start();// thread objesini calistriyoruz
         
    }
}
