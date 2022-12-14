import java.util.ArrayList;                                     //curr - текущий, pred - предыдущий, value - значение ключа

public class SetImpl<T extends Comparable<T>> implements Set<T> {        // ?

    //    создание пустого списка, сотоящего из 2-x граничных элементов
    public final Node<T> vtoroe;         
    public final Node<T> pervoe;

    public SetImpl() {                // ?
        vtoroe = new Node<>(null, null);       
        pervoe = new Node<>(null, vtoroe);
    }

    public ArrayList<Node<T>> find(T value) {
        ArrayList<Node<T>> arrayN = new ArrayList<Node<T>>(2);     // ?
    
        find_again:
   
        while (true) {                  // ?
            Node<T> pred = pervoe;
            Node<T> curr = pervoe.next.getReference();            // возвращает текущее значение ссылки
           
            while (true) {
                boolean[] cmk = {false};              // cmk - ?
                Node<T> succ = curr.next.get(cmk);       // curr.next.get(cmk) - ?
                if (cmk[0]) {
                    if (pred.next.compareAndSet(curr, succ, false, false)) {    // с compareAndSet знакома, но что тут значат переменные через точку pred.next
                        continue find_again;
                    }
                    curr = succ;
                } else {
              
                    if (curr.item == null || curr.item.compareTo(value) >= 0) {     // curr.item - ?
                        arrayN.add(pred);            // arrayN.add ?
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
            ArrayList<Node<T>> arrayN = find(value);        // ?
            Node<T> pred = arrayN.get(0);                // get - ?
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
        Node<T> curr = pervoe.next.getReference();
        while (curr.item != null && curr.item.compareTo(value) < 0) {
            curr = curr.next.getReference();
        }
        return value == curr.item && !curr.next.isMarked();
    }

    @Override
    public boolean isEmpty() {            //проверка множества на пустоту
        while (pervoe.next.getReference() != vtoroe) {
            Node<T> curr = pervoe.next.getReference();
            if (!curr.next.isMarked()) {
                return false;
            } else {
                pervoe.next.compareAndSet(curr, curr.next.getReference(), false, false);
            }
        }
        return true;
    }
}
