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
    DUPLICATE_STORE_NAME(400, "TEST19", "이미 존재하는 매장 이름입니다. storeName : %s"),
    DUPLICATE_MENU_NAME_IN_STORE(400, "TEST20", "이미 매장 내 존재하는 메뉴 이름입니다. menuName : %s"),
    DUPLICATE_EMAIL(400, "TEST21", "이미 존재하는 이메일입니다. email : %s"),
    DUPLICATE_NICKNAME(400, "TEST22", "이미 존재하는 닉네임입니다. nickname : %s"),
    PASSWORD_MISMATCH(400, "TEST23", "패스워드가 일치하지 않습니다."),
    DUPLICATE_STORE_COUPON_CONDITION(400, "TEST24", "동일한 할인 금액과 최소 주문 가격으로 매장 내 쿠폰을 발행할 수 없습니다. discountAmount : %d, minOrderPrice : %d"),
    INVALID_INPUT_VALUE(400, "TEST25", "입력값이 올바르지 않습니다."),
    INVALID_ORDER_STATUS(400, "TEST26", "주문의 상태가 올바르지 않아 변경할 수 없습니다. currentStatus : %s, targetStatus : %s"),
    INVALID_PAYMENT_STATUS(400, "TEST27", "결제의 상태가 올바르지 않아 변경할 수 없습니다. currentStatus : %s, targetStatus : %s");


    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
