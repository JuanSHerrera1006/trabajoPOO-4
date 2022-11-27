

import static UiInterfaz.UiInterfaz.mainMenu;
import gestor_cedula.FileManager;
import java.io.IOException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws IOException {
       Scanner sc =  new Scanner(System.in);
       FileManager fileManager = new FileManager("./userData.txt");
       
       mainMenu(sc, fileManager);
       
    }   
}
