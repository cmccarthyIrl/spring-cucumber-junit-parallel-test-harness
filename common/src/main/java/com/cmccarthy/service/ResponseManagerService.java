package com.cmccarthy.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ResponseManagerService {

    private final ThreadLocal<Map<String, Object>> getResponseRootThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<Map<String, Object>> putResponseRootThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<Map<String, Object>> deleteResponseRootThreadLocal = new ThreadLocal<>();

    public Map<String, Object> getGetResponse() {
        return getResponseRootThreadLocal.get();
    }

    public Map<String, Object> getPutResponse() {
        return putResponseRootThreadLocal.get();
    }

    public Map<String, Object> getDeleteResponse() {
        return deleteResponseRootThreadLocal.get();
    }

    public void setGetResponseThread(Map<String, Object> getResponseRoot) {
        getResponseRootThreadLocal.set(getResponseRoot);
    }

    public void setPutResponseThread(Map<String, Object> putResponseRoot) {
        putResponseRootThreadLocal.set(putResponseRoot);
    }

    public void setDeleteResponseThread(Map<String, Object> deleteResponseRoot) {
        deleteResponseRootThreadLocal.set(deleteResponseRoot);
    }
}

