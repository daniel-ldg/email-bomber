package emailbomber.bomber;

import java.util.ArrayList;
import java.util.List;

public abstract class Bomber extends Thread {

    protected List<BomberThread> bomberThreads;
    protected List<BomberUpdateListener> updateListeners;
    private int totalPeticiones;
    private int peticionesHechas;
    private int peticionesRechazadas;

    public Bomber(String urlAtaque, Email email, int numHilos, int totalPeticiones) {
        this.bomberThreads = new ArrayList<>();
        this.updateListeners = new ArrayList<>();
        this.totalPeticiones = totalPeticiones;
        this.peticionesHechas = 0;
        this.peticionesRechazadas = 0;
        
        int[] cargaPorHilo = calcularCarga(totalPeticiones, numHilos);
        for (int carga : cargaPorHilo) {
            this.bomberThreads.add(new BomberThread(urlAtaque, email, carga));
        }
    }
    
    public boolean ataqueTerminado() {
        boolean terminado = true;
        for (BomberThread thread : this.bomberThreads) {
            if (!thread.ataqueTerminado()) {
                terminado = false;
                break;
            }
        }
        return terminado;
    }
    
    public void addUpdateListener(BomberUpdateListener updateListener) {
        this.updateListeners.add(updateListener);
    }
    
    public void abortar() {
        for (BomberThread thread : this.bomberThreads) {
            thread.cancelar();
        }
    }

    private int[] calcularCarga(int numPeticiones, int numHilos) {
        int[] arr = new int[numHilos];
        int remain = numPeticiones;
        int partsLeft = numHilos;
        for (int i = 0; partsLeft > 0; i++) {
            int size = (remain + partsLeft - 1) / partsLeft; // rounded up, aka ceiling
            arr[i] = size;
            remain -= size;
            partsLeft--;
        }
        return arr;
    }
    
    private void  calcularProgreso() {
        int peticiones = 0;
        int rechazadas = 0;
        
        for (BomberThread thread : this.bomberThreads) {
            peticiones += thread.getPeticionesHechas();
            rechazadas += thread.getPeticionesRechazadas();
        }
        
        this.peticionesHechas = peticiones;
        this.peticionesRechazadas = rechazadas;
        
    }
    
    private void triggerUpdate(int peticionesHechas, int peticionesRechazadas) {
        for (BomberUpdateListener updateListener : this.updateListeners) {
            updateListener.update(peticionesHechas, peticionesRechazadas);
        }
    }
    
    protected abstract void inicializarAtaque();

    @Override
    public void run() {
        triggerUpdate(0, 0);
        
        inicializarAtaque();
        
        while (!ataqueTerminado()) {
            calcularProgreso();
            triggerUpdate(this.peticionesHechas, this.peticionesRechazadas);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                // ignore
            }
        }
        
        calcularProgreso(); 
        triggerUpdate(this.peticionesHechas, this.peticionesRechazadas);
    }
}
