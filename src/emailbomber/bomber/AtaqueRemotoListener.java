package emailbomber.bomber;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import emailbomber.network.HttpClient;
import emailbomber.network.HttpException;
import java.io.IOException;

public class AtaqueRemotoListener extends Thread {

    private AtaqueRemoto ataqueRemoto;
    private boolean nuevoAtaque;
    private String UrlServidorLocal;
    private ObjectMapper mapper;
    private boolean conectado;

    public AtaqueRemotoListener(String UrlServidorLocal) {
        this.UrlServidorLocal = UrlServidorLocal;
        this.mapper = new ObjectMapper();
        this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.nuevoAtaque = false;
        this.conectado = false;
    }

    public AtaqueRemoto getAtaqueRemoto() {
        return ataqueRemoto;
    }

    public String getUrlServidorLocal() {
        return UrlServidorLocal;
    }

    public boolean isConectado() {
        return conectado;
    }

    public boolean isNuevoAtaque() {
        if (nuevoAtaque) {
            this.nuevoAtaque = false;
            return true;
        } else {
            return false;
        }
    }

    private AtaqueRemoto downloadAtaqueRemoto() throws HttpException, IOException {
        String json = HttpClient.getRequestJson(UrlServidorLocal);
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
                        this.ataqueRemoto = lastestAtaqueRemoto;
                        this.nuevoAtaque = true;
                    }
                }
                this.conectado = true;
            } catch (HttpException | IOException ex) {
                this.conectado = false;
                this.ataqueRemoto = null;
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
