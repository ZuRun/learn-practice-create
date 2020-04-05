package cn.zull.lpc.common.basis.utils;

import cn.zull.lpc.common.basis.constants.ErrorCode;
import cn.zull.lpc.common.basis.constants.IMessage;
import cn.zull.lpc.common.basis.exception.AssertException;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * @author ZuRun
 * @date 2020/4/5 11:51:46
 */
@Slf4j
public class Assert {
    public static void notEmpty(Object object, FImessage<IMessage> errorMsg) {
        notEmpty(object, errorMsg, () -> null);
    }

    public static void notEmpty(Object object, IMessage errorMsg) {
        notEmpty(object, () -> errorMsg, errorMsg::getErrMsg);
    }

    public static void notEmpty(Object object, Supplier<String> messageSupplier) {
        notEmpty(object, () -> ErrorCode.ASSERT.NOTEMPTY, messageSupplier);
    }

    public static void notEmpty(Object object, String message) {
        notEmpty(object, () -> message);
    }

    public static void notEmpty(Object object) {
        notEmpty(object, ErrorCode.ASSERT.NOTEMPTY);
    }

    public static void notEmpty(Object object, FImessage<IMessage> iMessageFImessage, Supplier<String> messageSupplier) {

        if (object == null) {
            assertThrow(iMessageFImessage, messageSupplier);
        }
        if (object instanceof String) {
            String str = (String) object;
            if (StringUtils.isEmpty(str)) {
                assertThrow(iMessageFImessage, messageSupplier);
            }
            return;
        }
        //todo 其他类型 自行添加

    }




    private static void assertThrow(FImessage<IMessage> iMessageFImessage, Supplier<String> messageSupplier) {
        IMessage iMessage = iMessageFImessage.get();
        if (iMessage == null) {
            iMessage = ErrorCode.ASSERT.NOTEMPTY;
        }
        String message = messageSupplier.get();
        if (StringUtils.isEmpty(message)) {
            message = iMessage.getErrMsg();
        }
        log.warn("[断言失败] {}", message);
        throw new AssertException(iMessage, message);
    }

    @FunctionalInterface
    public interface FImessage<T> {
        T get();
    }
}
