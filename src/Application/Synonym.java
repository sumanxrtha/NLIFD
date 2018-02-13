package Application;

import javafx.beans.property.SimpleStringProperty;

public class Synonym {

    private final SimpleStringProperty sname;
    private final SimpleStringProperty scolumn;
    private final SimpleStringProperty stable;

    public Synonym(String syname, String sycolumn, String sytable) {
        this.sname = new SimpleStringProperty(syname);
        this.scolumn = new SimpleStringProperty(sycolumn);
        this.stable = new SimpleStringProperty(sytable);
    }

    public String getSname() {
        return sname.get();
    }

    public void setSname(String syname) {
        sname.set(syname);
    }

    public String getScolumn() {
        return scolumn.get();
    }

    public void setScolumn(String sycolumn) {
        scolumn.set(sycolumn);
    }

    public String getStable() {
        return stable.get();
    }

    public void setStable(String sytable) {
//        this.stable = stable;
        stable.set(sytable);
    }

    @Override
    public String toString() {
        return "Synonym{" +
                "sname='" + sname + '\'' +
                ", scolumn='" + scolumn + '\'' +
                ", stable='" + stable + '\'' +
                '}';
    }
}
