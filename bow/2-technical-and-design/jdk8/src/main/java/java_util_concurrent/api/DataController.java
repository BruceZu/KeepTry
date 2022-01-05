package java_util_concurrent.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class DataController {
  @Autowired
  public DataController(StorageService store) {
    this.store = store;
  }

  private StorageService store;

  @GetMapping(value = "/{repository}/{objectID}")
  public Object getObject(
      @PathVariable("repository") String repository, @PathVariable("objectID") String objectID) {
    byte[] r = store.get(repository, objectID);
    boolean foundData = r != null;
    // Get the data from somewhere
    if (foundData) {
      Object result = r;
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      return new ResponseEntity<>(result, headers, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping(
      value = "/{repository}",
      consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
  public Object uploadObject(
      @PathVariable("repository") String repository,
      @RequestBody byte[] data,
      UriComponentsBuilder builder) {
    String id = store.put(repository, data);

    Map<String, Object> map = new HashMap<>();
    map.put("oid", id);
    map.put("size", data.length);

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(builder.path("/" + repository + "/{id}").buildAndExpand(id).toUri());
    return new ResponseEntity<>(map, headers, HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{repository}/{objectID}")
  public Object deleteObject(
      @PathVariable("repository") String repository, @PathVariable("objectID") String objectID) {
    boolean couldDeleteObject = store.delete(repository, objectID);
    if (couldDeleteObject) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
