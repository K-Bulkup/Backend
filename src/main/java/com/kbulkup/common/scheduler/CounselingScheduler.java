package com.kbulkup.common.scheduler;

import com.kbulkup.counseling.service.CounselingReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CounselingScheduler {

    private final CounselingReservationService counselingReservationService;

    // 매분 0초에 실행하여 예약 시작 시간이 된 예약들을 활성화
    @Scheduled(cron = "0 * * * * *")
    public void activateReservations() {
        try {
            counselingReservationService.activateReservations();
            log.debug("Reservation activation check completed");
        } catch (Exception e) {
            log.error("Error occurred while activating reservations", e);
        }
    }

    // 매분 0초에 실행하여 예약 종료 시간이 된 예약들을 완료 처리
    @Scheduled(cron = "0 * * * * *")
    public void completeReservations() {
        try {
            counselingReservationService.completeReservations();
            log.debug("Reservation completion check completed");
        } catch (Exception e) {
            log.error("Error occurred while completing reservations", e);
        }
    }
}
