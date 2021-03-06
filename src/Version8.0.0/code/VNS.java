package code;

import java.util.Random;

public class VNS extends Algorithm{

	public VNS(Instancia initInst) {
		super(new Instancia(initInst), "VNS");
		setN_ITERACIONES(50);
	}

	@Override
	public void exec() {
		long time_start, time_end;
		time_start = System.currentTimeMillis();
		construccionGreedy();
		Solucion newSolution = new Solucion(getSolution());
		setSolution(rvns(newSolution));
		time_end = System.currentTimeMillis();
		setTiempoEjec(time_end - time_start);
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
		System.out.println("------------------------------- VNS -------------------------------");
		System.out.println("SOLUCIÓN INICIAL:");
	    for(int j = 0; j < getSolution().getMachineList().size(); j++) {
		    System.out.println(getSolution().getMachineList().get(j).getTareasRealizadas().toString());
		    System.out.println("VALOR TOTAL LATENCIA: " + getSolution().getMachineList().get(j).getLatencia(getInitialInstance()));
	    }
	    System.out.println("==========================================================================");
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
			if(auxSolution.getLatenciaTotal(getInitialInstance()) < actualSolution.getLatenciaTotal(getInitialInstance())) {
				actualSolution = auxSolution;
			}
			// si no se ha mejorado paramos el bucle
			else
				stop = true;
			//System.out.println("==========================================================================");
		}
		return actualSolution;
	}
	
	public Solucion rvns(Solucion actualSolution) {
		int numIteraciones = getN_ITERACIONES();
		while(numIteraciones >= 0) {
			int actualEntorno = 0;
			Solucion auxSolution = new Solucion(actualSolution);
			// hasta que se hayan usado todas las estructuras de entorno
			while(actualEntorno < getSolution().getNumEntornos()) {
				Solucion tmp = new Solucion(auxSolution);
				Solucion auxSolution2 = shake(actualEntorno, tmp);
				auxSolution2 = vnd(auxSolution2);
				// si se ha mejorado pues se actualiza auxSolution
				if(auxSolution2.getLatenciaTotal(getInitialInstance()) < auxSolution.getLatenciaTotal(getInitialInstance())) {
					auxSolution = auxSolution2;
				}
				// si no se ha mejorado se pasa a la siguiente estructura de entorno
				else
					actualEntorno++;
			}
			// si se ha mejorado se actualiza actualSolution
			if(auxSolution.getLatenciaTotal(getInitialInstance()) < actualSolution.getLatenciaTotal(getInitialInstance())) {
				actualSolution = auxSolution;
				numIteraciones = getN_ITERACIONES();
			}
			// si no se ha mejorado paramos el bucle
			else
				numIteraciones--;
			
		}
		return actualSolution;
	}

	public Solucion shake(int entorno, Solucion sol) {
		Random rnd = new Random();
		// Intramáquina
		if(entorno == 0 || entorno == 1) {
			Integer machine = rnd.nextInt(sol.getMachineList().size());
			
			while(sol.getMachineList().get(machine).getTareasRealizadas().size() <= 0)
				machine = rnd.nextInt(sol.getMachineList().size());

			
			Integer tarea1, tarea2;
			do{
				tarea1 = rnd.nextInt(sol.getMachineList().get(machine).getTareasRealizadas().size());
				tarea2 = rnd.nextInt(sol.getMachineList().get(machine).getTareasRealizadas().size());
				if(sol.getMachineList().get(machine).getTareasRealizadas().size() == 1) {
					tarea1 = 0;
					tarea2 = -1;
				}
			} while(tarea1 == tarea2);
			
			if(sol.getMachineList().get(machine).getTareasRealizadas().size() == 1) {
				tarea1 = 0;
				tarea2 = 0;
			}
			
			Maquina auxMachine = new Maquina(sol.getMachineList().get(machine));
			auxMachine.changePosItem(tarea1, tarea2);
			
			sol.getMachineList().set(machine, auxMachine);
		}
		// Entremáquina
		else if(entorno == 2 || entorno == 3) {
			if(sol.getMachineList().size() > 1) {
				Integer maquina1, maquina2;
				do {
					maquina1 = rnd.nextInt(sol.getMachineList().size());
					maquina2 = rnd.nextInt(sol.getMachineList().size());
				} while(maquina1 == maquina2
						|| sol.getMachineList().get(maquina1).getTareasRealizadas().size() <= 0
						|| sol.getMachineList().get(maquina2).getTareasRealizadas().size() <= 0);
				
				Integer tarea1 = rnd.nextInt(sol.getMachineList().get(maquina1).getTareasRealizadas().size());
				Integer tarea2 = rnd.nextInt(sol.getMachineList().get(maquina2).getTareasRealizadas().size());
				
				sol = sol.exchangeMachineItem(sol.getMachineList().get(maquina1).getTarea(tarea1).getID(), sol.getMachineList().get(maquina2).getTarea(tarea2).getID());
			}
		}
		// Reinserción
		else if(entorno == 4 || entorno == 5) {
			if(sol.getMachineList().size() > 1) {
				Integer machine = rnd.nextInt(sol.getMachineList().size());
				
				while(sol.getMachineList().get(machine).getTareasRealizadas().size() <= 0)
					machine = rnd.nextInt(sol.getMachineList().size());
				
				Integer tarea = rnd.nextInt(sol.getMachineList().get(machine).getTareasRealizadas().size());
				
				Integer maquinaDestino;
				do maquinaDestino = rnd.nextInt(sol.getMachineList().size());
				while(machine == maquinaDestino);
				
				Integer posicion = rnd.nextInt(sol.getMachineList().get(maquinaDestino).getTareasRealizadas().size() + 1);
				
				sol = sol.changeMachineItem(sol.getMachineList().get(machine).getTareasRealizadas().get(tarea).getID(), maquinaDestino, posicion);
			}
		}
		
		return sol;
	}
	
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