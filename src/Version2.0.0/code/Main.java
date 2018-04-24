package code;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    
	public static void main(String[] args) throws IOException{
	    ArrayList<Instancia> instancias = new ArrayList<Instancia>();
	    for (int i = 0; i < args.length; i++){
	    	Instancia aux = new Instancia();
	    	aux.readFromInstance(args[i]);
		    instancias.add(aux);
	    }
	    //Ejecutar algoritmos
	    
    }
}
