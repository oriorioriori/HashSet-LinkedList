public class CustomHashSet {
    int n;
    CustomLinkedList[] set;

    public CustomHashSet() {
        n = 4;
        set = new CustomLinkedList[n];
        for (int i = 0; i < n; i++) {
            set[i] = new CustomLinkedList();
        }
    }

    private boolean needRehash() {
        int amount = 0; //количество списков, в которых два и больше элементов
        for (CustomLinkedList list: set) {
            if (list != null && list.size() >= 2) {
                amount++;
            }
        }
        return amount / n >= 0.75;
    }

    private void rehash() {
        int newCount = n * 2;
        CustomLinkedList[] newContent = new CustomLinkedList[newCount];
        for (CustomLinkedList list: set) {
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    Object element = list.get(i);
                    int newIndex = element.hashCode() % newCount;
                    if (newContent[newIndex] == null) {
                        newContent[newIndex] = new CustomLinkedList();
                    }
                    newContent[newIndex].add(element);
                }
            }
        }
        set = newContent;
        n = newCount;
    }

    private int h(Object object) {
        return object.hashCode() % n;
    }

    public void add(Object object) {
        if (needRehash()) {
            rehash();
        }
        int h = h(object);
        if (!set[h].contains(object)) {
            set[h].add(object);
        }
    }

    public boolean contains(Object object) {
        int h = h(object);
        return set[h] != null && set[h].contains(object);
    }

    public void remove(Object object) {
        int h = h(object);
        String objectString = object.toString();
        CustomLinkedList list = set[h];
        if (list != null && list.contains(objectString)) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(objectString)) {
                    list.remove(i);
                }
            }
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        CustomHashSet set = (CustomHashSet) object;
        return hashCode() == set.hashCode();
    }

    public String toString() {
        String s = "{";
        for (CustomLinkedList list: set) {
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    s += list.get(i) + ", ";
                }
            }
        }
        if (s.length() > 1)
            s = s.substring(0, s.length() - 2); //удаление ненужной последней запятой
        s += "}";
        return s;
    }
}
