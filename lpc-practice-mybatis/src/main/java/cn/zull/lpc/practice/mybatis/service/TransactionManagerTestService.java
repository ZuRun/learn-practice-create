package cn.zull.lpc.practice.mybatis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author zurun
 * @date 2020/5/13 11:02:20
 */
@Slf4j
@Service
public class TransactionManagerTestService {

    private final TransactionTemplate transactionTemplate;
    private final PlatformTransactionManager transactionManager;

    public TransactionManagerTestService(TransactionTemplate transactionTemplate, PlatformTransactionManager transactionManager) {
        this.transactionTemplate = transactionTemplate;
        this.transactionManager = transactionManager;
    }

    public void transactionTemplateTest() {
        transactionTemplate.execute(status -> {
            status.flush();
            return null;
        });
    }

    public void transactionManagerTest() {
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {

            transactionManager.commit(transaction);
        } finally {
            transactionManager.rollback(transaction);
        }

    }
}
