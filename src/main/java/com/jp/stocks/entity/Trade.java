package com.jp.stocks.entity;

import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Trade {
    private final String symbol;
    private final LocalDateTime tradeTime;
    private final int quantity;
    private final TradeLeg tradeLeg;
    private final BigDecimal price;

    public Trade(String symbol, LocalDateTime tradeTime, int quantity, TradeLeg tradeLeg, BigDecimal price) {
        this.symbol = symbol;
        this.tradeTime = tradeTime;
        this.quantity = quantity;
        this.tradeLeg = tradeLeg;
        this.price = price;
    }


    public  String getSymbol() {
        return this.symbol;
    }

    public LocalDateTime getTradeTime() {
        return this.tradeTime;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public TradeLeg getTradeLeg() {
        return this.tradeLeg;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Trade)) return false;
        final Trade other = (Trade) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$symbol = this.getSymbol();
        final Object other$symbol = other.getSymbol();
        if (this$symbol == null ? other$symbol != null : !this$symbol.equals(other$symbol)) return false;
        final Object this$tradeTime = this.getTradeTime();
        final Object other$tradeTime = other.getTradeTime();
        if (this$tradeTime == null ? other$tradeTime != null : !this$tradeTime.equals(other$tradeTime)) return false;
        if (this.getQuantity() != other.getQuantity()) return false;
        final Object this$tradeLeg = this.getTradeLeg();
        final Object other$tradeLeg = other.getTradeLeg();
        if (this$tradeLeg == null ? other$tradeLeg != null : !this$tradeLeg.equals(other$tradeLeg)) return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Trade;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $symbol = this.getSymbol();
        result = result * PRIME + ($symbol == null ? 43 : $symbol.hashCode());
        final Object $tradeTime = this.getTradeTime();
        result = result * PRIME + ($tradeTime == null ? 43 : $tradeTime.hashCode());
        result = result * PRIME + this.getQuantity();
        final Object $tradeLeg = this.getTradeLeg();
        result = result * PRIME + ($tradeLeg == null ? 43 : $tradeLeg.hashCode());
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        return result;
    }

    public String toString() {
        return "Trade(symbol=" + this.getSymbol() + ", tradeTime=" + this.getTradeTime() + ", quantity=" + this.getQuantity() + ", tradeLeg=" + this.getTradeLeg() + ", price=" + this.getPrice() + ")";
    }
}
