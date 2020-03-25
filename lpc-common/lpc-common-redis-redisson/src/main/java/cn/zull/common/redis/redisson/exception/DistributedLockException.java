package cn.zull.common.redis.redisson.exception;


import cn.zull.lpc.common.basis.constants.ErrorCode;
import cn.zull.lpc.common.basis.constants.IMessage;
import cn.zull.lpc.common.basis.exception.BusinessException;

/**
 * 调用分布式锁异常
 *
 * @author zurun
 * @date 2018/4/2 15:17:03
 */
public class DistributedLockException extends BusinessException {

    public DistributedLockException(String errMsg) {
        super(ErrorCode.distributedLock.DEFAULT_LOCK_FAIL, errMsg);
    }

    public DistributedLockException(IMessage errCode) {
        super(errCode);
    }

    public DistributedLockException(IMessage errCode, String errMsg) {
        super(errCode, errMsg);
    }

    public DistributedLockException(Throwable cause, IMessage errCode) {
        super(cause, errCode);
    }
}