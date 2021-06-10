CREATE TABLE `test_device_info` (
    `id` int NOT NULL,
    `did` varchar(255) NOT NULL COMMENT 'did',
    `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
    `sum` int NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ix_did` (`did`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;