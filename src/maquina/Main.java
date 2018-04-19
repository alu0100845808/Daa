import java.io.*;

public class Main {
    
    public static void main(String[] args) {
        readInstance(args[0]);
        
    }
    
    public Instancia readInstance(String filename){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            
            Instancia instancia;
            
            String line = br.readLine(); //primera linea (Numero de Tareas)
            String[] parts = line.split("/s");
            Integer numeroTareas = Integer.parseInt(parts[2]);
            
            line = br.readLine();  //segunda linea (Numero de Maquinas)
            String[] parts = line.split("/s");
            Integer numeroMaquinas = Integer.parseInt(parts[2]);
            instancia = new Instancia(numeroTareas, numeroMaquinas);
            
            line = br.readLine();  //tercera linea (Lista de Tareas)
            String[] parts = line.split("/s");
            for(int i = 1; i <= numeroMaquinas; i++){
                instancia.addTarea(new Tarea(i - 1, parts[i]));
            }
            
            line = br.readLine();  //cuarta linea (Descripcion de la matriz)
            int i = 0;
            while((line = br.readLine()) != null) {
                String[] parts = line.split("/s");
                for(int j = 0; j < numeroMaquinas; j++){
                    distancias[i][j] = parts[j];
                }
                j++;
            }   
            // Always close files.
            br.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + fileName + "'");                
        }
        
        
    }
}
