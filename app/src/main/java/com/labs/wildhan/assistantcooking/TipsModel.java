package com.labs.wildhan.assistantcooking;

import android.widget.BaseAdapter;

import java.io.Serializable;

/**
 * Created by WILDHAN on 12/07/2017.
 */

public class TipsModel implements Serializable {
    Integer id;
    String nama, link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
