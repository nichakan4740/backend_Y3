package sit.int221.oasip.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sit.int221.oasip.Service.FileService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/files")
public class FilesController {

    @Autowired
    private FileService fileService;

    //        @GetMapping("/{filename:.+}")
//        @ResponseBody
//        public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//            Resource file = fileService.loadFileAsResource(filename);
//            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
//        }
//
    @PostMapping("/{eventId}")
    public String fileUpload(@RequestParam("file") MultipartFile file, @PathVariable Integer eventId) {
        fileService.store(file, eventId);
        return "You successfully uploaded " + file.getOriginalFilename() + "!";
    }

    @DeleteMapping("/{eventId}")
    public String deleteFileById(@PathVariable Integer eventId) {
        fileService.deleteFileById(eventId);
        return "You successfully deleted file with eventId " + eventId + "!";
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Object> getFileById(@PathVariable Integer eventId) {
        List<String> filenames = fileService.listFileName(eventId);
        return ResponseEntity.ok().body(filenames);
    }

    @GetMapping(path = "/{eventId}/{filename:.+}", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable Integer eventId, @PathVariable String filename, HttpServletRequest request){

        Resource file = fileService.loadFileAsResource(eventId, filename);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
            System.out.println(contentType);
        } catch (IOException ex) {
            throw new RuntimeException("Could not determine file type.", ex);
//            throw new StorageException("Could not determine file type.", ex);
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
//				.contentType(MediaType.ALL)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
