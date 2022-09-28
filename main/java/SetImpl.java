import java.util.ArrayList;                                     //curr - текущий, pred - предыдущий, value - значение ключа

public class SetImpl<T extends Comparable<T>> implements Set<T> {        // ?

    //    создание пустого списка, сотоящего из 2-x граничных элементов
    public final Node<T> startt;         
    public final Node<T> konez;

    public SetImpl() {
        startt = new Node<>(null, null);        // ?
        konez = new Node<>(null, startt);
    }

    public ArrayList<Node<T>> find(T value) {
        ArrayList<Node<T>> arrayN = new ArrayList<Node<T>>(2);
    
        find_again:
   
        while (true) {
            Node<T> pred = konez;
            Node<T> curr = konez.next.getReference();            // возвращает текущее значение ссылки
           
            while (true) {
                boolean[] cmk = {false};
                Node<T> succ = curr.next.get(cmk);
                if (cmk[0]) {
                    if (pred.next.compareAndSet(curr, succ, false, false)) {    
                        continue find_again;
                    }
                    curr = succ;
                } else {
              
                    if (curr.item == null || curr.item.compareTo(value) >= 0) {
                        arrayN.add(pred);
                        arrayN.add(curr);
                        return arrayN;
                    } else {
                        pred = curr;
                        curr = succ;
                    }
                }
            }
        }
    }


    @Override
    public boolean add(T value) {    //добавить ключ к множеству
        while (true) {
            ArrayList<Node<T>> arrayN = find(value);
            Node<T> pred = arrayN.get(0);
            Node<T> curr = arrayN.get(1);
            if (curr.item != null && curr.item.compareTo(value) == 0) {
                return false;
            } else {
                Node<T> addNode = new Node<>(value, null);
                addNode.next.set(curr, false);
                if (pred.next.compareAndSet(curr, addNode, false, false)) {
                    return true;
                }
            }
        }
    }

    @Override
    public boolean remove(T value) {        //удалить ключ из множества
        while (true) {
            ArrayList<Node<T>> arrayN = find(value);
            Node<T> pred = arrayN.get(0);
            Node<T> curr = arrayN.get(1);
            if (curr.item == null || curr.item.compareTo(value) != 0) {
                return false;
            } else {
                Node<T> removeNode = curr.next.getReference();
                if (!curr.next.compareAndSet(removeNode, removeNode, false, true)) {
                    continue;
                }
                pred.next.compareAndSet(curr, removeNode, false, false);
                return true;
            }
        }
    }

    @Override
    public boolean contains(T value) {             //проверка наличия ключа в множестве
        Node<T> curr = konez.next.getReference();
        while (curr.item != null && curr.item.compareTo(value) < 0) {
            curr = curr.next.getReference();
        }
        return value == curr.item && !curr.next.isMarked();
    }

    @Override
    public boolean isEmpty() {            //проверка множества на пустоту
        while (konez.next.getReference() != startt) {
            Node<T> curr = konez.next.getReference();
            if (!curr.next.isMarked()) {
                return false;
            } else {
                konez.next.compareAndSet(curr, curr.next.getReference(), false, false);
            }
        }
        return true;
    }
}
