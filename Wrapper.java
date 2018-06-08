public class Wrapper {
    private Object instance;
    private Wrapper prev;
    private Wrapper next;

    public Wrapper(Object instance) {
        this.instance = instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public void setPrev(Wrapper prev) {
        this.prev = prev;
    }

    public void setNext(Wrapper next) {
        this.next = next;
    }

    public Object getInstance() {
        return instance;
    }

    public Wrapper getPrev() {
        return prev;
    }

    public Wrapper getNext() {
        return next;
    }

    public String toString() {
        return instance.toString();
    }
}
