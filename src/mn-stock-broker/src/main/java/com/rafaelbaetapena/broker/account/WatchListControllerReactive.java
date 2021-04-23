package com.rafaelbaetapena.broker.account;

import com.rafaelbaetapena.broker.model.WatchList;
import com.rafaelbaetapena.broker.store.InMemoryAccountStore;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Controller("/account/watchlist-reactive")
public class WatchListControllerReactive {

    private static final Logger LOG = LoggerFactory.getLogger(WatchListControllerReactive.class);
    static final UUID ACCOUNT_ID = UUID.randomUUID();

    private final InMemoryAccountStore store;
    private final Scheduler scheduler;

    public WatchListControllerReactive(
            @Named(TaskExecutors.IO) ExecutorService executorService,
            InMemoryAccountStore store) {
        this.store = store;
        this.scheduler = Schedulers.from(executorService);
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public WatchList get() {
        LOG.debug("getWatchList - {}", Thread.currentThread().getName());
        return store.getWatchList(ACCOUNT_ID);
    }

    @Get(
            value = "/single",
            produces = MediaType.APPLICATION_JSON
    )
    public Single<WatchList> getAsSingle() {
        return Single.fromCallable(() -> {
            LOG.debug("getAsSingle - {}", Thread.currentThread().getName());
            return store.getWatchList(ACCOUNT_ID);
        }).subscribeOn(scheduler);
    }

    @Put(
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON
    )
    @ExecuteOn(TaskExecutors.IO)
    public WatchList update(@Body WatchList watchList) {
        return  store.updateWatchList(ACCOUNT_ID, watchList);
    }

    @Delete(
            value = "/{accountId}",
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON
    )
    @ExecuteOn(TaskExecutors.IO)
    public void delete(@PathVariable UUID accountId) {
        store.deleteWatchList(accountId);
    }
}
