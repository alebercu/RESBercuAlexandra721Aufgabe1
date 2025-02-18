package org.example;


import java.time.LocalDate;

public class Kampf {

    public int id;
    public String Held;
    public String Antagonist;
    Konfrontationstyp typ;
    public String Ort;
    public LocalDate datum;
    public double GlobalerEinfluss;

    public Kampf(int id, String held, String antagonist, Konfrontationstyp typ, String ort, LocalDate datum, double globalerEinfluss) {
        this.id = id;
        Held = held;
        Antagonist = antagonist;
        this.typ = typ;
        Ort = ort;
        this.datum = datum;
        GlobalerEinfluss = globalerEinfluss;
    }

    @Override
    public String toString() {
        return "Kampf{" +
                "id=" + id +
                ", Held='" + Held + '\'' +
                ", Antagonist='" + Antagonist + '\'' +
                ", typ=" + typ +
                ", Ort='" + Ort + '\'' +
                ", datum=" + datum +
                ", GlobalerEinfluss=" + GlobalerEinfluss +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getHeld() {
        return Held;
    }

    public String getAntagonist() {
        return Antagonist;
    }

    public Konfrontationstyp getTyp() {
        return typ;
    }

    public String getOrt() {
        return Ort;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public double getGlobalerEinfluss() {
        return GlobalerEinfluss;
    }
}
