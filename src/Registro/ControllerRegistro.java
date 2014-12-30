/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Registro;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo
 */
public class ControllerRegistro {

    public static Integer lerTempo() {
        Integer retorno = 15;
        try {
            String value = WinRegistry.readString(
                    WinRegistry.HKEY_LOCAL_MACHINE, //HKEY
                    "SOFTWARE\\Inatividade", //Key
                    "Tempo");                                              //ValueName
            //System.out.println("Windows Distribution = " + value);
            try {
                retorno = value != null ? Integer.parseInt(value)
                        : escreveTempo(Integer.parseInt(JOptionPane.showInputDialog("Seja bem vindo! \nInforme um tempo em segundos para bloquear seu computador")));
            } catch (Exception e) {
                System.exit(0);
            }

        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ControllerRegistro.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ControllerRegistro.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ControllerRegistro.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } catch (Exception e) {
            System.exit(0);
        }
        return retorno;
    }

    public static boolean lerTipo() {
        boolean retorno = true;
        try {
            String value = WinRegistry.readString(
                    WinRegistry.HKEY_LOCAL_MACHINE, //HKEY
                    "SOFTWARE\\Inatividade", //Key
                    "Bloqueio"); //ValueName
            //System.out.println("Windows Distribution = " + value);
            try {
                retorno = value != null ? Boolean.parseBoolean(value) : escreveTipo(true);
            } catch (Exception e) {
                System.exit(0);
            }

        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ControllerRegistro.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ControllerRegistro.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ControllerRegistro.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } catch (Exception e) {
            System.exit(0);
        }
        return retorno;
    }

    public static Integer escreveTempo(Integer tempo) {
        Integer limiteMenor = 1;
        Integer limiteMaior = 700;
        if (tempo > limiteMenor && tempo < limiteMaior) {
            try {
                Process process
                        = Runtime.getRuntime().exec("REG ADD HKLM\\Software\\Inatividade /v Tempo /d " + tempo + " /f");
            } catch (IOException ex) {
                Logger.getLogger(ControllerRegistro.class.getName()).log(Level.SEVERE, null, ex);
            }
            return tempo;
        } else {
            JOptionPane.showMessageDialog(null, "Você informou um tempo incorreto(Maior que " + limiteMaior + " ou menor que " + limiteMenor + ")");
            return lerTempo();
        }
    }

    public static boolean escreveTipo(boolean valor) {
        Integer limiteMenor = 1;
        Integer limiteMaior = 700;
        try {
            Process process
                    = Runtime.getRuntime().exec("REG ADD HKLM\\Software\\Inatividade /v Bloqueio /d " + valor + " /f");
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ControllerRegistro.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
