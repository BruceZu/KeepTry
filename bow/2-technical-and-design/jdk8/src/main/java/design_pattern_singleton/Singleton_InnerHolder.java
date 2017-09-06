package design_pattern_singleton;

public class InnerStaticHolder {
    private static class Holder {
        public static InnerStaticHolder instanceInHolder = new InnerStaticHolder();
    }

    public static InnerStaticHolder getInstance() {
        return Holder.instanceInHolder;
    }
}
