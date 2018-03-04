/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kitchen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mohit Arora
 */
public class KitchenThread extends Thread{

    /**
     * @param args the command line arguments
     */
    protected KitchenMain o;
    protected ArrayList<String> receivedArray = new ArrayList<>();
    protected LinkedHashSet<String> receivedSet;
    
    public KitchenThread(KitchenMain o){
        this.o = o;
    }
    
    public void run() {
        // TODO code application logic here
        
        while (true){
        try{ 
            ServerSocket ss = new ServerSocket(9766);
            Socket socket=ss.accept();//establishes connection   
            InputStream inp = null;
            BufferedReader brinp = null;
            try {
                inp = socket.getInputStream();
                brinp = new BufferedReader(new InputStreamReader(inp));
            } catch (IOException e) {
                return;
            }
            String line;
            while (true) {
                try {
                    line = brinp.readLine();
                    if ((line == null) || line.equalsIgnoreCase("DONE")) {
                        socket.close();
                        o.putData(receivedArray);
                        receivedSet = new LinkedHashSet(new ArrayList(receivedArray.subList(1, receivedArray.size())));
                        receivedArray.clear();
                        receivedSet.clear();
                        ss.close();
                        break;
                    } else {
                        receivedArray.add(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
            } catch (Exception e) {
                System.out.println(e);
            }  
        }  
    }
}
