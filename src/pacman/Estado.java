/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacman;

/**
 *
 * @author Aaron
 */

/*
no indicara si hay pared para que que personaje no pueda pasar por ahi
*/
public class Estado {
    
    private boolean estado;
    private int ejeX;
    private int ejeY;

    public Estado(boolean estado, int ejeX, int ejeY) {
        this.estado = estado;
        this.ejeX = ejeX;
        this.ejeY = ejeY;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
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
    
    
    
}
