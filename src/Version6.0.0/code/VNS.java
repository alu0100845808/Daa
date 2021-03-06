package code;

public class VNS extends Algorithm{

	public VNS(Instancia initInst) {
		super(initInst);
	}

	@Override
	public void exec() {
		construccionGreedy();
		Solucion newSolution = new Solucion(getSolution());
		// TODO bestSolution ???????????????
		setSolution(rvns(newSolution));
	}
	
	public void construccionGreedy() {
		Integer machineCandidata;
		// hasta que se vacíe el vector de tareas
		while(getActualTareas().size() > 0) {
			// obtenemos la máquina con menor latencia
			machineCandidata = getSolution().getCandidatoID(getInitialInstance());
			int index = 0;
			Maquina auxMachine = new Maquina(getSolution().getMachineList().get(machineCandidata));
			// le metemos la tarea con menor tiempo (están ordenadas)
			auxMachine.addTarea(index, getActualTareas().get(0));
			int min = auxMachine.getLatencia(getInitialInstance());
			// buscamos la mejor posición para meter la tarea copiando la máquina y metiendo la tarea en cada posición
			// posible almacenando la latencia mínima de las posibilidades generadas
			for(int i = 0; i < getSolution().getMachineList().get(machineCandidata).getTareasRealizadas().size() + 1; i++) {
				auxMachine = new Maquina(getSolution().getMachineList().get(machineCandidata));
				auxMachine.addTarea(i, getActualTareas().get(0));
				if(auxMachine.getLatencia(getInitialInstance()) < min) {
					index = i;
					min = auxMachine.getLatencia(getInitialInstance());
				}
			}
			getSolution().getMachineList().get(machineCandidata).addTarea(index, getActualTareas().get(0));
			getActualTareas().remove(0);
		}
	}
	
	public Solucion vnd(Solucion actualSolution) {		
		boolean stop = false; // boolean that turns true when there is no improvement
		while(!stop) {
			int actualEntorno = 0;
			Solucion auxSolution = new Solucion(actualSolution);
			// hasta que se hayan usado todas las estructuras de entorno
			while(actualEntorno < getSolution().getNumEntornos()) {
				Solucion auxSolution2 = new Solucion(getSolution());
				auxSolution2 = getEntorno(actualEntorno, auxSolution2);
				// si se ha mejorado pues se actualiza auxSolution
				if(auxSolution2.getLatenciaTotal(getInitialInstance()) < auxSolution.getLatenciaTotal(getInitialInstance()))
					auxSolution = auxSolution2;
				// si no se ha mejorado se pasa a la siguiente estructura de entorno
				else
					actualEntorno++;
			}
			// si se ha mejorado se actualiza actualSolution
			if(auxSolution.getLatenciaTotal(getInitialInstance()) < actualSolution.getLatenciaTotal(getInitialInstance()))
				actualSolution = auxSolution;
			// si no se ha mejorado paramos el bucle
			else
				stop = true;
		}
		return actualSolution;
	}
	
	public Solucion rvns(Solucion actualSolution) {
		boolean stop = false; // boolean that turns true when there is no improvement
		while(!stop) {
			int actualEntorno = 0;
			Solucion auxSolution = new Solucion(actualSolution);
			// hasta que se hayan usado todas las estructuras de entorno
			while(actualEntorno < getSolution().getNumEntornos()) {
				Solucion auxSolution2 = shake(actualEntorno, auxSolution);
				auxSolution2 = vnd(auxSolution);
				// si se ha mejorado pues se actualiza auxSolution
				if(auxSolution2.getLatenciaTotal(getInitialInstance()) < auxSolution.getLatenciaTotal(getInitialInstance()))
					auxSolution = auxSolution2;
				// si no se ha mejorado se pasa a la siguiente estructura de entorno
				else
					actualEntorno++;
			}
			// si se ha mejorado se actualiza actualSolution
			if(auxSolution.getLatenciaTotal(getInitialInstance()) < actualSolution.getLatenciaTotal(getInitialInstance()))
				actualSolution = auxSolution;
			// si no se ha mejorado paramos el bucle
			else
				stop = true;
		}
		return actualSolution;
	}

	public Solucion shake(int entorno, Solucion sol) {
		Random rnd = new Random();
		// Intramáquina
		if(entorno == 0 || entorno == 1) {
			rnd.nextInt(/*numero maximo + 1*/);
			// TODO cambiar aleatoriamente elementos en cada máquina
		}
		// Entremáquina
		else if(entorno == 2 || entorno == 3) {
			// TODO cambiar aleatoriamente elementos entre las máquinas
		}
		// Reinserción
		else if(entorno == 4 || entorno == 5) {
			// TODO pues na, lo mismo de antes pero con reinserción
		}
		return sol;
	}
	
	// TODO switch smell
	public Solucion getEntorno(int entorno, Solucion sol) {
		switch(entorno) {
		case 0:
			return sol.BLIntraPM(getInitialInstance());
		case 1:
			return sol.BLIntraGreedy(getInitialInstance());
		case 2:
			return sol.BLEntrePM(getInitialInstance());
		case 3:
			return sol.BLEntreGreedy(getInitialInstance());
		case 4:
			return sol.BLReinsertPM(getInitialInstance());
		case 5:
			return sol.BLReinsertGreedy(getInitialInstance());
		default:
			return sol;
		}
	}
}