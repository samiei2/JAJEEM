//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
//     Runtime Version:2.0.50727.4952
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace org.drools.runtime {
    
    
    #region Component Designer generated code 
    [global::net.sf.jni4net.attributes.JavaInterfaceAttribute()]
    public partial interface Globals {
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Ljava/lang/String;)Ljava/lang/Object;")]
        global::java.lang.Object get(global::java.lang.String par0);
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Ljava/lang/String;Ljava/lang/Object;)V")]
        void set(global::java.lang.String par0, global::java.lang.Object par1);
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lorg/drools/runtime/Globals;)V")]
        void setDelegate(global::org.drools.runtime.Globals par0);
    }
    #endregion
    
    #region Component Designer generated code 
    public partial class Globals_ {
        
        public static global::java.lang.Class _class {
            get {
                return global::org.drools.runtime.@__Globals.staticClass;
            }
        }
    }
    #endregion
    
    #region Component Designer generated code 
    [global::net.sf.jni4net.attributes.JavaProxyAttribute(typeof(global::org.drools.runtime.Globals), typeof(global::org.drools.runtime.Globals_))]
    [global::net.sf.jni4net.attributes.ClrWrapperAttribute(typeof(global::org.drools.runtime.Globals), typeof(global::org.drools.runtime.Globals_))]
    internal sealed partial class @__Globals : global::java.lang.Object, global::org.drools.runtime.Globals {
        
        internal new static global::java.lang.Class staticClass;
        
        internal static global::net.sf.jni4net.jni.MethodId _get0;
        
        internal static global::net.sf.jni4net.jni.MethodId _set1;
        
        internal static global::net.sf.jni4net.jni.MethodId _setDelegate2;
        
        private @__Globals(global::net.sf.jni4net.jni.JNIEnv @__env) : 
                base(@__env) {
        }
        
        private static void InitJNI(global::net.sf.jni4net.jni.JNIEnv @__env, java.lang.Class @__class) {
            global::org.drools.runtime.@__Globals.staticClass = @__class;
            global::org.drools.runtime.@__Globals._get0 = @__env.GetMethodID(global::org.drools.runtime.@__Globals.staticClass, "get", "(Ljava/lang/String;)Ljava/lang/Object;");
            global::org.drools.runtime.@__Globals._set1 = @__env.GetMethodID(global::org.drools.runtime.@__Globals.staticClass, "set", "(Ljava/lang/String;Ljava/lang/Object;)V");
            global::org.drools.runtime.@__Globals._setDelegate2 = @__env.GetMethodID(global::org.drools.runtime.@__Globals.staticClass, "setDelegate", "(Lorg/drools/runtime/Globals;)V");
        }
        
        public global::java.lang.Object get(global::java.lang.String par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            return global::net.sf.jni4net.utils.Convertor.FullJ2C<global::java.lang.Object>(@__env, @__env.CallObjectMethodPtr(this, global::org.drools.runtime.@__Globals._get0, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0)));
            }
        }
        
        public void set(global::java.lang.String par0, global::java.lang.Object par1) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 14)){
            @__env.CallVoidMethod(this, global::org.drools.runtime.@__Globals._set1, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0), global::net.sf.jni4net.utils.Convertor.ParFullC2J<global::java.lang.Object>(@__env, par1));
            }
        }
        
        public void setDelegate(global::org.drools.runtime.Globals par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallVoidMethod(this, global::org.drools.runtime.@__Globals._setDelegate2, global::net.sf.jni4net.utils.Convertor.ParFullC2J<global::org.drools.runtime.Globals>(@__env, par0));
            }
        }
        
        private static global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod> @__Init(global::net.sf.jni4net.jni.JNIEnv @__env, global::java.lang.Class @__class) {
            global::System.Type @__type = typeof(__Globals);
            global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod> methods = new global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod>();
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "get", "get0", "(Ljava/lang/String;)Ljava/lang/Object;"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "set", "set1", "(Ljava/lang/String;Ljava/lang/Object;)V"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "setDelegate", "setDelegate2", "(Lorg/drools/runtime/Globals;)V"));
            return methods;
        }
        
        private static global::net.sf.jni4net.utils.JniHandle get0(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle par0) {
            // (Ljava/lang/String;)Ljava/lang/Object;
            // (Ljava/lang/String;)Ljava/lang/Object;
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            global::net.sf.jni4net.utils.JniHandle @__return = default(global::net.sf.jni4net.utils.JniHandle);
            try {
            global::org.drools.runtime.Globals @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.runtime.Globals>(@__env, @__obj);
            @__return = global::net.sf.jni4net.utils.Convertor.FullC2J<global::java.lang.Object>(@__env, @__real.get(global::net.sf.jni4net.utils.Convertor.StrongJ2CpString(@__env, par0)));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static void set1(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle par0, global::net.sf.jni4net.utils.JniLocalHandle par1) {
            // (Ljava/lang/String;Ljava/lang/Object;)V
            // (Ljava/lang/String;Ljava/lang/Object;)V
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            try {
            global::org.drools.runtime.Globals @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.runtime.Globals>(@__env, @__obj);
            @__real.set(global::net.sf.jni4net.utils.Convertor.StrongJ2CpString(@__env, par0), global::net.sf.jni4net.utils.Convertor.FullJ2C<global::java.lang.Object>(@__env, par1));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
        }
        
        private static void setDelegate2(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle par0) {
            // (Lorg/drools/runtime/Globals;)V
            // (Lorg/drools/runtime/Globals;)V
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            try {
            global::org.drools.runtime.Globals @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.runtime.Globals>(@__env, @__obj);
            @__real.setDelegate(global::net.sf.jni4net.utils.Convertor.FullJ2C<global::org.drools.runtime.Globals>(@__env, par0));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
        }
        
        new internal sealed class ContructionHelper : global::net.sf.jni4net.utils.IConstructionHelper {
            
            public global::net.sf.jni4net.jni.IJvmProxy CreateProxy(global::net.sf.jni4net.jni.JNIEnv @__env) {
                return new global::org.drools.runtime.@__Globals(@__env);
            }
        }
    }
    #endregion
}
