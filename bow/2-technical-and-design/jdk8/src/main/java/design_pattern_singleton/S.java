package design_pattern_singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Singleton Inner Holder
public class S {
  static Logger log = LoggerFactory.getLogger(S.class);
  //  lazy initialization code
  private static class Holder {
    public static S INSTANCE = new S();
  }

  private S() {
    log.info("constructor");
  }

  public static S getInstance() {
    log.info("get Instance start");
    return Holder.INSTANCE;
  }

  public static void main(String[] args) {
    S obj = S.getInstance();
  }
}
