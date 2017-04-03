package com.github.kazy1991.dependencykit;


import com.github.kazy1991.dependencykit.sample.AppleWatch;
import com.github.kazy1991.dependencykit.sample.Computer;
import com.github.kazy1991.dependencykit.sample.Mac;
import com.github.kazy1991.dependencykit.sample.MacLazy;
import com.github.kazy1991.dependencykit.sample.Pixel;
import com.github.kazy1991.dependencykit.sample.SmartPhone;
import com.github.kazy1991.dependencykit.sample.SmartWatch;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(Enclosed.class)
public class DependencyKitTest {

    public static class ValidCondition {
        @Inject
        SmartPhone smartPhone;

        @Inject
        Computer computer;

        @Inject
        SmartWatch smartWatch;

        private static class TestModule extends Module {
            @Override
            protected void configure() {
                bind(SmartPhone.class).to(Pixel.class);
                bind(Computer.class).to(new MacLazy());
                bind(SmartWatch.class).to(new AppleWatch());
            }
        }

        @Before
        public void before() {
            DependencyKit dependencyKit = new DependencyKit();
            dependencyKit.configure(new TestModule());
            dependencyKit.inject(ValidCondition.this);
        }

        @Test
        public void injectFieldAndNoError() {
            assertThat(smartPhone).isNotNull();
        }

        @Test
        public void injectFieldAndCheckInstanceType() {
            assertThat(smartPhone).isInstanceOf(Pixel.class);
        }

        @Test
        public void injectLazyFieldAndNoError() {
            assertThat(computer).isNotNull();
        }

        @Test
        public void injectLazyFieldAndCheckInstanceType() {
            assertThat(computer).isInstanceOf(Mac.class);
        }

        @Test
        public void injectSingleTonFieldAndNoError() {
            assertThat(smartWatch).isNotNull();
        }

        @Test
        public void injectSingleTonFieldAndCheckInstanceType() {
            assertThat(smartWatch).isInstanceOf(AppleWatch.class);
        }

    }

}
