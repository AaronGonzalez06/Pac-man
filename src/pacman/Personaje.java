/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacman;

/**
 *
 * @author Aaron
 */
public class Personaje {
    private int ejeX;
    private int ejeY;
    private String secuencia1;
    private String secuencia2;

    public Personaje(int ejeX, int ejeY, String secuencia1, String secuencia2) {
        this.ejeX = ejeX;
        this.ejeY = ejeY;
        this.secuencia1 = secuencia1;
        this.secuencia2 = secuencia2;
    }

    public int getEjeX() {
        return ejeX;
    }

    public void setEjeX(int ejeX) {
        this.ejeX = ejeX;
    }

    public int getEjeY() {
        return ejeY;
    }

    public void setEjeY(int ejeY) {
        this.ejeY = ejeY;
    }

    public String getSecuencia1() {
        return secuencia1;
    }

    public void setSecuencia1(String secuencia1) {
        this.secuencia1 = secuencia1;
    }

    public String getSecuencia2() {
        return secuencia2;
    }

    public void setSecuencia2(String secuencia2) {
        this.secuencia2 = secuencia2;
    }
    
    
    
}
