package maquina;

import java.io.*;

public class Main {
    
	public static void main(String[] args) throws IOException {
		Instancia instancia = readInstance(args[0]);
    }
    
    public static Instancia readInstance(String filename) throws IOException {
        Instancia instancia = new Instancia();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            
            String line = br.readLine(); //primera linea (Numero de Tareas)
            String[] parts = line.split("\\s");
            Integer numeroTareas = Integer.parseInt(parts[1]);
            
            line = br.readLine();  //segunda linea (Numero de Maquinas)
            parts = line.split("\\s");
            Integer numeroMaquinas = Integer.parseInt(parts[1]);
            instancia = new Instancia(numeroTareas, numeroMaquinas);
            
            line = br.readLine();  //tercera linea (Lista de Tareas)
            parts = line.split("\\s");
            for(int i = 1; i <= numeroTareas; i++){
                instancia.addTarea(new Tarea(i - 1, Integer.parseInt(parts[i])));
            }
            
            line = br.readLine();  //cuarta linea (Descripcion de la matriz)
            int i = 0;
            while((line = br.readLine()) != null && i < numeroTareas) {
                parts = line.split("\\s");
                for(int j = 0; j < numeroTareas; j++){
                    instancia.setDistancia(i, j, Integer.parseInt(parts[j]));
                }
                i++;
            }
            
            // TODO excepcion que i == numeroTareas
            br.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filename + "'");                
        }
        
        return instancia;
        
    }
}
