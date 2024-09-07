package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class DuplicateNicknameException extends BusinessException{
    public DuplicateNicknameException(String nickname) {
        super(ErrorCode.DUPLICATE_NICKNAME, nickname);
    }
}
