package java_util_concurrent.api;

public interface StorageService {
  /*
   save the new entry
  */
  String put(String repo, byte[] data);

  byte[] get(String repo, String oId);

  boolean delete(String repo, String oId);
}
