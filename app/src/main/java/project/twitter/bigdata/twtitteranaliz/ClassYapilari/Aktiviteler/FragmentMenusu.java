package project.twitter.bigdata.twtitteranaliz.ClassYapilari.Aktiviteler;

import android.app.ProgressDialog;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Aktiviteler.FragmentYapilari.Duygu_Fragment;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Kullanici;
import project.twitter.bigdata.twtitteranaliz.R;

public class FragmentMenusu extends AppCompatActivity {
    Class fragmentClass = null;
    ImageView mutluluk_orani_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_menusu);
        gorselAta();
        listenerAyarla();
    }


    void gorselAta() {
        mutluluk_orani_btn = (ImageView) findViewById(R.id.mutluluk_orani_btn);

    }

    void listenerAyarla() {
        mutluluk_orani_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mutlulukOranFragmentGecis();
            }
        });
    }

    public void mutlulukOranFragmentGecis() {
        Fragment fragment = null;
        fragmentClass = Duygu_Fragment.class;
        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragmentManager.beginTransaction().replace(R.id.fragment_tutucu, fragment).commit();
    }

}
