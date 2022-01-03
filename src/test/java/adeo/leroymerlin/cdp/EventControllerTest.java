package adeo.leroymerlin.cdp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventController.class)
public class EventControllerTest {

    @MockBean
    private EventService eventService;

    @Autowired
    private EventController eventController;

    private Event event = new Event();
    private List<Event> eventList = new ArrayList<>();

    @Before
    public void initialize() {
        event.setId(1L);
        event.setTitle("test");
        event.setNbStars(3);
        eventList.add(event);
    }

    @Test
    public void findEventsTest1() {
        when(eventService.getEvents()).thenReturn(eventList);
        List<Event> events = eventController.findEvents();
        verify(eventService, times(1)).getEvents();
        assertEquals("test", events.get(0).getTitle());
    }

    @Test
    public void findEventsTest2() {
        when(eventService.getFilteredEvents(anyString())).thenReturn(eventList);
        List<Event> events = eventController.findEvents("wa");
        verify(eventService, times(1)).getFilteredEvents("wa");
        assertEquals("test", events.get(0).getTitle());
    }

    @Test
    public void deleteEventTest() {
        doNothing().when(eventService).delete(any());
        eventController.deleteEvent(1L);
        verify(eventService, times(1)).delete(any());
    }

    @Test
    public void updateEventTest() {
        doNothing().when(eventService).update(any(), any());
        eventController.updateEvent(1L, new Event());
        verify(eventService, times(1)).update(any(), any());
    }
}
