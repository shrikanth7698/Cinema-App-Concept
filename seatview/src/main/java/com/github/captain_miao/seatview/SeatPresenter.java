package com.github.captain_miao.seatview;

/**
 * @author YanLu
 * @since 16/10/2
 */

public interface SeatPresenter {
    /**
        return true : invalidate view
               false: do nothing
     */
    boolean onClickSeat(int row, int column, BaseSeatMo seat);
}
