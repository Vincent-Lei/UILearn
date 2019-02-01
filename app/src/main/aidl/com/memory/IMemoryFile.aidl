// IMemoryFile.aidl
package com.memory;
import android.os.ParcelFileDescriptor;
import com.memory.ISignalListener;
// Declare any non-default types here with import statements

interface IMemoryFile {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    ParcelFileDescriptor getParcelFileDescriptor();

    void noticeSignalReadAll();

    void registerSignalListener(in ISignalListener listener);
}
