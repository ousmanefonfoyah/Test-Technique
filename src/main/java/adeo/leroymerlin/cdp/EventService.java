package adeo.leroymerlin.cdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    public void delete(Long id) {
        eventRepository.delete(id);
    }

    /**
     * To test this method, I was not able to link directly through the IHM.
     * So you will have to play directly with the URL and you will see the result as a json.
     * For example http://localhost:8080/api/events/search/wa
     * @param query
     * @return
     */
    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAllBy();
        // Filter the events list in pure JAVA here
        List<Event> eventList = new ArrayList<>();
        events.forEach(event -> {
            event.getBands().forEach(band -> {
                band.getMembers().removeIf(member -> !member.getName().toLowerCase().contains(query.toLowerCase()));
            });
            event.getBands().removeIf(band -> band.getMembers().size()==0);
        });
        events.removeIf(event -> event.getBands().size() == 0);

        events.forEach(event -> {
            event.setTitle(event.getTitle() + " " + "[" + event.getBands().size() +"]");
            event.getBands().forEach(band -> { band.setName(band.getName() + " " + "[" + band.getMembers().size() +"]"); });
        });

        return events;
    }

    /**
     * This method resolves the first issue detected by calling the right query through the repository
     * @param id
     * @param event
     */
    public void update(Long id, Event event) {
        eventRepository.update(id, event.getNbStars(), event.getComment());
    }
}
