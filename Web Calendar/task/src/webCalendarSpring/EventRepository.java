package webCalendarSpring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    // https://stackoverflow.com/questions/30890076/spring-boot-show-sql-parameter-binding
    @Query(value = "SELECT ID, EVENT, DATE FROM EVENT WHERE DATE >= :fromDate AND DATE <= :toDate", nativeQuery = true)
    List<Event> getAllBetweenDates(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
}
