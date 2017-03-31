package com.github.kazy1991.dependencykit;


import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(Enclosed.class)
public class DependencyKitTest {

    private interface SmartPhone {

        String developer();

        String os();

        int storageSize();

        long weight();
    }

    public static class Pixel implements SmartPhone {

        @Inject
        public Pixel() {
        }

        @Override
        public String developer() {
            return "Google";
        }

        @Override
        public String os() {
            return "Android";
        }

        @Override
        public int storageSize() {
            return 64;
        }

        @Override
        public long weight() {
            return 300;
        }
    }

    public static class ValidCondition {

        @Inject
        SmartPhone smartPhone;

        private static class TestModule extends Module {
            @Override
            protected void configure() {
                bind(SmartPhone.class).to(Pixel.class);
            }
        }

        @Test
        public void injectFieldAndNoError() {
            DependencyKit dependencyKit = new DependencyKit();
            dependencyKit.configure(new TestModule());
            dependencyKit.inject(ValidCondition.this);
            assertThat(smartPhone).isNotNull();
        }

        @Test
        public void injectFieldAndCheckInstanceType() {
            DependencyKit dependencyKit = new DependencyKit();
            dependencyKit.configure(new TestModule());
            dependencyKit.inject(ValidCondition.this);
            assertThat(smartPhone).isInstanceOf(Pixel.class);
        }

    }

}
