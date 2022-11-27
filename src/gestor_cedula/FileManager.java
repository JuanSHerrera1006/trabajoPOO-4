package gestor_cedula;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cedbo
 */
public class FileManager {

    private String path;

    public FileManager(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String read() throws IOException {
        File file;
        BufferedReader br;
        String linea;
        FileReader fr = null;
        String data = "";
        try {
            file = new File(this.getPath());
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            while ((linea = br.readLine()) != null) {
                data += linea + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }

        }
        return data;
    }

    public void write(String data) {
        FileWriter file = null;
        PrintWriter pw = null;

        try {
            file = new FileWriter(this.getPath());
            pw = new PrintWriter(file);
            pw.println(data);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (file != null) {
                    pw.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }
    }
    
    public boolean isEmpty() throws IOException{
        File file = new File(this.getPath());
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader br = new BufferedReader(fr);
        return br.readLine() == null;
    }
}
