package com.shrikanthravi.cinemaappconcept.model;

import com.github.captain_miao.seatview.BaseSeatMo;

/**
 * @author YanLu
 */
public class SeatMo extends BaseMo implements BaseSeatMo {

    /**
     * seat full name
     */
    public String seatName;
    /**
     * row Name
     */
	public String rowName;
    /**
     * row index
     */
	public int row;
    /**
     * column index
     */
	public int column;

    /**
     * seat status:1：available，0：sold，-1：unavailable
     */
	public int status;


    @Override
    public String getSeatName() {
        return seatName;
    }

    @Override
    public String getRowName() {
        return rowName;
    }

    @Override
    public boolean isSold() {
        return status == 0;
    }

    @Override
    public boolean isOnSale() {
        return status == 1;
    }

    @Override
    public boolean isSelected() {
        return status == 2;
    }

    public void setSold() {
        status = 0;
    }
    public void setOnSale() {
        status = 1;
    }

    public void setSelected() {
        status = 2;
    }

}
