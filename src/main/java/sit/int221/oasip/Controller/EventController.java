package sit.int221.oasip.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sit.int221.oasip.DTO.AddEventDTO;
import sit.int221.oasip.DTO.EventDTO;
import sit.int221.oasip.Entity.Event;
import sit.int221.oasip.Service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/events")
class EventController {
    @Autowired
    private EventService service;

    //Get all Event
    @GetMapping("")
    public List<EventDTO> getAllEvent(@RequestParam(defaultValue = "eventStartTime") String sortBy,HttpServletRequest request){
        return service.getAllEvent(sortBy,request);
    }

    //Get Event with id
    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Integer id, HttpServletRequest httpServletRequest) {
        return service.getEventById(id,httpServletRequest);
    }

    //Add new Event
    @PostMapping("")
    public EventDTO create(@Validated @RequestPart("event") AddEventDTO newEvent, @RequestPart(value = "file" , required = false) MultipartFile file , HttpServletRequest httpServletRequest) throws JsonProcessingException {
        return service.save(newEvent,file,httpServletRequest);
    }

    //Delete an event with id = ?
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id, HttpServletRequest httpServletRequest) {
        service.deleteById(id,httpServletRequest);
    }

    //Update an event with id = ?
    @PutMapping("/{id}")
    public Event update(@RequestBody Event updateEvent, @PathVariable Integer id, HttpServletRequest httpServletRequest) {
        return service.updateId(updateEvent, id,httpServletRequest);
    }

}
