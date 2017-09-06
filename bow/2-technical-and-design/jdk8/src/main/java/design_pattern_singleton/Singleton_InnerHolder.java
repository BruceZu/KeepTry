package design_pattern_singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Singleton_InnerHolder {
    static Logger log = LoggerFactory.getLogger(Singleton_InnerHolder.class);
    //  lazy initialization code
    private static class Holder {
        public static Singleton_InnerHolder instanceInHolder = new Singleton_InnerHolder();
    }

    private Singleton_InnerHolder() {
        log.info("constructor");
    }

    public static Singleton_InnerHolder getInstance() {
        log.info("get Instance start");
        return Holder.instanceInHolder;
    }

    public static void main(String[] args) {
        Singleton_InnerHolder obj = Singleton_InnerHolder.getInstance();
    }
}
