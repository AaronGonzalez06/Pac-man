/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pacman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Aaron
 */
public class PacMan extends JFrame {

    public PacMan() {
        ventana();
        titulo();

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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PacMan juego = new PacMan();
        juego.setVisible(true);
    }

}
