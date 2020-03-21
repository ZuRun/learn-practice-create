package cn.zull.lpc.common.basis.exception;


import cn.zull.lpc.common.basis.constants.IMessage;

/**
 * @author zurun
 * @date 2018/5/12 12:10:21
 */
public class CipherException extends LpcRuntimeException {

    public CipherException(IMessage errCode) {
        super(errCode);
    }
}
