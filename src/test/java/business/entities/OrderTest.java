package business.entities;

import business.persistence.Database;
import business.persistence.UserMapper;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    public static Material material1;
    public static Material material2;
    public static Material material3;
    public static OrderLine orderLine1;
    public static OrderLine orderLine2;
    public static OrderLine orderLine3;
    public static Carport carport;
    public static Order order;

    @BeforeAll
    public static void setUpClass() {
        material1 = new Material("test","test",1,1,1,1,10);
        material2 = new Material("test","test",2,2,2,2,100);
        material3 = new Material("test","test",3,3,3,3,1000);
        orderLine1 = new OrderLine(1,1,"stk",material1,"test");
        orderLine2 = new OrderLine(1,1,"stk",material2,"test");
        orderLine3 = new OrderLine(1,1,"stk",material3,"test");
        carport = new Carport(material1,400,400,600,RoofType.Fladt_Tag,material1);
        order = new Order(Status.Request,1,carport);
    }

    @Test
    void calculateCostPriceByArrayList() {
        ArrayList<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine1);
        orderLines.add(orderLine2);
        orderLines.add(orderLine3);

        assertEquals(0,order.getCostPrice());
        order.calculateCostPriceByArrayList(orderLines);
        assertEquals(1110,order.getCostPrice());
    }

    @Test
    void calculateCostPrice() {
        TreeMap<Integer, OrderLine> orderLineTreeMap = new TreeMap<>();
        orderLineTreeMap.put(1,orderLine1);
        orderLineTreeMap.put(2,orderLine2);
        orderLineTreeMap.put(3,orderLine3);
        order.setBOM(orderLineTreeMap);
        assertEquals(0,order.getCostPrice());
        order.calculateCostPrice();
        assertEquals(1110,order.getCostPrice());
    }

    @Test
    void calculateTotalPrice() {
        calculateCostPrice();
        double ordreAvance = 10;
        order.calculateTotalPrice(ordreAvance);
        assertEquals(1221,order.getTotalPrice());
        ordreAvance = 20;
        order.calculateTotalPrice(ordreAvance);
        assertEquals(1332,order.getTotalPrice());
        ordreAvance = -10;
        order.calculateTotalPrice(ordreAvance);
        assertEquals(999,order.getTotalPrice());
    }
}