package emailbomber.network;

public class EstadoCliente {

    private int peticionesAtaque;
    private int peticionesHechas;
    private int peticionesRechazadas;
    private Estado estado;

    public int getPeticionesAtaque() {
        return peticionesAtaque;
    }

    public void setPeticionesAtaque(int peticionesAtaque) {
        this.peticionesAtaque = peticionesAtaque;
    }

    public int getPeticionesHechas() {
        return peticionesHechas;
    }

    public void setPeticionesHechas(int peticionesHechas) {
        this.peticionesHechas = peticionesHechas;
    }

    public int getPeticionesRechazadas() {
        return peticionesRechazadas;
    }

    public void setPeticionesRechazadas(int peticionesRechazadas) {
        this.peticionesRechazadas = peticionesRechazadas;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
