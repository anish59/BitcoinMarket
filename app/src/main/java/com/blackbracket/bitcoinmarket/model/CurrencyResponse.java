
package com.blackbracket.bitcoinmarket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyResponse {

    @SerializedName("USD")
    @Expose
    private USD uSD;

    @SerializedName("INR")
    @Expose
    private INR iNR;
    @SerializedName("JPY")
    @Expose
    private JPY jPY;
    @SerializedName("CNY")
    @Expose
    private CNY cNY;
    @SerializedName("SGD")
    @Expose
    private SGD sGD;
    @SerializedName("HKD")
    @Expose
    private HKD hKD;
    @SerializedName("CAD")
    @Expose
    private CAD cAD;
    @SerializedName("NZD")
    @Expose
    private NZD nZD;
    @SerializedName("AUD")
    @Expose
    private AUD aUD;
    @SerializedName("CLP")
    @Expose
    private CLP cLP;
    @SerializedName("GBP")
    @Expose
    private GBP gBP;
    @SerializedName("DKK")
    @Expose
    private DKK dKK;
    @SerializedName("SEK")
    @Expose
    private SEK sEK;
    @SerializedName("ISK")
    @Expose
    private ISK iSK;
    @SerializedName("CHF")
    @Expose
    private CHF cHF;
    @SerializedName("BRL")
    @Expose
    private BRL bRL;
    @SerializedName("EUR")
    @Expose
    private EUR eUR;
    @SerializedName("RUB")
    @Expose
    private RUB rUB;
    @SerializedName("PLN")
    @Expose
    private PLN pLN;
    @SerializedName("THB")
    @Expose
    private THB tHB;
    @SerializedName("KRW")
    @Expose
    private KRW kRW;
    @SerializedName("TWD")
    @Expose
    private TWD tWD;

    public USD getUSD() {
        return uSD;
    }

    public void setUSD(USD uSD) {
        this.uSD = uSD;
    }

    public JPY getJPY() {
        return jPY;
    }

    public void setJPY(JPY jPY) {
        this.jPY = jPY;
    }

    public CNY getCNY() {
        return cNY;
    }

    public void setCNY(CNY cNY) {
        this.cNY = cNY;
    }

    public SGD getSGD() {
        return sGD;
    }

    public void setSGD(SGD sGD) {
        this.sGD = sGD;
    }

    public HKD getHKD() {
        return hKD;
    }

    public void setHKD(HKD hKD) {
        this.hKD = hKD;
    }

    public CAD getCAD() {
        return cAD;
    }

    public void setCAD(CAD cAD) {
        this.cAD = cAD;
    }

    public NZD getNZD() {
        return nZD;
    }

    public void setNZD(NZD nZD) {
        this.nZD = nZD;
    }

    public AUD getAUD() {
        return aUD;
    }

    public void setAUD(AUD aUD) {
        this.aUD = aUD;
    }

    public CLP getCLP() {
        return cLP;
    }

    public void setCLP(CLP cLP) {
        this.cLP = cLP;
    }

    public GBP getGBP() {
        return gBP;
    }

    public void setGBP(GBP gBP) {
        this.gBP = gBP;
    }

    public DKK getDKK() {
        return dKK;
    }

    public void setDKK(DKK dKK) {
        this.dKK = dKK;
    }

    public SEK getSEK() {
        return sEK;
    }

    public void setSEK(SEK sEK) {
        this.sEK = sEK;
    }

    public ISK getISK() {
        return iSK;
    }

    public void setISK(ISK iSK) {
        this.iSK = iSK;
    }

    public CHF getCHF() {
        return cHF;
    }

    public void setCHF(CHF cHF) {
        this.cHF = cHF;
    }

    public BRL getBRL() {
        return bRL;
    }

    public void setBRL(BRL bRL) {
        this.bRL = bRL;
    }

    public EUR getEUR() {
        return eUR;
    }

    public void setEUR(EUR eUR) {
        this.eUR = eUR;
    }

    public RUB getRUB() {
        return rUB;
    }

    public void setRUB(RUB rUB) {
        this.rUB = rUB;
    }

    public PLN getPLN() {
        return pLN;
    }

    public void setPLN(PLN pLN) {
        this.pLN = pLN;
    }

    public THB getTHB() {
        return tHB;
    }

    public void setTHB(THB tHB) {
        this.tHB = tHB;
    }

    public KRW getKRW() {
        return kRW;
    }

    public void setKRW(KRW kRW) {
        this.kRW = kRW;
    }

    public TWD getTWD() {
        return tWD;
    }

    public void setTWD(TWD tWD) {
        this.tWD = tWD;
    }

    public INR getiNR() {
        return iNR;
    }

    public void setiNR(INR iNR) {
        this.iNR = iNR;
    }
}
