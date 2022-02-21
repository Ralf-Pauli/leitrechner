package Database;

public class Auftrag {
    private String auftragsnr;
    private String produkt;
    private String menge;

    public Auftrag(String auftragsnr, String produkt, String menge) {
        this.auftragsnr = auftragsnr;
        this.produkt = produkt;
        this.menge = menge;
    }

    public String getAuftragsnr() {
        return auftragsnr;
    }

    public void setAuftragsnr(String auftragsnr) {
        this.auftragsnr = auftragsnr;
    }

    public String getProdukt() {
        return produkt;
    }

    public void setProdukt(String produkt) {
        this.produkt = produkt;
    }

    public String getMenge() {
        return menge;
    }

    public void setMenge(String menge) {
        this.menge = menge;
    }
}
