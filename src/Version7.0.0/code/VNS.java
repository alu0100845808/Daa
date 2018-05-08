package code;

import java.util.Random;

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
			Integer machine = rnd.nextInt(sol.getMachineList().size());
			Integer tarea1, tarea2;
			do{
				tarea1 = rnd.nextInt(sol.getMachineList().get(machine).getTareasRealizadas().size());
				tarea2 = rnd.nextInt(sol.getMachineList().get(machine).getTareasRealizadas().size());
			} while(tarea1 == tarea2);
			sol.exchangeMachineItem(sol.getMachineList().get(machine).getTarea(tarea1).getID(), sol.getMachineList().get(machine).getTarea(tarea2).getID());
		}
		// Entremáquina
		else if(entorno == 2 || entorno == 3) {
			if(sol.getMachineList().size() > 1) {
				Integer maquina1, maquina2;
				do {
					maquina1 = rnd.nextInt(sol.getMachineList().size());
					maquina2 = rnd.nextInt(sol.getMachineList().size());
				} while(maquina1 == maquina2);
				Integer tarea1 = rnd.nextInt(sol.getMachineList().get(maquina1).getTareasRealizadas().size());
				Integer tarea2 = rnd.nextInt(sol.getMachineList().get(maquina2).getTareasRealizadas().size());
				sol.exchangeMachineItem(getInitialInstance().getTareas().get(tarea1).getID(), getInitialInstance().getTareas().get(tarea2).getID());
			}
		}
		// Reinserción
		else if(entorno == 4 || entorno == 5) {
			if(sol.getMachineList().size() > 1) {
				Integer machine = rnd.nextInt(sol.getMachineList().size());
				Integer tarea = rnd.nextInt(sol.getMachineList().get(machine).getTareasRealizadas().size());
				Integer maquinaDestino;
				do maquinaDestino = rnd.nextInt(sol.getMachineList().size());
				while(machine == maquinaDestino);
				Integer posicion = rnd.nextInt(sol.getMachineList().get(maquinaDestino).getTareasRealizadas().size() + 1);
				sol.changeMachineItem(sol.getMachineList().get(machine).getTareasRealizadas().get(tarea).getID(), maquinaDestino, posicion);
			}
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