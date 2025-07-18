-- `reviews` 테이블 생성
CREATE TABLE `reviews`
(
    `review_id`   BIGINT    NOT NULL,
    `user_id`     BIGINT    NOT NULL,
    `training_id` BIGINT    NOT NULL,
    `rating`      INT       NULL,
    `content`     TEXT      NULL,
    `created_at`  TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`review_id`)
);

-- `routine_results` 테이블 생성
CREATE TABLE `routine_results`
(
    `routine_result_id` BIGINT            NOT NULL,
    `enrollment_id`     BIGINT            NOT NULL,
    `routine_id`        BIGINT            NOT NULL,
    `status`            BOOLEAN           NULL,
    `awared_score`      INT               NULL,
    `pass_fail_result`  ENUM ('성공', '실패') NULL,
    `answer_text`       VARCHAR(255)      NULL,
    `evidence_url`      VARCHAR(255)      NULL,
    `submitted_at`      TIMESTAMP         NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`routine_result_id`)
);

-- `trainer_certificates` 테이블 생성
CREATE TABLE `trainer_certificates`
(
    `trainer_certificate_id` BIGINT                                          NOT NULL,
    `trainer_id`             BIGINT                                          NOT NULL,
    `cert_type`              ENUM ('전산회계운용사', '회계관리', '재경관리사','전산세무회계','기타') NULL,
    `cert_number`            VARCHAR(50)                                     NULL,
    `created_at`             TIMESTAMP                                       NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`trainer_certificate_id`)
);

-- `RoutineVideos` 테이블 생성
CREATE TABLE `RoutineVideos`
(
    `routine_id`        BIGINT       NOT NULL,
    `routine_video_url` VARCHAR(255) NULL,
    PRIMARY KEY (`routine_id`)
);

-- `trainer_profiles` 테이블 생성
CREATE TABLE `trainer_profiles`
(
    `trainer_id`           BIGINT    NOT NULL,
    `career`               TEXT      NULL,
    `total_average_rating` FLOAT     NULL,
    `total_trainee_count`  INT       NULL,
    `created_at`           TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`           TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`trainer_id`)
);

-- `qnas` 테이블 생성
CREATE TABLE `qnas`
(
    `qna_id`      BIGINT    NOT NULL,
    `user_id`     BIGINT    NOT NULL,
    `training_id` BIGINT    NOT NULL,
    `trainer_id`  BIGINT    NOT NULL,
    `question`    TEXT      NULL,
    `answer`      TEXT      NULL,
    `created_at`  TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `answered_at` TIMESTAMP NULL,
    PRIMARY KEY (`qna_id`)
);

-- `routines` 테이블 생성
CREATE TABLE `routines`
(
    `routine_id`   BIGINT                               NOT NULL,
    `training_id`  BIGINT                               NOT NULL,
    `title`        VARCHAR(100)                         NULL,
    `description`  TEXT                                 NULL, -- 추가/수정된 컬럼
    `routine_type` ENUM ('VIDEO', 'QUIZ', 'TEXT')       NULL,
    `quiz_type`    ENUM ('OX', 'PHOTO', 'SHORT_ANSWER') NULL,
    `order_number` INT                                  NULL,
    `score`        INT                                  NULL,
    `created_at`   TIMESTAMP                            NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`   TIMESTAMP                            NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`routine_id`)
);

-- `roles` 테이블 생성
CREATE TABLE `roles`
(
    `role_id` BIGINT                               NOT NULL,
    `role`    ENUM ('TRAINEE', 'TRAINER', 'ADMIN') NOT NULL,
    PRIMARY KEY (`role_id`)
);

-- `trainings` 테이블 생성
CREATE TABLE `trainings`
(
    `training_id`     BIGINT                                                               NOT NULL,
    `trainer_id`      BIGINT                                                               NOT NULL,
    `title`           VARCHAR(100)                                                         NULL,
    `description`     TEXT                                                                 NULL,
    `price`           INT                                                                  NULL,
    `category`        ENUM ('재무 설계', '현금 관리', '신용과 부채 관리', '위험 관리와 보험 설계','투자 설계','세금 설계') NOT NULL,
    `level`           ENUM ('초급', '중급', '고급')                                              NOT NULL,
    `thumbnail_url`   VARCHAR(255)                                                         NULL,
    `total_score`     INT                                                                  NULL,
    `approval_status` ENUM ('대기', '승인', '거부')                                              NOT NULL DEFAULT '대기',
    `average_rating`  FLOAT                                                                NULL,
    `trainee_count`   INT                                                                  NULL,
    `created_at`      TIMESTAMP                                                            NULL     DEFAULT CURRENT_TIMESTAMP,
    `updated_at`      TIMESTAMP                                                            NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`training_id`)
);

-- `routine_answers` 테이블 생성
CREATE TABLE `routine_answers`
(
    `routine_id` BIGINT NOT NULL,
    `answer`     TEXT   NULL,
    PRIMARY KEY (`routine_id`)
);

-- `counselings` 테이블 생성
CREATE TABLE `counselings`
(
    `counseling_id` BIGINT                        NOT NULL,
    `user_id`       BIGINT                        NOT NULL,
    `trainer_id`    BIGINT                        NOT NULL,
    `training_id`   BIGINT                        NOT NULL,
    `status`        ENUM ('대기', '승인', '거절', '완료') NOT NULL DEFAULT '대기',
    `start_at`      TIMESTAMP                     NULL,
    `expires_at`    TIMESTAMP                     NULL,
    `message_count` INT                           NULL,
    PRIMARY KEY (`counseling_id`)
);

-- `user_fintech_auths` 테이블 생성
CREATE TABLE `user_fintech_auths`
(
    `Key`             BIGINT       NOT NULL,
    `fintech_use_num` VARCHAR(100) NULL,
    `bank_code`       VARCHAR(10)  NULL,
    `account_name`    VARCHAR(50)  NULL,
    `created_at`      TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`Key`)
);

-- `enrollments` 테이블 생성
CREATE TABLE `enrollments`
(
    `enrollment_id` BIGINT    NOT NULL,
    `user_id`       BIGINT    NOT NULL,
    `training_id`   BIGINT    NOT NULL,
    `progress`      FLOAT     NULL,
    `created_at`    TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `completed_at`  TIMESTAMP NULL,
    PRIMARY KEY (`enrollment_id`)
);

-- `portfolios` 테이블 생성
CREATE TABLE `portfolios`
(
    `portfolio_id`       BIGINT    NOT NULL,
    `user_id`            BIGINT    NOT NULL,
    `total_income`       BIGINT    NOT NULL DEFAULT 0,
    `total_expense`      BIGINT    NOT NULL DEFAULT 0,
    `avg_daily_spending` FLOAT     NOT NULL DEFAULT 0.0,
    `created_at`         TIMESTAMP NULL     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`portfolio_id`)
);

-- `admin_approval_logs` 테이블 생성
CREATE TABLE `admin_approval_logs`
(
    `admin_approval_log_id` BIGINT            NOT NULL,
    `admin_id`              BIGINT            NOT NULL,
    `training_id`           BIGINT            NOT NULL,
    `trainer_id`            BIGINT            NOT NULL,
    `status`                ENUM ('승인', '거절') NULL,
    `reason`                TEXT              NULL,
    `created_at`            DATETIME          NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`admin_approval_log_id`)
);

-- `users` 테이블 생성
CREATE TABLE `users`
(
    `user_id`          BIGINT                           NOT NULL,
    `email`            VARCHAR(100)                     NOT NULL,
    `password`         VARCHAR(255)                     NOT NULL,
    `username`         VARCHAR(30)                      NOT NULL,
    `login_type`       ENUM ('LOCAL', 'KAKAO', 'NAVER') NOT NULL,
    `user_profile_url` VARCHAR(100)                     NULL,
    `is_deleted`       BOOLEAN                          NULL     DEFAULT FALSE,
    `growth_score`     INT                              NOT NULL DEFAULT 0,
    `created_at`       TIMESTAMP                        NULL     DEFAULT CURRENT_TIMESTAMP,
    `updated_at`       TIMESTAMP                        NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`)
);

-- `user_roles` 테이블 생성
CREATE TABLE `user_roles`
(
    `FK1` BIGINT NOT NULL,
    `FK2` BIGINT NOT NULL,
    PRIMARY KEY (`FK1`, `FK2`)
);

-- 외래 키 제약 조건 추가
-- 기존에 제공된 외래 키
ALTER TABLE `RoutineVideos`
    ADD CONSTRAINT `FK_RoutineVideos_routines` FOREIGN KEY (`routine_id`)
        REFERENCES `routines` (`routine_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `trainer_profiles`
    ADD CONSTRAINT `FK_trainer_profiles_users` FOREIGN KEY (`trainer_id`)
        REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `routine_answers`
    ADD CONSTRAINT `FK_routine_answers_routines` FOREIGN KEY (`routine_id`)
        REFERENCES `routines` (`routine_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `user_fintech_auths`
    ADD CONSTRAINT `FK_user_fintech_auths_users` FOREIGN KEY (`Key`)
        REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `user_roles`
    ADD CONSTRAINT `FK_user_roles_users` FOREIGN KEY (`FK1`)
        REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `user_roles`
    ADD CONSTRAINT `FK_user_roles_roles` FOREIGN KEY (`FK2`)
        REFERENCES `roles` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE;

-- 논리적으로 필요하여 추가된 외래 키 (이전 답변에서 포함되었던 내용)
ALTER TABLE `counselings`
    ADD CONSTRAINT `FK_counselings_users` FOREIGN KEY (`user_id`)
        REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `counselings`
    ADD CONSTRAINT `FK_counselings_trainer_profiles` FOREIGN KEY (`trainer_id`)
        REFERENCES `trainer_profiles` (`trainer_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `counselings`
    ADD CONSTRAINT `FK_counselings_trainings` FOREIGN KEY (`training_id`)
        REFERENCES `trainings` (`training_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `reviews`
    ADD CONSTRAINT `FK_reviews_users` FOREIGN KEY (`user_id`)
        REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `reviews`
    ADD CONSTRAINT `FK_reviews_trainings` FOREIGN KEY (`training_id`)
        REFERENCES `trainings` (`training_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `enrollments`
    ADD CONSTRAINT `FK_enrollments_users` FOREIGN KEY (`user_id`)
        REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `enrollments`
    ADD CONSTRAINT `FK_enrollments_trainings` FOREIGN KEY (`training_id`)
        REFERENCES `trainings` (`training_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `routines`
    ADD CONSTRAINT `FK_routines_trainings` FOREIGN KEY (`training_id`)
        REFERENCES `trainings` (`training_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `trainings`
    ADD CONSTRAINT `FK_trainings_trainer_profiles` FOREIGN KEY (`trainer_id`)
        REFERENCES `trainer_profiles` (`trainer_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `routine_results`
    ADD CONSTRAINT `FK_routine_results_enrollments` FOREIGN KEY (`enrollment_id`)
        REFERENCES `enrollments` (`enrollment_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `routine_results`
    ADD CONSTRAINT `FK_routine_results_routines` FOREIGN KEY (`routine_id`)
        REFERENCES `routines` (`routine_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `portfolios`
    ADD CONSTRAINT `FK_portfolios_users` FOREIGN KEY (`user_id`)
        REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `qnas`
    ADD CONSTRAINT `FK_qnas_users` FOREIGN KEY (`user_id`)
        REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `qnas`
    ADD CONSTRAINT `FK_qnas_trainings` FOREIGN KEY (`training_id`)
        REFERENCES `trainings` (`training_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `qnas`
    ADD CONSTRAINT `FK_qnas_trainer_profiles` FOREIGN KEY (`trainer_id`)
        REFERENCES `trainer_profiles` (`trainer_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `trainer_certificates`
    ADD CONSTRAINT `FK_trainer_certificates_trainer_profiles` FOREIGN KEY (`trainer_id`)
        REFERENCES `trainer_profiles` (`trainer_id`) ON DELETE CASCADE ON UPDATE CASCADE;

-- `admin_approval_logs` 테이블의 `admin_id`에 대한 참조 테이블이 없으므로, 주석 처리 유지
-- ALTER TABLE `admin_approval_logs` ADD CONSTRAINT `FK_admin_approval_logs_admins` FOREIGN KEY (`admin_id`)
-- REFERENCES `admins` (`admin_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `admin_approval_logs`
    ADD CONSTRAINT `FK_admin_approval_logs_trainings` FOREIGN KEY (`training_id`)
        REFERENCES `trainings` (`training_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `admin_approval_logs`
    ADD CONSTRAINT `FK_admin_approval_logs_trainer_profiles` FOREIGN KEY (`trainer_id`)
        REFERENCES `trainer_profiles` (`trainer_id`) ON DELETE CASCADE ON UPDATE CASCADE;