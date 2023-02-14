import static org.junit.Assert.*;

import org.junit.Test;

public class RandomExpressionsTest {
    @Test
    public void testRandomExpressions() {
        RandomExpressions.ExpNode exp = RandomExpressions.randomExpression(3);
        assertNotNull(exp);
        double x = 2.0;
        double result = exp.value(x);
        assertTrue(Double.isFinite(result));
        assertEquals(exp.toString(), exp.copy().toString());
    }
}
