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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    //movimientos
    boolean derechaM = false;
    boolean izquierdaM = false;
    boolean arribaM = false;
    boolean abajoM = false;

    //pruebas con los enemigos
    //enemigo1
    Enemigo enemigo1;
    int movimientoEnemigo1 = 2;
    int cambiarMovimiento1 = 0;
    //enemigo2
    Enemigo enemigo2;
    int movimientoEnemigo2 = 1;
    int cambiarMovimiento2 = 0;
    //enemigo3
    Enemigo enemigo3;
    int movimientoEnemigo3 = 1;
    int cambiarMovimiento3 = 0;
    //enemigo4
    Enemigo enemigo4;
    int movimientoEnemigo4 = 3;
    int cambiarMovimiento4 = 0;

    //vidas
    JPanel vidas;
    JLabel vida1, vida2, vida3;
    int vidasActuales = 3;

    Timer timer = new Timer(200, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ganar();
            seguirMovimientos();
            enemigo1();
            enemigo2();
            enemigo3();
            enemigo4();
            colisionConEnemigos();
            
        }
    });

    public PacMan() {
        ventana();
        titulo();
        vidas();
        panel();
        logica();
        timer.start();
        seguirMovimientos();
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

    public void vidas() {
        vidas = new JPanel();
        vidas.setBounds(30, 120, 150, 75);
        vidas.setOpaque(false);
        this.add(vidas);
        vida1 = new JLabel();
        vida2 = new JLabel();
        vida3 = new JLabel();
        //añadimos la imagen
        vida1.setBounds(0, 0, 50, 50);
        vida2.setBounds(25, 0, 50, 50);
        vida3.setBounds(50, 0, 50, 50);
        vidas.add(vida1);
        vidas.add(vida2);
        vidas.add(vida3);
        String ruta = "img/vida/vida.png";
        ImageIcon imageiconvida1 = new ImageIcon(ruta);
        Icon iconfondovida1 = new ImageIcon(imageiconvida1.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING));
        vida1.setIcon(iconfondovida1);
        vida2.setIcon(iconfondovida1);
        vida3.setIcon(iconfondovida1);
        
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
                } else if (x == 1 && y == 12) {

                    JLabel imagen = new JLabel();
                    String nombre = "img/enemigos/enemigo1.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[x][y].add(imagen);
                    matriz[x][y].setBackground(Color.black);

                    //creamos el objeto Enemigo1
                    enemigo1 = new Enemigo(x, y);

                    //añadimos tambien la moneda
                    monedas.add(new Moneda(x, y, true));

                } else if (x == 23 && y == 12) {

                    JLabel imagen = new JLabel();
                    String nombre = "img/enemigos/enemigo2.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[x][y].add(imagen);
                    matriz[x][y].setBackground(Color.black);

                    //creamos el objeto Enemigo1
                    enemigo2 = new Enemigo(x, y);

                    //añadimos tambien la moneda
                    monedas.add(new Moneda(x, y, true));

                } else if (x == 11 && y == 12) {

                    JLabel imagen = new JLabel();
                    String nombre = "img/enemigos/enemigo3.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[x][y].add(imagen);
                    matriz[x][y].setBackground(Color.black);

                    //creamos el objeto Enemigo1
                    enemigo3 = new Enemigo(x, y);

                    //añadimos tambien la moneda
                    monedas.add(new Moneda(x, y, true));

                } else if (x == 15 && y == 23) {

                    JLabel imagen = new JLabel();
                    String nombre = "img/enemigos/enemigo4.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[x][y].add(imagen);
                    matriz[x][y].setBackground(Color.black);

                    //creamos el objeto Enemigo1
                    enemigo4 = new Enemigo(x, y);

                    //añadimos tambien la moneda
                    monedas.add(new Moneda(x, y, true));

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
                        derechaM = true;
                        izquierdaM = false;
                        arribaM = false;
                        abajoM = false;

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

                    for (int x = 0; x < pared.size(); x++) {
                        Estado coordenadasPared = (Estado) pared.get(x);
                        int paredX = coordenadasPared.getEjeX();
                        int paredY = coordenadasPared.getEjeY();
                        if ((paredX == ejeX) && (paredY == izquierda) && coordenadasPared.isEstado() == true) {
                            colision = true;
                        }
                    }

                    if (colision == true) {
                        colision = false;
                    } else {

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

                        derechaM = false;
                        izquierdaM = true;
                        arribaM = false;
                        abajoM = false;

                    }

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

                    for (int x = 0; x < pared.size(); x++) {
                        Estado coordenadasPared = (Estado) pared.get(x);
                        int paredX = coordenadasPared.getEjeX();
                        int paredY = coordenadasPared.getEjeY();
                        if ((paredX == arriba) && (paredY == ejeY) && coordenadasPared.isEstado() == true) {
                            colision = true;
                        }
                    }

                    if (colision == true) {
                        colision = false;
                    } else {

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

                        derechaM = false;
                        izquierdaM = false;
                        arribaM = true;
                        abajoM = false;

                    }

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

                    for (int x = 0; x < pared.size(); x++) {
                        Estado coordenadasPared = (Estado) pared.get(x);
                        int paredX = coordenadasPared.getEjeX();
                        int paredY = coordenadasPared.getEjeY();
                        if ((paredX == abajo) && (paredY == ejeY) && coordenadasPared.isEstado() == true) {
                            colision = true;
                        }
                    }

                    if (colision == true) {
                        colision = false;
                    } else {

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

                        derechaM = false;
                        izquierdaM = false;
                        arribaM = false;
                        abajoM = true;

                    }
                }

            }

        }
        );

    }

    public void ganar() {
        if (num == 331) {
            JOptionPane.showMessageDialog(null, "Has ganado");
        }
    }

    public void seguirMovimientos() {
        if (derechaM) {

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
                derechaM = true;
                izquierdaM = false;
                arribaM = false;
                abajoM = false;

            }

        } else if (izquierdaM) {

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

            for (int x = 0; x < pared.size(); x++) {
                Estado coordenadasPared = (Estado) pared.get(x);
                int paredX = coordenadasPared.getEjeX();
                int paredY = coordenadasPared.getEjeY();
                if ((paredX == ejeX) && (paredY == izquierda) && coordenadasPared.isEstado() == true) {
                    colision = true;
                }
            }

            if (colision == true) {
                colision = false;
            } else {

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

                derechaM = false;
                izquierdaM = true;
                arribaM = false;
                abajoM = false;

            }

        } else if (arribaM) {

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

            for (int x = 0; x < pared.size(); x++) {
                Estado coordenadasPared = (Estado) pared.get(x);
                int paredX = coordenadasPared.getEjeX();
                int paredY = coordenadasPared.getEjeY();
                if ((paredX == arriba) && (paredY == ejeY) && coordenadasPared.isEstado() == true) {
                    colision = true;
                }
            }

            if (colision == true) {
                colision = false;
            } else {

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

                derechaM = false;
                izquierdaM = false;
                arribaM = true;
                abajoM = false;

            }

        } else if (abajoM) {

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

            for (int x = 0; x < pared.size(); x++) {
                Estado coordenadasPared = (Estado) pared.get(x);
                int paredX = coordenadasPared.getEjeX();
                int paredY = coordenadasPared.getEjeY();
                if ((paredX == abajo) && (paredY == ejeY) && coordenadasPared.isEstado() == true) {
                    colision = true;
                }
            }

            if (colision == true) {
                colision = false;
            } else {

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

                derechaM = false;
                izquierdaM = false;
                arribaM = false;
                abajoM = true;

            }
        }
    }

    public void enemigo1() {
        /*        
        1 --> derecha
        2 --> izquierda
        3 --> arriba
        4 --> abajo
         */
        //movimientoEnemigo1 = (int) Math.floor(Math.random()*4+1);
        //System.out.println(movimientoEnemigo1);

        if (movimientoEnemigo1 == 1) {
            int ejeX = enemigo1.getEjeX();
            int ejeY = enemigo1.getEjeY();
            int derecha = enemigo1.getEjeY() + 1;
            int izquierda = enemigo1.getEjeY() - 1;

            for (int x = 0; x < monedas.size(); x++) {
                Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                int monedaX = coordenadasMoneda.getEjeX();
                int monedaY = coordenadasMoneda.getEjeY();

                if ((monedaX == ejeX) && (monedaY == izquierda) && coordenadasMoneda.isEstado() == true) {

                    matriz[ejeX][izquierda].removeAll();
                    matriz[ejeX][izquierda].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][izquierda].add(imagen);
                    matriz[ejeX][izquierda].setBackground(Color.black);

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
                do {
                    movimientoEnemigo1 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo1);
                } while (movimientoEnemigo1 == 1);
                cambiarMovimiento1 = 0;

            } else if (cambiarMovimiento1 == 5) {
                colision = false;
                do {
                    movimientoEnemigo1 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo1);
                } while (movimientoEnemigo1 == 1);
                cambiarMovimiento1 = 0;
            } else {

                //reseteamos panel derecho
                matriz[ejeX][derecha].removeAll();
                matriz[ejeX][derecha].repaint();
                JLabel imagen = new JLabel();
                String nombre = "img/enemigos/enemigo1.png";
                ImageIcon imageicon = new ImageIcon(nombre);
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][derecha].add(imagen);
                matriz[ejeX][derecha].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                enemigo1.setEjeY(derecha);
                cambiarMovimiento1++;
            }

        } else if (movimientoEnemigo1 == 2) {

            int ejeX = enemigo1.getEjeX();
            int ejeY = enemigo1.getEjeY();
            int derecha = enemigo1.getEjeY() + 1;
            int izquierda = enemigo1.getEjeY() - 1;

            for (int x = 0; x < monedas.size(); x++) {
                Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                int monedaX = coordenadasMoneda.getEjeX();
                int monedaY = coordenadasMoneda.getEjeY();

                if ((monedaX == ejeX) && (monedaY == derecha) && coordenadasMoneda.isEstado() == true) {

                    matriz[ejeX][derecha].removeAll();
                    matriz[ejeX][derecha].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][derecha].add(imagen);
                    matriz[ejeX][derecha].setBackground(Color.black);

                }
            }

            for (int x = 0; x < pared.size(); x++) {
                Estado coordenadasPared = (Estado) pared.get(x);
                int paredX = coordenadasPared.getEjeX();
                int paredY = coordenadasPared.getEjeY();
                if ((paredX == ejeX) && (paredY == izquierda) && coordenadasPared.isEstado() == true) {
                    colision = true;
                }
            }

            if (colision == true) {
                colision = false;
                do {
                    movimientoEnemigo1 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo1);
                } while (movimientoEnemigo1 == 2);
                cambiarMovimiento1 = 0;

            } else if (cambiarMovimiento1 == 5) {
                colision = false;
                do {
                    movimientoEnemigo1 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo1);
                } while (movimientoEnemigo1 == 2);
                cambiarMovimiento1 = 0;
            } else {

                //reseteamos panel derecho
                matriz[ejeX][izquierda].removeAll();
                matriz[ejeX][izquierda].repaint();
                JLabel imagen = new JLabel();
                String nombre = "img/enemigos/enemigo1.png";
                ImageIcon imageicon = new ImageIcon(nombre);
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][izquierda].add(imagen);
                matriz[ejeX][izquierda].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                enemigo1.setEjeY(izquierda);

                //pruebas 
                cambiarMovimiento1++;

            }

        } else if (movimientoEnemigo1 == 3) {

            int ejeX = enemigo1.getEjeX();
            int ejeY = enemigo1.getEjeY();
            int arriba = enemigo1.getEjeX() - 1;
            int abajo = enemigo1.getEjeX() + 1;

            for (int x = 0; x < monedas.size(); x++) {
                Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                int monedaX = coordenadasMoneda.getEjeX();
                int monedaY = coordenadasMoneda.getEjeY();

                if ((monedaX == abajo) && (monedaY == ejeY) && coordenadasMoneda.isEstado() == true) {

                    matriz[abajo][ejeY].removeAll();
                    matriz[abajo][ejeY].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[abajo][ejeY].add(imagen);
                    matriz[abajo][ejeY].setBackground(Color.black);

                }
            }

            for (int x = 0; x < pared.size(); x++) {
                Estado coordenadasPared = (Estado) pared.get(x);
                int paredX = coordenadasPared.getEjeX();
                int paredY = coordenadasPared.getEjeY();
                if ((paredX == arriba) && (paredY == ejeY) && coordenadasPared.isEstado() == true) {
                    colision = true;
                }
            }

            if (colision == true) {
                colision = false;
                do {
                    movimientoEnemigo1 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo1);
                } while (movimientoEnemigo1 == 3);
                cambiarMovimiento1 = 0;

            } else if (cambiarMovimiento1 == 5) {
                colision = false;
                do {
                    movimientoEnemigo1 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println(movimientoEnemigo1);
                } while (movimientoEnemigo1 == 3);
                cambiarMovimiento1 = 0;
            } else {
                matriz[arriba][ejeY].removeAll();
                matriz[arriba][ejeY].repaint();
                JLabel imagen = new JLabel();
                String nombre = "img/enemigos/enemigo1.png";
                ImageIcon imageicon = new ImageIcon(nombre);
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[arriba][ejeY].add(imagen);
                matriz[arriba][ejeY].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                enemigo1.setEjeX(arriba);

                //pruebas 
                cambiarMovimiento1++;

            }

        } else if (movimientoEnemigo1 == 4) {

            int ejeX = enemigo1.getEjeX();
            int ejeY = enemigo1.getEjeY();
            int arriba = enemigo1.getEjeX() - 1;
            int abajo = enemigo1.getEjeX() + 1;

            for (int x = 0; x < monedas.size(); x++) {
                Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                int monedaX = coordenadasMoneda.getEjeX();
                int monedaY = coordenadasMoneda.getEjeY();

                if ((monedaX == arriba) && (monedaY == ejeY) && coordenadasMoneda.isEstado() == true) {

                    matriz[arriba][ejeY].removeAll();
                    matriz[arriba][ejeY].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[arriba][ejeY].add(imagen);
                    matriz[arriba][ejeY].setBackground(Color.black);

                }
            }

            for (int x = 0; x < pared.size(); x++) {
                Estado coordenadasPared = (Estado) pared.get(x);
                int paredX = coordenadasPared.getEjeX();
                int paredY = coordenadasPared.getEjeY();
                if ((paredX == abajo) && (paredY == ejeY) && coordenadasPared.isEstado() == true) {
                    colision = true;
                }
            }

            if (colision == true) {
                colision = false;
                do {
                    movimientoEnemigo1 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo1);
                } while (movimientoEnemigo1 == 4);
                cambiarMovimiento1 = 0;

            } else if (cambiarMovimiento1 == 5) {
                colision = false;
                do {
                    movimientoEnemigo1 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println(movimientoEnemigo1);
                } while (movimientoEnemigo1 == 4);
                cambiarMovimiento1 = 0;
            } else {
                matriz[abajo][ejeY].removeAll();
                matriz[abajo][ejeY].repaint();
                JLabel imagen = new JLabel();
                String nombre = "img/enemigos/enemigo1.png";
                ImageIcon imageicon = new ImageIcon(nombre);
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[abajo][ejeY].add(imagen);
                matriz[abajo][ejeY].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                enemigo1.setEjeX(abajo);

                //pruebas 
                cambiarMovimiento1++;

            }

        }

    }

    public void enemigo2() {

        if (movimientoEnemigo2 == 1) {
            int ejeX = enemigo2.getEjeX();
            int ejeY = enemigo2.getEjeY();
            int derecha = enemigo2.getEjeY() + 1;
            int izquierda = enemigo2.getEjeY() - 1;

            for (int x = 0; x < monedas.size(); x++) {
                Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                int monedaX = coordenadasMoneda.getEjeX();
                int monedaY = coordenadasMoneda.getEjeY();

                if ((monedaX == ejeX) && (monedaY == izquierda) && coordenadasMoneda.isEstado() == true) {

                    matriz[ejeX][izquierda].removeAll();
                    matriz[ejeX][izquierda].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][izquierda].add(imagen);
                    matriz[ejeX][izquierda].setBackground(Color.black);

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
                do {
                    movimientoEnemigo2 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo2);
                } while (movimientoEnemigo2 == 1);
                cambiarMovimiento2 = 0;

            } else if (cambiarMovimiento2 == 5) {
                colision = false;
                do {
                    movimientoEnemigo2 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo2);
                } while (movimientoEnemigo2 == 1);
                cambiarMovimiento2 = 0;
            } else {

                //reseteamos panel derecho
                matriz[ejeX][derecha].removeAll();
                matriz[ejeX][derecha].repaint();
                JLabel imagen = new JLabel();
                String nombre = "img/enemigos/enemigo2.png";
                ImageIcon imageicon = new ImageIcon(nombre);
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][derecha].add(imagen);
                matriz[ejeX][derecha].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                enemigo2.setEjeY(derecha);
                cambiarMovimiento2++;
            }

        } else if (movimientoEnemigo2 == 2) {

            int ejeX = enemigo2.getEjeX();
            int ejeY = enemigo2.getEjeY();
            int derecha = enemigo2.getEjeY() + 1;
            int izquierda = enemigo2.getEjeY() - 1;

            for (int x = 0; x < monedas.size(); x++) {
                Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                int monedaX = coordenadasMoneda.getEjeX();
                int monedaY = coordenadasMoneda.getEjeY();

                if ((monedaX == ejeX) && (monedaY == derecha) && coordenadasMoneda.isEstado() == true) {

                    matriz[ejeX][derecha].removeAll();
                    matriz[ejeX][derecha].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][derecha].add(imagen);
                    matriz[ejeX][derecha].setBackground(Color.black);

                }
            }

            for (int x = 0; x < pared.size(); x++) {
                Estado coordenadasPared = (Estado) pared.get(x);
                int paredX = coordenadasPared.getEjeX();
                int paredY = coordenadasPared.getEjeY();
                if ((paredX == ejeX) && (paredY == izquierda) && coordenadasPared.isEstado() == true) {
                    colision = true;
                }
            }

            if (colision == true) {
                colision = false;
                do {
                    movimientoEnemigo2 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo2);
                } while (movimientoEnemigo2 == 2);
                cambiarMovimiento2 = 0;

            } else if (cambiarMovimiento2 == 5) {
                colision = false;
                do {
                    movimientoEnemigo2 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo1);
                } while (movimientoEnemigo2 == 2);
                cambiarMovimiento2 = 0;
            } else {

                //reseteamos panel derecho
                matriz[ejeX][izquierda].removeAll();
                matriz[ejeX][izquierda].repaint();
                JLabel imagen = new JLabel();
                String nombre = "img/enemigos/enemigo2.png";
                ImageIcon imageicon = new ImageIcon(nombre);
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][izquierda].add(imagen);
                matriz[ejeX][izquierda].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                enemigo2.setEjeY(izquierda);

                //pruebas 
                cambiarMovimiento2++;

            }

        } else if (movimientoEnemigo2 == 3) {

            int ejeX = enemigo2.getEjeX();
            int ejeY = enemigo2.getEjeY();
            int arriba = enemigo2.getEjeX() - 1;
            int abajo = enemigo2.getEjeX() + 1;

            for (int x = 0; x < monedas.size(); x++) {
                Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                int monedaX = coordenadasMoneda.getEjeX();
                int monedaY = coordenadasMoneda.getEjeY();

                if ((monedaX == abajo) && (monedaY == ejeY) && coordenadasMoneda.isEstado() == true) {

                    matriz[abajo][ejeY].removeAll();
                    matriz[abajo][ejeY].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[abajo][ejeY].add(imagen);
                    matriz[abajo][ejeY].setBackground(Color.black);

                }
            }

            for (int x = 0; x < pared.size(); x++) {
                Estado coordenadasPared = (Estado) pared.get(x);
                int paredX = coordenadasPared.getEjeX();
                int paredY = coordenadasPared.getEjeY();
                if ((paredX == arriba) && (paredY == ejeY) && coordenadasPared.isEstado() == true) {
                    colision = true;
                }
            }

            if (colision == true) {
                colision = false;
                do {
                    movimientoEnemigo2 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo2);
                } while (movimientoEnemigo2 == 3);
                cambiarMovimiento2 = 0;

            } else if (cambiarMovimiento2 == 5) {
                colision = false;
                do {
                    movimientoEnemigo2 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println(movimientoEnemigo1);
                } while (movimientoEnemigo2 == 3);
                cambiarMovimiento2 = 0;
            } else {
                matriz[arriba][ejeY].removeAll();
                matriz[arriba][ejeY].repaint();
                JLabel imagen = new JLabel();
                String nombre = "img/enemigos/enemigo2.png";
                ImageIcon imageicon = new ImageIcon(nombre);
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[arriba][ejeY].add(imagen);
                matriz[arriba][ejeY].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                enemigo2.setEjeX(arriba);

                //pruebas 
                cambiarMovimiento2++;

            }

        } else if (movimientoEnemigo2 == 4) {

            int ejeX = enemigo2.getEjeX();
            int ejeY = enemigo2.getEjeY();
            int arriba = enemigo2.getEjeX() - 1;
            int abajo = enemigo2.getEjeX() + 1;

            for (int x = 0; x < monedas.size(); x++) {
                Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                int monedaX = coordenadasMoneda.getEjeX();
                int monedaY = coordenadasMoneda.getEjeY();

                if ((monedaX == arriba) && (monedaY == ejeY) && coordenadasMoneda.isEstado() == true) {

                    matriz[arriba][ejeY].removeAll();
                    matriz[arriba][ejeY].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[arriba][ejeY].add(imagen);
                    matriz[arriba][ejeY].setBackground(Color.black);

                }
            }

            for (int x = 0; x < pared.size(); x++) {
                Estado coordenadasPared = (Estado) pared.get(x);
                int paredX = coordenadasPared.getEjeX();
                int paredY = coordenadasPared.getEjeY();
                if ((paredX == abajo) && (paredY == ejeY) && coordenadasPared.isEstado() == true) {
                    colision = true;
                }
            }

            if (colision == true) {
                colision = false;
                do {
                    movimientoEnemigo2 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo2);
                } while (movimientoEnemigo2 == 4);
                cambiarMovimiento2 = 0;

            } else if (cambiarMovimiento2 == 5) {
                colision = false;
                do {
                    movimientoEnemigo2 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println(movimientoEnemigo2);
                } while (movimientoEnemigo2 == 4);
                cambiarMovimiento2 = 0;
            } else {
                matriz[abajo][ejeY].removeAll();
                matriz[abajo][ejeY].repaint();
                JLabel imagen = new JLabel();
                String nombre = "img/enemigos/enemigo2.png";
                ImageIcon imageicon = new ImageIcon(nombre);
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[abajo][ejeY].add(imagen);
                matriz[abajo][ejeY].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                enemigo2.setEjeX(abajo);

                //pruebas 
                cambiarMovimiento2++;

            }

        }

    }

    public void enemigo3() {

        if (movimientoEnemigo3 == 1) {
            int ejeX = enemigo3.getEjeX();
            int ejeY = enemigo3.getEjeY();
            int derecha = enemigo3.getEjeY() + 1;
            int izquierda = enemigo3.getEjeY() - 1;

            for (int x = 0; x < monedas.size(); x++) {
                Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                int monedaX = coordenadasMoneda.getEjeX();
                int monedaY = coordenadasMoneda.getEjeY();

                if ((monedaX == ejeX) && (monedaY == izquierda) && coordenadasMoneda.isEstado() == true) {

                    matriz[ejeX][izquierda].removeAll();
                    matriz[ejeX][izquierda].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][izquierda].add(imagen);
                    matriz[ejeX][izquierda].setBackground(Color.black);

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
                do {
                    movimientoEnemigo3 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo3);
                } while (movimientoEnemigo3 == 1);
                cambiarMovimiento3 = 0;

            } else if (cambiarMovimiento3 == 5) {
                colision = false;
                do {
                    movimientoEnemigo3 = (int) Math.floor(Math.random() * 4 + 1);
                } while (movimientoEnemigo3 == 1);
                cambiarMovimiento3 = 0;
            } else {

                //reseteamos panel derecho
                matriz[ejeX][derecha].removeAll();
                matriz[ejeX][derecha].repaint();
                JLabel imagen = new JLabel();
                String nombre = "img/enemigos/enemigo3.png";
                ImageIcon imageicon = new ImageIcon(nombre);
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][derecha].add(imagen);
                matriz[ejeX][derecha].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                enemigo3.setEjeY(derecha);
                cambiarMovimiento3++;
            }

        } else if (movimientoEnemigo3 == 2) {

            int ejeX = enemigo3.getEjeX();
            int ejeY = enemigo3.getEjeY();
            int derecha = enemigo3.getEjeY() + 1;
            int izquierda = enemigo3.getEjeY() - 1;

            for (int x = 0; x < monedas.size(); x++) {
                Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                int monedaX = coordenadasMoneda.getEjeX();
                int monedaY = coordenadasMoneda.getEjeY();

                if ((monedaX == ejeX) && (monedaY == derecha) && coordenadasMoneda.isEstado() == true) {

                    matriz[ejeX][derecha].removeAll();
                    matriz[ejeX][derecha].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][derecha].add(imagen);
                    matriz[ejeX][derecha].setBackground(Color.black);

                }
            }

            for (int x = 0; x < pared.size(); x++) {
                Estado coordenadasPared = (Estado) pared.get(x);
                int paredX = coordenadasPared.getEjeX();
                int paredY = coordenadasPared.getEjeY();
                if ((paredX == ejeX) && (paredY == izquierda) && coordenadasPared.isEstado() == true) {
                    colision = true;
                }
            }

            if (colision == true) {
                colision = false;
                do {
                    movimientoEnemigo3 = (int) Math.floor(Math.random() * 4 + 1);
                } while (movimientoEnemigo3 == 3);
                cambiarMovimiento3 = 0;

            } else if (cambiarMovimiento3 == 5) {
                colision = false;
                do {
                    movimientoEnemigo3 = (int) Math.floor(Math.random() * 4 + 1);
                } while (movimientoEnemigo3 == 2);
                cambiarMovimiento2 = 0;
            } else {

                //reseteamos panel derecho
                matriz[ejeX][izquierda].removeAll();
                matriz[ejeX][izquierda].repaint();
                JLabel imagen = new JLabel();
                String nombre = "img/enemigos/enemigo3.png";
                ImageIcon imageicon = new ImageIcon(nombre);
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][izquierda].add(imagen);
                matriz[ejeX][izquierda].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                enemigo3.setEjeY(izquierda);

                //pruebas 
                cambiarMovimiento3++;

            }

        } else if (movimientoEnemigo3 == 3) {

            int ejeX = enemigo3.getEjeX();
            int ejeY = enemigo3.getEjeY();
            int arriba = enemigo3.getEjeX() - 1;
            int abajo = enemigo3.getEjeX() + 1;

            for (int x = 0; x < monedas.size(); x++) {
                Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                int monedaX = coordenadasMoneda.getEjeX();
                int monedaY = coordenadasMoneda.getEjeY();

                if ((monedaX == abajo) && (monedaY == ejeY) && coordenadasMoneda.isEstado() == true) {

                    matriz[abajo][ejeY].removeAll();
                    matriz[abajo][ejeY].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[abajo][ejeY].add(imagen);
                    matriz[abajo][ejeY].setBackground(Color.black);

                }
            }

            for (int x = 0; x < pared.size(); x++) {
                Estado coordenadasPared = (Estado) pared.get(x);
                int paredX = coordenadasPared.getEjeX();
                int paredY = coordenadasPared.getEjeY();
                if ((paredX == arriba) && (paredY == ejeY) && coordenadasPared.isEstado() == true) {
                    colision = true;
                }
            }

            if (colision == true) {
                colision = false;
                do {
                    movimientoEnemigo3 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo2);
                } while (movimientoEnemigo3 == 3);
                cambiarMovimiento3 = 0;

            } else if (cambiarMovimiento3 == 5) {
                colision = false;
                do {
                    movimientoEnemigo3 = (int) Math.floor(Math.random() * 4 + 1);
                } while (movimientoEnemigo3 == 3);
                cambiarMovimiento3 = 0;
            } else {
                matriz[arriba][ejeY].removeAll();
                matriz[arriba][ejeY].repaint();
                JLabel imagen = new JLabel();
                String nombre = "img/enemigos/enemigo3.png";
                ImageIcon imageicon = new ImageIcon(nombre);
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[arriba][ejeY].add(imagen);
                matriz[arriba][ejeY].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                enemigo3.setEjeX(arriba);

                //pruebas 
                cambiarMovimiento3++;

            }

        } else if (movimientoEnemigo3 == 4) {

            int ejeX = enemigo3.getEjeX();
            int ejeY = enemigo3.getEjeY();
            int arriba = enemigo3.getEjeX() - 1;
            int abajo = enemigo3.getEjeX() + 1;

            for (int x = 0; x < monedas.size(); x++) {
                Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                int monedaX = coordenadasMoneda.getEjeX();
                int monedaY = coordenadasMoneda.getEjeY();

                if ((monedaX == arriba) && (monedaY == ejeY) && coordenadasMoneda.isEstado() == true) {

                    matriz[arriba][ejeY].removeAll();
                    matriz[arriba][ejeY].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[arriba][ejeY].add(imagen);
                    matriz[arriba][ejeY].setBackground(Color.black);

                }
            }

            for (int x = 0; x < pared.size(); x++) {
                Estado coordenadasPared = (Estado) pared.get(x);
                int paredX = coordenadasPared.getEjeX();
                int paredY = coordenadasPared.getEjeY();
                if ((paredX == abajo) && (paredY == ejeY) && coordenadasPared.isEstado() == true) {
                    colision = true;
                }
            }

            if (colision == true) {
                colision = false;
                do {
                    movimientoEnemigo3 = (int) Math.floor(Math.random() * 4 + 1);
                } while (movimientoEnemigo3 == 4);
                cambiarMovimiento3 = 0;

            } else if (cambiarMovimiento3 == 5) {
                colision = false;
                do {
                    movimientoEnemigo3 = (int) Math.floor(Math.random() * 4 + 1);
                } while (movimientoEnemigo3 == 4);
                cambiarMovimiento3 = 0;
            } else {
                matriz[abajo][ejeY].removeAll();
                matriz[abajo][ejeY].repaint();
                JLabel imagen = new JLabel();
                String nombre = "img/enemigos/enemigo3.png";
                ImageIcon imageicon = new ImageIcon(nombre);
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[abajo][ejeY].add(imagen);
                matriz[abajo][ejeY].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                enemigo3.setEjeX(abajo);

                //pruebas 
                cambiarMovimiento3++;

            }

        }

    }

    public void enemigo4() {

        if (movimientoEnemigo4 == 1) {
            int ejeX = enemigo4.getEjeX();
            int ejeY = enemigo4.getEjeY();
            int derecha = enemigo4.getEjeY() + 1;
            int izquierda = enemigo4.getEjeY() - 1;

            for (int x = 0; x < monedas.size(); x++) {
                Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                int monedaX = coordenadasMoneda.getEjeX();
                int monedaY = coordenadasMoneda.getEjeY();

                if ((monedaX == ejeX) && (monedaY == izquierda) && coordenadasMoneda.isEstado() == true) {

                    matriz[ejeX][izquierda].removeAll();
                    matriz[ejeX][izquierda].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][izquierda].add(imagen);
                    matriz[ejeX][izquierda].setBackground(Color.black);

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
                do {
                    movimientoEnemigo4 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo1);
                } while (movimientoEnemigo4 == 1);
                cambiarMovimiento4 = 0;

            } else if (cambiarMovimiento4 == 5) {
                colision = false;
                do {
                    movimientoEnemigo4 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo1);
                } while (movimientoEnemigo4 == 1);
                cambiarMovimiento1 = 0;
            } else {

                //reseteamos panel derecho
                matriz[ejeX][derecha].removeAll();
                matriz[ejeX][derecha].repaint();
                JLabel imagen = new JLabel();
                String nombre = "img/enemigos/enemigo4.png";
                ImageIcon imageicon = new ImageIcon(nombre);
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][derecha].add(imagen);
                matriz[ejeX][derecha].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                enemigo4.setEjeY(derecha);
                cambiarMovimiento4++;
            }

        } else if (movimientoEnemigo4 == 2) {

            int ejeX = enemigo4.getEjeX();
            int ejeY = enemigo4.getEjeY();
            int derecha = enemigo4.getEjeY() + 1;
            int izquierda = enemigo4.getEjeY() - 1;

            for (int x = 0; x < monedas.size(); x++) {
                Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                int monedaX = coordenadasMoneda.getEjeX();
                int monedaY = coordenadasMoneda.getEjeY();

                if ((monedaX == ejeX) && (monedaY == derecha) && coordenadasMoneda.isEstado() == true) {

                    matriz[ejeX][derecha].removeAll();
                    matriz[ejeX][derecha].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][derecha].add(imagen);
                    matriz[ejeX][derecha].setBackground(Color.black);

                }
            }

            for (int x = 0; x < pared.size(); x++) {
                Estado coordenadasPared = (Estado) pared.get(x);
                int paredX = coordenadasPared.getEjeX();
                int paredY = coordenadasPared.getEjeY();
                if ((paredX == ejeX) && (paredY == izquierda) && coordenadasPared.isEstado() == true) {
                    colision = true;
                }
            }

            if (colision == true) {
                colision = false;
                do {
                    movimientoEnemigo4 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo1);
                } while (movimientoEnemigo4 == 2);
                cambiarMovimiento4 = 0;

            } else if (cambiarMovimiento4 == 5) {
                colision = false;
                do {
                    movimientoEnemigo4 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo1);
                } while (movimientoEnemigo4 == 2);
                cambiarMovimiento1 = 0;
            } else {

                //reseteamos panel derecho
                matriz[ejeX][izquierda].removeAll();
                matriz[ejeX][izquierda].repaint();
                JLabel imagen = new JLabel();
                String nombre = "img/enemigos/enemigo4.png";
                ImageIcon imageicon = new ImageIcon(nombre);
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][izquierda].add(imagen);
                matriz[ejeX][izquierda].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                enemigo4.setEjeY(izquierda);

                //pruebas 
                cambiarMovimiento4++;

            }

        } else if (movimientoEnemigo4 == 3) {

            int ejeX = enemigo4.getEjeX();
            int ejeY = enemigo4.getEjeY();
            int arriba = enemigo4.getEjeX() - 1;
            int abajo = enemigo4.getEjeX() + 1;

            for (int x = 0; x < monedas.size(); x++) {
                Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                int monedaX = coordenadasMoneda.getEjeX();
                int monedaY = coordenadasMoneda.getEjeY();

                if ((monedaX == abajo) && (monedaY == ejeY) && coordenadasMoneda.isEstado() == true) {

                    matriz[abajo][ejeY].removeAll();
                    matriz[abajo][ejeY].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[abajo][ejeY].add(imagen);
                    matriz[abajo][ejeY].setBackground(Color.black);

                }
            }

            for (int x = 0; x < pared.size(); x++) {
                Estado coordenadasPared = (Estado) pared.get(x);
                int paredX = coordenadasPared.getEjeX();
                int paredY = coordenadasPared.getEjeY();
                if ((paredX == arriba) && (paredY == ejeY) && coordenadasPared.isEstado() == true) {
                    colision = true;
                }
            }

            if (colision == true) {
                colision = false;
                do {
                    movimientoEnemigo4 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo1);
                } while (movimientoEnemigo4 == 3);
                cambiarMovimiento4 = 0;

            } else if (cambiarMovimiento4 == 5) {
                colision = false;
                do {
                    movimientoEnemigo4 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println(movimientoEnemigo1);
                } while (movimientoEnemigo4 == 3);
                cambiarMovimiento4 = 0;
            } else {
                matriz[arriba][ejeY].removeAll();
                matriz[arriba][ejeY].repaint();
                JLabel imagen = new JLabel();
                String nombre = "img/enemigos/enemigo4.png";
                ImageIcon imageicon = new ImageIcon(nombre);
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[arriba][ejeY].add(imagen);
                matriz[arriba][ejeY].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                enemigo4.setEjeX(arriba);

                //pruebas 
                cambiarMovimiento4++;

            }

        } else if (movimientoEnemigo4 == 4) {

            int ejeX = enemigo4.getEjeX();
            int ejeY = enemigo4.getEjeY();
            int arriba = enemigo4.getEjeX() - 1;
            int abajo = enemigo4.getEjeX() + 1;

            for (int x = 0; x < monedas.size(); x++) {
                Moneda coordenadasMoneda = (Moneda) monedas.get(x);
                int monedaX = coordenadasMoneda.getEjeX();
                int monedaY = coordenadasMoneda.getEjeY();

                if ((monedaX == arriba) && (monedaY == ejeY) && coordenadasMoneda.isEstado() == true) {

                    matriz[arriba][ejeY].removeAll();
                    matriz[arriba][ejeY].repaint();

                    JLabel imagen = new JLabel();
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[arriba][ejeY].add(imagen);
                    matriz[arriba][ejeY].setBackground(Color.black);

                }
            }

            for (int x = 0; x < pared.size(); x++) {
                Estado coordenadasPared = (Estado) pared.get(x);
                int paredX = coordenadasPared.getEjeX();
                int paredY = coordenadasPared.getEjeY();
                if ((paredX == abajo) && (paredY == ejeY) && coordenadasPared.isEstado() == true) {
                    colision = true;
                }
            }

            if (colision == true) {
                colision = false;
                do {
                    movimientoEnemigo4 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoEnemigo1);
                } while (movimientoEnemigo4 == 4);
                cambiarMovimiento4 = 0;

            } else if (cambiarMovimiento4 == 5) {
                colision = false;
                do {
                    movimientoEnemigo4 = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println(movimientoEnemigo1);
                } while (movimientoEnemigo4 == 4);
                cambiarMovimiento4 = 0;
            } else {
                matriz[abajo][ejeY].removeAll();
                matriz[abajo][ejeY].repaint();
                JLabel imagen = new JLabel();
                String nombre = "img/enemigos/enemigo4.png";
                ImageIcon imageicon = new ImageIcon(nombre);
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[abajo][ejeY].add(imagen);
                matriz[abajo][ejeY].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                enemigo4.setEjeX(abajo);

                //pruebas 
                cambiarMovimiento4++;

            }

        }

    }

    public void colisionConEnemigos() {

        if ((Pacman.getEjeX() == enemigo1.getEjeX()) && (Pacman.getEjeY() == enemigo1.getEjeY())) {
           vidasActuales--;
           quitarVidas();
           JOptionPane.showMessageDialog(null, "Te quedan "+ vidasActuales+".");
        } else if ((Pacman.getEjeX() == enemigo2.getEjeX()) && (Pacman.getEjeY() == enemigo2.getEjeY())) {
            vidasActuales--;
            quitarVidas();
            JOptionPane.showMessageDialog(null, "Te quedan "+ vidasActuales+".");
        } else if ((Pacman.getEjeX() == enemigo3.getEjeX()) && (Pacman.getEjeY() == enemigo3.getEjeY())) {
            vidasActuales--;
            quitarVidas();
            JOptionPane.showMessageDialog(null, "Te quedan "+ vidasActuales+".");
        } else if ((Pacman.getEjeX() == enemigo4.getEjeX()) && (Pacman.getEjeY() == enemigo4.getEjeY())) {
            vidasActuales--;
            quitarVidas();            
            JOptionPane.showMessageDialog(null, "Te quedan "+ vidasActuales+".");
        }
        System.out.println(vidasActuales);
    }
    
    public void volverPosicion(){    
        //pacman
        matriz[Pacman.getEjeX()][Pacman.getEjeY()].removeAll();
        matriz[Pacman.getEjeX()][Pacman.getEjeY()].repaint();        
        Pacman.setEjeX(17);
        Pacman.setEjeY(12);  
        
        //enemigo1
        matriz[enemigo1.getEjeX()][enemigo1.getEjeY()].removeAll();
        matriz[enemigo1.getEjeX()][enemigo1.getEjeY()].repaint(); 
        enemigo1.setEjeX(1);
        enemigo1.setEjeY(12);
        
        matriz[enemigo2.getEjeX()][enemigo2.getEjeY()].removeAll();
        matriz[enemigo2.getEjeX()][enemigo2.getEjeY()].repaint();
        enemigo2.setEjeX(23);
        enemigo2.setEjeY(12);
        
        matriz[enemigo3.getEjeX()][enemigo3.getEjeY()].removeAll();
        matriz[enemigo3.getEjeX()][enemigo3.getEjeY()].repaint();
        enemigo3.setEjeX(11);
        enemigo3.setEjeY(12);
        
        matriz[enemigo4.getEjeX()][enemigo4.getEjeY()].removeAll();
        matriz[enemigo4.getEjeX()][enemigo4.getEjeY()].repaint();
        enemigo4.setEjeX(15);
        enemigo4.setEjeY(23);
    
    };
    
    public void quitarVidas(){
        
        switch(vidasActuales) {
			case 2:
				vida3.setIcon(null);
                                volverPosicion();
                                break;
			case 1:
				vida2.setIcon(null);
                                volverPosicion();
                                break;
			case 0:
				vida1.setIcon(null);
                                JOptionPane.showMessageDialog(null, "Game Over");
			default:
				System.out.println("erros , no deberias estar aqui");
		}
    
        
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
