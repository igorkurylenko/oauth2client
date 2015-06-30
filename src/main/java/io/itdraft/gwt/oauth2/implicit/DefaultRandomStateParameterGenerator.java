package io.itdraft.gwt.oauth2.implicit;

import java.util.Random;

public class DefaultRandomStateParameterGenerator implements StateParameterGenerator {
    @Override
    public String generateState() {
        return new Random().nextInt() + "";
    }
}
