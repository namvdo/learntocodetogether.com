import com.learntocodetogether.TableOrdering;
import com.learntocodetogether.ToposortUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TopoSortTest {
    static {

    }

    @Test
    void testGraphWithNoCycle() {
        TableOrdering tableA = TableOrdering.of("a", List.of());
        TableOrdering tableB = TableOrdering.of("b", List.of("a"));
        TableOrdering tableC = TableOrdering.of("c", List.of());
        TableOrdering tableD = TableOrdering.of("d", List.of());
        TableOrdering tableE = TableOrdering.of("e", List.of("c", "d"));
        TableOrdering tableF = TableOrdering.of("f", List.of());
        TableOrdering tableG = TableOrdering.of("g", List.of("f"));
        List<TableOrdering> tableOrderings = List.of(tableA, tableB, tableC, tableD, tableE, tableF, tableG);
        List<TableOrdering> newOrder = ToposortUtils.findLinearOrdering(tableOrderings);
        Assertions.assertTrue(indexOf("a", newOrder) < indexOf("b", newOrder));
        Assertions.assertTrue(indexOf("c", newOrder) < indexOf("e", newOrder));
    }


    @Test
    void testGraphWithCycle() {
        TableOrdering tableA = TableOrdering.of("a", List.of("b"));
        TableOrdering tableB = TableOrdering.of("b", List.of("c"));
        TableOrdering tableC = TableOrdering.of("c", List.of("d"));
        TableOrdering tableD = TableOrdering.of("d", List.of("a"));
        TableOrdering tableE = TableOrdering.of("e", List.of());
        List<TableOrdering> tableOrderings = List.of(tableA, tableB, tableC, tableD, tableE);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ToposortUtils.findLinearOrdering(tableOrderings));
    }

    static int indexOf(String e, List<TableOrdering> tableOrderings) {
        for(int i = 0; i < tableOrderings.size(); i++) {
            if (tableOrderings.get(i).getNode().equals(e)) {
                return i;
            }
        }
        return -1;
    }

}
