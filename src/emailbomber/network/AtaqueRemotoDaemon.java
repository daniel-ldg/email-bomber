package emailbomber.network;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class AtaqueRemotoDaemon extends Thread {

    private AtaqueRemotoHandler ataqueRemotoHandler;
    private AtaqueRemoto ataqueRemoto;
    private String urlServidor;
    private ObjectMapper mapper;
    private boolean conectado;

    public AtaqueRemotoDaemon(AtaqueRemotoHandler ataqueRemotoHandler, String urlServidor) {
        this.ataqueRemotoHandler = ataqueRemotoHandler;
        this.urlServidor = urlServidor;
        this.mapper = new ObjectMapper();
        this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.conectado = false;
    }

    public AtaqueRemoto getAtaqueRemoto() {
        return ataqueRemoto;
    }

    public String getUrlServidor() {
        return urlServidor;
    }

    public void setUrlServidor(String urlServidor) {
        this.urlServidor = urlServidor;
        this.ataqueRemoto = null;
    }

    private AtaqueRemoto downloadAtaqueRemoto() throws HttpException, IOException {
        String json = HttpClient.getRequestJson(urlServidor);
        return this.mapper.readValue(json, AtaqueRemoto.class);
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (this.ataqueRemoto == null) {
                    this.ataqueRemoto = downloadAtaqueRemoto();
                } else {
                    AtaqueRemoto lastestAtaqueRemoto = downloadAtaqueRemoto();
                    if (!this.ataqueRemoto.equals(lastestAtaqueRemoto)) {
                        this.ataqueRemotoHandler.handleNuevoAtaque(lastestAtaqueRemoto);
                        this.ataqueRemoto = lastestAtaqueRemoto;
                    }
                }
                if (!conectado) {
                    this.conectado = true;
                    this.ataqueRemotoHandler.conexionEstablecida();
                }
            } catch (HttpException | IOException ex) {
                this.ataqueRemoto = null;
                if (this.conectado) {
                    this.conectado = false;
                    this.ataqueRemotoHandler.conexionPerdida();
                }
            } finally {
                try {
                    sleep(3000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
