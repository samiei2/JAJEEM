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
    [global::net.sf.jni4net.attributes.JavaClassAttribute()]
    public partial class DefaultKnowledgeAgentEventListener : global::java.lang.Object, global::org.drools.@event.knowledgeagent.KnowledgeAgentEventListener {
        
        internal new static global::java.lang.Class staticClass;
        
        internal static global::net.sf.jni4net.jni.MethodId _afterChangeSetApplied0;
        
        internal static global::net.sf.jni4net.jni.MethodId _afterChangeSetProcessed1;
        
        internal static global::net.sf.jni4net.jni.MethodId _afterResourceProcessed2;
        
        internal static global::net.sf.jni4net.jni.MethodId _beforeChangeSetApplied3;
        
        internal static global::net.sf.jni4net.jni.MethodId _beforeChangeSetProcessed4;
        
        internal static global::net.sf.jni4net.jni.MethodId _beforeResourceProcessed5;
        
        internal static global::net.sf.jni4net.jni.MethodId _knowledgeBaseUpdated6;
        
        internal static global::net.sf.jni4net.jni.MethodId _resourceCompilationFailed7;
        
        internal static global::net.sf.jni4net.jni.MethodId @__ctorDefaultKnowledgeAgentEventListener8;
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()V")]
        public DefaultKnowledgeAgentEventListener() : 
                base(((global::net.sf.jni4net.jni.JNIEnv)(null))) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            @__env.NewObject(global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener.staticClass, global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener.@__ctorDefaultKnowledgeAgentEventListener8, this);
            }
        }
        
        protected DefaultKnowledgeAgentEventListener(global::net.sf.jni4net.jni.JNIEnv @__env) : 
                base(@__env) {
        }
        
        public static global::java.lang.Class _class {
            get {
                return global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener.staticClass;
            }
        }
        
        private static void InitJNI(global::net.sf.jni4net.jni.JNIEnv @__env, java.lang.Class @__class) {
            global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener.staticClass = @__class;
            global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener._afterChangeSetApplied0 = @__env.GetMethodID(global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener.staticClass, "afterChangeSetApplied", "(Lorg/drools/event/knowledgeagent/AfterChangeSetAppliedEvent;)V");
            global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener._afterChangeSetProcessed1 = @__env.GetMethodID(global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener.staticClass, "afterChangeSetProcessed", "(Lorg/drools/event/knowledgeagent/AfterChangeSetProcessedEvent;)V");
            global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener._afterResourceProcessed2 = @__env.GetMethodID(global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener.staticClass, "afterResourceProcessed", "(Lorg/drools/event/knowledgeagent/AfterResourceProcessedEvent;)V");
            global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener._beforeChangeSetApplied3 = @__env.GetMethodID(global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener.staticClass, "beforeChangeSetApplied", "(Lorg/drools/event/knowledgeagent/BeforeChangeSetAppliedEvent;)V");
            global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener._beforeChangeSetProcessed4 = @__env.GetMethodID(global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener.staticClass, "beforeChangeSetProcessed", "(Lorg/drools/event/knowledgeagent/BeforeChangeSetProcessedEvent;)V");
            global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener._beforeResourceProcessed5 = @__env.GetMethodID(global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener.staticClass, "beforeResourceProcessed", "(Lorg/drools/event/knowledgeagent/BeforeResourceProcessedEvent;)V");
            global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener._knowledgeBaseUpdated6 = @__env.GetMethodID(global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener.staticClass, "knowledgeBaseUpdated", "(Lorg/drools/event/knowledgeagent/KnowledgeBaseUpdatedEvent;)V");
            global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener._resourceCompilationFailed7 = @__env.GetMethodID(global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener.staticClass, "resourceCompilationFailed", "(Lorg/drools/event/knowledgeagent/ResourceCompilationFailedEvent;)V");
            global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener.@__ctorDefaultKnowledgeAgentEventListener8 = @__env.GetMethodID(global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener.staticClass, "<init>", "()V");
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lorg/drools/event/knowledgeagent/AfterChangeSetAppliedEvent;)V")]
        public virtual void afterChangeSetApplied(global::org.drools.@event.knowledgeagent.AfterChangeSetAppliedEvent par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallVoidMethod(this, global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener._afterChangeSetApplied0, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lorg/drools/event/knowledgeagent/AfterChangeSetProcessedEvent;)V")]
        public virtual void afterChangeSetProcessed(global::org.drools.@event.knowledgeagent.AfterChangeSetProcessedEvent par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallVoidMethod(this, global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener._afterChangeSetProcessed1, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lorg/drools/event/knowledgeagent/AfterResourceProcessedEvent;)V")]
        public virtual void afterResourceProcessed(global::org.drools.@event.knowledgeagent.AfterResourceProcessedEvent par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallVoidMethod(this, global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener._afterResourceProcessed2, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lorg/drools/event/knowledgeagent/BeforeChangeSetAppliedEvent;)V")]
        public virtual void beforeChangeSetApplied(global::org.drools.@event.knowledgeagent.BeforeChangeSetAppliedEvent par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallVoidMethod(this, global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener._beforeChangeSetApplied3, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lorg/drools/event/knowledgeagent/BeforeChangeSetProcessedEvent;)V")]
        public virtual void beforeChangeSetProcessed(global::org.drools.@event.knowledgeagent.BeforeChangeSetProcessedEvent par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallVoidMethod(this, global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener._beforeChangeSetProcessed4, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lorg/drools/event/knowledgeagent/BeforeResourceProcessedEvent;)V")]
        public virtual void beforeResourceProcessed(global::org.drools.@event.knowledgeagent.BeforeResourceProcessedEvent par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallVoidMethod(this, global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener._beforeResourceProcessed5, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lorg/drools/event/knowledgeagent/KnowledgeBaseUpdatedEvent;)V")]
        public virtual void knowledgeBaseUpdated(global::org.drools.@event.knowledgeagent.KnowledgeBaseUpdatedEvent par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallVoidMethod(this, global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener._knowledgeBaseUpdated6, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lorg/drools/event/knowledgeagent/ResourceCompilationFailedEvent;)V")]
        public virtual void resourceCompilationFailed(global::org.drools.@event.knowledgeagent.ResourceCompilationFailedEvent par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallVoidMethod(this, global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener._resourceCompilationFailed7, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0));
            }
        }
        
        new internal sealed class ContructionHelper : global::net.sf.jni4net.utils.IConstructionHelper {
            
            public global::net.sf.jni4net.jni.IJvmProxy CreateProxy(global::net.sf.jni4net.jni.JNIEnv @__env) {
                return new global::org.drools.@event.rule.DefaultKnowledgeAgentEventListener(@__env);
            }
        }
    }
    #endregion
}
