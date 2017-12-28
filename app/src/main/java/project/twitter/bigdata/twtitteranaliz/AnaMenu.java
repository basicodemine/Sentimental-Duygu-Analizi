package project.twitter.bigdata.twtitteranaliz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Aktiviteler.BaslangicEkran;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Aktiviteler.FragmentMenusu;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Attr.Takimlar.BJK_K_words;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Attr.Takimlar.ESK_K_words;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Attr.Takimlar.FB_K_words;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Attr.Takimlar.GS_K_words;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Attr.Takimlar.TS_K_words;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Kullanici;
import project.twitter.bigdata.twtitteranaliz.Kutuphaneler.Callback;
import project.twitter.bigdata.twtitteranaliz.Kutuphaneler.Kutuphane;

public class AnaMenu extends AppCompatActivity {

    Kutuphane kutuphane;
    EditText et_id;
    Button btn_cek;
    Button sayfa_gecis;
    int aaaaa;
    String gelendata = "";
    int sayfano = 1;
    veriCekimiTamam cekimiTamam;
    int adet = 0;
    Kullanici kullanici;
    ProgressDialog pd;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasarimsal_layout);
        gorselAta();
        listenerAyarla();
    }


    void gorselAta() {
        kullanici = (Kullanici) getApplicationContext();
        kutuphane = new Kutuphane(this);
        pd = new ProgressDialog(this);
        cekimiTamam = new veriCekimiTamam() {
            @Override
            public void veriCekildi(String cevap) {

            }
        };
        imageView = (ImageView) findViewById(R.id.imageView);
        sayfa_gecis = (Button) findViewById(R.id.mutluluk);
        sayfa_gecis.setEnabled(false);
        et_id = (EditText) findViewById(R.id.tv_id);
        btn_cek = (Button) findViewById(R.id.tv_cek);
        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.yan_son);
        imageView.startAnimation(startAnimation);
    }

    void listenerAyarla() {
        sayfano = 1;
        gelendata = "";
        btn_cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adet = 0;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        f_dahacek_doldur(et_id.getText().toString(), "200");
                    }
                });
            }
        });
    }

    void f_dahacek_doldur(final String id, String sayi) {
        pd.setMessage(id + " hesabından " + adet + " adet veri çekildi");
        pd.setCancelable(false);
        if (!pd.isShowing())
            pd.show();
        kullanici.setId(id);
        String url = "http://egemen.bilisimeviniz.com/webservices.php?isim=" + id + "&sayi=200&sayfa=" + sayfano;
        kutuphane.EvoData(url, Request.Method.GET, new Callback() {
            @Override
            public void cevap(String cevap) {
                String[] tws = Html.fromHtml(cevap).toString().split("%%%%%%%");
                adet += tws.length;
                pd.setMessage(id + " hesabından " + adet + " adet veri çekildi");

                Log.e("SİZE:", "" + tws.length);
                gelendata += Html.fromHtml(cevap).toString();
                if (tws.length > 190) {
                    sayfano++;
                    f_dahacek_doldur(et_id.getText().toString(), "");
                } else {
                    cekimiTamam.veriCekildi(gelendata);
                    kullanici.setGelenveri(gelendata);
                    kullanici.setCekilentweetSayisi(gelendata.split("%%%%%%%").length);
                    kullanici.setCekilenkelimeSayisi(gelendata.split(" ").length);
                    if (pd.isShowing())
                        pd.dismiss();
                    sayfa_gecis.setEnabled(true);
                    et_id.setEnabled(false);
                }
            }

            @Override
            public void hata(String hata) {
                if (pd.isShowing())
                    pd.dismiss();
            }
        });
    }


    void sinametikPuan(String cevap) {

    }


    void enCokIletisimeGecilenler(String cevap) {
        String girdi = Html.fromHtml(cevap).toString().replace("%%%%%%%", " ");
        Map<String, Integer> occurrences = new HashMap<String, Integer>();
        String[] ayrac = girdi.split(" ");

        for (String word : ayrac) {
            if (word.length() > 0) {
                if (word.charAt(0) == '@') {
                    Integer oldCount = occurrences.get(word);
                    if (oldCount == null) {
                        oldCount = 0;
                    }
                    occurrences.put(word, oldCount + 1);
                }
            }
        }
        List<String> keys = new ArrayList<String>(occurrences.keySet());
        List<Integer> values = new ArrayList<Integer>(occurrences.values());
        for (int i = 0; i < values.size(); i++) {
            Log.e("Kişi: " + keys.get(i), values.get(i) + " kere");
        }
    }






    public void mutluluk(View view) {
        startActivity(new Intent(AnaMenu.this, BaslangicEkran.class));
    }

    public interface veriCekimiTamam {
        void veriCekildi(String cevap);
    }

}
