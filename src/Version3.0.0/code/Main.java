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
	    GreedyAlgorithm x = new GreedyAlgorithm(instancias.get(0));
	    x.exec();
	    System.out.println(x.getSolution().getMachineList().get(0).getTareasRealizadas().toString());
	    System.out.println(x.getSolution().getMachineList().get(0).getLatencia(x.getInitialInstance()));
	    System.out.println(x.getSolution().getMachineList().get(1).getTareasRealizadas().toString());
	    System.out.println(x.getSolution().getMachineList().get(1).getLatencia(x.getInitialInstance()));
    }
}
