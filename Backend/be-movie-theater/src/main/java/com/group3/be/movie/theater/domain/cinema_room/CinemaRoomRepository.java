package com.group3.be.movie.theater.domain.cinema_room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CinemaRoomRepository extends JpaRepository<CinemaRoom, Long> {

    boolean existsByCinemaRoomNameAndActiveTrue(String roomName);

    boolean existsByCinemaRoomIdAndActiveTrue(Long roomId);

    boolean existsByCinemaRoomIdNotAndCinemaRoomNameAndActiveTrue(Long roomId, String cinemaRoomName);

    Optional<CinemaRoom> findByCinemaRoomIdAndActiveTrue(Long roomId);

    List<CinemaRoom> findByActiveTrue();
    
    @Query(value = "SELECT cinema_room_id FROM movietheater_cinema_room WHERE cinema_room_name = :roomName LIMIT 1", nativeQuery = true)
    List<Long> findRoomIdByName(@Param("roomName") String roomName);
}
