import java.util.concurrent.atomic.AtomicMarkableReference;

public class Node<T extends Comparable<T>> {      //?
    T item;
    AtomicMarkableReference<Node<T>> next;      //объеденим ссылку на следующий элемент списка и метку о том, что узел удален в одно поле


    public Node(T item, Node<T> next) {
        this.item = item;
        this.next = new AtomicMarkableReference<>(next, false);    //Замена ссылки будет производиться следующим образом: сравнивается первый аргумент
    }                                                              //с действительной ссылкой и то, что флаг по-прежнему равен false, и в случае успеха 
}                                                                  //ссылка заменяется вторым аргументом. 
