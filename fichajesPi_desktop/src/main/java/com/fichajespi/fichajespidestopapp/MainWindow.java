package com.fichajespi.fichajespidestopapp;

import com.fichajespi.fichajespidestopapp.smartcard.CardReader;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MainWindow extends JFrame {

    private JFXPanel jfxPanel;
    private WebEngine webEngine;

    public MainWindow() {
        initComponents();
    }

    private void initComponents() {
        jfxPanel = new JFXPanel();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FichajesPi");
        setMinimumSize(new java.awt.Dimension(480, 320));
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);

        getContentPane().add(jfxPanel, BorderLayout.CENTER);

        Platform.runLater(this::createScene);
        
        pack();
    }

    private void createScene() {
        WebView webView = new WebView();
        webEngine = webView.getEngine();
        
        URL url = getClass().getResource("/ui/index.html");
        if (url != null) {
            webEngine.load(url.toExternalForm());
        } else {
            System.err.println("Could not find index.html resource");
        }

        jfxPanel.setScene(new Scene(webView));
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            MainWindow mw = new MainWindow();
            mw.setVisible(true);
            new CardReader(mw).start();
        });
    }

    public void changeTime(String hora) {
        // Handled by JS in HTML
    }

    public void changeDate(String date) {
        // Handled by JS in HTML
    }

    public void changeNombre(String nombre) {
        // Will be called by showFichaje integration
    }

    public void changeFichaje(String fichaje) {
        // Will be called by showFichaje integration
    }

    public void changeNumero(String numero) {
        // Will be called by showFichaje integration
    }

    public void showFichaje(String nombre, String id, String tipo) {
        Platform.runLater(() -> {
            webEngine.executeScript(String.format("mostrarFichaje('%s', '%s', '%s')", nombre, id, tipo));
        });
    }

    public void resetScreen() {
        Platform.runLater(() -> {
            webEngine.executeScript("resetScreen()");
        });
    }
}
