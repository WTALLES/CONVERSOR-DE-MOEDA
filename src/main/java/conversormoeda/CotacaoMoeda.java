package conversormoeda;

import com.google.gson.annotations.SerializedName;

public class CotacaoMoeda {
    private String code;
    private String codein;
    private String name;
    private String high;
    private String low;
    private String varBid;
    private String pctChange;
    private String bid;
    private String ask;
    private String timestamp;
    
    @SerializedName("create_date")
    private String createDate;
    
    public CotacaoMoeda() {}
    
    public String getCode() { return code; }
    public String getCodein() { return codein; }
    public String getName() { return name; }
    public String getHigh() { return high; }
    public String getLow() { return low; }
    public String getVarBid() { return varBid; }
    public String getPctChange() { return pctChange; }
    public String getAsk() { return ask; }
    public String getTimestamp() { return timestamp; }
    public String getCreateDate() { return createDate; }
    
    public double getBid() {
        try {
            return Double.parseDouble(bid);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    
    public void setCode(String code) { this.code = code; }
    public void setCodein(String codein) { this.codein = codein; }
    public void setName(String name) { this.name = name; }
    public void setHigh(String high) { this.high = high; }
    public void setLow(String low) { this.low = low; }
    public void setVarBid(String varBid) { this.varBid = varBid; }
    public void setPctChange(String pctChange) { this.pctChange = pctChange; }
    public void setBid(String bid) { this.bid = bid; }
    public void setAsk(String ask) { this.ask = ask; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public void setCreateDate(String createDate) { this.createDate = createDate; }
    
    @Override
    public String toString() {
        return String.format("%s: %.4f (Variação: %s%%)", 
                           name, getBid(), pctChange);
    }
}
