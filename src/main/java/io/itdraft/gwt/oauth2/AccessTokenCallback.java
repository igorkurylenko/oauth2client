package io.itdraft.gwt.oauth2;


import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;

public abstract class AccessTokenCallback implements Callback<AccessToken, FailureReason> {

    private boolean invoked = false;

    @Override
    public final void onFailure(FailureReason reason) {
        if (!invoked) {
            doOnFailure(reason);
            invoked = true;
        }
    }

    protected abstract void doOnFailure(FailureReason reason);

    @Override
    public final void onSuccess(AccessToken token) {
        if (!invoked) {
            doOnSuccess(token);
            invoked = true;
        }
    }

    protected abstract void doOnSuccess(AccessToken token);

    public final void deferredOnSuccess(final AccessToken token) {
        Scheduler.get().scheduleDeferred(new Command() {
            @Override
            public void execute() {
                onSuccess(token);
            }
        });
    }

    public final void deferredOnFailure(final FailureReason reason) {
        Scheduler.get().scheduleDeferred(new Command() {
            @Override
            public void execute() {
                onFailure(reason);
            }
        });
    }
}
