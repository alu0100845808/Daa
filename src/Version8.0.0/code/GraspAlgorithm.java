package code;

import java.util.Random;

public class GraspAlgorithm extends Algorithm{

	private Integer LRC_SIZE = 3;
	
	public GraspAlgorithm(Instancia initInst) {
		super(new Instancia(initInst), "GRASP");
	}
	
	public GraspAlgorithm(Instancia initInst, int LRC_SIZE, int N_ITERACIONES) {
		super(new Instancia(initInst), "GRASP", N_ITERACIONES);
		setLRC_SIZE(LRC_SIZE);
		
	}
	public void exec() {
		long time_start, time_end;
		time_start = System.currentTimeMillis();
		/************************************************/
		Solucion bestSolucion = new Solucion(construccion());
		bestSolucion = new Solucion(mejora(bestSolucion));
		Solucion solAux = new Solucion();
		for (int i = 1; i < getN_ITERACIONES(); i++) {
			solAux = new Solucion(construccion());
			solAux = new Solucion(mejora(bestSolucion));
			if (solAux.getLatenciaTotal(getInitialInstance()) < bestSolucion.getLatenciaTotal(getInitialInstance())) {
				bestSolucion = new Solucion(solAux);
			}
		}
		setSolution(new Solucion(bestSolucion));
		/**********************************************/
		time_end = System.currentTimeMillis();
		setTiempoEjec(time_end - time_start);
	}
	
	public Solucion construccion() {
		Solucion solucion = new Solucion(getSolution());
		Integer machineCandidata;
		while(getActualTareas().size() > 0) {
			machineCandidata = solucion.getCandidatoID(getInitialInstance());
			int index = 0, random = generateRandom();
			Maquina auxMachine = new Maquina(solucion.getMachineList().get(machineCandidata));
			auxMachine.addTarea(index, getActualTareas().get(random));
			int min = auxMachine.getLatencia(getInitialInstance());
			for(int i = 0; i < solucion.getMachineList().get(machineCandidata).getTareasRealizadas().size() + 1; i++) {
				auxMachine = new Maquina(solucion.getMachineList().get(machineCandidata));
				auxMachine.addTarea(i, getActualTareas().get(random));
				if(auxMachine.getLatencia(getInitialInstance()) < min) {
					index = i;
					min = auxMachine.getLatencia(getInitialInstance());
				}
			}
			solucion.getMachineList().get(machineCandidata).addTarea(index, getActualTareas().get(random));
			getActualTareas().remove(random);
		}
	    setActualTareas(getInitialInstance().getTareas());	//Reconstruir lista de tareas
	    return solucion;
	}
	
	public Solucion mejora(Solucion solucion) {
		boolean stop = false;
		Solucion bestSolucion = new Solucion();
		bestSolucion = new Solucion(solucion.BLEntreGreedy(getInitialInstance()));
		Solucion auxSolucion = new Solucion();
		while(!stop) {
			auxSolucion = new Solucion(bestSolucion.BLIntraGreedy(getInitialInstance()));
			if(auxSolucion.getLatenciaTotal(getInitialInstance()) < bestSolucion.getLatenciaTotal(getInitialInstance())) {
				bestSolucion = new Solucion(auxSolucion);
			} else {
				stop = true;
			}
		}
		return bestSolucion;
	}
	
	public Solucion mejora2(Solucion solucion) {
		boolean stop = false;
		Solucion bestSolucion = new Solucion();
		bestSolucion = new Solucion(solucion.BLIntraGreedy(getInitialInstance()));
		Solucion auxSolucion = new Solucion();
		while(!stop) {
			auxSolucion = new Solucion(bestSolucion.BLEntreGreedy(getInitialInstance()));
			if(auxSolucion.getLatenciaTotal(getInitialInstance()) < bestSolucion.getLatenciaTotal(getInitialInstance())) {
				bestSolucion = new Solucion(auxSolucion);
			} else {
				stop = true;
			}
		}
		return bestSolucion;
	}
	
	public Integer generateRandom() {
		Random rnd = new Random();
		Integer random = rnd.nextInt(getLRC_SIZE());
		if(getActualTareas().size() != 1) {
			while(random > getActualTareas().size() - 1) {
				random = rnd.nextInt(getLRC_SIZE());
			}
			return random;
		}
		return 0;		
	}
	
	public Integer getLRC_SIZE() { return LRC_SIZE; }
	public void setLRC_SIZE(Integer lRC_SIZE) { LRC_SIZE = lRC_SIZE; }
}

