package com.labs.wildhan.assistantcooking;

/**
 * Created by WILDHAN on 29/03/2017.
 */

public class BahanModel {
    String bahan;

    public BahanModel(String bahan, Boolean selected){
        this.bahan = bahan;
        this.selected = selected;
    }

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getBahan() {
        return bahan;
    }

    public void setBahan(String bahan) {
        this.bahan = bahan;
    }

    Boolean selected = false;
}
