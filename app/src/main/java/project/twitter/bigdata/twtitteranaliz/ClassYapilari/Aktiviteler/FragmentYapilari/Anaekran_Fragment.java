package project.twitter.bigdata.twtitteranaliz.ClassYapilari.Aktiviteler.FragmentYapilari;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import project.twitter.bigdata.twtitteranaliz.ClassYapilari.AdapterInclude.TweeterAdapter;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Aktiviteler.BaslangicEkran;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Kullanici;
import project.twitter.bigdata.twtitteranaliz.Kutuphaneler.Kutuphane;
import project.twitter.bigdata.twtitteranaliz.R;


public class Anaekran_Fragment extends Fragment {

    ListView tweetlistview;
    Kullanici kullanici;
    ImageView anaekran_pf;
    TextView anaekran_id;
    Kutuphane kutuphane;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_anaekran_, container, false);
        gorselata(v);
        return v;
    }

    void gorselata(View v){
        kutuphane = new Kutuphane(getActivity());
        kullanici = (Kullanici) getActivity().getApplicationContext();
        anaekran_id = (TextView)v. findViewById(R.id.anaekran_id);
        anaekran_pf = (ImageView)v. findViewById(R.id.anaekran_pf);
        tweetlistview = (ListView) v.findViewById(R.id.tweetlistview);
        ArrayList<String> strings = new ArrayList<>();
        kullanici = (Kullanici) getActivity().getApplicationContext();
        String[] gelenveri = kullanici.getGelenveri().split("%%%%%%%");
        Collections.addAll(strings, gelenveri);
        tweetlistview.setAdapter(new TweeterAdapter(strings, getActivity()));
        kutuphane.netResim(anaekran_pf, "https://twitter.com/" + kullanici.getId() + "/profile_image?size=original");
        anaekran_id.setText("@" + kullanici.getId());
    }

}
