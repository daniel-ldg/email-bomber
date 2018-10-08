package emailbomber.network;

import java.util.Objects;

public class AtaqueRemoto {
    
    private int id;
    private String urlServidorAtacado;
    private int numHilos;
    private int numIteraciones;
    private boolean enParalelo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlServidorAtacado() {
        return urlServidorAtacado;
    }

    public void setUrlServidorAtacado(String urlServidorAtacado) {
        this.urlServidorAtacado = urlServidorAtacado;
    }

    public int getNumHilos() {
        return numHilos;
    }

    public void setNumHilos(int numHilos) {
        this.numHilos = numHilos;
    }

    public int getNumIteraciones() {
        return numIteraciones;
    }

    public void setNumIteraciones(int numIteraciones) {
        this.numIteraciones = numIteraciones;
    }

    public boolean isEnParalelo() {
        return enParalelo;
    }

    public void setEnParalelo(boolean enParalelo) {
        this.enParalelo = enParalelo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AtaqueRemoto other = (AtaqueRemoto) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.numHilos != other.numHilos) {
            return false;
        }
        if (this.numIteraciones != other.numIteraciones) {
            return false;
        }
        if (this.enParalelo != other.enParalelo) {
            return false;
        }
        if (!Objects.equals(this.urlServidorAtacado, other.urlServidorAtacado)) {
            return false;
        }
        return true;
    }
}
