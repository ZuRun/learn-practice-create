package cn.zull.lpc.common.mq.core.constants;

import cn.zull.lpc.common.basis.constants.IMessage;

/**
 * @author zurun
 * @date 2018/10/31 01:00:02
 */
public interface ErrorCode extends cn.zull.lpc.common.basis.constants.ErrorCode {
    enum mq implements IMessage {
        /**
         * MQ生产方默认异常
         */
        DEFAULT_MQ_PROVIDER_ERROR(840, "MQ生产方异常"),
        MQ_START_FAILD(841, "mq启动失败"),
        MQ_SEND_FAILD(842, "消费发送失败"),
        PRODUCER_GROUP_NOT_CONFIGURED(843, "生产组未配置"),
        ;
        private Integer errCode;
        private String errMsg;


        mq(Integer errCode, String errMsg) {
            this.errCode = errCode;
            this.errMsg = errMsg;
        }

        @Override
        public Integer getErrCode() {
            return this.errCode;
        }

        @Override
        public String getErrMsg() {
            return this.errMsg;
        }
    }
}
