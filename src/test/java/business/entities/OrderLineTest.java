package business.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderLineTest {
    public static Material material1;
    public static OrderLine orderLine1;


    @BeforeAll
    public static void setUpClass() {
        material1 = new Material("test","test",1,1,1,1,10);
        orderLine1 = new OrderLine(10,1,"stk",material1,"test");
    }


    @Test
    void testCalculateAccumulatedPrice() {
        //Constructor calls CalculateAccumulatedPrice
    assertEquals(100,orderLine1.getAccumulatedPrice());
    }
}