package com.hightower.dailypractice.core.model;

public class LinkNode<T> {

    public static LinkNode<Integer> of(int ... values) {
        LinkNode<Integer> head = new LinkNode<>(-1);
        LinkNode<Integer> cur = head;
        for (int val : values) {
            cur.next = new LinkNode<>(val);
            cur = cur.next;
        }
        return head.next;
    }

    public static LinkNode<Integer> ofWithCycle(int index, int ... values) {
        LinkNode<Integer> head = new LinkNode<>(-1);
        LinkNode<Integer> cur = head;
        LinkNode<Integer> cycleRef = null;
        for (int i = 0; i < values.length; i++) {
            cur.next = new LinkNode<>(values[i]);
            cur = cur.next;
            if (index == i) {
                cycleRef = cur;
            }
        }
        cur.next = cycleRef;
        return head.next;
    }

    public final T value;
    public LinkNode<T> next;

    public LinkNode(T value) {
        this.value = value;
    }

}
