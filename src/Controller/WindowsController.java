package Controller;

import java.io.IOException;
/**
 * Classe que gerencia o sistema, para executar funções do windows
 * @author Accorsi
 */
public class WindowsController {

    public static void bloqueia() throws IOException {
        String shutdownCmd = "rundll32 user32,LockWorkStation";
        Process child = Runtime.getRuntime().exec(shutdownCmd);
    }
}
