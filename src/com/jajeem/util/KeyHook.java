package com.jajeem.util;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;

/** Sample implementation of a low-level keyboard hook on W32. */
// source:
// https://github.com/thepaul/libjna-java/blob/master/contrib/w32keyhook/KeyHook.java

public class KeyHook implements Runnable {
	private static HHOOK hhk;
	private static LowLevelKeyboardProc keyboardHook;
	private static boolean ignoreCallback;
	protected Thread thread;

	public KeyHook() {
		thread = new Thread(this);
	}

	public void start() {
		thread.start();
	}

	public boolean isIgnoreCallback() {
		return ignoreCallback;
	}

	public void setIgnoreCallback(boolean ignoreCallback) {
		KeyHook.ignoreCallback = ignoreCallback;
	}

	@Override
	public void run() {
		final User32 lib = User32.INSTANCE;
		HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);

		keyboardHook = new LowLevelKeyboardProc() {
			public LRESULT callback(int nCode, WPARAM wParam,
					KBDLLHOOKSTRUCT info) {
				if (nCode >= 0) {
					switch (wParam.intValue()) {
					case WinUser.WM_KEYUP:
					case WinUser.WM_KEYDOWN:
					case WinUser.WM_SYSKEYUP:
					case WinUser.WM_SYSKEYDOWN:
//						System.err.println("in callback, key=" + info.vkCode);
						if (isIgnoreCallback()) {
							return new LRESULT(1);
						}
					}
				}
				return lib
						.CallNextHookEx(hhk, nCode, wParam, info.getPointer());
			}
		};
		
		hhk = lib.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, keyboardHook, hMod,
				0);

		MSG msg = new MSG();
		lib.GetMessage(msg, null, 0, 0);
	}

}