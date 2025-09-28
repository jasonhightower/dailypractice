package com.hightower.dailypractice.core.model;

import java.util.HashSet;
import java.util.Set;

public class LinkNode<T> {

    public static <T> String asString(LinkNode<T> node) {
        return node == null ? "[]" : node.toString();
    }

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

    @Override
    public String toString() {
        Set<LinkNode<T>> visited = new HashSet<>();
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        LinkNode<T> cur = this;
        while (cur != null) {
            if (visited.contains(cur)) {
                break;
            }
            visited.add(cur);
            if (builder.length() > 1) {
                builder.append(",");
            }
            builder.append(cur.value);
            cur = cur.next;
        }
        builder.append("]");
        return builder.toString();
    }

}
