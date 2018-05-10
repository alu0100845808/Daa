package code;

import java.util.ArrayList;
import java.util.Random;

public class Multiarranque extends Algorithm{

	private ArrayList<Solucion> Soluciones;
	private int numSoluciones;
	

	public Multiarranque(Instancia initInst, int numSoluciones) {
		super(new Instancia(initInst), "Multiarranque");
		setNumSoluciones(numSoluciones);
		setSoluciones(new ArrayList<Solucion>(numSoluciones));
	}

	@Override
	public void exec() {
		for (int i = 0; i < getNumSoluciones(); i++){
			Soluciones.add(conseguirSolucion());
			System.out.println("SOLUCION " + i + " :" + Soluciones.get(i).parseFile(getInitialInstance(), "", 0));
		}
		int bestIndex = 0;
		for (int i = 1; i < Soluciones.size(); i++){
			if (Soluciones.get(bestIndex).getLatenciaTotal(getInitialInstance()) > Soluciones.get(i).getLatenciaTotal(getInitialInstance())){
				bestIndex = i;
			}
		}
		if (!Soluciones.isEmpty()) {
			setSolution(Soluciones.get(bestIndex));
		}
	}
	
	public Solucion conseguirSolucion() {
		Random aux = new Random();
		GraspAlgorithm gr = new GraspAlgorithm(new Instancia(getInitialInstance()), 1 + aux.nextInt(getInitialInstance().getTareas().size() - 1), 1);
		gr.exec(); 
		return (new Solucion(gr.getSolution()));
	}

	/**
	 * @return the soluciones
	 */
	public ArrayList<Solucion> getSoluciones() {
		return Soluciones;
	}

	/**
	 * @param soluciones the soluciones to set
	 */
	public void setSoluciones(ArrayList<Solucion> soluciones) {
		Soluciones = soluciones;
	}

	/**
	 * @return the numSoluciones
	 */
	public int getNumSoluciones() {
		return numSoluciones;
	}

	/**
	 * @param numSoluciones the numSoluciones to set
	 */
	public void setNumSoluciones(int numSoluciones) {
		this.numSoluciones = numSoluciones;
	}
}