package View;

import Registro.ControllerRegistro;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class TryClass {

    private static TryClass instance;
    private Integer tempo;

    public TryClass() {
        this.tempo = ControllerRegistro.lerTempo();
    }

    public static Runnable launchTry() {
        Runnable runner = new Runnable() {
            public void run() {
                if (SystemTray.isSupported()) {
                    SystemTray tray = SystemTray.getSystemTray();
                    Image image = new ImageIcon(getClass().getResource("/Imagens/lock.png")).getImage();
                    PopupMenu popup = new PopupMenu();
                    MenuItem item = new MenuItem("Sair");
                    MenuItem setTempo = new MenuItem("Definir Tempo");
                    popup.add(setTempo);
                    popup.add(item);
                    TrayIcon trayIcon = new TrayIcon(image, "Windows Block", popup);
                    trayIcon.setImageAutoSize(true);
                    try {
                        tray.add(trayIcon);
                    } catch (AWTException e) {
                        System.err.println("Não pode adicionar a tray");
                    }
                    item.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    });
                    setTempo.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String novoTempo = instance.informeTempo();
                            instance.setTempo(novoTempo != null ? Integer.parseInt(novoTempo) : instance.getTempo());
                        }
                    });
                } else {
                    System.err.println("Tray indisponível");
                }
            }
        };
        return runner;
    }

    private String informeTempo() {
        return JOptionPane.showInputDialog("Tempo(em segundos)", ControllerRegistro.lerTempo());
    }

    public static TryClass getInstance() {
        if (instance == null) {
            instance = new TryClass();
        }
        return instance;
    }

    public Integer getTempo() {
        return tempo;
    }

    public void setTempo(Integer tempo) {
        this.tempo = ControllerRegistro.escreveTempo(tempo);
    }

}
