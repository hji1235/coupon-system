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
    MENU_NOT_BELONG_TO_STORE(400, "TEST12", "해당 메뉴가 매장에 속하지 않습니다. menuId : %d, storeId : %d"),
    ALREADY_USED_COUPON(400, "TEST13", "이미 사용된 쿠폰입니다. MemberCouponId : %d"),
    EXPIRED_COUPON(400, "TEST14", "이미 만료된 쿠폰입니다. expiredAt : %s"),
    COUPON_TIME_RESTRICTION(400, "TEST15", "해당 쿠폰을 사용할 수 없는 시간입니다. timeLimitStartAt : %s, timeLimitEndAt : %s"),
    COUPON_ISSUER_MISMATCH(400, " TEST16", "해당 쿠폰을 사용할 수 없는 매장입니다. currentStoreId: %d, memberCouponIssuerId: %d"),
    ORDER_PRICE_BELOW_MINIMUM(400, "TEST17", "주문 금액이 최소 주문 가격 미만입니다. currentOrderPrice : %d, couponMinOrderPrice : %d"),
    DUPLICATE_BRAND_NAME(400, "TEST18", "이미 존재하는 브랜드 이름입니다. brandName : %s"),
    DUPLICATE_STORE_NAME(400, "TEST19", "이미 존재하는 매장 이름입니다. storeName : %s");


    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
