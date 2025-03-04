import model.ComputerRepairRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ComputerRepairRequestTest {
    @Test
    @DisplayName("test1")
    public void test1() {
        ComputerRepairRequest crr = new ComputerRepairRequest();
        assertEquals("", crr.getOwnerName());
        assertEquals("", crr.getOwnerAddress());
    }

    @Test
    @DisplayName("test2")
    public void test2() {
        ComputerRepairRequest crr = new ComputerRepairRequest();
        crr.setOwnerName("nume-test");
        assertEquals("nume-test", crr.getOwnerName());
    }
}
