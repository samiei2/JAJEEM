// ------------------------------------------------------------------------------
//  <autogenerated>
//      This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
// 
//      Changes to this file may cause incorrect behavior and will be lost if 
//      the code is regenerated.
//  </autogenerated>
// ------------------------------------------------------------------------------

package org.drools.event.knowledgebase;

@net.sf.jni4net.attributes.ClrTypeInfo
public final class AfterKnowledgePackageAddedEvent_ {
    
    //<generated-static>
    private static system.Type staticType;
    
    public static system.Type typeof() {
        return org.drools.event.knowledgebase.AfterKnowledgePackageAddedEvent_.staticType;
    }
    
    private static void InitJNI(net.sf.jni4net.inj.INJEnv env, system.Type staticType) {
        org.drools.event.knowledgebase.AfterKnowledgePackageAddedEvent_.staticType = staticType;
    }
    //</generated-static>
}

//<generated-proxy>
@net.sf.jni4net.attributes.ClrProxy
class __AfterKnowledgePackageAddedEvent extends system.Object implements org.drools.event.knowledgebase.AfterKnowledgePackageAddedEvent {
    
    protected __AfterKnowledgePackageAddedEvent(net.sf.jni4net.inj.INJEnv __env, long __handle) {
            super(__env, __handle);
    }
    
    @net.sf.jni4net.attributes.ClrMethod("()Lorg/drools/KnowledgeBase;")
    public native org.drools.KnowledgeBase getKnowledgeBase();
    
    @net.sf.jni4net.attributes.ClrMethod("()Lorg/drools/definition/KnowledgePackage;")
    public native org.drools.definition.KnowledgePackage getKnowledgePackage();
}
//</generated-proxy>
