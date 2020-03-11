package com.jp.stocks.entity;

import lombok.NonNull;

public class Stock extends Instrument {
    private final String ticker;
    private final StockType stockType;
    private final double lastDividend;
    private final double fixedDividend;
    private final int parValue;

    public Stock(String ticker, StockType stockType, double lastDividend, double fixedDividend, int parValue) {
        this.ticker = ticker;
        this.stockType = stockType;
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
    }

    public String getTicker() {
        return this.ticker;
    }

    public StockType getStockType() {
        return this.stockType;
    }

    public double getLastDividend() {
        return this.lastDividend;
    }

    public double getFixedDividend() {
        return this.fixedDividend;
    }

    public int getParValue() {
        return this.parValue;
    }

    public String toString() {
        return "Stock(ticker=" + this.getTicker() + ", stockType=" + this.getStockType() + ", lastDividend=" + this.getLastDividend() + ", fixedDividend=" + this.getFixedDividend() + ", parValue=" + this.getParValue() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Stock)) return false;
        final Stock other = (Stock) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$ticker = this.getTicker();
        final Object other$ticker = other.getTicker();
        if (this$ticker == null ? other$ticker != null : !this$ticker.equals(other$ticker)) return false;
        final Object this$stockType = this.getStockType();
        final Object other$stockType = other.getStockType();
        if (this$stockType == null ? other$stockType != null : !this$stockType.equals(other$stockType)) return false;
        if (Double.compare(this.getLastDividend(), other.getLastDividend()) != 0) return false;
        if (Double.compare(this.getFixedDividend(), other.getFixedDividend()) != 0) return false;
        if (this.getParValue() != other.getParValue()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Stock;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $ticker = this.getTicker();
        result = result * PRIME + ($ticker == null ? 43 : $ticker.hashCode());
        final Object $stockType = this.getStockType();
        result = result * PRIME + ($stockType == null ? 43 : $stockType.hashCode());
        final long $lastDividend = Double.doubleToLongBits(this.getLastDividend());
        result = result * PRIME + (int) ($lastDividend >>> 32 ^ $lastDividend);
        final long $fixedDividend = Double.doubleToLongBits(this.getFixedDividend());
        result = result * PRIME + (int) ($fixedDividend >>> 32 ^ $fixedDividend);
        result = result * PRIME + this.getParValue();
        return result;
    }
}
