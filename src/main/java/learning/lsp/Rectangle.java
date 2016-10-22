package learning.lsp;

import lombok.Data;

/**
 * @author bingoohuang [bingoohuang@gmail.com] Created on 2016/10/22.
 */
@Data
public class Rectangle {
    private int length;
    private int breadth;

    public int getArea() {
        return this.length * this.breadth;
    }
}
