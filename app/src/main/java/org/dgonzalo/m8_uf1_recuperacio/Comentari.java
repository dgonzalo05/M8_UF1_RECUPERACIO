package org.dgonzalo.m8_uf1_recuperacio;

public class Comentari {

    String recomenada;
    String comentari;

    public Comentari(){

    }

    public Comentari(String comentari, String recomenada) {
        this.recomenada = recomenada;
        this.comentari = comentari;
    }

    public String getRecomenada() {
        return recomenada;
    }

    public void setRecomenada(String recomenada) {
        this.recomenada = recomenada;
    }

    public String getComentari() {
        return comentari;
    }

    public void setComentari(String comentari) {
        this.comentari = comentari;
    }
}
