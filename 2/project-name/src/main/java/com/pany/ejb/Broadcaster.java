/**
 * @(#) Broadcaster.java
 */

package com.pany.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class handling server PUSH, responsible for updating client UI
 *
 * @author Sobek
 *
 */
public class Broadcaster {

    static ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static List<BroadcastListener> listeners = new ArrayList<>();

    /**
     * Adding of new UI
     *
     * @param listener
     */
    public static synchronized void register(BroadcastListener listener) {
        listeners.add(listener);
        // send init data
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                listener.update();
            }
        });
    }

    /**
     * Removes inactive UI
     *
     * @param listener
     */
    public static synchronized void unregister(BroadcastListener listener) {
        listeners.remove(listener);
    }

    /**
     * Updates UI
     */
    public static synchronized void broadcast() {
        for (final BroadcastListener listener : listeners) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    listener.update();
                }
            });
        }
    }

}
