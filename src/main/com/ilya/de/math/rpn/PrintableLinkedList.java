package com.ilya.de.math.rpn;

import lombok.NoArgsConstructor;

import java.util.LinkedList;

@NoArgsConstructor
class PrintableLinkedList<E> extends LinkedList<E> {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (E obj : this) {
            sb.append("'").append(obj.toString()).append("' ");
        }
        return sb.toString();
    }

}
