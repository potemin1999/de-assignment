package com.ilya.de.math.graph;

import lombok.*;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Point {

    @Getter
    @Setter
    //x coordinate
    private double x;

    @Getter
    @Setter
    //y coordinate
    private double y;

}
