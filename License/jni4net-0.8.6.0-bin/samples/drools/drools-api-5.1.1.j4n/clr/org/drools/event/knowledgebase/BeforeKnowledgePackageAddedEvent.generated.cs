//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
//     Runtime Version:2.0.50727.4952
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace org.drools.@event.knowledgebase {
    
    
    #region Component Designer generated code 
    [global::net.sf.jni4net.attributes.JavaInterfaceAttribute()]
    public partial interface BeforeKnowledgePackageAddedEvent : global::org.drools.@event.knowledgebase.KnowledgeBaseEvent {
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Lorg/drools/definition/KnowledgePackage;")]
        global::org.drools.definition.KnowledgePackage getKnowledgePackage();
    }
    #endregion
    
    #region Component Designer generated code 
    public partial class BeforeKnowledgePackageAddedEvent_ {
        
        public static global::java.lang.Class _class {
            get {
                return global::org.drools.@event.knowledgebase.@__BeforeKnowledgePackageAddedEvent.staticClass;
            }
        }
    }
    #endregion
    
    #region Component Designer generated code 
    [global::net.sf.jni4net.attributes.JavaProxyAttribute(typeof(global::org.drools.@event.knowledgebase.BeforeKnowledgePackageAddedEvent), typeof(global::org.drools.@event.knowledgebase.BeforeKnowledgePackageAddedEvent_))]
    [global::net.sf.jni4net.attributes.ClrWrapperAttribute(typeof(global::org.drools.@event.knowledgebase.BeforeKnowledgePackageAddedEvent), typeof(global::org.drools.@event.knowledgebase.BeforeKnowledgePackageAddedEvent_))]
    internal sealed partial class @__BeforeKnowledgePackageAddedEvent : global::java.lang.Object, global::org.drools.@event.knowledgebase.BeforeKnowledgePackageAddedEvent {
        
        internal new static global::java.lang.Class staticClass;
        
        internal static global::net.sf.jni4net.jni.MethodId _getKnowledgeBase0;
        
        internal static global::net.sf.jni4net.jni.MethodId _getKnowledgePackage1;
        
        private @__BeforeKnowledgePackageAddedEvent(global::net.sf.jni4net.jni.JNIEnv @__env) : 
                base(@__env) {
        }
        
        private static void InitJNI(global::net.sf.jni4net.jni.JNIEnv @__env, java.lang.Class @__class) {
            global::org.drools.@event.knowledgebase.@__BeforeKnowledgePackageAddedEvent.staticClass = @__class;
            global::org.drools.@event.knowledgebase.@__BeforeKnowledgePackageAddedEvent._getKnowledgeBase0 = @__env.GetMethodID(global::org.drools.@event.knowledgebase.@__BeforeKnowledgePackageAddedEvent.staticClass, "getKnowledgeBase", "()Lorg/drools/KnowledgeBase;");
            global::org.drools.@event.knowledgebase.@__BeforeKnowledgePackageAddedEvent._getKnowledgePackage1 = @__env.GetMethodID(global::org.drools.@event.knowledgebase.@__BeforeKnowledgePackageAddedEvent.staticClass, "getKnowledgePackage", "()Lorg/drools/definition/KnowledgePackage;");
        }
        
        public global::org.drools.KnowledgeBase getKnowledgeBase() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.KnowledgeBase>(@__env, @__env.CallObjectMethodPtr(this, global::org.drools.@event.knowledgebase.@__BeforeKnowledgePackageAddedEvent._getKnowledgeBase0));
            }
        }
        
        public global::org.drools.definition.KnowledgePackage getKnowledgePackage() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.definition.KnowledgePackage>(@__env, @__env.CallObjectMethodPtr(this, global::org.drools.@event.knowledgebase.@__BeforeKnowledgePackageAddedEvent._getKnowledgePackage1));
            }
        }
        
        private static global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod> @__Init(global::net.sf.jni4net.jni.JNIEnv @__env, global::java.lang.Class @__class) {
            global::System.Type @__type = typeof(__BeforeKnowledgePackageAddedEvent);
            global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod> methods = new global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod>();
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "getKnowledgeBase", "getKnowledgeBase0", "()Lorg/drools/KnowledgeBase;"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "getKnowledgePackage", "getKnowledgePackage1", "()Lorg/drools/definition/KnowledgePackage;"));
            return methods;
        }
        
        private static global::net.sf.jni4net.utils.JniHandle getKnowledgeBase0(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj) {
            // ()Lorg/drools/KnowledgeBase;
            // ()Lorg/drools/KnowledgeBase;
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            global::net.sf.jni4net.utils.JniHandle @__return = default(global::net.sf.jni4net.utils.JniHandle);
            try {
            global::org.drools.@event.knowledgebase.BeforeKnowledgePackageAddedEvent @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.@event.knowledgebase.BeforeKnowledgePackageAddedEvent>(@__env, @__obj);
            @__return = global::net.sf.jni4net.utils.Convertor.FullC2J<global::org.drools.KnowledgeBase>(@__env, ((global::org.drools.@event.knowledgebase.KnowledgeBaseEvent)(@__real)).getKnowledgeBase());
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static global::net.sf.jni4net.utils.JniHandle getKnowledgePackage1(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj) {
            // ()Lorg/drools/definition/KnowledgePackage;
            // ()Lorg/drools/definition/KnowledgePackage;
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            global::net.sf.jni4net.utils.JniHandle @__return = default(global::net.sf.jni4net.utils.JniHandle);
            try {
            global::org.drools.@event.knowledgebase.BeforeKnowledgePackageAddedEvent @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.@event.knowledgebase.BeforeKnowledgePackageAddedEvent>(@__env, @__obj);
            @__return = global::net.sf.jni4net.utils.Convertor.FullC2J<global::org.drools.definition.KnowledgePackage>(@__env, @__real.getKnowledgePackage());
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        new internal sealed class ContructionHelper : global::net.sf.jni4net.utils.IConstructionHelper {
            
            public global::net.sf.jni4net.jni.IJvmProxy CreateProxy(global::net.sf.jni4net.jni.JNIEnv @__env) {
                return new global::org.drools.@event.knowledgebase.@__BeforeKnowledgePackageAddedEvent(@__env);
            }
        }
    }
    #endregion
}
