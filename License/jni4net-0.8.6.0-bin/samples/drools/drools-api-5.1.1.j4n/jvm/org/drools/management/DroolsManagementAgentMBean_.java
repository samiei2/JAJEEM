// ------------------------------------------------------------------------------
//  <autogenerated>
//      This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
// 
//      Changes to this file may cause incorrect behavior and will be lost if 
//      the code is regenerated.
//  </autogenerated>
// ------------------------------------------------------------------------------

package org.drools.management;

@net.sf.jni4net.attributes.ClrTypeInfo
public final class DroolsManagementAgentMBean_ {
    
    //<generated-static>
    private static system.Type staticType;
    
    public static system.Type typeof() {
        return org.drools.management.DroolsManagementAgentMBean_.staticType;
    }
    
    private static void InitJNI(net.sf.jni4net.inj.INJEnv env, system.Type staticType) {
        org.drools.management.DroolsManagementAgentMBean_.staticType = staticType;
    }
    //</generated-static>
}

//<generated-proxy>
@net.sf.jni4net.attributes.ClrProxy
class __DroolsManagementAgentMBean extends system.Object implements org.drools.management.DroolsManagementAgentMBean {
    
    protected __DroolsManagementAgentMBean(net.sf.jni4net.inj.INJEnv __env, long __handle) {
            super(__env, __handle);
    }
    
    @net.sf.jni4net.attributes.ClrMethod("()J")
    public native long getKnowledgeBaseCount();
    
    @net.sf.jni4net.attributes.ClrMethod("()J")
    public native long getSessionCount();
}
//</generated-proxy>
