package com.github.captain_miao.seatview;

import java.io.Serializable;

/**
 * @author YanLu
 */
public interface BaseSeatMo extends Serializable {


    /**
     * seat full name
     */
    String getSeatName();

    /**
     * row Name
     */
    String getRowName();

    /**
     * seat status:available
     */
    boolean isOnSale();

    /**
     * seat status:sold
     */
    boolean isSold();

    /**
     * seat status:selected
     */
    boolean isSelected();

}
