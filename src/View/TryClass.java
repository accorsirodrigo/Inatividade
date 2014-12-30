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
    private boolean bloqueio;
    private String posTitle;
    private String preTitle;

    public TryClass() {
        this.tempo = ControllerRegistro.lerTempo();
        this.bloqueio = ControllerRegistro.lerTipo();
    }

    public static Runnable launchTry() {
        Runnable runner = new Runnable() {
            public void run() {
                MenuItem blockPos = new MenuItem();
                MenuItem blockPre = new MenuItem();
                if (SystemTray.isSupported()) {
                    TryClass.getInstance().ajustaSelecionado();
                    SystemTray tray = SystemTray.getSystemTray();
                    Image image = new ImageIcon(getClass().getResource("/Imagens/lock.png")).getImage();
                    PopupMenu popup = new PopupMenu();
                    MenuItem item = new MenuItem("Sair");
                    MenuItem setTempo = new MenuItem("Definir Tempo");
                    Menu menuBlock = new Menu("Bloqueio");
                    blockPos.setLabel(TryClass.getInstance().getPosTitle());
                    blockPre.setLabel(TryClass.getInstance().getPreTitle());
                    menuBlock.add(blockPos);
                    menuBlock.add(blockPre);
                    popup.add(menuBlock);
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
                    blockPos.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (ControllerRegistro.escreveTipo(false)) {
                                JOptionPane.showMessageDialog(null, "Alterado com sucesso");
                                TryClass.getInstance().setBloqueio(ControllerRegistro.lerTipo());
                            } else {
                                JOptionPane.showMessageDialog(null, "Falha ao alterar");
                            }
                        }
                    });
                    blockPre.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (ControllerRegistro.escreveTipo(true)) {
                                JOptionPane.showMessageDialog(null, "Alterado com sucesso");
                                TryClass.getInstance().setBloqueio(ControllerRegistro.lerTipo());
                            } else {
                                JOptionPane.showMessageDialog(null, "Falha ao alterar");
                            }
                        }
                    });
                } else {
                    System.err.println("Tray indisponível");
                }
            }
        };
        return runner;
    }

    private void ajustaSelecionado() {
        if (ControllerRegistro.lerTipo()) {
            posTitle = "Pós";
            preTitle = "Pré";
        } else {
            posTitle = "Pós";
            preTitle = "Pré";
        }
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

    public boolean isBloqueio() {
        return bloqueio;
    }

    public void setBloqueio(boolean bloqueio) {
        this.bloqueio = bloqueio;
    }

    public String getPosTitle() {
        return posTitle;
    }

    public void setPosTitle(String posTitle) {
        this.posTitle = posTitle;
    }

    public String getPreTitle() {
        return preTitle;
    }

    public void setPreTitle(String preTitle) {
        this.preTitle = preTitle;
    }

}
