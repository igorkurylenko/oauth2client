package io.itdraft.gwt.oauth2.implicit;

import java.util.Random;

public class DefaultRandomStateGenerator implements StateGenerator{
    @Override
    public String generateState() {
        return new Random().nextInt() + "";
    }
}
