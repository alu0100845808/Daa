
import java.util.ArrayList;

public class Solucion {
	ArrayList<Maquina> maquinas;
	
	public Solucion(Integer nMaquinas) {
		maquinas = new ArrayList<Maquina>();
		for (int i = 0; i < nMaquinas; i++) {
			maquinas.add(new Maquina());
		}
	}
	
	public ArrayList<Maquina> getSolucion() {
	    return maquinas;
	}

    public Maquina getSolucionAt(Integer maquina) {
        return maquina.get(maquina);
    }
}