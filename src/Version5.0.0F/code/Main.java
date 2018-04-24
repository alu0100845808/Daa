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
	    //GreedyAlgorithm gr = new GreedyAlgorithm(instancias.get(0));
	    GraspAlgorithm gr = new GraspAlgorithm(instancias.get(0));
	    gr.exec();
	    System.out.println("==========================================================================");
	    System.out.println("SOLUCIÓN FINAL:");
	    for(int j = 0; j < gr.getSolution().getMachineList().size(); j++) {
		    System.out.println(gr.getSolution().getMachineList().get(j).getTareasRealizadas().toString());
		    System.out.println("VALOR TOTAL LATENCIA: " + gr.getSolution().getMachineList().get(j).getLatencia(gr.getInitialInstance()));
	    }
    }
}
