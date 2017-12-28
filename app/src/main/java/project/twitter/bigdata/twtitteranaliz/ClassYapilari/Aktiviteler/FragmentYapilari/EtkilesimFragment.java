package project.twitter.bigdata.twtitteranaliz.ClassYapilari.Aktiviteler.FragmentYapilari;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.twitter.bigdata.twtitteranaliz.ClassYapilari.AdapterInclude.EtkilesimAdapter;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Kullanici;
import project.twitter.bigdata.twtitteranaliz.Kutuphaneler.Kutuphane;
import project.twitter.bigdata.twtitteranaliz.R;


public class EtkilesimFragment extends Fragment {
    GridView kisicontainer;
    Kullanici kullanici;
    ImageView encok_resim;
    TextView encok_isim;
    Button encok_gor;
    Kutuphane kutuphane;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_etkilesim, container, false);
        gorselAta(v);
        return v;
    }

    void gorselAta(View v) {
        kutuphane=new Kutuphane(getActivity());
        kullanici = (Kullanici) getActivity().getApplicationContext();
        kisicontainer = v.findViewById(R.id.gridview);
        encok_resim = v.findViewById(R.id.encok_resim);
        encok_gor = v.findViewById(R.id.encok_gor);
        encok_isim = v.findViewById(R.id.encok_isim);
        EtkilesimAdapter etkilesimAdapter = new EtkilesimAdapter(enCokIletisimeGecilenler(kullanici.getGelenveri()), getActivity());
        kisicontainer.setAdapter(etkilesimAdapter);
    }


    ArrayList<String> enCokIletisimeGecilenler(String cevap) {
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
                    occurrences.put(word.replace(":", ""), oldCount + 1);
                }
            }
        }
        final ArrayList<String> kisiler = HashMapSirala(occurrences);
        kutuphane.netResim(encok_resim, "https://twitter.com/" + kisiler.get(0).split("%=%")[0] + "/profile_image?size=original");
        encok_gor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://twitter.com/"+kisiler.get(0).split("%=%")[0];
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        encok_isim.setText(kullanici.getId()+" isimli hesap en çok "+kisiler.get(0).split("%=%")[0]+" isimli hesapla "+kisiler.get(0).split("%=%")[1] + " kere iletişime geçmiştir.");
        return kisiler;
    }

    ArrayList<String> HashMapSirala(Map<String, Integer> map) {
        Object[] a = map.entrySet().toArray();
        ArrayList<String> sira = new ArrayList<>();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o1).getValue().compareTo(
                        ((Map.Entry<String, Integer>) o2).getValue());
            }
        });

        for (int i = 0; i < a.length; i++) {
            System.out.println(i + ". Eleman " + ((Map.Entry<String, Integer>) a[i]).getKey() + " : "
                    + ((Map.Entry<String, Integer>) a[i]).getValue());
            sira.add("" + ((Map.Entry<String, Integer>) a[i]).getKey() + "%=%" + ((Map.Entry<String, Integer>) a[i]).getValue());
        }
        Collections.reverse(sira);
        return sira;
    }
}
