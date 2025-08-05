DROP DATABASE IF EXISTS kbulkupdb;
CREATE DATABASE kbulkupdb;
USE kbulkupdb;

-- users
CREATE TABLE `users`
(
    user_id          BIGINT                           NOT NULL AUTO_INCREMENT,
    email            VARCHAR(100)                     NOT NULL,
    password         VARCHAR(255),
    username         VARCHAR(30)                      NOT NULL,
    birthdate        DATE,
    login_type       ENUM ('LOCAL', 'KAKAO', 'NAVER') NOT NULL,
    provider_id      VARCHAR(255),
    user_profile_url VARCHAR(100),
    is_deleted       BOOLEAN                                   DEFAULT FALSE,
    growth_score     INT                              NOT NULL DEFAULT 0,
    created_at       TIMESTAMP                                 DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP                                 DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id),
    UNIQUE (email),
    UNIQUE (provider_id)
);

-- roles
CREATE TABLE `roles`
(
    role_id BIGINT                               NOT NULL AUTO_INCREMENT,
    role    ENUM ('TRAINEE', 'TRAINER', 'ADMIN') NOT NULL UNIQUE,
    PRIMARY KEY (role_id)
);
INSERT INTO roles (role)
VALUES ('TRAINEE');
INSERT INTO roles (role)
VALUES ('TRAINER');
INSERT INTO roles (role)
VALUES ('ADMIN');

-- user_roles
CREATE TABLE `user_roles`
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (role_id) ON DELETE RESTRICT
);

-- trainer_profiles
CREATE TABLE `trainer_profiles`
(
    `trainer_id`           BIGINT    NOT NULL AUTO_INCREMENT,
    `career`               TEXT           DEFAULT NULL,
    `total_average_rating` FLOAT     NULL DEFAULT 0,
    `total_trainee_count`  INT       NULL DEFAULT 0,
    `created_at`           TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`           TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`trainer_id`)
);

-- trainer_certificates
CREATE TABLE `trainer_certificates`
(
    `trainer_certificate_id` BIGINT                                          NOT NULL AUTO_INCREMENT,
    `trainer_id`             BIGINT                                          NOT NULL,
    `cert_type`              ENUM ('전산회계운용사', '회계관리', '재경관리사','전산세무회계','기타') NULL,
    `cert_number`            VARCHAR(50)                                     NULL,
    `created_at`             TIMESTAMP                                       NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`trainer_certificate_id`)
);

-- trainings
CREATE TABLE `trainings`
(
    `training_id`     BIGINT                                                               NOT NULL AUTO_INCREMENT,
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

-- routines
CREATE TABLE `routines`
(
    `routine_id`   BIGINT                               NOT NULL AUTO_INCREMENT,
    `training_id`  BIGINT                               NOT NULL,
    `title`        VARCHAR(100)                         NULL,
    `description`  TEXT                                 NULL,
    `routine_type` ENUM ('VIDEO', 'QUIZ', 'TEXT')       NULL,
    `quiz_type`    ENUM ('OX', 'PHOTO', 'SHORT_ANSWER') NULL,
    `order_number` INT                                  NULL,
    `score`        INT                                  NULL,
    `created_at`   TIMESTAMP                            NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`   TIMESTAMP                            NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`routine_id`)
);

-- RoutineVideos
CREATE TABLE `routine_videos`
(
    `routine_id`        BIGINT       NOT NULL,
    `routine_video_url` VARCHAR(255) NULL,
    PRIMARY KEY (`routine_id`)
);

-- routine_answers
CREATE TABLE `routine_answers`
(
    `routine_id` BIGINT NOT NULL,
    `answer`     TEXT   NULL,
    PRIMARY KEY (`routine_id`)
);

-- enrollments
CREATE TABLE `enrollments`
(
    `enrollment_id` BIGINT    NOT NULL AUTO_INCREMENT,
    `user_id`       BIGINT    NOT NULL,
    `training_id`   BIGINT    NOT NULL,
    `progress`      FLOAT     NULL,
    `created_at`    TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `completed_at`  TIMESTAMP NULL,
    PRIMARY KEY (`enrollment_id`)
);

-- routine_results
CREATE TABLE `routine_results`
(
    `routine_result_id` BIGINT            NOT NULL AUTO_INCREMENT,
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

-- reviews
CREATE TABLE `reviews`
(
    `review_id`   BIGINT    NOT NULL AUTO_INCREMENT,
    `user_id`     BIGINT    NOT NULL,
    `training_id` BIGINT    NOT NULL,
    `rating`      INT       NULL,
    `content`     TEXT      NULL,
    `created_at`  TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`review_id`)
);

-- qnas
CREATE TABLE `qnas`
(
    `qna_id`      BIGINT    NOT NULL AUTO_INCREMENT,
    `user_id`     BIGINT    NOT NULL,
    `training_id` BIGINT    NOT NULL,
    `trainer_id`  BIGINT    NOT NULL,
    `question`    TEXT      NULL,
    `answer`      TEXT      NULL,
    `created_at`  TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `answered_at` TIMESTAMP NULL,
    PRIMARY KEY (`qna_id`)
);

-- portfolios
CREATE TABLE portfolios
(
    user_id BIGINT PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

-- transactions
CREATE TABLE transactions
(
    transaction_id         BIGINT PRIMARY KEY,
    user_id                BIGINT            NOT NULL,
    transaction_type       ENUM ('입금', '출금') NOT NULL,
    amount                 BIGINT            NOT NULL,
    `transaction_category` ENUM (
        '식비', '교통비', '주거/공과금', '생필품',
        '의료/건강', '패션/미용', '문화생활/여가', '기타',
        '월급', '부수입'
        )                                    NULL,
    tran_date              DATETIME          NOT NULL,
    FOREIGN KEY (user_id) REFERENCES portfolios (user_id)
);

-- snapshots
CREATE TABLE snapshots
(
    snapshot_id   BIGINT PRIMARY KEY,
    user_id       BIGINT NOT NULL,
    balance       BIGINT NOT NULL,
    snapshot_date DATE   NOT NULL,
    FOREIGN KEY (user_id) REFERENCES portfolios (user_id)
);

-- compositions
CREATE TABLE compositions
(
    composition_id    BIGINT PRIMARY KEY,
    user_id           BIGINT NOT NULL,
    asset_composition JSON   NOT NULL,
    FOREIGN KEY (user_id) REFERENCES portfolios (user_id)
);

-- counselings
CREATE TABLE `counselings`
(
    `counseling_id`  BIGINT             NOT NULL AUTO_INCREMENT,
    `user_id`        BIGINT             NOT NULL,
    `trainer_id`     BIGINT             NOT NULL,
    `training_id`    BIGINT             NOT NULL,
    `room_id`        VARCHAR(100)       NOT NULL UNIQUE,
    `status`         ENUM ('진행중', '만료') NOT NULL DEFAULT '진행중',
    `latest_message` TEXT,
    `latest_at`      DATETIME,
    `start_at`       TIMESTAMP          NULL,
    `expires_at`     TIMESTAMP          NULL,
    `message_count`  INT                NULL,
    PRIMARY KEY (`counseling_id`)
);

-- user_fintech_auths (수정된 부분)
CREATE TABLE `user_fintech_auths`
(
    `user_id`         BIGINT       NOT NULL,
    `fintech_use_num` VARCHAR(100) NULL,
    `bank_code`       VARCHAR(10)  NULL,
    `created_at`      TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`)
);

-- admin_approval_logs
CREATE TABLE `admin_approval_logs`
(
    `admin_approval_log_id` BIGINT            NOT NULL AUTO_INCREMENT,
    `admin_id`              BIGINT            NOT NULL,
    `training_id`           BIGINT            NOT NULL,
    `trainer_id`            BIGINT            NOT NULL,
    `status`                ENUM ('승인', '거절') NULL,
    `reason`                TEXT              NULL,
    `created_at`            DATETIME          NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`admin_approval_log_id`)
);


-- 외래 키 제약 조건
ALTER TABLE `routine_videos`
    ADD CONSTRAINT `FK_RoutineVideos_routines` FOREIGN KEY (`routine_id`) REFERENCES `routines` (`routine_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `trainer_profiles`
    ADD CONSTRAINT `FK_trainer_profiles_users` FOREIGN KEY (`trainer_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `routine_answers`
    ADD CONSTRAINT `FK_routine_answers_routines` FOREIGN KEY (`routine_id`) REFERENCES `routines` (`routine_id`) ON DELETE CASCADE ON UPDATE CASCADE;

-- user_fintech_auths 외래 키 (수정된 올바른 구문만 남김)
ALTER TABLE `user_fintech_auths`
    ADD CONSTRAINT `FK_user_fintech_auths_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `user_roles`
    ADD CONSTRAINT `FK_user_roles_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `FK_user_roles_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `counselings`
    ADD CONSTRAINT `FK_counselings_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `FK_counselings_trainer_profiles` FOREIGN KEY (`trainer_id`) REFERENCES `trainer_profiles` (`trainer_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `FK_counselings_trainings` FOREIGN KEY (`training_id`) REFERENCES `trainings` (`training_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `reviews`
    ADD CONSTRAINT `FK_reviews_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `FK_reviews_trainings` FOREIGN KEY (`training_id`) REFERENCES `trainings` (`training_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `enrollments`
    ADD CONSTRAINT `FK_enrollments_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `FK_enrollments_trainings` FOREIGN KEY (`training_id`) REFERENCES `trainings` (`training_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `routines`
    ADD CONSTRAINT `FK_routines_trainings` FOREIGN KEY (`training_id`) REFERENCES `trainings` (`training_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `trainings`
    ADD CONSTRAINT `FK_trainings_trainer_profiles` FOREIGN KEY (`trainer_id`) REFERENCES `trainer_profiles` (`trainer_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `routine_results`
    ADD CONSTRAINT `FK_routine_results_enrollments` FOREIGN KEY (`enrollment_id`) REFERENCES `enrollments` (`enrollment_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `FK_routine_results_routines` FOREIGN KEY (`routine_id`) REFERENCES `routines` (`routine_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `qnas`
    ADD CONSTRAINT `FK_qnas_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `FK_qnas_trainings` FOREIGN KEY (`training_id`) REFERENCES `trainings` (`training_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `FK_qnas_trainer_profiles` FOREIGN KEY (`trainer_id`) REFERENCES `trainer_profiles` (`trainer_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `trainer_certificates`
    ADD CONSTRAINT `FK_trainer_certificates_trainer_profiles` FOREIGN KEY (`trainer_id`) REFERENCES `trainer_profiles` (`trainer_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `admin_approval_logs`
    ADD CONSTRAINT `FK_admin_approval_logs_trainings` FOREIGN KEY (`training_id`) REFERENCES `trainings` (`training_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `FK_admin_approval_logs_trainer_profiles` FOREIGN KEY (`trainer_id`) REFERENCES `trainer_profiles` (`trainer_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE portfolios
    ADD CONSTRAINT fk_portfolios_user
        FOREIGN KEY (user_id) REFERENCES users (user_id)
            ON DELETE CASCADE;

-- 자산 구성
ALTER TABLE compositions
    ADD CONSTRAINT fk_compositions_portfolio
        FOREIGN KEY (user_id) REFERENCES portfolios (user_id)
            ON DELETE CASCADE;

-- 자산 추이
ALTER TABLE snapshots
    ADD CONSTRAINT fk_snapshots_portfolio
        FOREIGN KEY (user_id) REFERENCES portfolios (user_id)
            ON DELETE CASCADE;

-- 거래 내역
ALTER TABLE transactions
    ADD CONSTRAINT fk_transactions_portfolio
        FOREIGN KEY (user_id) REFERENCES portfolios (user_id)
            ON DELETE CASCADE;

-- trainer_certificates certType 변경
ALTER TABLE trainer_certificates
    MODIFY cert_type ENUM ('투자자산운용사', '금융투자분석사', '재무위험관리사', '투자권유자문인력', '투자권유대행인') NULL;

-- admin_approval_logs 의 admin_id 외래키 생략 주석 유지
-- ALTER TABLE admin_approval_logs ADD CONSTRAINT FK_admin_approval_logs_admins FOREIGN KEY (admin_id)
-- REFERENCES admins (admin_id) ON DELETE CASCADE ON UPDATE CASCADE;

-- 이미지 url 문자 길이 수정
ALTER TABLE `users`
    MODIFY COLUMN `user_profile_url` VARCHAR(255);

-- admin 계정 생성
-- 1. 'admin@gmail.com' 사용자를 'users' 테이블에 추가합니다.
-- '여기에_암호화된_비밀번호_붙여넣기' 부분에 테스트 실행 후 콘솔에 출력된 값을 넣어주세요.
INSERT INTO users (email, password, username, login_type)
VALUES ('admin@gmail.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', '관리자', 'LOCAL');

-- 2. 방금 추가한 사용자의 user_id를 찾습니다.
SET @last_user_id = LAST_INSERT_ID();

-- 3. 'ADMIN' 역할의 role_id를 찾습니다. (roles 테이블에 'ADMIN'이 이미 있다고 가정)
SET @admin_role_id = (SELECT role_id
                      FROM roles
                      WHERE role = 'ADMIN');

-- 4. user_roles 테이블에 사용자 ID와 역할 ID를 매핑하여 관리자 권한을 부여합니다.
INSERT INTO user_roles (user_id, role_id)
VALUES (@last_user_id, @admin_role_id);


-- Dummy User Data (100 records)
INSERT INTO users (username, email, password, created_at, updated_at) VALUES
('user_001', 'user_001@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_002', 'user_002@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_003', 'user_003@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_004', 'user_004@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_005', 'user_005@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_006', 'user_006@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_007', 'user_007@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_008', 'user_008@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_009', 'user_009@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_010', 'user_010@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_011', 'user_011@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_012', 'user_012@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_013', 'user_013@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_014', 'user_014@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_015', 'user_015@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_016', 'user_016@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_017', 'user_017@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_018', 'user_018@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_019', 'user_019@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW()),
('user_020', 'user_020@example.com', '{bcrypt}$2a$10$kzwNFYFDMNwkRv/8033bN.54k5SPfZk7F8lpUtEVXw.okRTgb2TJK', NOW(), NOW());

select * from users;