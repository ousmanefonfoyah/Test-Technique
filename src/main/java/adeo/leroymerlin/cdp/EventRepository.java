package adeo.leroymerlin.cdp;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * At this stage, to resolve the second issue, namely the delete does not work, I juste removed the readOnly in
 * the transactional annotation
 */
@Transactional
public interface EventRepository extends Repository<Event, Long> {

    void delete(Long eventId);

    List<Event> findAllBy();

    /**
     * For Resolving the first issue, I just added the query to allow the updating in the repository.
     * The @Modifying annotation is to allow a query that does not return a result set to work
     * @param id
     * @param nbStars
     * @param comment
     */
    @Modifying
    @Query(value ="UPDATE Event SET nb_stars= :nbStars, comment= :comment WHERE id= :id", nativeQuery = true)
    void update (@Param("id") Long id, @Param("nbStars") Integer nbStars,@Param("comment") String comment);
}
