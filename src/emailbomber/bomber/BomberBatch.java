package emailbomber.bomber;

public class BomberBatch extends Bomber {

    public BomberBatch(String urlAtaque, Email email, int numHilos, int totalPeticiones) {
        super(urlAtaque, email, numHilos, totalPeticiones);
    }
    
    @Override
    protected void inicializarAtaque() {
        new Thread(() -> {
            for (BomberThread thread : this.bomberThreads) {
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException ex) {
                    // ignore
                }
            }
        }).start();
    }
    
}
