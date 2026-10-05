package com.group3.be.movie.theater.domain.schedule_seat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleSeatRepository extends JpaRepository<ScheduleSeat, Long> {
    List<ScheduleSeat> findByCinemaRoomIdAndScheduleIdAndShowDateId(Long roomId, Long scheduleId, Long showDateId);
    boolean existsByCinemaRoomId(Long roomId);
    long countByScheduleId(Long scheduleId);

    @Query(value = "SELECT schedule_seat_id FROM movietheater_schedule_seat WHERE schedule_id = :schId AND seat_id = :stId LIMIT 1", nativeQuery = true)
    List<Long> checkExistSeatNative(@Param("schId") Long schId, @Param("stId") Long stId);
}
