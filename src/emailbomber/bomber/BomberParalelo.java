package emailbomber.bomber;

public class BomberParalelo extends Bomber {

    public BomberParalelo(String urlAtaque, Email email, int numHilos, int totalPeticiones) {
        super(urlAtaque, email, numHilos, totalPeticiones);
    }
    
    @Override
    protected void inicializarAtaque() {
        for (BomberThread thread : this.bomberThreads) {
            thread.start();
        }
    }
}
