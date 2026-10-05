package com.group3.be.movie.theater.domain.schedule;

import com.group3.be.movie.theater.domain.show_dates.ShowDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByShowDate_ShowDateId(Long showDateId);
    void deleteByShowDate_ShowDateIdIn(List<Long> showDateIds);

    void deleteByShowDate_ShowDateId(Long showDateId);

    List<Schedule> findByShowDate_ShowDateIdIn(List<Long> showDateIds);

    Schedule findByScheduleId(Long scheduleId);

    Optional<Object> findByShowDate_ShowDateIdAndScheduleTime(Long showDateId, LocalTime scheduleTime);

    @Query(value = "SELECT s.schedule_id, s.show_date_id FROM movietheater_schedule s JOIN movietheater_show_dates sd ON s.show_date_id = sd.show_date_id WHERE sd.show_date = :sdate AND s.schedule_time = :time LIMIT 1", nativeQuery = true)
    List<Object[]> findScheduleAndDateId(@Param("sdate") java.sql.Date sdate, @Param("time") String time);
}
