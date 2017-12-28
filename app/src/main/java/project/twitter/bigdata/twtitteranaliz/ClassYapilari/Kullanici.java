package project.twitter.bigdata.twtitteranaliz.ClassYapilari;

import android.app.Application;

/**
 * Created by eGo on 31/10/2017.
 */

public class Kullanici extends Application {
    public Kullanici() {
    }
    String gelenveri;
    int cekilentweetSayisi;
    int cekilenkelimeSayisi;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGelenveri() {
        return gelenveri;
    }

    public void setGelenveri(String gelenveri) {
        this.gelenveri = gelenveri;
    }

    public int getCekilentweetSayisi() {
        return cekilentweetSayisi;
    }

    public void setCekilentweetSayisi(int cekilentweetSayisi) {
        this.cekilentweetSayisi = cekilentweetSayisi;
    }

    public int getCekilenkelimeSayisi() {
        return cekilenkelimeSayisi;
    }

    public void setCekilenkelimeSayisi(int cekilenkelimeSayisi) {
        this.cekilenkelimeSayisi = cekilenkelimeSayisi;
    }
}
