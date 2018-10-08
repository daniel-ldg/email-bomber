package emailbomber.network;

public interface AtaqueRemotoHandler {
    
    void handleNuevoAtaque(AtaqueRemoto ataqueRemoto);
    
    void conexionEstablecida();
    
    void conexionPerdida();
    
    void abortarAtaqueRemoto();
}
