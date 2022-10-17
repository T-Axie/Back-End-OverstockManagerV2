package digitalcity.demeyert.overstockmanager.controller;

import digitalcity.demeyert.overstockmanager.helper.CSVHelper;
import digitalcity.demeyert.overstockmanager.message.ResponseMessage;
import digitalcity.demeyert.overstockmanager.model.dto.CollecDTO;
import digitalcity.demeyert.overstockmanager.model.forms.CollecCreateForm;
import digitalcity.demeyert.overstockmanager.service.CSVService;
import digitalcity.demeyert.overstockmanager.service.CollectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("http://localhost:8100")
@RestController
@RequestMapping("/collect")
public class CollectController {

    private final CollectService service;
    private final CSVService fileService;

    public CollectController(CollectService service, CSVService fileService) {
        this.service = service;
        this.fileService = fileService;
    }

    @GetMapping("/{id:[0-9]+}")
    public CollecDTO getOne(@PathVariable long id) {
        return service.getOne(id);
    }

    @GetMapping({ "", "/all" })
    public List<CollecDTO> getAll() {
        return service.getAll();
    }

    @PostMapping
    public CollecDTO insert(UsernamePasswordAuthenticationToken auth, @Valid @RequestBody CollecCreateForm form) {
        return service.create(auth, form);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String delete(UsernamePasswordAuthenticationToken auth, @PathVariable long id) {
        service.delete(auth, id);
        return "Your collection is successfully removed";
    }
    @PutMapping("/{id}")
    public CollecDTO update(UsernamePasswordAuthenticationToken auth, @PathVariable long id, @Valid @RequestBody CollecDTO collecDTO) {
        return service.update(id, collecDTO);
    }

    @PatchMapping(value = "/upload", consumes = { "multipart/form-data" })
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam MultipartFile file, Long id) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                fileService.save(file, id);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }
}
