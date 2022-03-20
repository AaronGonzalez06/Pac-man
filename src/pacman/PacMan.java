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
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Aaron
 */
public class PacMan extends JFrame {

    JPanel panel;
    JPanel[][] matriz = new JPanel[25][25];

    public PacMan() {
        ventana();
        titulo();
        panel();

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
        titulo.setText("PACMAN");
        titulo.setBounds(300, 50, 400, 75);
        titulo.setForeground(Color.YELLOW);
        File fuente = new File("fuente/fuente.ttf");
        try {
            //crea la fuente
            Font font = Font.createFont(Font.TRUETYPE_FONT, fuente);
            //dar tanaño fuente
            Font sizedFont = font.deriveFont(75f);
            titulo.setFont(sizedFont);
            titulo.setFont(sizedFont);
        } catch (FontFormatException ex) {
            System.err.println("error en font format");
        } catch (IOException ex) {
            System.err.println("error de entrada/salida");
        }

        this.add(titulo);
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
                } else if (x == 24) {
                    matriz[x][y].setBackground(Color.blue);
                } else if (y == 0) {
                    matriz[x][y].setBackground(Color.blue);
                } else if (y == 24) {
                    matriz[x][y].setBackground(Color.blue);
                } else if (x == 2 && y > 1 && y < 23) {
                    matriz[x][y].setBackground(Color.blue);
                } else if (x == 4 && y > 1 && y < 23) {
                    matriz[x][y].setBackground(Color.blue);
                } else if (x == 6 && y > 1 && y < 23) {
                    matriz[x][y].setBackground(Color.blue);
                } else if (x == 8 && y > 1 && y < 23) {
                    matriz[x][y].setBackground(Color.blue);
                } else if (x == 10 && y > 1 && y < 23) {
                    matriz[x][y].setBackground(Color.blue);
                } else if (x == 16 && y > 1 && y < 23) {
                    matriz[x][y].setBackground(Color.blue);
                } else if (x == 18 && y > 1 && y < 23) {
                    matriz[x][y].setBackground(Color.blue);
                } else if (x == 20 && y > 1 && y < 23) {
                    matriz[x][y].setBackground(Color.blue);
                } else if (x == 22 && y > 1 && y < 23) {
                    matriz[x][y].setBackground(Color.blue);
                } else {

                    JLabel imagen = new JLabel();
                    //añadimos la imagen
                    String nombre = "img/moneda.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    //se crea la imagen y se escala a lo que le hemos dicho con Image.SCALE_DEFAULT
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
                    //se añade la imagen a la etiqueta
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 25, 25);
                    //se añade la etiqueta al panel
                    //panelItro.add(imagen, BorderLayout.EAST);
                    matriz[x][y].add(imagen);

                    matriz[x][y].setBackground(Color.gray);
                }

                panel.add(matriz[x][y]);
            }
        }

        this.add(panel);

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
