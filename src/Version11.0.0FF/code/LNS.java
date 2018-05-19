package code;

import java.util.Random;

import sun.util.locale.provider.AuxLocaleProviderAdapter;

public class LNS extends Algorithm{
	
	private double porDestr;
	private final double MAXDESTR = 0.7;

	public LNS(Instancia initInst, int maxIter) {
		super(new Instancia(initInst), "LNS", maxIter);
		setPorDestr(0.1);
	}

	@Override
	public void exec() {
		int iters = 0;
		long time_start, time_end;
		time_start = System.currentTimeMillis();
		Solucion inicial = construirSolucion();
		int mejorLatencia = inicial.getLatenciaTotal(getInitialInstance());
		Solucion bestSolucion = new Solucion(inicial);
		Solucion auxSolucion = new Solucion(inicial);
		while(iters < getN_ITERACIONES()){
			if(getPorDestr() > 0.7) {
				inicial = construirSolucion();
				auxSolucion = new Solucion(inicial);
				setPorDestr(0.1);
			}
			auxSolucion = destruirParteSolucion(auxSolucion);
			auxSolucion = reConstruirSolucion(auxSolucion);
			auxSolucion = vnd(auxSolucion);
			if(mejorLatencia > auxSolucion.getLatenciaTotal(getInitialInstance())){
				mejorLatencia = auxSolucion.getLatenciaTotal(getInitialInstance());
				bestSolucion = new Solucion(auxSolucion);
				setPorDestr(0.1);
			} else {
				setPorDestr(getPorDestr() + 0.1);
			}
			if(auxSolucion.getLatenciaTotal(getInitialInstance()) < bestSolucion.getLatenciaTotal(getInitialInstance())) {
				bestSolucion = new Solucion(auxSolucion);
			}
			iters++;
		}
		setSolution(bestSolucion);
		time_end = System.currentTimeMillis();
		setTiempoEjec(time_end - time_start);
	}
	
	public Solucion construirSolucion() {
		GraspAlgorithm grasp = new GraspAlgorithm(getInitialInstance(), getInitialInstance().getNumeroMaquinas()/2, 1);
		grasp.exec();
		getActualTareas().clear();
		return new Solucion(grasp.getSolution());
	}
	
	public Solucion destruirParteSolucion(Solucion solucion){
		Random randCantidad = new Random();
		int cantidad = (int) (getInitialInstance().getNumeroTareas() * getPorDestr());
		for(int i = 0; i < cantidad; i++){
			int idMaquina = randCantidad.nextInt(getInitialInstance().getNumeroMaquinas());
			if(!solucion.getMachineList().get(idMaquina).getTareasRealizadas().isEmpty()) {
				int posTarea = randCantidad.nextInt(solucion.getMachineList().get(idMaquina).getTareasRealizadas().size());
				getActualTareas().add(solucion.getMachineList().get(idMaquina).getTareasRealizadas().get(posTarea));
				solucion.getMachineList().get(idMaquina).getTareasRealizadas().remove(posTarea);
			}
		}
		return new Solucion(solucion);
	}
	
	public Solucion reConstruirSolucion(Solucion solucion) {
		Integer machineCandidata;
		while(getActualTareas().size() > 0) {
			machineCandidata = solucion.getCandidatoID(getInitialInstance());
			int index = solucion.getMachineList().get(machineCandidata).bestPos(getActualTareas().get(0), getInitialInstance());
			solucion.getMachineList().get(machineCandidata).addTarea(index, getActualTareas().get(0));
			getActualTareas().remove(0);
		}
		return new Solucion(solucion);
	}
	
	public Solucion vnd(Solucion actualSolution) {
		boolean stop = false; // boolean that turns true when there is no improvement
		while(!stop) {
			int actualEntorno = 0;
			Solucion auxSolution = new Solucion(actualSolution);
			// hasta que se hayan usado todas las estructuras de entorno
			while(actualEntorno < actualSolution.getNumEntornos()) {
				Solucion auxSolution2 = new Solucion(actualSolution);
				auxSolution2 = getEntorno(actualEntorno, auxSolution2);
				// si se ha mejorado pues se actualiza auxSolution
				if(auxSolution2.getLatenciaTotal(getInitialInstance()) < auxSolution.getLatenciaTotal(getInitialInstance()))
					auxSolution = new Solucion(auxSolution2);
				// si no se ha mejorado se pasa a la siguiente estructura de entorno
				else
					actualEntorno++;
			}
			// si se ha mejorado se actualiza actualSolution
			if(auxSolution.getLatenciaTotal(getInitialInstance()) < actualSolution.getLatenciaTotal(getInitialInstance())) {
				actualSolution = new Solucion(auxSolution);
			}
			// si no se ha mejorado paramos el bucle
			else
				stop = true;
		}
		return new Solucion(actualSolution);
	}
	
	public Solucion getEntorno(int entorno, Solucion sol) {
		switch(entorno) {
		case 0:
			return new Solucion(sol.BLIntraPM(getInitialInstance()));
		case 1:
			return new Solucion(sol.BLIntraGreedy(getInitialInstance()));
		case 2:
			return new Solucion(sol.BLEntrePM(getInitialInstance()));
		case 3:
			return new Solucion(sol.BLEntreGreedy(getInitialInstance()));
		case 4:
			return new Solucion(sol.BLReinsertPM(getInitialInstance()));
		case 5:
			return new Solucion(sol.BLReinsertGreedy(getInitialInstance()));
		default:
			return new Solucion(sol);
		}
	}

	public double getPorDestr() { return porDestr; }
	public void setPorDestr(double porDestr) { this.porDestr = porDestr; } 
}