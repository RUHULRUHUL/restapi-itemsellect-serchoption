package ruhulbdapp.com.blogspot;

import java.util.List;

import ruhulbdapp.com.blogspot.model.Continents;

public class OurMainDataClass {

    public List<Continents> data;

    public List<Continents> getData() {
        return data;
    }

    public void setData(List<Continents> data) {
        this.data = data;
    }

    public OurMainDataClass(List<Continents> data) {
        this.data = data;
    }
}
