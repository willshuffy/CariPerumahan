
package cariperumahan.rini.com.cariperumahan.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Rumah implements Parcelable
{

    private String id;
    private String nama;
    private String harga;
    private String alamat;
    private String tipe;
    private String foto;
    private Object kontak_tlp;
    private String pondasi;
    private String dinding;
    private String sumber_air;
    private String listrik;
    private String lantai;
    private String rangka_atap;
    private String plafon;
    private String atap;
    private String latitude;
    private String longitude;
    public final static Parcelable.Creator<Rumah> CREATOR = new Creator<Rumah>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Rumah createFromParcel(Parcel in) {
            return new Rumah(in);
        }

        public Rumah[] newArray(int size) {
            return (new Rumah[size]);
        }

    }
            ;

    protected Rumah(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.nama = ((String) in.readValue((String.class.getClassLoader())));
        this.harga = ((String) in.readValue((String.class.getClassLoader())));
        this.alamat = ((String) in.readValue((String.class.getClassLoader())));
        this.tipe = ((String) in.readValue((String.class.getClassLoader())));
        this.foto = ((String) in.readValue((String.class.getClassLoader())));
        this.kontak_tlp = ((Object) in.readValue((Object.class.getClassLoader())));
        this.pondasi = ((String) in.readValue((String.class.getClassLoader())));
        this.dinding = ((String) in.readValue((String.class.getClassLoader())));
        this.sumber_air = ((String) in.readValue((String.class.getClassLoader())));
        this.listrik = ((String) in.readValue((String.class.getClassLoader())));
        this.lantai = ((String) in.readValue((String.class.getClassLoader())));
        this.rangka_atap = ((String) in.readValue((String.class.getClassLoader())));
        this.plafon = ((String) in.readValue((String.class.getClassLoader())));
        this.atap = ((String) in.readValue((String.class.getClassLoader())));
        this.latitude = ((String) in.readValue((String.class.getClassLoader())));
        this.longitude = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Rumah() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Object getKontak_tlp() {
        return kontak_tlp;
    }

    public void setKontak_tlp(Object kontak_tlp) {
        this.kontak_tlp = kontak_tlp;
    }

    public String getPondasi() {
        return pondasi;
    }

    public void setPondasi(String pondasi) {
        this.pondasi = pondasi;
    }

    public String getDinding() {
        return dinding;
    }

    public void setDinding(String dinding) {
        this.dinding = dinding;
    }

    public String getSumber_air() {
        return sumber_air;
    }

    public void setSumber_air(String sumber_air) {
        this.sumber_air = sumber_air;
    }

    public String getListrik() {
        return listrik;
    }

    public void setListrik(String listrik) {
        this.listrik = listrik;
    }

    public String getLantai() {
        return lantai;
    }

    public void setLantai(String lantai) {
        this.lantai = lantai;
    }

    public String getRangka_atap() {
        return rangka_atap;
    }

    public void setRangka_atap(String rangka_atap) {
        this.rangka_atap = rangka_atap;
    }

    public String getPlafon() {
        return plafon;
    }

    public void setPlafon(String plafon) {
        this.plafon = plafon;
    }

    public String getAtap() {
        return atap;
    }

    public void setAtap(String atap) {
        this.atap = atap;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(nama);
        dest.writeValue(harga);
        dest.writeValue(alamat);
        dest.writeValue(tipe);
        dest.writeValue(foto);
        dest.writeValue(kontak_tlp);
        dest.writeValue(pondasi);
        dest.writeValue(dinding);
        dest.writeValue(sumber_air);
        dest.writeValue(listrik);
        dest.writeValue(lantai);
        dest.writeValue(rangka_atap);
        dest.writeValue(plafon);
        dest.writeValue(atap);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
    }

    public int describeContents() {
        return 0;
    }

}
