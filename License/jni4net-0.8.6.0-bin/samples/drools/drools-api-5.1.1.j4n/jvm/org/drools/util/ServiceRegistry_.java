// ------------------------------------------------------------------------------
//  <autogenerated>
//      This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
// 
//      Changes to this file may cause incorrect behavior and will be lost if 
//      the code is regenerated.
//  </autogenerated>
// ------------------------------------------------------------------------------

package org.drools.util;

@net.sf.jni4net.attributes.ClrTypeInfo
public final class ServiceRegistry_ {
    
    //<generated-static>
    private static system.Type staticType;
    
    public static system.Type typeof() {
        return org.drools.util.ServiceRegistry_.staticType;
    }
    
    private static void InitJNI(net.sf.jni4net.inj.INJEnv env, system.Type staticType) {
        org.drools.util.ServiceRegistry_.staticType = staticType;
    }
    //</generated-static>
}

//<generated-proxy>
@net.sf.jni4net.attributes.ClrProxy
class __ServiceRegistry extends system.Object implements org.drools.util.ServiceRegistry {
    
    protected __ServiceRegistry(net.sf.jni4net.inj.INJEnv __env, long __handle) {
            super(__env, __handle);
    }
    
    @net.sf.jni4net.attributes.ClrMethod("(Ljava/lang/Class;)Ljava/lang/Object;")
    public native java.lang.Object get(java.lang.Class par0);
    
    @net.sf.jni4net.attributes.ClrMethod("(Ljava/lang/Class;Ljava/lang/Object;)V")
    public native void registerLocator(java.lang.Class par0, java.util.concurrent.Callable par1);
    
    @net.sf.jni4net.attributes.ClrMethod("(Ljava/lang/Class;)V")
    public native void unregisterLocator(java.lang.Class par0);
    
    @net.sf.jni4net.attributes.ClrMethod("(Ljava/lang/Class;Ljava/lang/String;)V")
    public native void addDefault(java.lang.Class par0, java.lang.String par1);
}
//</generated-proxy>
