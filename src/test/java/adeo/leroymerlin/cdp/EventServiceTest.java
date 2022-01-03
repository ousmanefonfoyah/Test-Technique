package adeo.leroymerlin.cdp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventService.class)
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @MockBean
    private EventRepository eventRepository;

    private Event event = new Event();
    private List<Event> eventList = new ArrayList<>();
    private Set<Member> members = new HashSet<>();;
    private Member member = new Member();
    private Set<Band> bands = new HashSet<>();;
    private Band band = new Band();

    @Before
    public void initialize() {
        member.setName("Queen Anika Walsh");
        members.add(member);

        band.setName("Metallica");
        band.setMembers(members);
        bands.add(band);

        event.setId(1L);
        event.setTitle("test");
        event.setNbStars(3);
        event.setBands(bands);

        eventList.add(event);
    }

    @Test
    public void getEventsTest() {
        when(eventRepository.findAllBy()).thenReturn(eventList);
        List<Event> events = eventService.getEvents();
        verify(eventRepository, times(1)).findAllBy();
        assertEquals("test", events.get(0).getTitle());
    }

    @Test
    public void deleteTest() {
        doNothing().when(eventRepository).delete(any());
        eventService.delete(1L);
        verify(eventRepository, times(1)).delete(any());
    }

    @Test
    public void getFilteredEventsTest() {
        when(eventRepository.findAllBy()).thenReturn(eventList);
        List<Event> events = eventService.getFilteredEvents("wa");
        verify(eventRepository, times(1)).findAllBy();
        assertEquals("test [1]", events.get(0).getTitle());
    }

    @Test
    public void updateTest() {
        doNothing().when(eventRepository).update(anyLong(), anyInt(), anyString());
        eventService.update(1L, event);
        verify(eventRepository, times(1)).update(any(), any(), any());
    }

}
