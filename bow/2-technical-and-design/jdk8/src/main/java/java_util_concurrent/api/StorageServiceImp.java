package java_util_concurrent.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StorageServiceImp implements StorageService {
  // mocked DB
  private IdGenerater idGenerater;
  private Map<String, Map<String, byte[]>> db;

  @Autowired
  public StorageServiceImp(IdGenerater ig) {
    this.idGenerater = ig;
    db = new HashMap<>();
  }

  @Override
  public synchronized String put(String repo, byte[] data) {
    db.putIfAbsent(repo, new HashMap<>());
    Map<String, byte[]> kv = db.get(repo);
    String id;
    while (true) {
      id = idGenerater.newId(data);
      if (kv.containsKey(id)) continue;
      break;
    }
    kv.put(id, data);
    return id;
  }

  @Override
  public byte[] get(String repo, String oId) {
    if (db.containsKey(repo)) {
      return db.get(repo).get(oId); // still can be null;
    }
    return null;
  }

  @Override
  public boolean delete(String repo, String oId) {
    if (db.containsKey(repo)) {
      Map<String, byte[]> kv = db.get(repo);
      synchronized (kv) {
        if (db.get(repo).containsKey(oId)) {
          db.get(repo).remove(oId);
          return true;
        }
      }
    }
    return false;
  }
}
