// IMainProcessBridgerService.aidl
package com.slib.processconnect;

// Declare any non-default types here with import statements

interface IMainProcessBridgerService {
    String getStringFromMainProcess(String param);

    void requireToMainProcess(String id,String action,String param);
}
