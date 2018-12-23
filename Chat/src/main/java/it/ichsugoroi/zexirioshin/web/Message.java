package it.ichsugoroi.zexirioshin.web;

public class Message {
    private String id;
    private String contenuto;
    private String mittente;
    private String destinatario;
    private String dataInvio;
    private String oraInvio;

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public String getMittente() {
        return mittente;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getDataInvio() {
        return dataInvio;
    }

    public void setDataInvio(String dataInvio) {
        this.dataInvio = dataInvio;
    }

    public String getOraInvio() {
        return oraInvio;
    }

    public void setOraInvio(String oraInvio) {
        this.oraInvio = oraInvio;
    }

    public String toString() {
        return  "id: " + getId() + "\n"
              + "contenuto: " + getContenuto() + "\n"
              + "mittente: " + getMittente() + "\n"
              + "destinatario: " + getDestinatario() + "\n"
              + "data_invio: " + getDataInvio() + "\n"
              + "ora_invio: " + getOraInvio() + "\n";
    }
}
