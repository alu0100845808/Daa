
public class Main {
    int numeroTareas = 0 ;
    int numeroMaquinas = 0 ;
    ArrayList<Tarea> tareas;
    Integer[][] distancias;
    
    public static void main(String[] args) {
        readInstance(args[0]);
        
    }
    
    public Instancia readInstance(String filename){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            
            Instancia instancia = new Instancia();
            
            String line = br.readLine(); //primera linea (Numero de Tareas)
            String[] parts = line.split("/s");
            numeroTareas = Integer.parseInt(parts[2]);
            tareas = new ArrayList<Tarea>(numeroTareas);
            
            line = br.readLine();  //segunda linea (Numero de Maquinas)
            String[] parts = line.split("/s");
            numeroMaquinas = Integer.parseInt(parts[2]);
            distancias = new Integer[numeroTareas][numeroTareas];
            
            line = br.readLine();  //tercera linea (Lista de Tareas)
            String[] parts = line.split("/s");
            for(int i = 1; i <= numeroMaquinas; i++){
                instancia.add(new Tarea(i - 1, parts[i]));
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
