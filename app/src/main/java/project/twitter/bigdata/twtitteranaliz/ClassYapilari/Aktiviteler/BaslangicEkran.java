package project.twitter.bigdata.twtitteranaliz.ClassYapilari.Aktiviteler;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Aktiviteler.FragmentYapilari.Anaekran_Fragment;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Aktiviteler.FragmentYapilari.Duygu_Fragment;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Aktiviteler.FragmentYapilari.EtkilesimFragment;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Aktiviteler.FragmentYapilari.HangiTakim_Fragment;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Aktiviteler.FragmentYapilari.ProjeHakkindaFragment;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Aktiviteler.FragmentYapilari.SanatSeviyesi_Fragment;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Kullanici;
import project.twitter.bigdata.twtitteranaliz.Kutuphaneler.Kutuphane;
import project.twitter.bigdata.twtitteranaliz.R;

public class BaslangicEkran extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Class fragmentClass = null;
    Kutuphane kutuphane;
    Kullanici kullanici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baslangic_ekran);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        gorselAta();
        View hView = navigationView.getHeaderView(0);
        ImageView kisiresmi = (ImageView) hView.findViewById(R.id.kisi_resim_nav);
        TextView kisiismi = (TextView) hView.findViewById(R.id.kisi_isim_nav);
        kisiismi.setText("@" + kullanici.getId());
        kutuphane.netResim(kisiresmi, "https://twitter.com/" + kullanici.getId() + "/profile_image?size=original");

    }

    public void gorselAta() {
        kullanici = (Kullanici) getApplicationContext();
        kutuphane = new Kutuphane(this);
        anaEkran();

    }

    public void takimEkran() {
        actionbarAyarla("Spor Analiz", "Ekranı");
        Fragment fragment = null;
        fragmentClass = HangiTakim_Fragment.class;
        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragmentManager.beginTransaction().replace(R.id.fragment_tutucu, fragment).commit();
    }

    public void anaEkran() {
        actionbarAyarla("Kişi Görüntüleme", "Ekranı");
        Fragment fragment = null;
        fragmentClass = Anaekran_Fragment.class;
        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragmentManager.beginTransaction().replace(R.id.fragment_tutucu, fragment).commit();
    }

    public void duyguEkran() {
        actionbarAyarla("Duygu Analiz", "Ekranı");
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

    public void etkilesimeGecilenler() {
        actionbarAyarla("Etkileşim", "Ekranı");
        Fragment fragment = null;
        fragmentClass = EtkilesimFragment.class;
        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragmentManager.beginTransaction().replace(R.id.fragment_tutucu, fragment).commit();
    }

    public void sanatsalPuanlama() {
        actionbarAyarla("Sanatsal Puan", "Ekranı");
        Fragment fragment = null;
        fragmentClass = SanatSeviyesi_Fragment.class;
        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragmentManager.beginTransaction().replace(R.id.fragment_tutucu, fragment).commit();
    }
    public void Hakkimizda() {
        actionbarAyarla("Proje", "Hakkında");
        Fragment fragment = null;
        fragmentClass = ProjeHakkindaFragment.class;
        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragmentManager.beginTransaction().replace(R.id.fragment_tutucu, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            anaEkran();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.baslangic_ekran, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tweets) {
            anaEkran();
        } else if (id == R.id.nav_mutluluk) {
            duyguEkran();
        } else if (id == R.id.nav_takim) {
            takimEkran();
        } else if (id == R.id.nav_etkilesim) {
            etkilesimeGecilenler();
        } else if (id == R.id.nav_hakkinda) {
            Hakkimizda();
        } else if (id == R.id.nav_sanat) {
            sanatsalPuanlama();
        } else if (id == R.id.nav_cikis) {
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void actionbarAyarla(String ust, String alt) {
        getSupportActionBar().setTitle(ust);
        getSupportActionBar().setSubtitle(alt);
    }
}
