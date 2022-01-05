package java_util_concurrent.api;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.UUID;

@Component
public class IdGeneraterImp implements IdGenerater {

  @Override
  public synchronized String newId(byte[] data) {
    try {
      // TODO: make it better later
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(data);
      byte[] digest = md.digest();
      BigInteger bigInteger = new BigInteger(1, digest);
      String hash = String.format("%0" + (digest.length << 1) + "x", bigInteger);
      return hash + UUID.randomUUID();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
