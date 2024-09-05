package com.github.hji1235.coupon_system.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    BRAND_NOT_FOUND(400, "TEST1", "Can not find a Brand for id"),
    STORE_NOT_FOUND(400, "TEST2", "Can not find a Store for id"),
    MENU_NOT_FOUND(400, "TEST3", "Can not find a Menu for id"),
    MEMBER_NOT_FOUND(400, "TEST4", "Can not find a Member for id"),
    COUPON_NOT_FOUND(400, "TEST5", "Can not find a Coupon for id"),
    COUPON_MAX_COUNT_OVER(400, "TEST6", "Can not issue coupon. coupon maxCount : %d, allocatedCount : %d, requestCount : %d"),
    COUPON_MAX_COUNT_FOR_MEMBER_OVER(400, "TEST7", "Can not issue coupon. coupon maxCountPerMember : %d, currentCount : %d"),
    ORDER_NOT_FOUND(400, "TEST7", "Can not find a Order for id"),
    MEMBERCOUPON_NOT_FOUND(400, "TEST8", "Can not find a MemberCoupon for id"),
    PAYMENT_NOT_FOUND(400, "TEST9", "Can not find a Payment for id"),
    INVALID_MENU_ACCESS(400, "TEST10", "This Menu does not belong to the Store. storeId : %d, menuId : %d"),
    ALREADY_ALLOCATED_COUPON_CODE(400, "TEST11", "이미 할당된 쿠폰 코드입니다. couponCode : %s"),
    MENU_NOT_BELONG_TO_STORE(400, "TEST12", "해당 메뉴가 매장에 속하지 않습니다. menuId : %d, storeId : %d");


    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
