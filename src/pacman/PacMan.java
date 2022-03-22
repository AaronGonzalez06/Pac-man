/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pacman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Aaron
 */
public class PacMan extends JFrame {

    JPanel panel;
    JPanel[][] matriz = new JPanel[25][25];
    ArrayList monedas = new ArrayList();
    ArrayList pared = new ArrayList();
    Personaje Pacman;
    JLabel puntuacion;
    int num = 0;
    boolean colision = false;

    public PacMan() {
        ventana();
        titulo();
        panel();
        logica();
    }

    public void ventana() {

        this.setSize(1000, 1000);
        //pone titulo a la ventana
        setTitle("Pacman");
        //nos hace el cierre de la ventana 
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(1000, 1000));
        this.setResizable(false);
        this.getContentPane().setBackground(Color.BLACK);
        this.setLayout(null);

    }

    public void titulo() {
        JLabel titulo = new JLabel();
        puntuacion = new JLabel();
        titulo.setText("PACMAN");
        puntuacion.setText("Puntuación: " + num);
        titulo.setBounds(300, 50, 400, 75);
        puntuacion.setBounds(800, 100, 150, 75);
        titulo.setForeground(Color.YELLOW);
        puntuacion.setForeground(Color.YELLOW);
        File fuente = new File("fuente/fuente.ttf");
        try {
            //crea la fuente
            Font font = Font.createFont(Font.TRUETYPE_FONT, fuente);
            //dar tanaño fuente
            Font sizedFont = font.deriveFont(75f);
            Font sizedPuntuacion = font.deriveFont(12f);
            titulo.setFont(sizedFont);
            puntuacion.setFont(sizedPuntuacion);
            //titulo.setFont(sizedFont);
        } catch (FontFormatException ex) {
            System.err.println("error en font format");
        } catch (IOException ex) {
            System.err.println("error de entrada/salida");
        }

        this.add(titulo);
        this.add(puntuacion);
    }

    public void panel() {
        panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setBounds(150, 180, 650, 650);
        panel.setLayout(new GridLayout(25, 25));

        for (int x = 0; x < 25; x++) {
            for (int y = 0; y < 25; y++) {
                matriz[x][y] = new JPanel();
                if (x == 0) {
                    matriz[x][y].setBackground(Color.blue);
                    pared.add(new Estado(true, x, y));
                } else if (x == 24) {
                    matriz[x][y].setBackground(Color.blue);
                    pared.add(new Estado(true, x, y));
                } else if (y == 0) {
                    matriz[x][y].setBackground(Color.blue);
                    pared.add(new Estado(true, x, y));
                } else if (y == 24) {
                    matriz[x][y].setBackground(Color.blue);
                    pared.add(new Estado(true, x, y));
                } else if ((x == 2 && y > 1 && y < 6) || (x == 2 && (y > 7 && y < 13)) || (x == 2 && (y > 14 && y < 21))) {
                    matriz[x][y].setBackground(Color.blue);
                    pared.add(new Estado(true, x, y));
                } else if ((x == 4 && y > 1 && y < 4) || (x == 4 && (y > 5 && y < 10)) || (x == 4 && (y > 10 && y < 16)) || (x == 4 && (y > 16 && y < 22))) {
                    matriz[x][y].setBackground(Color.blue);
                    pared.add(new Estado(true, x, y));
                } else if ((x == 6 && y > 1 && y < 15) || (x == 6 && (y > 15 && y < 23))) {
                    matriz[x][y].setBackground(Color.blue);
                    pared.add(new Estado(true, x, y));
                } else if ((x == 8 && y > 1 && y < 9) || (x == 8 && (y > 9 && y < 23))) {
                    matriz[x][y].setBackground(Color.blue);
                    pared.add(new Estado(true, x, y));
                } else if ((x == 10 && y > 1 && y < 9) || (x == 10 && (y > 9 && y < 18)) || (x == 10 && (y > 18 && y < 23))) {
                    matriz[x][y].setBackground(Color.blue);
                    pared.add(new Estado(true, x, y));
                } else if ((x == 12 && (y > 1 && y < 5)) || (x == 12 && (y > 6 && y < 11)) || (x == 12 && (y > 12 && y < 22))) {
                    matriz[x][y].setBackground(Color.blue);
                    pared.add(new Estado(true, x, y));
                } else if ((x == 14 && y > 1 && y < 16) || (x == 14 && (y > 16 && y < 23))) {
                    matriz[x][y].setBackground(Color.blue);
                    pared.add(new Estado(true, x, y));
                } else if ((y == 2 && x > 15 && x < 23) || (y == 22 && x > 15 && x < 23) || (y == 6 && x > 19 && x < 23) || (y == 18 && x > 19 && x < 23) || (y == 4 && x > 17 && x < 23) || (y == 20 && x > 17 && x < 23)) {
                    matriz[x][y].setBackground(Color.blue);
                    pared.add(new Estado(true, x, y));
                } else if (x == 16 && y > 3 && y < 21) {
                    matriz[x][y].setBackground(Color.blue);
                    pared.add(new Estado(true, x, y));
                } else if (x == 18 && y > 4 && y < 20) {
                    matriz[x][y].setBackground(Color.blue);
                    pared.add(new Estado(true, x, y));
                } else if (x == 21 && y > 7 && y < 17) {
                    matriz[x][y].setBackground(Color.blue);
                    pared.add(new Estado(true, x, y));
                } else if (x == 17 && y == 12) {
                    JLabel imagen = new JLabel();
                    String nombre = "img/comecoco.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[x][y].add(imagen);
                    matriz[x][y].setBackground(Color.black);

                    //creamos el objeto PAcmen
                    Pacman = new Personaje(x, y, "a", "b");

                } else {
                    JLabel imagen = new JLabel();
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[x][y].add(imagen);
                    monedas.add(new Moneda(x, y, true));
                    matriz[x][y].setBackground(Color.black);
                }

                panel.add(matriz[x][y]);
            }
        }

        this.add(panel);
        System.out.println(monedas.size());
    }

    public void logica() {
        System.out.println(Pacman.getEjeX());
        System.out.println(Pacman.getEjeY());

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //cuando soltamos la tecla

                if (e.getKeyChar() == 'd') {
                    //derecha
                    //ejespacman   
                    int ejeX = Pacman.getEjeX();
                    int ejeY = Pacman.getEjeY();
                    int derecha = Pacman.getEjeY() + 1;

                    for (int x = 0; x < monedas.size(); x++) {
                        Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                        int monedaX = coordenadasMoneda.getEjeX();
                        int monedaY = coordenadasMoneda.getEjeY();

                        if ((monedaX == ejeX) && (monedaY == derecha) && coordenadasMoneda.isEstado() == true) {
                            System.out.println("yes");
                            puntuacion.setText("Puntuación: " + num++);
                            coordenadasMoneda.setEstado(false);
                        }
                    }

                    for (int x = 0; x < pared.size(); x++) {
                        Estado coordenadasPared = (Estado) pared.get(x);
                        int paredX = coordenadasPared.getEjeX();
                        int paredY = coordenadasPared.getEjeY();
                        if ((paredX == ejeX) && (paredY == derecha) && coordenadasPared.isEstado() == true) {
                            colision = true;
                        }
                    }

                    if (colision == true) {
                        colision = false;
                    } else {

                        //reseteamos panel derecho
                        matriz[ejeX][derecha].removeAll();
                        matriz[ejeX][derecha].repaint();
                        JLabel imagen = new JLabel();
                        String nombre = "img/comecoco.png";
                        ImageIcon imageicon = new ImageIcon(nombre);
                        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                        imagen.setIcon(icon);
                        imagen.setBounds(0, 0, 22, 22);
                        matriz[ejeX][derecha].add(imagen);
                        matriz[ejeX][derecha].setBackground(Color.black);
                        //elimina todo la posicion actual donde esta el pacman
                        matriz[ejeX][ejeY].removeAll();
                        matriz[ejeX][ejeY].repaint();

                        Pacman.setEjeY(derecha);

                    }

                } else if (e.getKeyChar() == 'a') {
                    //izquierda
                    int ejeX = Pacman.getEjeX();
                    int ejeY = Pacman.getEjeY();
                    int izquierda = Pacman.getEjeY() - 1;

                    for (int x = 0; x < monedas.size(); x++) {
                        Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                        int monedaX = coordenadasMoneda.getEjeX();
                        int monedaY = coordenadasMoneda.getEjeY();

                        if ((monedaX == ejeX) && (monedaY == izquierda) && coordenadasMoneda.isEstado() == true) {
                            System.out.println("yes");
                            puntuacion.setText("Puntuación: " + num++);
                            coordenadasMoneda.setEstado(false);
                        }
                    }

                    //reseteamos panel derecho
                    matriz[ejeX][izquierda].removeAll();
                    matriz[ejeX][izquierda].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/comecoco.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][izquierda].add(imagen);
                    matriz[ejeX][izquierda].setBackground(Color.black);
                    //elimina todo la posicion actual donde esta el pacman
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();

                    Pacman.setEjeY(izquierda);

                } else if (e.getKeyChar() == 'w') {
                    //para arriba funciona

                    int ejeX = Pacman.getEjeX();
                    int ejeY = Pacman.getEjeY();
                    int arriba = Pacman.getEjeX() - 1;
                    //reseteamos panel derecho
                    for (int x = 0; x < monedas.size(); x++) {
                        Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                        int monedaX = coordenadasMoneda.getEjeX();
                        int monedaY = coordenadasMoneda.getEjeY();

                        if ((monedaX == arriba) && (monedaY == ejeY) && coordenadasMoneda.isEstado() == true) {
                            System.out.println("yes");
                            puntuacion.setText("Puntuación: " + num++);
                            coordenadasMoneda.setEstado(false);
                        }
                    }

                    matriz[arriba][ejeY].removeAll();
                    matriz[arriba][ejeY].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/comecoco.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[arriba][ejeY].add(imagen);
                    matriz[arriba][ejeY].setBackground(Color.black);
                    //elimina todo la posicion actual donde esta el pacman
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();

                    Pacman.setEjeX(arriba);

                } else if (e.getKeyChar() == 's') {
                    //abajo funciona

                    int ejeX = Pacman.getEjeX();
                    int ejeY = Pacman.getEjeY();
                    int abajo = Pacman.getEjeX() + 1;
                    //reseteamos panel derecho
                    for (int x = 0; x < monedas.size(); x++) {
                        Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                        int monedaX = coordenadasMoneda.getEjeX();
                        int monedaY = coordenadasMoneda.getEjeY();

                        if ((monedaX == abajo) && (monedaY == ejeY) && coordenadasMoneda.isEstado() == true) {
                            System.out.println("yes");
                            puntuacion.setText("Puntuación: " + num++);
                            coordenadasMoneda.setEstado(false);
                        }
                    }

                    matriz[abajo][ejeY].removeAll();
                    matriz[abajo][ejeY].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/comecoco.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[abajo][ejeY].add(imagen);
                    matriz[abajo][ejeY].setBackground(Color.black);
                    //elimina todo la posicion actual donde esta el pacman
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();

                    Pacman.setEjeX(abajo);

                }

            }

        }
        );

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PacMan juego = new PacMan();
        juego.setVisible(true);
    }

}
