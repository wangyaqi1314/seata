package com.teamer.servicetcc.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Result holder.
 *
 * @author zhangsen
 */
public class ResultHolder {

    private static Map<String, String> actionResults = new ConcurrentHashMap<String, String>();


    /**
     * Set action  result.
     * @param txId   the tx id
     * @param result the result
     */
    public static void setActionOneResult(String txId, String result) {
        actionResults.put(txId, result);
    }
    /**
     * Get action  result string.
     * @param txId the tx id
     * @return the string
     */
    public static String getActionOneResult(String txId) {
        return actionResults.get(txId);
    }

}
