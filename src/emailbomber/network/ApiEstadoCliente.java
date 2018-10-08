package emailbomber.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class ApiEstadoCliente {

    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 2000;

    private long lastUpdateEstadoAtaque;
    private String urlServidor;
    private ObjectMapper mapper;
    private EstadoCliente estadoCliente;

    public ApiEstadoCliente(String urlServidor, boolean descargar) {
        this.urlServidor = urlServidor;
        this.mapper = new ObjectMapper();
        this.lastUpdateEstadoAtaque = System.currentTimeMillis();
        if (descargar) {
            refreshEstadoCliente();
        } else {
            resetEstadoCliente();
        }
    }

    public void setUrlServidor(String urlServidor) {
        this.urlServidor = urlServidor;
    }

    public EstadoCliente getEstadoCliente() {
        return estadoCliente;
    }

    public void resetEstadoCliente() {
        this.estadoCliente = new EstadoCliente();
        this.estadoCliente.setPeticionesAtaque(0);
        this.estadoCliente.setPeticionesHechas(0);
        this.estadoCliente.setPeticionesRechazadas(0);
        this.estadoCliente.setEstado(Estado.ESPERANDO);
        uploadEstadoCliente();
    }

    public void refreshEstadoCliente() {
        downloadEstadoCliente();
    }

    public void updateEstadoAtaque(int peticionesHechas, int peticionesRechazadas) {
        this.estadoCliente.setPeticionesHechas(peticionesHechas);
        this.estadoCliente.setPeticionesRechazadas(peticionesRechazadas);

        long currentTime = System.currentTimeMillis();
        long timeBetween = (this.lastUpdateEstadoAtaque - currentTime) * -1;
        if (timeBetween > MINIMUM_TIME_BETWEEN_UPDATES || ataqueTerminado()) {
            uploadEstadoCliente();
            this.lastUpdateEstadoAtaque = currentTime;
        }
    }

    private boolean ataqueTerminado() {
        return this.estadoCliente.getPeticionesAtaque() == this.estadoCliente.getPeticionesHechas();
    }

    public void setEstadoAtaqueTerminado() {
        this.estadoCliente.setEstado(Estado.ESPERANDO);
        uploadEstadoCliente();
    }

    public void setEstadoAtacando(int peticionesAtaque, boolean esRemoto) {
        this.estadoCliente.setPeticionesAtaque(peticionesAtaque);
        this.estadoCliente.setPeticionesHechas(0);
        this.estadoCliente.setPeticionesRechazadas(0);
        if (esRemoto) {
            this.estadoCliente.setEstado(Estado.ATAQUE_REMOTO);
        } else {
            this.estadoCliente.setEstado(Estado.ATAQUE_LOCAL);
        }
        uploadEstadoCliente();
    }

    private void uploadEstadoCliente() {
        try {
            String jsonPeticion = this.mapper.writeValueAsString(this.estadoCliente);

            HttpClient.postRequestJson(this.urlServidor, jsonPeticion);
        } catch (JsonProcessingException | HttpException ex) {
            this.estadoCliente.setEstado(Estado.OFFLINE);
        }
    }

    private void downloadEstadoCliente() {
        try {
            String json = HttpClient.getRequestJson(this.urlServidor);
            this.estadoCliente = this.mapper.readValue(json, EstadoCliente.class);
        } catch (IOException | HttpException ex) {
            this.estadoCliente = new EstadoCliente();
            this.estadoCliente.setEstado(Estado.OFFLINE);
        }
    }

}
