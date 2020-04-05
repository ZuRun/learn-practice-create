package cn.zull.lpc.common.mq.core.constants;


import cn.zull.lpc.common.basis.constants.IMessage;
import cn.zull.lpc.common.basis.exception.LpcRuntimeException;


/**
 * @author zurun
 * @date 2018/10/31 01:06:20
 */
public class MqException extends LpcRuntimeException {
    public MqException(String errMsg) {
        super(ErrorCode.mq.DEFAULT_MQ_PROVIDER_ERROR, errMsg);
    }

    public MqException(IMessage errCode) {
        super(errCode);
    }

    public MqException(IMessage errCode, String errMsg) {
        super(errCode, errMsg);
    }
}
