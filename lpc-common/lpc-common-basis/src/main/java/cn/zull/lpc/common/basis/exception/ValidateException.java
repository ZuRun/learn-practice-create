package cn.zull.lpc.common.basis.exception;

import cn.zull.lpc.common.basis.constants.ErrorCode;
import cn.zull.lpc.common.basis.constants.IMessage;

/**
 * @author zurun
 * @date 2018/10/12 15:31:33
 */
public class ValidateException extends BusinessException {
    public ValidateException(String errMsg) {
        super(ErrorCode.ASSERT.ILLEGAL_ARGUMENT, errMsg);
    }

    public ValidateException(IMessage errCode) {
        super(errCode);
    }

    public ValidateException(IMessage errCode, String errMsg) {
        super(errCode, errMsg);
    }
}
