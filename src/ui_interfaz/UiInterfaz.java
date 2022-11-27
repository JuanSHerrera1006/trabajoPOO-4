package ui_interfaz;

import gestor_cedula.FileManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UiInterfaz {

    public static void initMessage() {
        System.out.println("+=================+");
        System.out.println("| GESTOR DE DATOS |");
        System.out.println("+=================+");
        System.out.println("");

    }

    public static void mainMenu(Scanner sc, FileManager fm) throws IOException {
        String opt;

        initMessage();
        outside:
        while (true) {
            System.out.println("Opcione: ");
            System.out.println("1. Agregar un nuevo usuario");
            System.out.println("2. Editar usuario existente");
            System.out.println("3. Eliminar usuario");
            System.out.println("4. Buscar usuario");
            System.out.println("5. Ver todos los usuario");
            System.out.println("6. Salir");

            System.out.print("Digita la opcion que seleccionaste: ");
            opt = sc.nextLine();
            System.out.println("");

            switch (opt) {
                case "1":
                    createUser(sc, fm);
                    break;
                case "2":
                    updateUser(sc, fm);
                    break;
                case "3":
                    deleteUser(sc, fm);
                    break;
                case "4":
                    searchUser(sc, fm);
                    break;
                case "5":
                    showUsers(sc, fm);
                    break;
                case "6":
                    break outside;
                default:
                    System.out.println("Has ingresado una opcion no valida...");
                    System.out.println("");
            }
        }

    }

    public static void showUsers(Scanner sc, FileManager fm) throws IOException {
        ArrayList<ArrayList<String>> allData = getData(fm);
        ArrayList<String> user;
        boolean isEmpty;
        
        System.out.println("+=========================+");
        System.out.println("|    LISTA DE USUARIOS    |");
        System.out.println("+=========================+");
        System.out.println("");
        
        int n = allData.size();
        isEmpty = fm.isEmpty();
        if (isEmpty) {
            System.out.println("No hay datos disponibles...");
            System.out.println("");
            return;
        }
        System.out.println("");
        System.out.println("***USUARIOS***");
        for (int i = 0; i < n; i++) {
            user = allData.get(i);
            System.out.println("Cedula: " + user.get(0));
            System.out.println("Nombre: " + user.get(1));
            System.out.println("Apellido: " + user.get(2));
            System.out.println("");     
        }
        System.out.print("Presiona ENTER para continuar...");
        System.out.println("");
        sc.nextLine();

    }

    public static void searchUser(Scanner sc, FileManager fm) throws IOException {
        ArrayList<ArrayList<String>> allData = getData(fm);
        ArrayList<String> user;
        boolean found = false;
        String id;
        
        System.out.println("+======================+");
        System.out.println("|    BUSCAR USUARIO    |");
        System.out.println("+======================+");
        System.out.println("");

        System.out.print("Digite la cedula del usuario que desea buscar: ");
        id = sc.nextLine();
        

        int n = allData.size();
        for (int i = 0; i < n; i++) {
            user = allData.get(i);
            if (user.get(0).equals(id)) {
                found = true;
                System.out.println("***USUARIO ENCONTRADO***");
                System.out.println("Cedula: " + user.get(0));
                System.out.println("Nombre: " + user.get(1));
                System.out.println("Apellido: " + user.get(2));
                System.out.println("");
                break;
            }
        }
        if (!found) {
            System.out.println("No se ha encontrado el usuario... ");
        }
        System.out.print("Presiona ENTER para continuar...");
        sc.nextLine();
        System.out.println("");
    }

    public static void createUser(Scanner sc, FileManager fm) throws IOException {
        String id;
        String name;
        String lastName;
        String rawData;
        String rawUser;

        System.out.println("+=================+");
        System.out.println("|  CREAR USUARIO  |");
        System.out.println("+=================+");
        System.out.println("");

        System.out.print("Ingrese el documento de identidad del usuario: ");
        id = sc.nextLine();
        System.out.print("Ingrese el nombre del usuario: ");
        name = sc.nextLine();
        System.out.print("Ingrese el apellido del usuario: ");
        lastName = sc.nextLine();
        System.out.println("");

        id = id.trim();
        name = name.toUpperCase().trim();
        lastName = lastName.toUpperCase().trim();

        rawUser = id + ";" + name + ";" + lastName;
        System.out.println(fm.isEmpty());
        rawData = fm.isEmpty() ? rawUser : (fm.read() + rawUser);
        fm.write(rawData);

    }

    public static void loadData(ArrayList<ArrayList<String>> data, FileManager fm) {
        String strData = "";
        for (ArrayList<String> userData : data) {
            strData += String.join(";", userData) + "\n";
        }
        strData = strData.trim();
        fm.write(strData);

    }

    public static ArrayList<ArrayList<String>> getData(FileManager fm) throws IOException {
        String[] rawData;
        String[] rawUser;
        ArrayList<String> userData;
        ArrayList<ArrayList<String>> allData = new ArrayList<>();

        rawData = fm.read().split("\n");
        for (String user : rawData) {
            rawUser = user.split(";");
            userData = new ArrayList<>(Arrays.asList(rawUser));
            allData.add(userData);
        }
        return allData;

    }

    public static void updateUser(Scanner sc, FileManager fm) throws IOException {
        ArrayList<ArrayList<String>> allData = getData(fm);
        ArrayList<String> user;
        boolean found = false;
        String id;
        String name;
        String lastName;

        System.out.println("+======================+");
        System.out.println("|  ACTUALIZAR USUARIO  |");
        System.out.println("+======================+");
        System.out.println("");

        System.out.print("Digite el numero de la cedula que desea editar: ");
        id = sc.nextLine();

        System.out.print("Ingrese el nuevo nombre: ");
        name = sc.nextLine().toUpperCase();

        System.out.print("Ingrese el nuevo apellido: ");
        lastName = sc.nextLine().toUpperCase();

        int n = allData.size();
        for (int i = 0; i < n; i++) {
            user = allData.get(i);
            if (user.get(0).equals(id)) {
                ArrayList<String> updatedUser = new ArrayList<>();
                found = true;
                allData.remove(i);
                updatedUser.add(id);
                updatedUser.add(name);
                updatedUser.add(lastName);
                allData.add(updatedUser);
                break;
            }
        }
        if (!found) {
            System.out.println("No se ha encontrado el usuario...");
            System.out.println("");
        }
        loadData(allData, fm);
    }

    public static void deleteUser(Scanner sc, FileManager fm) throws IOException {
        ArrayList<ArrayList<String>> allData = getData(fm);
        ArrayList<String> user;
        boolean isDelete = false;
        String cedula;

        System.out.println("+======================+");
        System.out.println("|   ELIMINAR USUARIO   |");
        System.out.println("+======================+");
        System.out.println("");

        System.out.print("Digite el numero de la cedula que desea: ");
        cedula = sc.nextLine();

        int n = allData.size();
        for (int i = 0; i < n; i++) {
            user = allData.get(i);
            if (user.get(0).equals(cedula)) {
                isDelete = true;
                allData.remove(i);
                break;
            }
        }
        if (!isDelete) {
            System.out.println("No se ha encontrado la cedula en la base de datos...");
            System.out.println("");
        }
        loadData(allData, fm);
    }
}
