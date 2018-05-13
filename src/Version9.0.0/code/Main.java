package code;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Main {
	private static String NombreCarpeta;
	final static int NUMEROALG = 5;
	final static Integer LRC_SIZE = 3;
	final static Integer N_ITERACIONES = 100;
	
	public static void execAlgoritmos(Instancia instancia, String NombreCarpeta) {
		for (int j = 0; j <= NUMEROALG; j++) {
			switch (j) {
			case 0:
				GreedyAlgorithm greedy = new GreedyAlgorithm(instancia);
				greedy.exec();
				greedy.printSolucion(NombreCarpeta);
				break;
			case 1:
				GraspAlgorithm grasp = new GraspAlgorithm(instancia, LRC_SIZE, N_ITERACIONES);
				grasp.exec();
				grasp.printSolucion(NombreCarpeta);
				break;
			case 2:
				LNS lns = new LNS(instancia, N_ITERACIONES);
				lns.exec();
				lns.printSolucion(NombreCarpeta);
				break;
			case 3:
				
				TS ts = new TS(instancia, 3, N_ITERACIONES);
				ts.exec();
				ts.printSolucion(NombreCarpeta);
				break;
			case 4:
				
				VNS vns = new VNS(instancia);
				vns.exec();
				vns.printSolucion(NombreCarpeta);
				break;
			case 5:
				Multiarranque mult = new Multiarranque(instancia, N_ITERACIONES);
				mult.exec();
				mult.printSolucion(NombreCarpeta);
				break;
			default:
				break;
			}
		}
	}
	
	public static void listarFicherosPorCarpeta(final File carpeta) {
	    for (final File ficheroEntrada : carpeta.listFiles()) {
	        if (ficheroEntrada.isDirectory()) {
	        	NombreCarpeta = ficheroEntrada.getName() + getTimeStamp();
	            listarFicherosPorCarpeta(ficheroEntrada);
	        } else {
	        	Instancia aux = new Instancia();
	        	
		    	try {
		    		System.out.println(ficheroEntrada.getAbsolutePath());
					aux.readFromInstance(ficheroEntrada.getAbsolutePath());
					execAlgoritmos(aux, NombreCarpeta);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
	        }
	    }
	}
	
	public static void main(String[] args) throws IOException {
		final File carpeta = new File(args[0]);
		NombreCarpeta = args[0] + getTimeStamp();
		listarFicherosPorCarpeta(carpeta);
	}
	
	public static String getTimeStamp() {
		Date fecha = new Date();
		String fechaText = fecha.toString();
		return "_" + fechaText.replaceAll("\\s", "_");
	}
}