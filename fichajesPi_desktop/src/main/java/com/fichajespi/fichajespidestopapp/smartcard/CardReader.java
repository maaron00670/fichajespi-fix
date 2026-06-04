/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fichajespi.fichajespidestopapp.smartcard;

import com.fichajespi.fichajespidestopapp.MainWindow;
import com.fichajespi.fichajespidestopapp.entity.Fichaje;
import com.fichajespi.fichajespidestopapp.httpClient.RequestSender;
import java.awt.Robot;
import java.awt.event.InputEvent;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

/**
 *
 * @author alex
 */
public class CardReader extends Thread {

  private MainWindow instance;
  private RequestSender rs;

  public CardReader(MainWindow instance) {
    this.instance = instance;
    this.rs = new RequestSender();
  }

  @Override
  public void run() {
    while (true) {
      try {
        TerminalFactory factory = TerminalFactory.getDefault();
        List<CardTerminal> terminals = factory.terminals().list();

        if (terminals.isEmpty()) {
          Thread.sleep(1000);
          continue;
        }

        CardTerminal terminal = terminals.get(0);

        if (terminal.isCardPresent()) {
          Robot robot = new Robot();
          robot.mousePress(InputEvent.BUTTON1_MASK);
          robot.mouseRelease(InputEvent.BUTTON1_MASK);

          Card card = terminal.connect("*");
          CardChannel channel = card.getBasicChannel();

          ResponseAPDU response = channel.transmit(new CommandAPDU(new byte[]{
            (byte) 0xFF, (byte) 0xCA, (byte) 0x00, (byte) 0x00, (byte) 0x00}));

          if (response.getSW1() != 0x63 || response.getSW2() != 0x00) {
            BigInteger decimal = new BigInteger(bin2hex(response.getData()), 16);
            System.out.println("UID leída: " + decimal);
            fichar(decimal.toString());
            card.disconnect(false);
          }
        }
        
        Thread.sleep(500); // Pequeña pausa para no saturar la CPU

      } catch (Exception e) {
        try { Thread.sleep(1000); } catch (InterruptedException ex) {}
      }
    }
  }

  private void fichar(String number) throws InterruptedException, IOException {
    Fichaje fichaje = rs.sendRequest(number);
    if (fichaje != null) {
      System.out.println("Fichaje OK");
      String tipo = (fichaje.getTipo() != null) ? fichaje.getTipo().toLowerCase() : "entrada";
      instance.showFichaje(fichaje.getNombreUsuario(), number, tipo);
      CardReader.sleep(5000);
    } else {
      instance.showFichaje("Error: No encontrado", number, "desconocido");
      CardReader.sleep(5000);
    }

    instance.resetScreen();
  }

  private String bin2hex(byte[] data) {
    return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));
  }

}
