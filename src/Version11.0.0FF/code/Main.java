package code;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Main {
	private static String NombreCarpeta;
	final static int NUMEROALG = 5;
	final static Integer LRC_SIZE = 3;
	static Integer N_ITERACIONES = 10;
	
	public static void execAlgoritmos(Instancia instancia, String NombreCarpeta) {
		System.out.println("Calculando la instancia: " + NombreCarpeta);
		Algorithm alg = null;
		for (int j = 0; j <= NUMEROALG; j++) {
			switch (j) {
			case 0:
				alg = new GreedyAlgorithm(instancia);
				break;
			case 1:
				alg = new GraspAlgorithm(instancia, LRC_SIZE, N_ITERACIONES);
				break;
			case 2:
				alg = new LNS(instancia, N_ITERACIONES);
				break;
			case 3:
				alg = new TS(instancia, 3, N_ITERACIONES);
				break;
			case 4:
				alg = new VNS(instancia, N_ITERACIONES);
				break;
			case 5:
				alg = new Multiarranque(instancia, N_ITERACIONES);
				break;
			default:
				break;
			}
			System.out.println("Iniciando algorimo " + alg.getNomAlgoritmo());
			alg.exec();
			alg.printSolucion(NombreCarpeta);
			System.out.println("Algorimo "+ alg.getNomAlgoritmo() +" finalizado (" + alg.getTiempoEjec() + " ms)");
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
		    		if (!ficheroEntrada.getName().matches("[.].*")) {
		    		System.out.println(ficheroEntrada.getAbsolutePath());
					aux.readFromInstance(ficheroEntrada.getAbsolutePath());
					execAlgoritmos(aux, NombreCarpeta  + "_" + N_ITERACIONES + " Iteraciones");
		    		}
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
		System.out.println("Iniciando ejecucion...");
		listarFicherosPorCarpeta(carpeta);
		System.out.println("¡Proceso finalizado con exito!");
	}
	
	public static String getTimeStamp() {
		Date fecha = new Date();
		String fechaText = fecha.toString();
		return "_" + fechaText.replaceAll("\\s", "_");
	}
}