//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
//     Runtime Version:2.0.50727.4952
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace org.drools.@event.rule {
    
    
    #region Component Designer generated code 
    [global::net.sf.jni4net.attributes.JavaInterfaceAttribute()]
    public partial interface AgendaEventListener {
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lorg/drools/event/rule/ActivationCreatedEvent;)V")]
        void activationCreated(global::org.drools.@event.rule.ActivationCreatedEvent par0);
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lorg/drools/event/rule/ActivationCancelledEvent;)V")]
        void activationCancelled(global::org.drools.@event.rule.ActivationCancelledEvent par0);
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lorg/drools/event/rule/BeforeActivationFiredEvent;)V")]
        void beforeActivationFired(global::org.drools.@event.rule.BeforeActivationFiredEvent par0);
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lorg/drools/event/rule/AfterActivationFiredEvent;)V")]
        void afterActivationFired(global::org.drools.@event.rule.AfterActivationFiredEvent par0);
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lorg/drools/event/rule/AgendaGroupPoppedEvent;)V")]
        void agendaGroupPopped(global::org.drools.@event.rule.AgendaGroupPoppedEvent par0);
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lorg/drools/event/rule/AgendaGroupPushedEvent;)V")]
        void agendaGroupPushed(global::org.drools.@event.rule.AgendaGroupPushedEvent par0);
    }
    #endregion
    
    #region Component Designer generated code 
    public partial class AgendaEventListener_ {
        
        public static global::java.lang.Class _class {
            get {
                return global::org.drools.@event.rule.@__AgendaEventListener.staticClass;
            }
        }
    }
    #endregion
    
    #region Component Designer generated code 
    [global::net.sf.jni4net.attributes.JavaProxyAttribute(typeof(global::org.drools.@event.rule.AgendaEventListener), typeof(global::org.drools.@event.rule.AgendaEventListener_))]
    [global::net.sf.jni4net.attributes.ClrWrapperAttribute(typeof(global::org.drools.@event.rule.AgendaEventListener), typeof(global::org.drools.@event.rule.AgendaEventListener_))]
    internal sealed partial class @__AgendaEventListener : global::java.lang.Object, global::org.drools.@event.rule.AgendaEventListener {
        
        internal new static global::java.lang.Class staticClass;
        
        internal static global::net.sf.jni4net.jni.MethodId _activationCreated0;
        
        internal static global::net.sf.jni4net.jni.MethodId _activationCancelled1;
        
        internal static global::net.sf.jni4net.jni.MethodId _beforeActivationFired2;
        
        internal static global::net.sf.jni4net.jni.MethodId _afterActivationFired3;
        
        internal static global::net.sf.jni4net.jni.MethodId _agendaGroupPopped4;
        
        internal static global::net.sf.jni4net.jni.MethodId _agendaGroupPushed5;
        
        private @__AgendaEventListener(global::net.sf.jni4net.jni.JNIEnv @__env) : 
                base(@__env) {
        }
        
        private static void InitJNI(global::net.sf.jni4net.jni.JNIEnv @__env, java.lang.Class @__class) {
            global::org.drools.@event.rule.@__AgendaEventListener.staticClass = @__class;
            global::org.drools.@event.rule.@__AgendaEventListener._activationCreated0 = @__env.GetMethodID(global::org.drools.@event.rule.@__AgendaEventListener.staticClass, "activationCreated", "(Lorg/drools/event/rule/ActivationCreatedEvent;)V");
            global::org.drools.@event.rule.@__AgendaEventListener._activationCancelled1 = @__env.GetMethodID(global::org.drools.@event.rule.@__AgendaEventListener.staticClass, "activationCancelled", "(Lorg/drools/event/rule/ActivationCancelledEvent;)V");
            global::org.drools.@event.rule.@__AgendaEventListener._beforeActivationFired2 = @__env.GetMethodID(global::org.drools.@event.rule.@__AgendaEventListener.staticClass, "beforeActivationFired", "(Lorg/drools/event/rule/BeforeActivationFiredEvent;)V");
            global::org.drools.@event.rule.@__AgendaEventListener._afterActivationFired3 = @__env.GetMethodID(global::org.drools.@event.rule.@__AgendaEventListener.staticClass, "afterActivationFired", "(Lorg/drools/event/rule/AfterActivationFiredEvent;)V");
            global::org.drools.@event.rule.@__AgendaEventListener._agendaGroupPopped4 = @__env.GetMethodID(global::org.drools.@event.rule.@__AgendaEventListener.staticClass, "agendaGroupPopped", "(Lorg/drools/event/rule/AgendaGroupPoppedEvent;)V");
            global::org.drools.@event.rule.@__AgendaEventListener._agendaGroupPushed5 = @__env.GetMethodID(global::org.drools.@event.rule.@__AgendaEventListener.staticClass, "agendaGroupPushed", "(Lorg/drools/event/rule/AgendaGroupPushedEvent;)V");
        }
        
        public void activationCreated(global::org.drools.@event.rule.ActivationCreatedEvent par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallVoidMethod(this, global::org.drools.@event.rule.@__AgendaEventListener._activationCreated0, global::net.sf.jni4net.utils.Convertor.ParFullC2J<global::org.drools.@event.rule.ActivationCreatedEvent>(@__env, par0));
            }
        }
        
        public void activationCancelled(global::org.drools.@event.rule.ActivationCancelledEvent par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallVoidMethod(this, global::org.drools.@event.rule.@__AgendaEventListener._activationCancelled1, global::net.sf.jni4net.utils.Convertor.ParFullC2J<global::org.drools.@event.rule.ActivationCancelledEvent>(@__env, par0));
            }
        }
        
        public void beforeActivationFired(global::org.drools.@event.rule.BeforeActivationFiredEvent par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallVoidMethod(this, global::org.drools.@event.rule.@__AgendaEventListener._beforeActivationFired2, global::net.sf.jni4net.utils.Convertor.ParFullC2J<global::org.drools.@event.rule.BeforeActivationFiredEvent>(@__env, par0));
            }
        }
        
        public void afterActivationFired(global::org.drools.@event.rule.AfterActivationFiredEvent par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallVoidMethod(this, global::org.drools.@event.rule.@__AgendaEventListener._afterActivationFired3, global::net.sf.jni4net.utils.Convertor.ParFullC2J<global::org.drools.@event.rule.AfterActivationFiredEvent>(@__env, par0));
            }
        }
        
        public void agendaGroupPopped(global::org.drools.@event.rule.AgendaGroupPoppedEvent par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallVoidMethod(this, global::org.drools.@event.rule.@__AgendaEventListener._agendaGroupPopped4, global::net.sf.jni4net.utils.Convertor.ParFullC2J<global::org.drools.@event.rule.AgendaGroupPoppedEvent>(@__env, par0));
            }
        }
        
        public void agendaGroupPushed(global::org.drools.@event.rule.AgendaGroupPushedEvent par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallVoidMethod(this, global::org.drools.@event.rule.@__AgendaEventListener._agendaGroupPushed5, global::net.sf.jni4net.utils.Convertor.ParFullC2J<global::org.drools.@event.rule.AgendaGroupPushedEvent>(@__env, par0));
            }
        }
        
        private static global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod> @__Init(global::net.sf.jni4net.jni.JNIEnv @__env, global::java.lang.Class @__class) {
            global::System.Type @__type = typeof(__AgendaEventListener);
            global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod> methods = new global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod>();
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "activationCreated", "activationCreated0", "(Lorg/drools/event/rule/ActivationCreatedEvent;)V"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "activationCancelled", "activationCancelled1", "(Lorg/drools/event/rule/ActivationCancelledEvent;)V"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "beforeActivationFired", "beforeActivationFired2", "(Lorg/drools/event/rule/BeforeActivationFiredEvent;)V"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "afterActivationFired", "afterActivationFired3", "(Lorg/drools/event/rule/AfterActivationFiredEvent;)V"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "agendaGroupPopped", "agendaGroupPopped4", "(Lorg/drools/event/rule/AgendaGroupPoppedEvent;)V"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "agendaGroupPushed", "agendaGroupPushed5", "(Lorg/drools/event/rule/AgendaGroupPushedEvent;)V"));
            return methods;
        }
        
        private static void activationCreated0(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle par0) {
            // (Lorg/drools/event/rule/ActivationCreatedEvent;)V
            // (Lorg/drools/event/rule/ActivationCreatedEvent;)V
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            try {
            global::org.drools.@event.rule.AgendaEventListener @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.@event.rule.AgendaEventListener>(@__env, @__obj);
            @__real.activationCreated(global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.@event.rule.ActivationCreatedEvent>(@__env, par0));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
        }
        
        private static void activationCancelled1(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle par0) {
            // (Lorg/drools/event/rule/ActivationCancelledEvent;)V
            // (Lorg/drools/event/rule/ActivationCancelledEvent;)V
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            try {
            global::org.drools.@event.rule.AgendaEventListener @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.@event.rule.AgendaEventListener>(@__env, @__obj);
            @__real.activationCancelled(global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.@event.rule.ActivationCancelledEvent>(@__env, par0));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
        }
        
        private static void beforeActivationFired2(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle par0) {
            // (Lorg/drools/event/rule/BeforeActivationFiredEvent;)V
            // (Lorg/drools/event/rule/BeforeActivationFiredEvent;)V
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            try {
            global::org.drools.@event.rule.AgendaEventListener @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.@event.rule.AgendaEventListener>(@__env, @__obj);
            @__real.beforeActivationFired(global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.@event.rule.BeforeActivationFiredEvent>(@__env, par0));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
        }
        
        private static void afterActivationFired3(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle par0) {
            // (Lorg/drools/event/rule/AfterActivationFiredEvent;)V
            // (Lorg/drools/event/rule/AfterActivationFiredEvent;)V
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            try {
            global::org.drools.@event.rule.AgendaEventListener @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.@event.rule.AgendaEventListener>(@__env, @__obj);
            @__real.afterActivationFired(global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.@event.rule.AfterActivationFiredEvent>(@__env, par0));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
        }
        
        private static void agendaGroupPopped4(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle par0) {
            // (Lorg/drools/event/rule/AgendaGroupPoppedEvent;)V
            // (Lorg/drools/event/rule/AgendaGroupPoppedEvent;)V
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            try {
            global::org.drools.@event.rule.AgendaEventListener @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.@event.rule.AgendaEventListener>(@__env, @__obj);
            @__real.agendaGroupPopped(global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.@event.rule.AgendaGroupPoppedEvent>(@__env, par0));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
        }
        
        private static void agendaGroupPushed5(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle par0) {
            // (Lorg/drools/event/rule/AgendaGroupPushedEvent;)V
            // (Lorg/drools/event/rule/AgendaGroupPushedEvent;)V
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            try {
            global::org.drools.@event.rule.AgendaEventListener @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.@event.rule.AgendaEventListener>(@__env, @__obj);
            @__real.agendaGroupPushed(global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.@event.rule.AgendaGroupPushedEvent>(@__env, par0));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
        }
        
        new internal sealed class ContructionHelper : global::net.sf.jni4net.utils.IConstructionHelper {
            
            public global::net.sf.jni4net.jni.IJvmProxy CreateProxy(global::net.sf.jni4net.jni.JNIEnv @__env) {
                return new global::org.drools.@event.rule.@__AgendaEventListener(@__env);
            }
        }
    }
    #endregion
}
