package cn.jpush.api;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class JUnitOrderedRunner extends BlockJUnit4ClassRunner {
    public JUnitOrderedRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        List<FrameworkMethod> list = super.computeTestMethods();
        Collections.sort(list, new Comparator<FrameworkMethod>() {
            @Override
            public int compare(FrameworkMethod f1, FrameworkMethod f2) {
                TestOrder o1 = f1.getAnnotation(TestOrder.class);
                TestOrder o2 = f2.getAnnotation(TestOrder.class);

                if (o1 == null || o2 == null)
                    return -1;

                return o1.order() - o2.order();
            }
        });
        return list;
    }
}
