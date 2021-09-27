package ru.trofimov.warehouse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "item_position")
public class ItemPosition extends BaseEntity {

    private int row;

    private int place;

    private int amount;

    @Column(name = "goods_id")
    private Long goodsId;

    public ItemPosition() {
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "ItemPosition{" +
                "row=" + row +
                ", place=" + place +
                ", amount=" + amount +
                ", goodsId=" + goodsId +
                '}';
    }
}
